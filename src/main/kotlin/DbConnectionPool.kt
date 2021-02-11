
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



    private val connectionOptions = ""

    init {

        datasourceConfig.apply {

            //driverClassName = "org.mariadb.jdbc.Driver"
            //jdbcUrl = "jdbc:mariadb://$dbHost:$dbPort/$dbName"
            jdbcUrl = "jdbc:mysql://$dbHost:$dbPort/$dbName"
            driverClassName = "com.mysql.jdbc.Driver"
            username = dbUser
            password = dbPass
            connectionTimeout = 10000
            //addDataSourceProperty(STATEMENTS_CACHE, "true")
            //addDataSourceProperty(STATEMENTS_CACHE_SIZE, "250")
            //addDataSourceProperty(STATEMENTS_CACHE_SQL_LIMIT, "2048")
            maximumPoolSize = 20

        }

        datasource = HikariDataSource(datasourceConfig)

    }

    @Throws
    internal fun getDatasource(): HikariDataSource = datasource

}