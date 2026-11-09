package dsm.oissbackend.oiss.monitor.persistent.entity

import dsm.oissbackend.oiss.monitor.persistent.entity.enums.SchemaSource
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
import org.hibernate.annotations.CreationTimestamp
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
) {

    @Id
    val monitorId: Long = 0

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "DATETIME", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
}
