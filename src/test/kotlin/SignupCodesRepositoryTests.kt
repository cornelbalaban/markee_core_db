import core_db.connector.ConnectionData
import core_db.connector.MarkeeDbConnector
import core_db.models.DaoResponseCode
import org.junit.Before
import org.junit.Test
import core_db.repositories.SignupCodesRepository
import utils.EncryptionUtils

class SignupCodesRepositoryTests {

    private val secret: String = "someVerySecret"

    lateinit var signupCodesRepository: SignupCodesRepository
    lateinit var markeeDbConnector: MarkeeDbConnector
    lateinit var encryptionUtils: EncryptionUtils

    @Before
    fun setup() {
        val connectionData = ConnectionData("127.0.0.1",3306,"markee_users","cornel","testare")
        markeeDbConnector = MarkeeDbConnector()
        markeeDbConnector.initialize(connectionData)
        encryptionUtils = EncryptionUtils(secret)
        signupCodesRepository = SignupCodesRepository(markeeDbConnector.usersDbConnection(), encryptionUtils)
    }

    @Test
    fun testCreateCodeSuccessfully() {
        val user = "12"
        assert(signupCodesRepository.create(user).responseCode == DaoResponseCode.SIGNUP_CODE_CREATED)
    }

    @Test
    fun testCreateCodeWrong() {
        val user = "someTestUser"
        assert(signupCodesRepository.create(user).responseCode == DaoResponseCode.SIGNUP_CODE_CREATE_FAILED)
    }

    @Test
    fun testDeleteCode() {
        val user = "12"
        val createdCodeResponse = signupCodesRepository.create(user)
        val createdCode = createdCodeResponse.responseResource.code
        println(createdCode)

        createdCode?.let {
            val response = signupCodesRepository.delete(it)
            println(response)
            assert(!response.isNullOrEmpty())
        }
    }

    @Test
    fun testCodeExistsTrue() {
        val user = "12"
        val createdCodeResponse = signupCodesRepository.create(user)
        val createdCode = createdCodeResponse.responseResource.code
        println("Generated code: $createdCode")

        assert(signupCodesRepository.codeExists(createdCode.toString()) == true)

    }
}
