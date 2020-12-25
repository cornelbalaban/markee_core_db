package models

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
