import models.ProjectsKeys
import models.UserModel
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before
import org.junit.Test


const val userName = "guzman@coredbtest.com"
const val pwdSalt = "coreDb"
const val hashedPwd = "coreDb"

class DbTests {

    lateinit var markeeUsersDb: MarkeeDbConnector
    var insertedUserId: Int? = null

    @Before
    fun setup() {
        markeeUsersDb = MarkeeDbConnector()
    }

    @Test
    fun testDbInsertUser() {
        println("---------------Inserting user -------------")
        insertedUserId = markeeUsersDb.insertUser(
            UserModel(null, userName, userName, pwdSalt,hashedPwd)
        )

       assert(insertedUserId != null)
       println("---------------Inserted user: $insertedUserId -------------")
    }


    @Test
    fun testGetUser() {
        println("---------------Retrieving user $insertedUserId -------------")
        var userId = markeeUsersDb.getUser(userName, hashedPwd)

        assert(userId == insertedUserId)
        println("---------------Retrieval complete -------------")
    }

    @Test
    fun testDeleteUser() {
        println("---------------Deleting user: $insertedUserId -------------")
        var deletedUserId = markeeUsersDb.deleteUser(insertedUserId as Int)

        assert(deletedUserId > 0)
        println("---------------Deletion complete -------------")
    }

    @Test
    fun crudUser(){
        println("---------------Starting CRUD Tests-------------")

        testDbInsertUser()
        testGetUser()
        testDeleteUser()

        println("---------------Ending CRUD Tests-------------")
    }
    
    @Test
    fun testInsertProjectKey () {
        transaction ( markeeUsersDb.usersDbConnection() ) {

            val createdProjectId = ProjectsKeys.insert {
                it[projectId] = 23
                it[projectAccessKey] = "coredbtest"
            } get ProjectsKeys.projectId

            assert(createdProjectId > 0)
        }
    }
}