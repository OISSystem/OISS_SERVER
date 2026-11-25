package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorQuota
import org.springframework.data.jpa.repository.JpaRepository

interface MonitorQuotaRepository: JpaRepository<MonitorQuota, Long>, MonitorQuotaRepositoryCustom
