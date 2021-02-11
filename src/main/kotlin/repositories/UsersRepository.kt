package repositories

import DbOperationsInterface
import models.*
import models.User
import models.User.emailUsr
import models.User.passwordSalt
import models.User.userId
import models.User.userName
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLIntegrityConstraintViolationException


class UsersRepository(private val database: Database) : DbOperationsInterface<UserModel, Int, DaoResponse<UserModel>> {


    override fun create(user: UserModel): DaoResponse<UserModel> {

        var response = DaoResponse<UserModel>(DaoResponseCode.USER_ALREADY_EXISTS, DaoResponseMessage.USER_ALREADY_EXISTS, user)

        if (user.isValid()) {
            try {
                val userId = transaction(database) {
                    User.insert {
                        it[emailUsr] = user.email
                        it[passwordHash] = user.passwordHash as String
                        it[passwordSalt] = user.passwordSalt as String
                        it[userName] = user.userName as String
                    } get User.userId
                }

                user.userId = userId

                response.apply {
                    responseCode = DaoResponseCode.USER_CREATED
                    responseMessage = DaoResponseMessage.USER_CREATED
                }

            } catch (i: ExposedSQLException) {

                response.apply {
                    responseCode = DaoResponseCode.USER_ALREADY_EXISTS
                    responseMessage = DaoResponseMessage.USER_ALREADY_EXISTS
                }
            }
        }

        return response
    }

    override fun update(user: UserModel): DaoResponse<UserModel> {
        return DaoResponse(DaoResponseCode.USER_UPDATED, DaoResponseMessage.USER_UPDATED, user)
    }

    override fun delete(userId: Int): Int {
        return transaction(database) {
            User.deleteWhere { User.userId eq userId }
        }
    }

    fun getUserId(withEmail: String, andPassword: String): Int {

        transaction(database) {
            User.slice(userId)
                    .select {
                        (User.emailUsr eq withEmail) and (User.passwordHash eq andPassword)
                    }.firstOrNull()
        }?.let {
            return it[userId]
        }

        return 0
    }

    fun getUser(withEmail: String, andPassword: String): UserModel? {

        transaction(database) {
            User.slice(userId, emailUsr, userName)
                    .select {
                        (emailUsr eq withEmail) and (User.passwordHash eq andPassword)
                    }.firstOrNull()
        }?.let {
            return UserModel(it[userId], it[emailUsr], it[userName], null, null)
        }

        return null
    }

    fun getPwdSaltByUserName(userName: String): String? {

        transaction(database) {
            User.slice(passwordSalt)
                    .select { (User.emailUsr eq userName) }.firstOrNull()
        }?.let {
            return it.get(passwordSalt).toString()
        }

        return null
    }

    fun delete(byEmail: String): Int = transaction(database) {
        User.deleteWhere { User.emailUsr eq byEmail }
    }

}