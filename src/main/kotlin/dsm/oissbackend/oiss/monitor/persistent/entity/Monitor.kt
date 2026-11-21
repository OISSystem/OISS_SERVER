package dsm.oissbackend.oiss.monitor.persistent.entity

import dsm.oissbackend.common.entity.BaseEntity
import dsm.oissbackend.oiss.monitor.persistent.entity.enums.HttpMethod
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
    var name: String,

    @Column(name = "provider", columnDefinition = "VARCHAR(512)", nullable = false)
    var provider: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "http_method", nullable = false)
    var httpMethod: HttpMethod,

    @Column(name = "encrypted_url", columnDefinition = "VARCHAR(512)", nullable = false)
    var encryptedUrl: String,

    // POST일 때만 사용
    @Column(name = "request_body", columnDefinition = "TEXT")
    var requestBody: String?,

    @Column(name = "check_interval_sec", columnDefinition = "INT", nullable = false)
    var checkIntervalSeconds: Int,

    @Column(name = "failure_threshold", columnDefinition = "TINYINT", nullable = false)
    var failureThreshold: Int,

    @Column(name = "recovery_threshold", columnDefinition = "TINYINT", nullable = false)
    var recoveryThreshold: Int,

    @Column(name = "timeout_ms", columnDefinition = "INT", nullable = false)
    var timeoutMs: Int,

    @Column(name = "latency_warn_ms", columnDefinition = "INT", nullable = false)
    var latencyWarnMs: Int,

    @Column(name = "enabled", columnDefinition = "BOOLEAN", nullable = false)
    var enabled: Boolean,

) : BaseEntity() {

    fun update(
        name: String,
        provider: String,
        httpMethod: HttpMethod,
        encryptedUrl: String,
        requestBody: String?,
        checkIntervalSeconds: Int,
        failureThreshold: Int,
        recoveryThreshold: Int,
        timeoutMs: Int,
        latencyWarnMs: Int,
        enabled: Boolean,
    ) {
        this.name = name
        this.provider = provider
        this.httpMethod = httpMethod
        this.encryptedUrl = encryptedUrl
        this.requestBody = requestBody
        this.checkIntervalSeconds = checkIntervalSeconds
        this.failureThreshold = failureThreshold
        this.recoveryThreshold = recoveryThreshold
        this.timeoutMs = timeoutMs
        this.latencyWarnMs = latencyWarnMs
        this.enabled = enabled
    }
}
