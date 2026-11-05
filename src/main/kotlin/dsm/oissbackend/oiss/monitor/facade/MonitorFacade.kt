package dsm.oissbackend.oiss.monitor.facade

import dsm.oissbackend.oiss.monitor.exception.errorcode.MonitorNotFoundException
import dsm.oissbackend.oiss.monitor.persistent.entity.Monitor
import dsm.oissbackend.oiss.monitor.persistent.repository.MonitorRepository
import org.springframework.stereotype.Component

@Component
class MonitorFacade(
    private val monitorRepository: MonitorRepository,
) {
    fun save(monitor: Monitor): Monitor {
        return monitorRepository.save(monitor)
    }

    fun findByMonitorId(monitorId: Long): Monitor? {
        return monitorRepository.findByMonitorId(monitorId) ?: throw MonitorNotFoundException
    }

    fun update(monitor: Monitor): Monitor {
        if (!monitorRepository.existsById(monitor.id)) {
            throw MonitorNotFoundException
        }

        return monitorRepository.save(monitor)
    }

    fun deleteByMonitorId(monitorId: Long) {
        monitorRepository.deleteById(monitorId)
    }
}