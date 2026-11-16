package dsm.oissbackend.oiss.monitor.persistent.entity.enums

enum class MonitorStatus(
    val description: String,
) {
    HEALTHY("정상"),
    WARNING("경고"),
    DOWN("장애"),
    PAUSED("일시 중지"),
    QUOTA_EXHAUSTED("할당량 소진"),
    PENDING("확인 대기")
}
