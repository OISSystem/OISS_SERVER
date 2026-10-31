package dsm.oissbackend.common.entity

import dsm.oissbackend.common.entity.enums.Status
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
abstract class SoftDeleteEntity: BaseEntity() {

    @Enumerated(EnumType.STRING)
    var status: Status = Status.ACTIVE

    var deletedAt: LocalDateTime? = null

    fun delete() {
        status = Status.DELETE
        deletedAt = LocalDateTime.now()
    }

    fun restore() {
        status = Status.ACTIVE
        deletedAt = null
    }
}