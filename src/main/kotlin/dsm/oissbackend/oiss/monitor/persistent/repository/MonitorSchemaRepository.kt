package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorSchema
import org.springframework.data.jpa.repository.JpaRepository

interface MonitorSchemaRepository: JpaRepository<MonitorSchema, Long>, MonitorSchemaRepositoryCustom
