package dsm.oissbackend.common.error

import dsm.oissbackend.common.error.exeption.GlobalErrorCode
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException

data class ValidationErrorResponse(
    val status: Int,
    val message: String,
    val errors: Map<String, String>,
) {
    companion object {
        fun of(e: BindException): ValidationErrorResponse {
            val errorMap = mutableMapOf<String, String>()

            for (error in e.fieldErrors) {
                errorMap[error.field] = error.defaultMessage ?: "Invalid value"
            }

            return ValidationErrorResponse(
                GlobalErrorCode.BAD_REQUEST.status,
                GlobalErrorCode.BAD_REQUEST.message,
                errorMap
            )
        }

        fun of(e: MethodArgumentNotValidException): ValidationErrorResponse {
            val errorMap = mutableMapOf<String, String>()

            for (error in e.fieldErrors) {
                errorMap[error.field] = error.defaultMessage ?: "Invalid value"
            }

            return ValidationErrorResponse(
                GlobalErrorCode.BAD_REQUEST.status,
                GlobalErrorCode.BAD_REQUEST.message,
                errorMap
            )
        }
    }
}