package dsm.oissbackend.oiss.monitor.exception.errorcode

import dsm.oissbackend.common.error.exeption.ErrorCode

enum class MonitorErrorCode(
    override val status: Int,
    override val message: String
): ErrorCode {
    NOT_FOUND(404, "Monitor Not Found"),
    CREDENTIAL_NOT_FOUND(404, "Monitor Credential Not Found"),
    QUOTA_NOT_FOUND(404, "Monitor Quota Not Found"),
    SCHEMA_NOT_FOUND(404, "Monitor Schema Not Found"),
}