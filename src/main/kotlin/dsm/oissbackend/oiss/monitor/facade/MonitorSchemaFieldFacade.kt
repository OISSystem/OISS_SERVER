package dsm.oissbackend.oiss.monitor.facade

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorSchemaField
import dsm.oissbackend.oiss.monitor.persistent.repository.MonitorSchemaFieldRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MonitorSchemaFieldFacade(
    private val monitorSchemaFieldRepository: MonitorSchemaFieldRepository,
) {
    fun save(monitorSchemaField: MonitorSchemaField): MonitorSchemaField {
        return monitorSchemaFieldRepository.save(monitorSchemaField)
    }

    fun saveAll(monitorSchemaFields: List<MonitorSchemaField>): List<MonitorSchemaField> {
        return monitorSchemaFieldRepository.saveAll(monitorSchemaFields)
    }

    fun findAllByMonitorId(monitorId: Long): List<MonitorSchemaField> {
        return monitorSchemaFieldRepository.findAllByMonitorId(monitorId)
    }

    @Transactional
    fun deleteAllByMonitorId(monitorId: Long) {
        monitorSchemaFieldRepository.deleteAllByMonitorId(monitorId)
    }
}
