package dsm.oissbackend.oiss.monitor.facade

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorHeader
import dsm.oissbackend.oiss.monitor.persistent.repository.MonitorHeaderRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MonitorHeaderFacade(
    private val monitorHeaderRepository: MonitorHeaderRepository,
) {
    fun save(monitorHeader: MonitorHeader): MonitorHeader {
        return monitorHeaderRepository.save(monitorHeader)
    }

    fun saveAll(monitorHeaders: List<MonitorHeader>): List<MonitorHeader> {
        return monitorHeaderRepository.saveAll(monitorHeaders)
    }

    fun findAllByMonitorId(monitorId: Long): List<MonitorHeader> {
        return monitorHeaderRepository.findAllByMonitorId(monitorId)
    }

    @Transactional
    fun deleteAllByMonitorId(monitorId: Long) {
        monitorHeaderRepository.deleteAllByMonitorId(monitorId)
    }
}
