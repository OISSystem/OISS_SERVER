package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorJsonPathRule
import org.springframework.data.jpa.repository.JpaRepository

interface MonitorJsonPathRuleRepository: JpaRepository<MonitorJsonPathRule, Long>, MonitorJsonPathRuleRepositoryCustom
