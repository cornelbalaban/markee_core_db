import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import java.sql.Connection
import java.util.*

private const val IS_DEBUG = true

/**
 * singleton containing references to database connections
 * using Hikari datasource for the MariaDB connection pool
 */
object MarkeeDbConnector {

    private val systemProperties = Properties()
    private var usersDatabase : Database

    init {

        systemProperties.load(ClassLoader.getSystemResourceAsStream(if (IS_DEBUG) {"debug.properties"} else {"prod.properties"}))

        val usersDatasource = DbConnectionPool(systemProperties.getProperty(SystemProperties.MYSQL_HOST.stringValue).toString(),
                                            systemProperties.getProperty(SystemProperties.MYSQL_PORT.stringValue).toInt(),
                                            systemProperties.getProperty(SystemProperties.USERS_DB.stringValue).toString(),
                                            systemProperties.getProperty(SystemProperties.MYSQL_USER.stringValue).toString(),
                                            systemProperties.getProperty(SystemProperties.MYSQL_PWD.stringValue).toString()).getDatasource()

        usersDatabase = Database.connect(usersDatasource)
    }

    fun usersDbConnection(): Database = usersDatabase


}