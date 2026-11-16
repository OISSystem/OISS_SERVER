package dsm.oissbackend.oiss.monitor.persistent.entity.enums

enum class SchemaSource(
    val description: String,
) {
    AUTO("자동 생성"),
    MANUAL("수동 등록")
}
