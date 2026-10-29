package dsm.oissbackend.oiss.monitor.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_monitor_header")
class MonitorHeader(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id", nullable = false)
    val monitor: Monitor,

    @Column(name = "name", columnDefinition = "VARCHAR(255)", nullable = false)
    val name: String,

    @Column(name = "value", columnDefinition = "VARCHAR(1024)", nullable = false)
    val value: String,

    @Column(name = "sort_order", columnDefinition = "SMALLINT", nullable = false)
    val sortOrder: Int,

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT", updatable = false)
    val id: Long = 0
}
