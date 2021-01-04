package dao

import DbOperationsInterface
import models.UserModel
import org.jetbrains.exposed.sql.Database

class UsersRepository(val database: Database): DbOperationsInterface<UserModel, Int> {


    override fun create(withObject: UserModel): UserModel {
        return withObject
    }

    override fun update(forObject: UserModel) {
        //tODO
    }

    override fun delete(forObject: Int) {
        //TODO
    }
}