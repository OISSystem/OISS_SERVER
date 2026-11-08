package dsm.oissbackend.oiss.monitor.presentation.dto.response

data class CreateMonitorResponse(
    val id: Long,
    val status: String,
    val warnings: List<MonitorWarningResponse> = emptyList(),
)

data class MonitorWarningResponse(
    val code: String,
    val message: String,

    val recommendedIntervalSec: Int? = null,
)
