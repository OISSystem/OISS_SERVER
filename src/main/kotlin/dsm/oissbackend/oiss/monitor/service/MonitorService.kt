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
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.SchemaSource
import dsm.oissbackend.oiss.monitor.presentation.dto.request.CreateMonitorRequest
import dsm.oissbackend.oiss.monitor.presentation.dto.response.CreateMonitorResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
        val hashedUrl = cryptoUtil.encrypt(request.url)

        // Monitor
        val monitor = Monitor(
            name = request.name,
            provider = request.provider,
            httpMethod = request.httpMethod,
            encryptedUrl = hashedUrl,
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
        request.requestBody?.let {
            val monitorSchema = MonitorSchema(
                monitor = savedMonitor,
                source = SchemaSource.MANUAL,
                requestBody = it
            )
            monitorSchemaFacade.save(monitorSchema)
        }

        return CreateMonitorResponse(
            id = savedMonitor.id
        )
    }
}