package models

import MarkeeUsersTables
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ForeignKeyConstraint
import org.jetbrains.exposed.sql.Table

internal object User: Table(MarkeeUsersTables.USERS_TABLE.stringValue) {
    val userId: Column<Int> = integer("id_usr").autoIncrement()
    val emailUsr: Column<String> = varchar("email_usr", 250).uniqueIndex()
    val passwordSalt: Column<String> = varchar("salt_usr", 250)
    val passwordHash: Column<String> = varchar("pwd_hash_usr", 250)
    val userName: Column<String> = varchar("name_usr", 250)

    override val primaryKey: PrimaryKey = PrimaryKey(userId)

}

internal object CompanyCustomer: Table(MarkeeUsersTables.CUSTOMERS_TABLE.stringValue) {

    var customerId: Column<Int> = integer("id_cust").autoIncrement()
    var customerName: Column<String> = varchar("name_cust", 250)
    var customerAddress: Column<String> = varchar("address_cust", 250)
    var customerCountry: Column<String> = varchar("country_cust", 250)
    var customerCity: Column<String> = varchar("city_cust", 250)
    var customerAdmin: Column<Int> = integer("admin_cust")

    override val primaryKey: PrimaryKey = PrimaryKey(customerId)
    //override val foreignKeyConstraint: ForeignKeyConstraint = ForeignKeyConstraint(customerAdmin)
}

internal object ProjectsKeys: Table(MarkeeUsersTables.PROJECT_KEYS_TABLE.stringValue) {

    val projectId: Column<Long> = long("id_project_key").uniqueIndex()
    val projectAccessKey: Column<String> = varchar("access_key", 50)

}

internal object Project: Table(MarkeeUsersTables.PROJECTS_TABLE.stringValue) {

    val projectId: Column<Long> = long("id_proj")
    val projectName: Column<String> = varchar("name_proj", 250)
    val projectType: Column<ProjectType> = enumeration("type_proj", ProjectType::class)
    val projectOwner: Column<Int> = integer("owner_proj")
    val projectToken: Column<String> = varchar("project_token_proj", 250)
    val projectParentCompany: Column<Int> = integer("parent_company_proj")
    val pushNotificationKey: Column<String> = varchar("push_notif_key_proj", 250)

    override val primaryKey: PrimaryKey = PrimaryKey(projectId)
}

internal object Tokens: Table(MarkeeUsersTables.TOKENS_TABLE.stringValue) {

}

internal object UsersToCompaniesMapping: Table(MarkeeUsersTables.USERS_TO_COMPANIES_TABLE.stringValue) {

}

internal object UsersToProjectsMapping: Table(MarkeeUsersTables.USERS_TO_PROJECTS_TABLE.stringValue) {

}

