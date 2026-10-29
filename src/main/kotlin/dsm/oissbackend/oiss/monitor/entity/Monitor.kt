package dsm.oissbackend.oiss.monitor.entity

import dsm.oissbackend.entity.BaseEntity
import dsm.oissbackend.oiss.monitor.entity.enums.HttpMethod
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_monitor")
class Monitor(

    // user_id

    @Column(name = "name", columnDefinition = "VARCHAR(512)", nullable = false)
    val name: String,

    @Column(name = "provider", columnDefinition = "VARCHAR(512)", nullable = false)
    val provider: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "http_method", nullable = false)
    val httpMethod: HttpMethod,

    @Column(name = "url", columnDefinition = "VARCHAR(512)", nullable = false)
    val url: String,

    // POST일 때만 사용
    @Column(name = "request_body", columnDefinition = "TEXT")
    val requestBody: String?,

    @Column(name = "check_interval_sec", columnDefinition = "INT", nullable = false)
    val checkIntervalSeconds: Int,

    @Column(name = "failure_threshold", columnDefinition = "TINYINT", nullable = false)
    val failureThreshold: Int,

    @Column(name = "recovery_threshold", columnDefinition = "TINYINT", nullable = false)
    val recoveryThreshold: Int,

    @Column(name = "timeout_ms", columnDefinition = "INT", nullable = false)
    val timeoutMs: Int,

    @Column(name = "latency_warn_ms", columnDefinition = "INT", nullable = false)
    val latencyWarnMs: Int,

    @Column(name = "enabled", columnDefinition = "BOOLEAN", nullable = false)
    val enabled: Boolean,

) : BaseEntity() {

    companion object {

    }
}
