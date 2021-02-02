import org.jetbrains.exposed.sql.*
import java.sql.Connection
import java.util.*

private const val IS_DEBUG = true

/**
 * singleton containing references to database connections
 * using Hikari datasource for the MariaDB connection pool
 */
class MarkeeDbConnector(connection: ConnectionData? = null) {

    private val systemProperties = Properties()
    private var usersDatabase : Database

    private var connectionData: ConnectionData = ConnectionData("86.124.71.22",3306,
            "markee_users","cornel", "testare_07")

    init {

        //systemProperties.load(ClassLoader.getSystemResourceAsStream(if (IS_DEBUG) {"debug.properties"} else {"prod.properties"}))

        /*val usersDatasource = DbConnectionPool(systemProperties.getProperty(SystemProperties.MYSQL_HOST.stringValue).toString(),
                                            systemProperties.getProperty(SystemProperties.MYSQL_PORT.stringValue).toInt(),
                                            MarkeeUsersTables.USERS_DB.stringValue,
                                            systemProperties.getProperty(SystemProperties.MYSQL_USER.stringValue).toString(),
                                            systemProperties.getProperty(SystemProperties.MYSQL_PWD.stringValue).toString()).getDatasource()*/

        connection?.let {
            this.connectionData = it
        }

        val usersDatasource = DbConnectionPool(connectionData.host,
                connectionData.port,
                connectionData.databaseName,
                connectionData.databaseUser,
                connectionData.databasePassword).getDatasource()

        usersDatabase = Database.connect(usersDatasource)
    }
    
    fun usersDbConnection(): Database = usersDatabase
}

data class ConnectionData (
        val host: String,
        val port: Int,
        val databaseName: String,
        val databaseUser: String,
        val databasePassword: String )