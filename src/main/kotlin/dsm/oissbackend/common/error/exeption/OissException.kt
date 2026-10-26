package dsm.oissbackend.common.error.exeption

abstract class OissException(
    val errorCode: ErrorCode,
    cause: Throwable? = null,
): RuntimeException(errorCode.message, cause) {
    val status: Int
        get() = errorCode.status

    override val message: String
        get() = errorCode.message
}