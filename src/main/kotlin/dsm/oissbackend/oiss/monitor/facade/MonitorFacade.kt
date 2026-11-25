package dsm.oissbackend.oiss.monitor.facade

import dsm.oissbackend.oiss.monitor.exception.MonitorNotFoundException
import dsm.oissbackend.oiss.monitor.persistent.entity.Monitor
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.MonitorStatus
import dsm.oissbackend.oiss.monitor.persistent.repository.MonitorRepository
import org.springframework.stereotype.Component

@Component
class MonitorFacade(
    private val monitorRepository: MonitorRepository,
) {
    fun save(monitor: Monitor): Monitor {
        return monitorRepository.save(monitor)
    }

    fun findByMonitorId(monitorId: Long): Monitor {
        return monitorRepository.findByMonitorId(monitorId) ?: throw MonitorNotFoundException
    }

    fun findAllByFilter(
        offset: Long,
        limit: Long,
        status: MonitorStatus?,
        provider: String?,
        keyword: String?,
    ): List<Monitor> {
        return monitorRepository.findAllByFilter(offset, limit, status, provider, keyword)
    }

    fun count(): Long {
        return monitorRepository.count()
    }

    fun countByEnabled(enabled: Boolean): Long {
        return monitorRepository.countByEnabled(enabled)
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