package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorCredential

interface MonitorCredentialRepositoryCustom {
    fun findByMonitorId(monitorId: Long): MonitorCredential?
}
