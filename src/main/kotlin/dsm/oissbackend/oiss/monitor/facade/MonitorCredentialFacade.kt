package dsm.oissbackend.oiss.monitor.facade

import dsm.oissbackend.oiss.monitor.exception.MonitorCredentialNotFoundException
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorCredential
import dsm.oissbackend.oiss.monitor.persistent.repository.MonitorCredentialRepository
import org.springframework.stereotype.Component

@Component
class MonitorCredentialFacade(
    private val monitorCredentialRepository: MonitorCredentialRepository,
) {
    fun save(monitorCredential: MonitorCredential): MonitorCredential {
        return monitorCredentialRepository.save(monitorCredential)
    }

    fun findByMonitorId(monitorId: Long): MonitorCredential {
        return monitorCredentialRepository.findByMonitorId(monitorId) ?: throw MonitorCredentialNotFoundException
    }

    fun findByMonitorIdOrNull(monitorId: Long): MonitorCredential? {
        return monitorCredentialRepository.findByMonitorId(monitorId)
    }

    fun update(monitorCredential: MonitorCredential): MonitorCredential {
        if (!monitorCredentialRepository.existsById(monitorCredential.monitorId)) {
            throw MonitorCredentialNotFoundException
        }

        return monitorCredentialRepository.save(monitorCredential)
    }

    fun deleteByMonitorId(monitorId: Long) {
        monitorCredentialRepository.deleteById(monitorId)
    }
}
