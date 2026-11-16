package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorHeader
import org.springframework.data.jpa.repository.JpaRepository

interface MonitorHeaderRepository: JpaRepository<MonitorHeader, Long>, MonitorHeaderRepositoryCustom
