package dsm.oissbackend.common.error.exeption

import dsm.oissbackend.common.error.ErrorResponse
import dsm.oissbackend.common.error.ValidationErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import kotlin.math.E

@RestControllerAdvice
class ExceptionHandler() {

    @ExceptionHandler(OissException::class)
    fun CustomExceptionHandler(exception: OissException, request: WebRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse.of(exception),
            HttpStatus.valueOf(exception.status))
    }

    @ExceptionHandler(BindException::class)
    fun BindExceptionHandler(exception: BindException, request: WebRequest): ResponseEntity<ValidationErrorResponse> {
        return ResponseEntity(
            ValidationErrorResponse.of(exception),
            HttpStatus.valueOf(GlobalErrorCode.BAD_REQUEST.status)
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun MethodArgumentNotValidExceptionHandler(exception: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<ValidationErrorResponse> {
        return ResponseEntity(
            ValidationErrorResponse.of(exception),
            HttpStatus.valueOf(GlobalErrorCode.BAD_REQUEST.status)
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun IllegalArgumentExceptionHandler(exception: IllegalArgumentException, request: WebRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse.of(
                status = GlobalErrorCode.BAD_REQUEST.status,
                message = exception.message?: "Bad Request",
            ),
            HttpStatus.valueOf(GlobalErrorCode.BAD_REQUEST.status)
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun HttpMessageNotReadableExceptionHandler(exception: HttpMessageNotReadableException, request: WebRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse.of(
                status = GlobalErrorCode.BAD_REQUEST.status,
                message = exception.message?: "Bad Request",
            ),
            HttpStatus.valueOf(GlobalErrorCode.BAD_REQUEST.status)
        )
    }

    @ExceptionHandler(IllegalStateException::class)
    fun IllegalStateExceptionHandler(exception: IllegalStateException, request: WebRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse.of(
                status = GlobalErrorCode.CONFLICT.status,
                message = exception.message?: "Conflict",
            ),
            HttpStatus.valueOf(GlobalErrorCode.CONFLICT.status)
        )
    }

    @ExceptionHandler(Exception::class)
    fun ExceptionHandler(exception: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse.of(
                status = GlobalErrorCode.INTERNAL_SERVER_ERROR.status,
                message = GlobalErrorCode.INTERNAL_SERVER_ERROR.message
            ),
            HttpStatus.valueOf(GlobalErrorCode.INTERNAL_SERVER_ERROR.status)
        )
    }
}