import org.jetbrains.exposed.sql.*
import java.lang.Exception
import java.sql.Connection
import java.util.*

private const val IS_DEBUG = true

/**
 * singleton containing references to database connections
 * using Hikari datasource for the MariaDB connection pool
 */
class MarkeeDbConnector {

    private val systemProperties = Properties()
    private var usersDatabase : Database? = null

    private lateinit var connectionData: ConnectionData

    fun initialize(connectionData: ConnectionData) {

        if (usersDatabase == null) {
            this.connectionData = connectionData
            val usersDatasource = DbConnectionPool(
                connectionData.host,
                connectionData.port,
                connectionData.databaseName,
                connectionData.databaseUser,
                connectionData.databasePassword
            ).getDatasource()

            usersDatabase = Database.connect(usersDatasource)
        } else {
            throw  ExceptionInInitializerError("Cannot re-initialize the connector as it is already initialized")
        }
    }

    fun usersDbConnection(): Database  {
        usersDatabase?.let { return it }
        throw ExceptionInInitializerError("Connector not initialized. Call the connector's initialize method first")
    }
}

data class ConnectionData (
        val host: String,
        val port: Int,
        val databaseName: String,
        val databaseUser: String,
        val databasePassword: String )