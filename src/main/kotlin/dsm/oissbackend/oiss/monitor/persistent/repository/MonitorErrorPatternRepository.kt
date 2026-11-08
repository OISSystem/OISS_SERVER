package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorErrorPattern
import org.springframework.data.jpa.repository.JpaRepository

interface MonitorErrorPatternRepository: JpaRepository<MonitorErrorPattern, Long>, MonitorErrorPatternRepositoryCustom
