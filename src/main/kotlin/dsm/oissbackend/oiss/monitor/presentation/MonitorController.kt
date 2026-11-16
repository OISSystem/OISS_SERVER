package dsm.oissbackend.oiss.monitor.presentation

import dsm.oissbackend.oiss.monitor.presentation.dto.request.CreateMonitorRequest
import dsm.oissbackend.oiss.monitor.presentation.dto.request.UpdateMonitorRequest
import dsm.oissbackend.oiss.monitor.presentation.dto.response.CreateMonitorResponse
import dsm.oissbackend.oiss.monitor.presentation.dto.response.GetMonitorConfigResponse
import dsm.oissbackend.oiss.monitor.presentation.dto.response.UpdateMonitorResponse
import dsm.oissbackend.oiss.monitor.service.MonitorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalTime

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

    @GetMapping("/{monitorId}/settings")
    fun getMonitorConfig(
        @PathVariable("monitorId") monitorId: Long,
    ): ResponseEntity<GetMonitorConfigResponse> {
        return ResponseEntity.ok(
            monitorService.getMonitorConfig(monitorId)
        )
    }

    @DeleteMapping( "/{monitorId}")
    fun deleteMonitor(@PathVariable monitorId: Long): ResponseEntity<Void> {
        monitorService.deleteMonitor(monitorId)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{monitorId}")
    fun updateMonitor(
        @PathVariable monitorId: Long,
        @RequestBody request: UpdateMonitorRequest
    ): ResponseEntity<UpdateMonitorResponse> {
        return ResponseEntity.ok(
            monitorService.updateMonitor(monitorId, request)
        )
    }
}