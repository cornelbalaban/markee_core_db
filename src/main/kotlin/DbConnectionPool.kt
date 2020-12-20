
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

/**
 * class that provides a connection pool to any mysql, postgres database
 * any new db connection should be done via this class
 */
internal class DbConnectionPool(dbHost: String,
                       dbPort: Int,
                       dbName: String,
                       dbUser: String,
                       dbPass: String) {


    private val STATEMENTS_CACHE = "cachePrepStmts"
    private val STATEMENTS_CACHE_SIZE = "prepStmtCacheSize"
    private val STATEMENTS_CACHE_SQL_LIMIT = "prepStmtCacheSqlLimit"

    private var datasourceConfig: HikariConfig = HikariConfig()
    private var datasource: HikariDataSource



    private val connectionOptions = "?autoReconnect=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Bucharest\""

    init {

        datasourceConfig.apply {

            jdbcUrl = "jdbc:mysql://$dbHost:$dbPort/$dbName$connectionOptions"
            username = dbUser
            password = dbPass
            addDataSourceProperty(STATEMENTS_CACHE, "true")
            addDataSourceProperty(STATEMENTS_CACHE_SIZE, "250")
            addDataSourceProperty(STATEMENTS_CACHE_SQL_LIMIT, "2048")
            maximumPoolSize = 10 //this is actually the default

        }

        datasource = HikariDataSource(datasourceConfig)

    }

    @Throws
    internal fun getDatasource(): HikariDataSource = datasource

}