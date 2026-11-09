package dsm.oissbackend.oiss.monitor.presentation

import dsm.oissbackend.oiss.monitor.presentation.dto.request.CreateMonitorRequest
import dsm.oissbackend.oiss.monitor.presentation.dto.response.CreateMonitorResponse
import dsm.oissbackend.oiss.monitor.service.MonitorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/monitor")
@RestController
class MonitorController(
    private val monitorService: MonitorService
) {

    @PostMapping()
    fun createMonitor(
        @RequestBody request: CreateMonitorRequest
    ): ResponseEntity<CreateMonitorResponse> {
        return ResponseEntity.ok(
            monitorService.createMonitor(request)
        )
    }

    @DeleteMapping("/{monitorId}")
    fun deleteMonitor(@PathVariable monitorId: Long): ResponseEntity<Void> {
        monitorService.deleteMonitor(monitorId)
        return ResponseEntity.noContent().build()
    }

}