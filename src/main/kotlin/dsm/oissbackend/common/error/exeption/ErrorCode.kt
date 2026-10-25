package dsm.oissbackend.common.error.exeption

enum class ErrorCode(
    val status: Int,
    val message: String,
) {
    TOKEN_INVALID(401, "Token Invalid"),
    TOKEN_EXPIRED(401, "Token Expired"),
    ACCESS_DENIED(403, "Access Denied"),
    UNMATCHED_VERIFY_CODE(403, "Unmatched Verify Code"),
    UNEXIST_VERIFY_CODE(403, "Unexist Verify Code"),

    // user
    UNMATCHED_PASSWORD(400, "Unmatched Password"),
    ALREADY_ACCOUNT_ID(409, "Already Exist Account Id"),
    ALREADY_EXIST_EMAIL(409, "Already Exist Email"),
    UNMATCHED_USER(403, "Unmatched User"),
    USER_NOT_FOUND(404, "User Not Found"),
    EMAIL_NOT_VERIFIED(403, "Email Not Verified"),
}