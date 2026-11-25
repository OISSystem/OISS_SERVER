package dsm.oissbackend.oiss.monitor.service

import dsm.oissbackend.common.utils.CryptoUtil
import dsm.oissbackend.common.utils.jsonUtils
import dsm.oissbackend.oiss.monitor.facade.MonitorCredentialFacade
import dsm.oissbackend.oiss.monitor.facade.MonitorErrorPatternFacade
import dsm.oissbackend.oiss.monitor.facade.MonitorFacade
import dsm.oissbackend.oiss.monitor.facade.MonitorHeaderFacade
import dsm.oissbackend.oiss.monitor.facade.MonitorJsonPathRuleFacade
import dsm.oissbackend.oiss.monitor.facade.MonitorQuotaFacade
import dsm.oissbackend.oiss.monitor.facade.MonitorQuotaPatternFacade
import dsm.oissbackend.oiss.monitor.facade.MonitorSchemaFacade
import dsm.oissbackend.oiss.monitor.persistent.entity.Monitor
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorCredential
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorErrorPattern
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorHeader
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorJsonPathRule
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorQuota
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorQuotaPattern
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorSchema
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.MonitorStatus
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.SchemaFieldType
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.SchemaSource
import dsm.oissbackend.oiss.monitor.presentation.dto.request.CreateMonitorRequest
import dsm.oissbackend.oiss.monitor.presentation.dto.request.UpdateMonitorRequest
import dsm.oissbackend.oiss.monitor.presentation.dto.response.CreateMonitorResponse
import dsm.oissbackend.oiss.monitor.presentation.dto.response.CredentialResponse
import dsm.oissbackend.oiss.monitor.presentation.dto.response.ExpectedSchemaResponse
import dsm.oissbackend.oiss.monitor.presentation.dto.response.GetMonitorConfigResponse
import dsm.oissbackend.oiss.monitor.presentation.dto.response.GetMonitorsResponse
import dsm.oissbackend.oiss.monitor.presentation.dto.response.HeaderResponse
import dsm.oissbackend.oiss.monitor.presentation.dto.response.JsonPathRuleResponse
import dsm.oissbackend.oiss.monitor.presentation.dto.response.MatchPatternResponse
import dsm.oissbackend.oiss.monitor.presentation.dto.response.MonitorListItemResponse
import dsm.oissbackend.oiss.monitor.presentation.dto.response.MonitorSummaryResponse
import dsm.oissbackend.oiss.monitor.presentation.dto.response.QuotaResponse
import dsm.oissbackend.oiss.monitor.presentation.dto.response.SchemaFieldResponse
import dsm.oissbackend.oiss.monitor.presentation.dto.response.UpdateMonitorResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.time.ZoneId

