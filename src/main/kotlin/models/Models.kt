package models

import MarkeeUsersTables
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ForeignKeyConstraint
import org.jetbrains.exposed.sql.Table

object User: Table(MarkeeUsersTables.USERS_TABLE.stringValue) {
    val userId: Column<Int> = integer("id_usr").autoIncrement()
    val emailUsr: Column<String> = varchar("email_usr", 250).uniqueIndex()
    val passwordSalt: Column<String> = varchar("salt_usr", 250)
    val passwordHash: Column<String> = varchar("pwd_hash_usr", 250)
    val userName: Column<String> = varchar("name_usr", 250)

    override val primaryKey: PrimaryKey = PrimaryKey(userId)

}

object CompanyCustomer: Table(MarkeeUsersTables.CUSTOMERS_TABLE.stringValue) {

    var customerId: Column<Int> = integer("id_cust").autoIncrement()
    var customerName: Column<String> = varchar("name_cust", 250)
    var customerAddress: Column<String> = varchar("address_cust", 250)
    var customerCountry: Column<String> = varchar("country_cust", 250)
    var customerCity: Column<String> = varchar("city_cust", 250)
    var customerAdmin: Column<Int> = integer("admin_cust")

    override val primaryKey: PrimaryKey = PrimaryKey(customerId)
    //override val foreignKeyConstraint: ForeignKeyConstraint = ForeignKeyConstraint(customerAdmin)
}

object ProjectsKeys: Table(MarkeeUsersTables.PROJECT_KEYS_TABLE.stringValue) {


}