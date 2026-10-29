package dsm.oissbackend.oiss.monitor.entity

import dsm.oissbackend.oiss.monitor.entity.enums.CredentialLocation
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
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "tbl_monitor_credential")
class MonitorCredential(

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id")
    val monitor: Monitor,

    @Enumerated(EnumType.STRING)
    @Column(name = "location", nullable = false)
    val location: CredentialLocation,

    @Column(name = "param_name", columnDefinition = "VARCHAR(100)", nullable = false)
    val paramName: String,

    // AES-GCM 암호문
    @Column(name = "encrypted_value", columnDefinition = "VARBINARY(512)", nullable = false)
    val encryptedValue: ByteArray,

    // 마스터키 식별자
    @Column(name = "key_id", columnDefinition = "VARCHAR(64)", nullable = false)
    val keyId: String,

    // 마스킹 표시용
    @Column(name = "last4", columnDefinition = "CHAR(4)", nullable = false)
    val last4: String,

) {

    @Id
    val monitorId: Long = 0

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
}
