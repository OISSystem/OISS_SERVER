package dsm.oissbackend.common.error

import dsm.oissbackend.common.error.exeption.OissException

data class ErrorResponse(
    val status: Int,
    val message: String,
) {
    companion object {
        fun of(exception: OissException): ErrorResponse {
            return ErrorResponse(
                status = exception.status,
                message = exception.message
            )
        }

        // 커스텀 예외
        fun of(status: Int, message: String): ErrorResponse {
            return ErrorResponse(
                status = status,
                message = message
            )
        }
    }
}
