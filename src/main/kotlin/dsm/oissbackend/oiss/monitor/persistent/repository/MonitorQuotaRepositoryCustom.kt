package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorQuota

interface MonitorQuotaRepositoryCustom {
    fun findByMonitorId(monitorId: Long): MonitorQuota?
}
