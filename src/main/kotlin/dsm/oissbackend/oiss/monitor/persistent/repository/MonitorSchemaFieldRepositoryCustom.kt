package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorSchemaField

interface MonitorSchemaFieldRepositoryCustom {
    fun findAllByMonitorId(monitorId: Long): List<MonitorSchemaField>

    fun deleteAllByMonitorId(monitorId: Long): Long
}