@Service
class MonitorService(
    private val cryptoUtil: CryptoUtil,
    private val jsonUtils: jsonUtils,
    private val monitorFacade: MonitorFacade,
    private val monitorHeaderFacade: MonitorHeaderFacade,
    private val monitorCredentialFacade: MonitorCredentialFacade,
    private val monitorErrorPatternFacade: MonitorErrorPatternFacade,
    private val monitorJsonPathRuleFacade: MonitorJsonPathRuleFacade,
    private val monitorQuotaPatternFacade: MonitorQuotaPatternFacade,
    private val monitorQuotaFacade: MonitorQuotaFacade,
    private val monitorSchemaFacade: MonitorSchemaFacade,
) {

    // 추후에 유저 "confirmDuplicate" 추가 필요

    @Transactional
    fun createMonitor(request: CreateMonitorRequest): CreateMonitorResponse {
        val encryptedUrl = cryptoUtil.encrypt(request.url)

        // Monitor
        val monitor = Monitor(
            name = request.name,
            provider = request.provider,
            httpMethod = request.httpMethod,
            encryptedUrl = encryptedUrl,
            requestBody = request.requestBody,
            checkIntervalSeconds = request.checkIntervalSec,
            failureThreshold = request.failureThreshold,
            recoveryThreshold = request.recoveryThreshold,
            timeoutMs = request.timeoutMs,
            latencyWarnMs = request.latencyWarnMs,
            enabled = true
        )

        val savedMonitor = monitorFacade.save(monitor)

        // MonitorHeader
        val monitorHeaders = mutableListOf<MonitorHeader>()

        request.headers.forEachIndexed { index, header ->
            val header = MonitorHeader(
                name = header.name,
                value = header.value,
                monitor = savedMonitor,
                sortOrder = index,
            )

            monitorHeaders.add(header)
        }

        monitorHeaderFacade.saveAll(monitorHeaders)

        // monitorCredential
        request.credential?.let {
            val encryptedValue = cryptoUtil.encrypt(it.value)

            val monitorCredential = MonitorCredential(
                monitor = savedMonitor,
                location = it.location,
                paramName = it.paramName,
                encryptedValue = encryptedValue.toByteArray(),
                last4 = it.value.takeLast(4)
            )

            monitorCredentialFacade.save(monitorCredential)
        }

        // monitorErrorPattern
        val monitorErrorPatterns = mutableListOf<MonitorErrorPattern>()

        request.errorPatterns.forEach {
            val ErrorPattern = MonitorErrorPattern(
                monitor = savedMonitor,
                matchType = it.matchType,
                pattern = it.pattern,
            )

            monitorErrorPatterns.add(ErrorPattern)
        }

        monitorErrorPatternFacade.saveAll(monitorErrorPatterns)

        // monitorQuotaPattern
        val monitorQuotaPatterns = mutableListOf<MonitorQuotaPattern>()

        request.quota?.exhaustedPatterns?.let {
            it.forEach {
                val QuotaPattern = MonitorQuotaPattern(
                    monitor = savedMonitor,
                    matchType = it.matchType,
                    pattern = it.pattern,
                )

                monitorQuotaPatterns.add(QuotaPattern)
            }

            monitorQuotaPatternFacade.saveAll(monitorQuotaPatterns)
        }

        // monitorJsonPathRule
        val monitorJsonPathRules = mutableListOf<MonitorJsonPathRule>()

        request.jsonPathRules.forEachIndexed { index, rule ->
            val jsonPathRule = MonitorJsonPathRule(
                monitor = savedMonitor,
                path = rule.path,
                operator = rule.operator,
                expected = rule.expected,
                sortOrder = index
            )

            monitorJsonPathRules.add(jsonPathRule)
        }

        monitorJsonPathRuleFacade.saveAll(monitorJsonPathRules)

        // monitorQuota
        request.quota?.let {
            val monitorQuota = MonitorQuota(
                monitor = savedMonitor,
                dailyLimit = it.dailyLimit,
                costPerRequest = it.costPerRequest,
                resetTime = it.resetTime,
                resetTimezone = it.resetTimezone,
                exhaustedHttpStatuses = it.exhaustedHttpStatuses,
            )

            monitorQuotaFacade.save(monitorQuota)
        }

        // monitorSchema
        request.expectedSchema?.let {
            val monitorSchema = MonitorSchema(
                monitor = savedMonitor,
                source = SchemaSource.MANUAL,
                requestBody = jsonUtils.mapToJson(it.fields.associate { field -> field.path to field.type.name })
            )
            monitorSchemaFacade.save(monitorSchema)
        }

        return CreateMonitorResponse(
            id = savedMonitor.id
        )
    }

    @Transactional
    fun deleteMonitor(monitorId: Long) {
        monitorFacade.findByMonitorId(monitorId)

        monitorHeaderFacade.deleteAllByMonitorId(monitorId)
        monitorErrorPatternFacade.deleteAllByMonitorId(monitorId)
        monitorQuotaPatternFacade.deleteAllByMonitorId(monitorId)
        monitorJsonPathRuleFacade.deleteAllByMonitorId(monitorId)
        monitorCredentialFacade.deleteByMonitorId(monitorId)
        monitorQuotaFacade.deleteByMonitorId(monitorId)
        monitorSchemaFacade.deleteByMonitorId(monitorId)

        monitorFacade.deleteByMonitorId(monitorId)
    }

    @Transactional
    fun updateMonitor(monitorId: Long, request: UpdateMonitorRequest): UpdateMonitorResponse {
        val encryptedUrl = cryptoUtil.encrypt(request.url)

        val monitor = monitorFacade.findByMonitorId(monitorId)

        // monitor
        monitor.update(
            name = request.name,
            provider = request.provider,
            httpMethod = request.httpMethod,
            encryptedUrl = encryptedUrl,
            requestBody = request.requestBody,
            checkIntervalSeconds = request.checkIntervalSec,
            failureThreshold = request.failureThreshold,
            recoveryThreshold = request.recoveryThreshold,
            timeoutMs = request.timeoutMs,
            latencyWarnMs = request.latencyWarnMs,
            enabled = monitor.enabled,
        )

        val updatedMonitor = monitorFacade.update(monitor)

        // MonitorHeader
        monitorHeaderFacade.deleteAllByMonitorId(monitorId)

        val monitorHeaders = mutableListOf<MonitorHeader>()

        request.headers.forEachIndexed { index, header ->
            val header = MonitorHeader(
                name = header.name,
                value = header.value,
                monitor = updatedMonitor,
                sortOrder = index,
            )

            monitorHeaders.add(header)
        }
        monitorHeaderFacade.saveAll(monitorHeaders)

        // monitorCredential
        monitorCredentialFacade.deleteByMonitorId(monitorId)

        request.credential?.let {
            val encryptedValue = cryptoUtil.encrypt(it.value)

            val monitorCredential = MonitorCredential(
                monitor = updatedMonitor,
                location = it.location,
                paramName = it.paramName,
                encryptedValue = encryptedValue.toByteArray(),
                last4 = it.value.takeLast(4)
            )

            monitorCredentialFacade.save(monitorCredential)
        }

        // monitorErrorPattern
        monitorErrorPatternFacade.deleteAllByMonitorId(monitorId)

        val monitorErrorPatterns = mutableListOf<MonitorErrorPattern>()

        request.errorPatterns.forEach {
            val ErrorPattern = MonitorErrorPattern(
                monitor = updatedMonitor,
                matchType = it.matchType,
                pattern = it.pattern,
            )

            monitorErrorPatterns.add(ErrorPattern)
        }

        monitorErrorPatternFacade.saveAll(monitorErrorPatterns)

        // monitorQuotaPattern
        monitorQuotaPatternFacade.deleteAllByMonitorId(monitorId)

        val monitorQuotaPatterns = mutableListOf<MonitorQuotaPattern>()

        request.quota?.exhaustedPatterns?.let {
            it.forEach {
                val QuotaPattern = MonitorQuotaPattern(
                    monitor = updatedMonitor,
                    matchType = it.matchType,
                    pattern = it.pattern,
                )

                monitorQuotaPatterns.add(QuotaPattern)
            }

            monitorQuotaPatternFacade.saveAll(monitorQuotaPatterns)
        }

        // monitorJsonPathRule
        monitorJsonPathRuleFacade.deleteAllByMonitorId(monitorId)

        val monitorJsonPathRules = mutableListOf<MonitorJsonPathRule>()

        request.jsonPathRules.forEachIndexed { index, rule ->
            val jsonPathRule = MonitorJsonPathRule(
                monitor = updatedMonitor,
                path = rule.path,
                operator = rule.operator,
                expected = rule.expected,
                sortOrder = index
            )

            monitorJsonPathRules.add(jsonPathRule)
        }

        monitorJsonPathRuleFacade.saveAll(monitorJsonPathRules)

        // monitorQuota
        monitorQuotaFacade.deleteByMonitorId(monitorId)

        request.quota?.let {
            val monitorQuota = MonitorQuota(
                monitor = updatedMonitor,
                dailyLimit = it.dailyLimit,
                costPerRequest = it.costPerRequest,
                resetTime = it.resetTime,
                resetTimezone = it.resetTimezone,
                exhaustedHttpStatuses = it.exhaustedHttpStatuses,
            )

            monitorQuotaFacade.save(monitorQuota)
        }

        // monitorSchema
        if (request.expectedSchema != null || request.resetSchema) {
            monitorSchemaFacade.deleteByMonitorId(monitorId)
        }

        request.expectedSchema?.let {
            val monitorSchema = MonitorSchema(
                monitor = updatedMonitor,
                source = SchemaSource.MANUAL,
                requestBody = jsonUtils.mapToJson(it.fields.associate { field -> field.path to field.type.name })
            )
            monitorSchemaFacade.save(monitorSchema)
        }

        return UpdateMonitorResponse(
            id = monitor.id
        )
    }
    
    @Transactional(readOnly = true)
    fun getMonitorConfig(monitorId: Long): GetMonitorConfigResponse {
        val monitor = monitorFacade.findByMonitorId(monitorId)

        // MonitorHeader
        val headers = monitorHeaderFacade.findAllByMonitorId(monitorId).map {
            HeaderResponse(
                name = it.name,
                value = it.value,
            )
        }

        // monitorCredential (원문은 내려주지 않고 마스킹 값만)
        val credential = monitorCredentialFacade.findByMonitorIdOrNull(monitorId)?.let {
            CredentialResponse(
                location = it.location,
                paramName = it.paramName,
                maskedValue = "****${it.last4}",
            )
        }

        // monitorErrorPattern
        val errorPatterns = monitorErrorPatternFacade.findAllByMonitorId(monitorId).map {
            MatchPatternResponse(
                matchType = it.matchType,
                pattern = it.pattern,
            )
        }

        // monitorJsonPathRule
        val jsonPathRules = monitorJsonPathRuleFacade.findAllByMonitorId(monitorId).map {
            JsonPathRuleResponse(
                path = it.path,
                operator = it.operator,
                expected = it.expected,
            )
        }

        // monitorSchema ({ path: type } JSON으로 저장됨)
        val expectedSchema = monitorSchemaFacade.findByMonitorIdOrNull(monitorId)?.let { schema ->
            val fields = jsonUtils.jsonToMap(schema.requestBody).orEmpty().map { (path, type) ->
                SchemaFieldResponse(
                    path = path.toString(),
                    type = SchemaFieldType.valueOf(type.toString()),
                )
            }

            ExpectedSchemaResponse(
                source = schema.source,
                generatedAt = schema.createdAt.atZone(ZoneId.systemDefault()).toOffsetDateTime(),
                fields = fields,
            )
        }

        // monitorQuota + monitorQuotaPattern
        val quota = monitorQuotaFacade.findByMonitorIdOrNull(monitorId)?.let { quota ->
            val exhaustedPatterns = monitorQuotaPatternFacade.findAllByMonitorId(monitorId).map {
                MatchPatternResponse(
                    matchType = it.matchType,
                    pattern = it.pattern,
                )
            }

            QuotaResponse(
                dailyLimit = quota.dailyLimit,
                costPerRequest = quota.costPerRequest,
                resetTime = quota.resetTime,
                resetTimezone = quota.resetTimezone,
                exhaustedHttpStatuses = quota.exhaustedHttpStatuses,
                exhaustedPatterns = exhaustedPatterns,
            )
        }

        return GetMonitorConfigResponse(
            id = monitor.id,
            enabled = monitor.enabled,
            name = monitor.name,
            provider = monitor.provider,
            httpMethod = monitor.httpMethod,
            url = cryptoUtil.decrypt(monitor.encryptedUrl),
            requestBody = monitor.requestBody,
            headers = headers,
            credential = credential,
            checkIntervalSec = monitor.checkIntervalSeconds,
            failureThreshold = monitor.failureThreshold,
            recoveryThreshold = monitor.recoveryThreshold,
            timeoutMs = monitor.timeoutMs,
            latencyWarnMs = monitor.latencyWarnMs,
            errorPatterns = errorPatterns,
            jsonPathRules = jsonPathRules,
            expectedSchema = expectedSchema,
            quota = quota,
        )
    }

    @Transactional(readOnly = true)
    fun getMonitors(
        offset: Int,
        limit: Int,
        status: MonitorStatus?,
        provider: String?,
        keyword: String?,
    ): GetMonitorsResponse {
        val monitors = monitorFacade.findAllByFilter(
            offset = offset.toLong(),
            limit = limit.toLong(),
            status = status,
            provider = provider,
            keyword = keyword,
        ).map {
            MonitorListItemResponse(
                id = it.id,
                name = it.name,
                provider = it.provider,
                status = MonitorStatus.PAUSED,
                availability24h = null,
                p95ResponseTimeMs = null,
                lastCheckedAt = null,
                checkIntervalSec = it.checkIntervalSeconds,
                checkDelayed = null,
            )
        }

        val summary = MonitorSummaryResponse(
            total = monitorFacade.count().toInt(),
            healthy = null,
            warning = null,
            down = null,
            paused = monitorFacade.countByEnabled(false).toInt(),
            quotaExhausted = null,
            pending = null,
            ongoingIncidents = null,
        )

        return GetMonitorsResponse(
            serverTime = OffsetDateTime.now(),
            summary = summary,
            monitors = monitors,
        )
    }
}