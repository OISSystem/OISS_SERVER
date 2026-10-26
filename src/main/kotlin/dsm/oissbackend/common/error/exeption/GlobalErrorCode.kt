package dsm.oissbackend.common.error.exeption

enum class GlobalErrorCode(
    override val status: Int,
    override val message: String
): ErrorCode {

    // internal
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    // Bad Request
    BAD_REQUEST(400, "Bad Request"),
    CONFLICT(409, "Conflict")
}