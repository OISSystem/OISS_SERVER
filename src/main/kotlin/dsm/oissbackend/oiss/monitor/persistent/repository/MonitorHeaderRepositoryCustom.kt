package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorHeader

interface MonitorHeaderRepositoryCustom {
    fun findAllByMonitorId(monitorId: Long): List<MonitorHeader>

    fun deleteAllByMonitorId(monitorId: Long): Long
}
