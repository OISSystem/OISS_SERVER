package dsm.oissbackend.oiss.monitor.entity.enums

enum class CredentialLocation(
    val description: String,
) {
    QUERY("쿼리 파라미터"),
    HEADER("요청 헤더")
}
