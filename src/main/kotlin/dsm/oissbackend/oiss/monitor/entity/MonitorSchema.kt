package dsm.oissbackend.oiss.monitor.entity

import dsm.oissbackend.oiss.monitor.entity.enums.SchemaSource
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "tbl_monitor_schema")
class MonitorSchema(

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id")
    val monitor: Monitor,

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    val source: SchemaSource,

    @Column(name = "generated_at", nullable = false)
    val generatedAt: LocalDateTime,

) {

    @Id
    val monitorId: Long = 0
}
