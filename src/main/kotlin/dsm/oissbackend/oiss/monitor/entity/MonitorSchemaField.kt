package dsm.oissbackend.oiss.monitor.entity

import dsm.oissbackend.oiss.monitor.entity.enums.SchemaFieldType
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
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "tbl_monitor_schema_field",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_schema_field_monitor_path", columnNames = ["monitor_id", "path"])
    ]
)
class MonitorSchemaField(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id", nullable = false)
    val schema: MonitorSchema,

    // ex) response.body.items.item[].pm10Value
    @Column(name = "path", columnDefinition = "VARCHAR(512)", nullable = false)
    val path: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    val type: SchemaFieldType,

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT", updatable = false)
    val id: Long = 0
}
