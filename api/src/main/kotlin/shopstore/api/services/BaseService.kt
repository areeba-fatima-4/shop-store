package shopstore.api.services

import shopstore.api.dtos.BaseParentDto
import shopstore.api.domain.AuditingEntityBaseKotlin
import shopstore.api.models.BaseCompleteModel
import shopstore.api.models.BaseParentModel
import shopstore.api.repositories.BaseRepository
import java.util.*

interface BaseDtoService<TDto: BaseParentDto<TModel>,  TModel: BaseParentModel> {
    fun create(dto: TDto): TDto
    fun read(id: UUID): TDto
    fun update(dto: TDto): TDto
    fun delete(id: UUID)
}

interface BaseService<TDto: BaseParentDto<TModel>, TModel: BaseParentModel> {
    fun create(model: TModel): TModel
    fun read(id: UUID): TModel
    fun update(model: TModel): TModel
    fun delete(id: UUID)
}

abstract class BaseDtoServiceImpl<
        TDto: BaseParentDto<TModel>,
        TModel: BaseCompleteModel<TDto, TEntity >,
        TEntity: AuditingEntityBaseKotlin<TModel>,
        TService : BaseServiceImpl<TDto, TModel, TEntity, TRepository>,
        TRepository : BaseRepository<TDto, TModel, TEntity>> (
    protected val inner: TService
): BaseDtoService<TDto, TModel> {

    override fun create(dto: TDto) = inner.create(dto.toModel()).toDto()
    override fun read(id: UUID) = inner.read(id).toDto()
    override fun update(dto: TDto) = inner.update(dto.toModel()).toDto()
    override fun delete(id: UUID) = inner.delete(id)
}

abstract class BaseServiceImpl<
        TDto: BaseParentDto<TModel>,
        TModel : BaseCompleteModel<TDto, TEntity>,
        TEntity : AuditingEntityBaseKotlin<TModel>,
        TRepository : BaseRepository<TDto, TModel, TEntity>> (
    protected val repository: TRepository
) : BaseService<TDto, TModel> {

    override fun create(model: TModel) = repository.create(model)
    override fun read(id: UUID) = repository.read(id) ?: throw Exception()
    override fun update(model: TModel) = repository.update(model)
    override fun delete(id: UUID) = repository.delete(id)
}
