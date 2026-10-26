package dsm.oissbackend.common.error.exeption

import org.springframework.http.HttpStatus

class CustomException(
    val httpStatus: HttpStatus,
    override val message: String,
): RuntimeException(message)