package dsm.oissbackend.oiss.monitor.facade

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorErrorPattern
import dsm.oissbackend.oiss.monitor.persistent.repository.MonitorErrorPatternRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MonitorErrorPatternFacade(
    private val monitorErrorPatternRepository: MonitorErrorPatternRepository,
) {
    fun save(monitorErrorPattern: MonitorErrorPattern): MonitorErrorPattern {
        return monitorErrorPatternRepository.save(monitorErrorPattern)
    }

    fun saveAll(monitorErrorPatterns: List<MonitorErrorPattern>): List<MonitorErrorPattern> {
        return monitorErrorPatternRepository.saveAll(monitorErrorPatterns)
    }

    fun findAllByMonitorId(monitorId: Long): List<MonitorErrorPattern> {
        return monitorErrorPatternRepository.findAllByMonitorId(monitorId)
    }

    @Transactional
    fun deleteAllByMonitorId(monitorId: Long) {
        monitorErrorPatternRepository.deleteAllByMonitorId(monitorId)
    }
}
