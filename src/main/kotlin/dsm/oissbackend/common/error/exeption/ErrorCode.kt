package dsm.oissbackend.common.error.exeption

interface ErrorCode {
    val status: Int
    val message: String
}