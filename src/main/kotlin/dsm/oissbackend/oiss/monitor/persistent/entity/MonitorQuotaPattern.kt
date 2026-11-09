package dsm.oissbackend.oiss.monitor.persistent.entity

import dsm.oissbackend.oiss.monitor.persistent.entity.enums.MatchType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId

@Entity
class MonitorQuotaPattern(

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id")
    val monitor: Monitor,

    @Enumerated(EnumType.STRING)
    @Column(name = "match_type", nullable = false)
    val matchType: MatchType,

    @Column(name = "pattern", columnDefinition = "VARCHAR(512)", nullable = false)
    val pattern: String,
) {

    @Id
    val monitorId: Long = 0
}