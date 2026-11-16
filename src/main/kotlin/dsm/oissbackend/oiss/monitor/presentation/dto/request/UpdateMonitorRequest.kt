package dsm.oissbackend.oiss.monitor.presentation.dto.request

import dsm.oissbackend.oiss.monitor.persistent.entity.enums.HttpMethod

data class UpdateMonitorRequest(
    val name: String,
    val provider: String,
    val httpMethod: HttpMethod,
    val url: String,
    val requestBody: String? = null,
    val headers: List<HeaderRequest> = emptyList(),
    val credential: CredentialRequest? = null,
    val checkIntervalSec: Int,
    val failureThreshold: Int,
    val recoveryThreshold: Int,
    val timeoutMs: Int,
    val latencyWarnMs: Int,
    val errorPatterns: List<MatchPatternRequest> = emptyList(),
    val jsonPathRules: List<JsonPathRuleRequest> = emptyList(),
    val expectedSchema: ExpectedSchemaRequest? = null,
    val quota: QuotaRequest? = null,
    val confirmDuplicate: Boolean = false,

    val resetSchema: Boolean = false,
)
