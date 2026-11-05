package dsm.oissbackend.oiss.monitor.facade

import dsm.oissbackend.oiss.monitor.persistent.entity.Monitor
import dsm.oissbackend.oiss.monitor.persistent.repository.MonitorRepository

class MonitorFacade(
    val monitorRepository: MonitorRepository,
) {
    fun save(monitor: Monitor): Monitor {
        return monitorRepository.save(monitor)
    }

    fun findByMonitorId(monitorId: Long): Monitor? {
        return monitorRepository.findByMonitorId(monitorId)
    }

    fun update(monitor: Monitor): Monitor {
        return monitorRepository.save(monitor)
    }

    fun deleteByMonitorId(monitorId: Long) {
        monitorRepository.deleteById(monitorId)
    }
}