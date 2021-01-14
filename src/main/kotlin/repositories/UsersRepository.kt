package repositories

import DbOperationsInterface
import models.User
import models.UserModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UsersRepository(val database: Database) : DbOperationsInterface<UserModel, Int> {


    override fun create(user: UserModel): UserModel {

        if (user.isValid()) {
            val userId = transaction(database) {
                User.insert {
                    it[emailUsr] = user.email
                    it[passwordHash] = user.passwordHash as String
                    it[passwordSalt] = user.passwordSalt as String
                    it[userName] = user.userName as String
                } get User.userId
            }

            user.userId = userId
        }

        return user
    }

    override fun update(user: UserModel): UserModel {
        return user
    }

    override fun delete(userId: Int): Int {
        return  transaction(database) {
            User.deleteWhere { User.userId eq userId }
        }
    }

    fun getUser(withEmail: String, andPassword: String): Int {

        return  transaction (database) {
            User.select {
                (User.emailUsr eq withEmail) and (User.passwordHash eq andPassword)
            }.first()[User.userId]
        }
    }

    fun delete(byEmail: String): Int = transaction(database) {
        User.deleteWhere { User.emailUsr eq byEmail }
    }

}