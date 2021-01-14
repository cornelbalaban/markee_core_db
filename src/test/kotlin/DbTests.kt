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
        markeeUsersDb = MarkeeDbConnector()
        usersRepository  = UsersRepository(markeeUsersDb.usersDbConnection())
    }

    @Test
    fun testDbInsertUser() {
        println("---------------Inserting user -------------")
       var createdUser = usersRepository.create(
            UserModel(null, userName, userName, pwdSalt,hashedPwd)
        )

       assert(createdUser.userId != null)
       println("---------------Inserted user: $insertedUserId -------------")
    }


    @Test
    fun testGetUser() {
        println("---------------Retrieving user $insertedUserId -------------")
        var userId = usersRepository.getUser(userName, hashedPwd)

        assert(userId != 0)
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