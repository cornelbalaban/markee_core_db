import models.User
import models.User.emailUsr
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class DbTests {

    lateinit var markeeUsersDb: Database

    @Before
    fun setup() {
        markeeUsersDb = MarkeeDbConnector.usersDbConnection()
    }

    @Test
    fun testDbInsertUser() {

        transaction(markeeUsersDb) {
            val cityId = User.insert {
                it[emailUsr] = "testCoreDb@coredb.com"
                it[passwordSalt] = "testCoreDb"
                it[passwordHash] = "testCoreDb"
                it[userName] = "testCoreDb"
            } get User.userId

            assert(cityId != 0)
        }
    }
}