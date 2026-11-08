package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorSchema

interface MonitorSchemaRepositoryCustom {
    fun findByMonitorId(monitorId: Long): MonitorSchema?
}
