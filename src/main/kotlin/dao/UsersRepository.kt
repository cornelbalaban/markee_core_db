package dao

import DbOperationsInterface
import models.User
import models.UserModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
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

    override fun update(forObject: UserModel) {
        //tODO
    }

    override fun delete(forObject: Int) {

    }

    fun delete(byEmail: String): Int = transaction(database) {
        User.deleteWhere { User.emailUsr eq byEmail }
    }

}