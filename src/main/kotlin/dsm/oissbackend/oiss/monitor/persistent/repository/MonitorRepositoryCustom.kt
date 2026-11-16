package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.Monitor
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.MonitorStatus

interface MonitorRepositoryCustom {
    fun findByMonitorId(monitorId: Long): Monitor?

    fun findAllByFilter(
        offset: Long,
        limit: Long,
        status: MonitorStatus?,
        provider: String?,
        keyword: String?,
    ): List<Monitor>
}
