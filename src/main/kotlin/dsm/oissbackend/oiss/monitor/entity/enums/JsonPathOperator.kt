package dsm.oissbackend.oiss.monitor.entity.enums

enum class JsonPathOperator(
    val description: String,
) {
    EQ("같음"),
    NE("같지 않음"),
    GT("초과"),
    LT("미만"),
    LENGTH_GT("길이 초과"),
    MATCHES("정규식 일치"),
    EXISTS("존재함")
}
