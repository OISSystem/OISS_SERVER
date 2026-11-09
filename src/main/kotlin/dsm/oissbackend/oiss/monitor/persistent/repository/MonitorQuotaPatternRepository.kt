package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorQuotaPattern
import org.springframework.data.jpa.repository.JpaRepository

interface MonitorQuotaPatternRepository: JpaRepository<MonitorQuotaPattern, Long>, MonitorQuotaPatternRepositoryCustom
