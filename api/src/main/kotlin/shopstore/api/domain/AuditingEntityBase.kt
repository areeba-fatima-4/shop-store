package shopstore.api.domain

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import shopstore.api.models.BaseParentModel
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AuditingEntityBase : EntityBase {
    @CreatedDate var createdDate: LocalDateTime = LocalDateTime.now()
    @LastModifiedDate var modifiedDate: LocalDateTime = LocalDateTime.now()
    @CreatedBy var createdBy: Int = -1
    @LastModifiedBy var modifiedBy: Int = -1
}

abstract class AuditingEntityBaseKotlin<TModel : BaseParentModel> :
        AuditingEntityBase(),
        EntityWithoutAuditing<TModel>

interface EntityWithoutAuditing<TModel: BaseParentModel> : EntityBase {
    fun toModel() : TModel
}

interface EntityBase