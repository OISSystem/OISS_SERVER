package dsm.oissbackend.oiss.monitor.facade

import dsm.oissbackend.oiss.monitor.exception.MonitorSchemaNotFoundException
import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorSchema
import dsm.oissbackend.oiss.monitor.persistent.repository.MonitorSchemaRepository
import org.springframework.stereotype.Component

@Component
class MonitorSchemaFacade(
    private val monitorSchemaRepository: MonitorSchemaRepository,
) {
    fun save(monitorSchema: MonitorSchema): MonitorSchema {
        return monitorSchemaRepository.save(monitorSchema)
    }

    fun findByMonitorId(monitorId: Long): MonitorSchema {
        return monitorSchemaRepository.findByMonitorId(monitorId) ?: throw MonitorSchemaNotFoundException
    }

    fun findByMonitorIdOrNull(monitorId: Long): MonitorSchema? {
        return monitorSchemaRepository.findByMonitorId(monitorId)
    }

    fun update(monitorSchema: MonitorSchema): MonitorSchema {
        if (!monitorSchemaRepository.existsById(monitorSchema.monitorId)) {
            throw MonitorSchemaNotFoundException
        }

        return monitorSchemaRepository.save(monitorSchema)
    }

    fun deleteByMonitorId(monitorId: Long) {
        monitorSchemaRepository.deleteById(monitorId)
    }
}
