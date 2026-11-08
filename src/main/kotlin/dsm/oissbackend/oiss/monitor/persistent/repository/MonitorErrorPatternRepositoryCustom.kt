package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorErrorPattern

interface MonitorErrorPatternRepositoryCustom {
    fun findAllByMonitorId(monitorId: Long): List<MonitorErrorPattern>

    fun deleteAllByMonitorId(monitorId: Long): Long
}
