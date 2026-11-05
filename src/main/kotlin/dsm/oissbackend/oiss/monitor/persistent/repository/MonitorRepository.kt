package dsm.oissbackend.oiss.monitor.persistent.repository

import dsm.oissbackend.oiss.monitor.persistent.entity.Monitor
import org.springframework.data.jpa.repository.JpaRepository

interface MonitorRepository: JpaRepository<Monitor, Long>, MonitorRepositoryCustom