package dsm.oissbackend.common.error

data class ValidationErrorResponse(
    val status: Int,
    val message: String,
    val errors: List<String>
) {

}