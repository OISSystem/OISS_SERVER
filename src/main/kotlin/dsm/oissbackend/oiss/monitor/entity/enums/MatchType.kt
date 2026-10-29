package dsm.oissbackend.oiss.monitor.entity.enums

enum class MatchType(
    val description: String,
) {
    CONTAINS("문자열 포함"),
    REGEX("정규식 일치")
}
