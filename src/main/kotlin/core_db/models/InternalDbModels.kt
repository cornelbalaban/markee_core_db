package core_db.models


import core_db.utils.MarkeeUsersTables
import org.jetbrains.exposed.sql.Column
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

    val projectId: Column<Int> = integer("id_project_key").uniqueIndex()
    val projectAccessKey: Column<String> = varchar("access_key", 50)

}

internal object Project: Table(MarkeeUsersTables.PROJECTS_TABLE.stringValue) {

    var projectId: Column<Int> = integer("id_proj").autoIncrement()
    val projectName: Column<String> = varchar("name_proj", 250)
    val projectType: Column<ProjectType> = enumerationByName("type_proj",20, ProjectType::class)
    val projectOwner: Column<Int> = integer("owner_proj")
    val projectToken: Column<String> = varchar("project_token_proj", 250)
    val projectParentCompany: Column<Int> = integer("parent_company_proj")
    val pushNotificationKey: Column<String> = varchar("push_notif_key_proj", 250)

    override val primaryKey: PrimaryKey = PrimaryKey(projectId)
}

internal object Tokens: Table(MarkeeUsersTables.TOKENS_TABLE.stringValue) {

    val token: Column<String> = varchar("",250)
    val expirationTtl: Column<Int> = integer("expiration_ttl_tkn")
    val generationDate: Column<Long> = long("generation_date_tkn")
    val userId: Column<Int> = integer("user_id_tkn")

}

internal object UsersToCompaniesMapping: Table(MarkeeUsersTables.USERS_TO_COMPANIES_TABLE.stringValue) {

}

internal object UsersToProjectsMapping: Table(MarkeeUsersTables.USERS_TO_PROJECTS_TABLE.stringValue) {

    val userId: Column<Long> = long("id_usr_u2p")
    val projectId: Column<Long> = long("id_proj_u2p")
    val role: Column<String> = text("role_usr")

}

internal object CodeModel: Table(MarkeeUsersTables.SIGNUP_CODES.stringValue) {
    val signupCode: Column<String> = varchar("code_cod",250)
    val creationTime: Column<Long> = long("time_created_cod")
    val userId: Column<Long> = long("user_cod")
}
