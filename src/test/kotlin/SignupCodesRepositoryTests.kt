import models.DaoResponse
import models.DaoResponseCode
import org.junit.Before
import org.junit.Test
import repositories.SignupCodesRepository
import utils.EncryptionUtils

class SignupCodesRepositoryTests {

    private val secret: String = "someVerySecret"

    lateinit var signupCodesRepository: SignupCodesRepository
    lateinit var markeeDbConnector: MarkeeDbConnector
    lateinit var encryptionUtils: EncryptionUtils

    @Before
    fun setup() {
        val connectionData = ConnectionData("127.0.0.1",3306,"markee_users","cornel","testare")
        markeeDbConnector = MarkeeDbConnector(connectionData)
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
}