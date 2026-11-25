package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.MonitorCredential
import org.springframework.data.jpa.repository.JpaRepository

interface MonitorCredentialRepository: JpaRepository<MonitorCredential, Long>, MonitorCredentialRepositoryCustom
