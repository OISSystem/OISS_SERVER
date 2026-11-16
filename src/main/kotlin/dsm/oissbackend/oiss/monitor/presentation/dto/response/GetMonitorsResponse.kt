package dsm.oissbackend.oiss.monitor.presentation.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.MonitorStatus
import java.time.OffsetDateTime

data class GetMonitorsResponse(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    val serverTime: OffsetDateTime,
    val summary: MonitorSummaryResponse,
    val monitors: List<MonitorListItemResponse>,
)

// 체크 결과 / 인시던트 기능 개발 전까지 계산 불가능한 값은 null
data class MonitorSummaryResponse(
    val total: Int,
    val healthy: Int?,
    val warning: Int?,
    val down: Int?,
    val paused: Int,
    val quotaExhausted: Int?,
    val pending: Int?,
    val ongoingIncidents: Int?,
)

data class MonitorListItemResponse(
    val id: Long,
    val name: String,
    val provider: String,
    // 체크 결과 기반 상태 계산 전까지는 PAUSED(비활성) 외에는 null
    val status: MonitorStatus?,
    val availability24h: Double?,
    val p95ResponseTimeMs: Int?,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    val lastCheckedAt: OffsetDateTime?,
    val checkIntervalSec: Int,
    val checkDelayed: Boolean?,
)
