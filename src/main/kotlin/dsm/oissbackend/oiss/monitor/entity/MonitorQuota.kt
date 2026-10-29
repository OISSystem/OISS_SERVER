package dsm.oissbackend.oiss.monitor.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalTime

@Entity
@Table(name = "tbl_monitor_quota")
class MonitorQuota(

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id")
    val monitor: Monitor,

    @Column(name = "daily_limit", columnDefinition = "INT", nullable = false)
    val dailyLimit: Int,

    @Column(name = "cost_per_request", columnDefinition = "INT", nullable = false)
    val costPerRequest: Int,

    @Column(name = "reset_time", columnDefinition = "TIME", nullable = false)
    val resetTime: LocalTime,

    @Column(name = "reset_timezone", columnDefinition = "VARCHAR(50)", nullable = false)
    val resetTimezone: String = "Asia/Seoul",

) {

    @Id
    val monitorId: Long = 0
}
