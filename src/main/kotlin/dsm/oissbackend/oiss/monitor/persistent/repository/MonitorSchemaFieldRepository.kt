package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorSchemaField
import org.springframework.data.jpa.repository.JpaRepository

interface MonitorSchemaFieldRepository: JpaRepository<MonitorSchemaField, Long>, MonitorSchemaFieldRepositoryCustom
