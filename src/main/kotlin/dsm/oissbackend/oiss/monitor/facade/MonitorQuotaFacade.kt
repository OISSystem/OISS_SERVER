package dsm.oissbackend.oiss.monitor.facade

import dsm.oissbackend.oiss.monitor.exception.MonitorQuotaNotFoundException
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorQuota
import dsm.oissbackend.oiss.monitor.persistent.repository.MonitorQuotaRepository
import org.springframework.stereotype.Component

@Component
class MonitorQuotaFacade(
    private val monitorQuotaRepository: MonitorQuotaRepository,
) {
    fun save(monitorQuota: MonitorQuota): MonitorQuota {
        return monitorQuotaRepository.save(monitorQuota)
    }

    fun findByMonitorId(monitorId: Long): MonitorQuota {
        return monitorQuotaRepository.findByMonitorId(monitorId) ?: throw MonitorQuotaNotFoundException
    }

    fun findByMonitorIdOrNull(monitorId: Long): MonitorQuota? {
        return monitorQuotaRepository.findByMonitorId(monitorId)
    }

    fun update(monitorQuota: MonitorQuota): MonitorQuota {
        if (!monitorQuotaRepository.existsById(monitorQuota.monitorId)) {
            throw MonitorQuotaNotFoundException
        }

        return monitorQuotaRepository.save(monitorQuota)
    }

    fun deleteByMonitorId(monitorId: Long) {
        monitorQuotaRepository.deleteById(monitorId)
    }
}
