package core_db.utils

enum class SystemProperties(val stringValue: String) {

    MYSQL_HOST("mysqlDbHost"),
    MYSQL_PORT("mysqlDbPort"),
    MYSQL_USER("mysqlUser"),
    MYSQL_PWD("mysqlPwd"),
    NOSQL_HOST("mongoDbHost"),
    NOSQL_PORT("mongoDbPort"),
}

enum class MarkeeUsersTables(val stringValue: String) {

    USERS_DB("markee_users"),
    USERS_TABLE("users_usr"),
    CUSTOMERS_TABLE("customers_cust"),
    PROJECT_KEYS_TABLE("projects_keys"),
    PROJECTS_TABLE("projects_proj"),
    TOKENS_TABLE("tokens_tkn"),
    USERS_TO_COMPANIES_TABLE("users_to_companies_uc"),
    USERS_TO_PROJECTS_TABLE("users_to_projects_u2p"),
    SIGNUP_CODES("signup_codes_cod")
}
