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
        insertedUserId = markeeUsersDb.insertUser(
            UserModel(null, userName, userName, pwdSalt,hashedPwd)
        )

       assert(insertedUserId != null)
    }


    @Test
    fun getUser() {
        var userId = markeeUsersDb.getUser(userName, hashedPwd)

        assert(userId != 0)
    }

    @Test
    fun testDeleteUser() {
        var deletedUserId = markeeUsersDb.deleteUser(159)

        assert(deletedUserId > 0)
    }

    @Test
    fun crudUser(){
        
        
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