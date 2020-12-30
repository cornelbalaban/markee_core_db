package models

import models.CompanyCustomer.autoIncrement
import org.jetbrains.exposed.sql.Column

enum class ProjectType (val stringValue: String) {
    FREE("free"),
    PRO("pro")
}

data class UserModel(
        var userId:Int? = null,
        val email: String,
        var userName: String?,
        var passwordSalt: String?,
        var passwordHash: String?
) {
    fun isValid():Boolean {
        return userName != null && passwordHash != null && passwordSalt != null
    }
}

data class CompanyModel(
        var customerId: Int? = null,
        val customerName: String,
        var customerCountry: String? = null,
        var customerCity: String? = null,
        var customerAddress: String? = null,
        var customerAdmin: Int? = null
)
