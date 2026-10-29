package dsm.oissbackend.oiss.monitor.entity

import dsm.oissbackend.oiss.monitor.entity.enums.MatchType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_monitor_error_pattern")
class MonitorErrorPattern(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id", nullable = false)
    val monitor: Monitor,

    @Enumerated(EnumType.STRING)
    @Column(name = "match_type", nullable = false)
    val matchType: MatchType,

    @Column(name = "pattern", columnDefinition = "VARCHAR(512)", nullable = false)
    val pattern: String,

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT", updatable = false)
    val id: Long = 0
}
