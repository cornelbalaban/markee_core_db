import dao.CompanyRepository
import models.User
import models.UserModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

private const val IS_DEBUG = true

/**
 * singleton containing references to database connections
 * using Hikari datasource for the MariaDB connection pool
 */
class MarkeeDbConnector {

    private val systemProperties = Properties()
    private var usersDatabase : Database
    private lateinit var companyRepository: CompanyRepository

    init {

        systemProperties.load(ClassLoader.getSystemResourceAsStream(if (IS_DEBUG) {"debug.properties"} else {"prod.properties"}))

        val usersDatasource = DbConnectionPool(systemProperties.getProperty(SystemProperties.MYSQL_HOST.stringValue).toString(),
                                            systemProperties.getProperty(SystemProperties.MYSQL_PORT.stringValue).toInt(),
                                            MarkeeUsersTables.USERS_DB.stringValue,
                                            systemProperties.getProperty(SystemProperties.MYSQL_USER.stringValue).toString(),
                                            systemProperties.getProperty(SystemProperties.MYSQL_PWD.stringValue).toString()).getDatasource()

        usersDatabase = Database.connect(usersDatasource)
    }
    
    fun usersDbConnection(): Database = usersDatabase

    //TODO users -- move to separate class
    fun insertUser(user: UserModel): Int? {
             
        var userId: Int? = null
        
        if (user.isValid()) {
           userId = transaction(usersDatabase) {
               User.insert {
                   it[emailUsr] = user.email
                   it[passwordHash] = user.passwordHash as String
                   it[passwordSalt] = user.passwordSalt as String
                   it[userName] = user.userName as String
               } get User.userId
           }            
        } 
        
        return userId
    }

    fun getUser(withEmail: String, andPassword: String): Int {
        
        return  transaction (usersDatabase) {
            User.select {
                (User.emailUsr eq withEmail) and (User.passwordHash eq andPassword)
            }.first()[User.userId]
        }
    }

    fun deleteUser(userId: Int): Int {
       return  transaction(usersDatabase) { 
           User.deleteWhere { User.userId eq userId } 
       }
    }

    //TODO companies -- TODO move to separate class
    

}