package dsm.oissbackend.oiss.monitor.presentation.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.CredentialLocation
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.HttpMethod
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.JsonPathOperator
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.MatchType
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.SchemaFieldType
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.SchemaSource
import java.time.LocalTime
import java.time.OffsetDateTime

data class GetMonitorConfigResponse(
    val id: Long,
    val enabled: Boolean,
    val name: String,
    val provider: String,
    val httpMethod: HttpMethod,
    val url: String,
    val requestBody: String?,
    val headers: List<HeaderResponse>,
    val credential: CredentialResponse?,
    val checkIntervalSec: Int,
    val failureThreshold: Int,
    val recoveryThreshold: Int,
    val timeoutMs: Int,
    val latencyWarnMs: Int,
    val errorPatterns: List<MatchPatternResponse>,
    val jsonPathRules: List<JsonPathRuleResponse>,
    val expectedSchema: ExpectedSchemaResponse?,
    val quota: QuotaResponse?,
)

data class HeaderResponse(
    val name: String,
    val value: String,
)

data class CredentialResponse(
    val location: CredentialLocation,
    val paramName: String,
    val maskedValue: String,
)

data class MatchPatternResponse(
    val matchType: MatchType,
    val pattern: String,
)

data class JsonPathRuleResponse(
    val path: String,
    val operator: JsonPathOperator,
    val expected: String?,
)

data class ExpectedSchemaResponse(
    val source: SchemaSource,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    val generatedAt: OffsetDateTime,
    val fields: List<SchemaFieldResponse>,
)

data class SchemaFieldResponse(
    val path: String,
    val type: SchemaFieldType,
)

data class QuotaResponse(
    val dailyLimit: Int,
    val costPerRequest: Int,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    val resetTime: LocalTime,
    val resetTimezone: String,
    val exhaustedHttpStatuses: List<Int>,
    val exhaustedPatterns: List<MatchPatternResponse>,
)
