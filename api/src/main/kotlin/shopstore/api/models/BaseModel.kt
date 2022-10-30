package shopstore.api.models

import com.fasterxml.jackson.annotation.JsonIgnore
import shopstore.api.dtos.DtoBase
import shopstore.api.domain.EntityBase
import java.util.UUID
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.validation.Validation
import javax.validation.ValidatorFactory

abstract class BaseParentModel : ModelBase {
    companion object {
        val validationFactory: ValidatorFactory =
            Validation.buildDefaultValidatorFactory()
    }

    protected fun validate() {
        val violations = getViolations()
        if (violations.any())
            throw ConstraintViolationException(violations)
    }

    @JsonIgnore
    fun getViolations(): Set<ConstraintViolation<BaseParentModel>> =
        validationFactory
            .validator
            .validate(this)
}

abstract class BaseModel<TEntity: EntityBase> : BaseParentModel() {
    abstract val id: UUID
    abstract fun toEntity(): TEntity
}

abstract class BaseCompleteModel<TDto: DtoBase, TEntity: EntityBase> :
    BaseModel<TEntity>(),
    ModelWithoutEntity<TDto>

interface ModelWithoutEntity<TDto: DtoBase> : ModelBase {
   fun toDto() : TDto
}

interface ModelBase