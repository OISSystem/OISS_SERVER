package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorQuotaPattern

interface MonitorQuotaPatternRepositoryCustom {
    fun findAllByMonitorId(monitorId: Long): List<MonitorQuotaPattern>

    fun deleteAllByMonitorId(monitorId: Long): Long
}
