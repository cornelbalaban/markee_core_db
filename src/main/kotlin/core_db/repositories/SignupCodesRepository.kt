package core_db.repositories

import core_db.interfaces.DbOperationsInterface
import core_db.models.*
import core_db.models.CodeModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import utils.EncryptionUtils

class SignupCodesRepository (private val database: Database, private val encryptionUtils: EncryptionUtils): DbOperationsInterface<String,String,DaoResponse<SignupCodeModel>> {

    override fun create(withUserId: String): DaoResponse<SignupCodeModel> {

        val createdCode = SignupCodeModel()
        var response: DaoResponse<SignupCodeModel> = DaoResponse(DaoResponseCode.SIGNUP_CODE_CREATE_FAILED,DaoResponseMessage.SIGNUP_CODE_CREATE_FAILED,
            createdCode
        )

        try {
            val userInt = withUserId.toLong()
            val codeAndDate: Pair<Long, String>? = createOnetimeCode(userInt)

            codeAndDate?.let {
                transaction(database) {
                    val insertedCode = CodeModel.insert {
                        it[signupCode] = codeAndDate.second
                        it[creationTime] = codeAndDate.first
                        it[userId] = userInt
                    } get CodeModel.signupCode

                    createdCode.apply {
                        code = insertedCode
                        userId = withUserId
                    }

                    response.apply {
                        responseCode = DaoResponseCode.SIGNUP_CODE_CREATED
                        responseMessage = DaoResponseMessage.SIGNUP_CODE_CREATED
                    }
                }
            }
        } catch (n: NumberFormatException) {
            return response
        }

        println("Generated code : ${response.responseResource.code}")

        return response
    }

    //this methid is not needed
    override fun update(code: String): DaoResponse<SignupCodeModel> {
        return  DaoResponse(DaoResponseCode.METHOD_UNAVAILABLE, DaoResponseMessage.METHOD_UNAVAILABLE, SignupCodeModel())
    }

    override fun delete(code: String): String {
        return transaction(database) {
            CodeModel.deleteWhere {
                CodeModel.signupCode eq code
            }
        }.toString()
    }

    fun codeExists(code: String) : Boolean {

        var result = false

        transaction(database) {

            val selectCondition = Op.build { CodeModel.signupCode eq code and (CodeModel.creationTime less System.currentTimeMillis())}
            val code = CodeModel
                .slice(CodeModel.signupCode)
                .select{ selectCondition}
                .count()
            println("$code")

            if (code > 0) {
               result = true
            }
        }

        return result
    }


    /**
     * creates a one time code based on the userId and the currentTimeStamp
     * @param userId --> userId for which the code needs to be created
     * @return Pair<Long, String> if the onetime code has been generated successfully else null
     */
    private fun createOnetimeCode(userId: Long): Pair<Long,String>?{

        val currentTime = System.currentTimeMillis()
        val stringToEncrypt = """$userId$currentTime"""

        val onetimeCode = encryptionUtils.encrypt(stringToEncrypt)

        onetimeCode?.let {
            return (currentTime to it)
        }

        return null
    }

}
