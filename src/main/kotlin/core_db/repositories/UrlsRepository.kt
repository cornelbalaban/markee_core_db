package core_db.repositories

import core_db.interfaces.DbOperationsInterface
import org.jetbrains.exposed.sql.Database

class UrlsRepository (private val database: Database): DbOperationsInterface<String, String, String> {


    override fun create(userId: String):  String {
        return ""
    }

    override fun update(forObject: String): String {
        return ""
    }

    override fun delete(forObject: String): String {
        return ""
    }

}
