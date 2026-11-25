package dsm.oissbackend.oiss.monitor.presentation.dto.request

import dsm.oissbackend.oiss.monitor.persistent.entity.enums.CredentialLocation
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.HttpMethod
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.JsonPathOperator
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.MatchType
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.SchemaFieldType
import java.time.LocalTime

data class CreateMonitorRequest(
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
)

data class HeaderRequest(
    val name: String,
    val value: String,
)

data class CredentialRequest(
    val location: CredentialLocation,
    val paramName: String,
    val value: String,
) {
    // 로그에 키 원문이 찍히지 않도록 마스킹
    override fun toString(): String =
        "CredentialRequest(location=$location, paramName=$paramName, value=****)"
}

// errorPatterns, quota.exhaustedPatterns 공용
data class MatchPatternRequest(
    val matchType: MatchType,
    val pattern: String,
)

data class JsonPathRuleRequest(
    val path: String,
    val operator: JsonPathOperator,
    val expected: String? = null,
)

data class ExpectedSchemaRequest(
    val fields: List<SchemaFieldRequest>,
)

data class SchemaFieldRequest(
    val path: String,
    val type: SchemaFieldType,
)

data class QuotaRequest(
    val dailyLimit: Int,
    val costPerRequest: Int,
    val resetTime: LocalTime,
    val resetTimezone: String = "Asia/Seoul",
    val exhaustedHttpStatuses: List<Int> = emptyList(),
    val exhaustedPatterns: List<MatchPatternRequest> = emptyList(),
)
