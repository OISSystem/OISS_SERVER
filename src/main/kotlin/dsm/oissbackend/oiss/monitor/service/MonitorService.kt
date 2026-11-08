package dsm.oissbackend.oiss.monitor.service

import dsm.oissbackend.oiss.monitor.facade.MonitorFacade
import dsm.oissbackend.oiss.monitor.persistent.repository.MonitorRepository
import dsm.oissbackend.oiss.monitor.presentation.dto.request.CreateMonitorRequest
import dsm.oissbackend.oiss.monitor.presentation.dto.response.CreateMonitorResponse
import org.springframework.stereotype.Service

@Service
class MonitorService(
    private val monitorFacade: MonitorFacade
) {

    fun createMonitor(request: CreateMonitorRequest): CreateMonitorResponse {
        TODO()
    }
}