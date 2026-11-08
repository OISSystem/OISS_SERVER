package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorJsonPathRule

interface MonitorJsonPathRuleRepositoryCustom {
    fun findAllByMonitorId(monitorId: Long): List<MonitorJsonPathRule>

    fun deleteAllByMonitorId(monitorId: Long): Long
}
