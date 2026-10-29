package dsm.oissbackend.oiss.monitor.entity

import dsm.oissbackend.oiss.monitor.entity.enums.JsonPathOperator
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
@Table(name = "tbl_monitor_jsonpath_rule")
class MonitorJsonPathRule(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id", nullable = false)
    val monitor: Monitor,

    // ex) $.response.header.resultCode
    @Column(name = "path", columnDefinition = "VARCHAR(512)", nullable = false)
    val path: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "operator", nullable = false)
    val operator: JsonPathOperator,

    // operator별로 해석, EXISTS는 값 없음
    @Column(name = "expected", columnDefinition = "VARCHAR(512)")
    val expected: String?,

    @Column(name = "sort_order", columnDefinition = "SMALLINT", nullable = false)
    val sortOrder: Int,

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT", updatable = false)
    val id: Long = 0
}
