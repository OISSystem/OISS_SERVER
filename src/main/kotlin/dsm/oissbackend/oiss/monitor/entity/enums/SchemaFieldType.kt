package dsm.oissbackend.oiss.monitor.entity.enums

enum class SchemaFieldType(
    val description: String,
) {
    STRING("문자열"),
    NUMBER("숫자"),
    BOOLEAN("불리언"),
    OBJECT("객체"),
    ARRAY("배열"),
    NULL("null")
}
