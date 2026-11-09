package dsm.oissbackend.oiss.monitor.facade

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorQuotaPattern
import dsm.oissbackend.oiss.monitor.persistent.repository.MonitorQuotaPatternRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MonitorQuotaPatternFacade(
    private val monitorQuotaPatternRepository: MonitorQuotaPatternRepository,
) {
    fun save(monitorQuotaPattern: MonitorQuotaPattern): MonitorQuotaPattern {
        return monitorQuotaPatternRepository.save(monitorQuotaPattern)
    }

    fun saveAll(monitorQuotaPatterns: List<MonitorQuotaPattern>): List<MonitorQuotaPattern> {
        return monitorQuotaPatternRepository.saveAll(monitorQuotaPatterns)
    }

    fun findAllByMonitorId(monitorId: Long): List<MonitorQuotaPattern> {
        return monitorQuotaPatternRepository.findAllByMonitorId(monitorId)
    }

    @Transactional
    fun deleteAllByMonitorId(monitorId: Long) {
        monitorQuotaPatternRepository.deleteAllByMonitorId(monitorId)
    }
}
