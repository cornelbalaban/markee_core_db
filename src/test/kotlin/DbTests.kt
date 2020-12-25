import models.ProjectsKeys
import models.User
import models.UserModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before
import org.junit.Test


class DbTests {

    lateinit var markeeUsersDb: MarkeeDbConnector

    @Before
    fun setup() {
        markeeUsersDb = MarkeeDbConnector()
    }

    @Test
    fun testDbInsertUser() {
        var generatedUserId = markeeUsersDb.insertUser(
            UserModel(null, "guzman@coredbtest.com", "coredb", "coreDb","coreDb")
        )

       assert(generatedUserId != null)
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