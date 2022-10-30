package shopstore.api.dtos

import shopstore.api.models.ModelBase

abstract class BaseParentDto<TModel : ModelBase> : DtoBase {
    abstract fun toModel() : TModel
}

interface DtoBase