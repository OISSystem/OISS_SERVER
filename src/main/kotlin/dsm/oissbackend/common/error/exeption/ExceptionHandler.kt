package dsm.oissbackend.common.error.exeption

import dsm.oissbackend.common.error.ErrorResponse
import dsm.oissbackend.common.error.ValidationErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

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


}