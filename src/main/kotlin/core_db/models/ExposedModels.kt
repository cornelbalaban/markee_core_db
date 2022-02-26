package core_db.models

enum class ProjectType(val stringValue: String) {
    free("free"),
    pro("pro")
}

data class UserModel(
        var userId: Int? = null,
        val email: String,
        var userName: String?,
        var passwordSalt: String?,
        var passwordHash: String?
) {
    fun isValid(): Boolean {
        return userName != null && passwordHash != null && passwordSalt != null
    }
}

data class CompanyModel(
        val customerName: String,
        var customerAdmin: Int,
        var customerId: Int? = null,
        var customerCountry: String? = null,
        var customerCity: String? = null,
        var customerAddress: String? = null
)


data class ProjectModel(
    val companyId: Int,
    val ownerId: Int,
    var type: ProjectType,
    var projectName: String = "Default Project",
    var projectId: Int? = null
)

data class SignupCodeModel(
    var code: String? = null,
    var userId: String? = null
)

class DaoResponse<T>(
    var responseCode: DaoResponseCode,
    var responseMessage: DaoResponseMessage,
    var responseResource: T
)

enum class DaoResponseCode {

    USER_ALREADY_EXISTS,
    USER_CREATED,
    PROJECT_CREATED,
    CUSTOMER_CREATED,
    CUSTOMER_UDPATED,
    USER_UPDATED,
    USER_DELETED,
    PROJECT_DELETED,
    PROJECT_UPDATED,
    CUSTOMER_DELETED,
    SIGNUP_CODE_CREATED,
    SIGNUP_CODE_DELETED,
    SIGNUP_CODE_CREATE_FAILED,
    METHOD_UNAVAILABLE

}

enum class DaoResponseMessage(var stringValue: String) {

    USER_ALREADY_EXISTS("user already exists"),
    USER_CREATED("user created"),
    PROJECT_CREATED("project created"),
    CUSTOMER_CREATED("customer created"),
    CUSTOMER_UDPATED("customer updated"),
    USER_UPDATED("user updated"),
    USER_DELETED("user deleted"),
    PROJECT_DELETED("project deleted"),
    PROJECT_UPDATED("project updated"),
    CUSTOMER_DELETED("customer deleted"),
    SIGNUP_CODE_CREATED("signup code created"),
    SIGNUP_CODE_DELETED("signup code deleted"),
    SIGNUP_CODE_CREATE_FAILED("signup code creation failed"),
    METHOD_UNAVAILABLE("method unavailable")
}
