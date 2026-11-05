package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.Monitor

interface MonitorRepositoryCustom {
    fun findByMonitorId(monitorId: Long): Monitor?
}