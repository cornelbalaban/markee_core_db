import models.DaoResponseCode
import repositories.UsersRepository
import models.ProjectsKeys
import models.UserModel
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before
import org.junit.Test
import java.util.*


const val userName = "guzman@coredbtest.com"
const val pwdSalt = "coreDb"
const val hashedPwd = "coreDb"

class DbTests {

    lateinit var markeeUsersDb: MarkeeDbConnector
    lateinit var usersRepository: UsersRepository
    var insertedUserId: Int? = null

    @Before
    fun setup() {
        val connectionData = ConnectionData("18.170.44.206",3306,
            "markee_users","cornek", "testare_07")
        println("---------------Setting up -------------")
        markeeUsersDb = MarkeeDbConnector()
        markeeUsersDb.initialize(connectionData)

        usersRepository  = UsersRepository(markeeUsersDb.usersDbConnection())
        println("---------------Setting up - COMPLETE -------------")
    }

    @Test
    fun testDbInsertUser() {
        println("---------------Inserting user -------------")
       var createdUser = usersRepository.create(
            UserModel(null, userName, userName, pwdSalt,hashedPwd)
        )

       assert(createdUser.responseCode == DaoResponseCode.USER_CREATED)
       println("---------------Inserted user: ${createdUser.responseResource?.userId} -------------")
    }

    @Test
    fun testUpdateUser() {
        val user = UserModel(null, userName, userName, pwdSalt,hashedPwd)
        assert(usersRepository.update(user).responseCode == DaoResponseCode.USER_UPDATED)
    }


    @Test
    fun testGetUser() {
        println("---------------Retrieving user $insertedUserId -------------")
        /*var userId = usersRepository.getUser(userName, hashedPwd)

        assert(userId != 0)*/
        println("---------------Retrieval complete -------------")
    }

    @Test
    fun testDeleteUser() {

        println("---------------Deleting user: $userName -------------")
        val deletedUserId = usersRepository.delete(userName)
        assert(deletedUserId > 0)
        println("---------------Deletion complete -------------")
    }

    @Test
    fun crudUser(){
        println("---------------Starting CRUD Tests-------------")

        /*testDbInsertUser()
        testGetUser()
        testDeleteUser()*/

        println("---------------Ending CRUD Tests-------------")
    }

    @Test
    fun getUserSalt() {
        val user = "ciocoiuo1@gmail.com"
        val userPwdSalt = usersRepository.getPwdSaltByUserName(user)
        println("User Salt : $userPwdSalt")

        assert(!userPwdSalt.isNullOrEmpty())
    }
    
    @Test
    fun testInsertProjectKey () {
        transaction ( markeeUsersDb.usersDbConnection() ) {

            val createdProjectId = ProjectsKeys.insert {
                it[projectId] = Random().nextInt(10097).toLong()
                it[projectAccessKey] = "coredbtest"
            } get ProjectsKeys.projectId

            assert(createdProjectId > 0)
        }
    }
}