package shopstore.api.repositories

import org.springframework.data.repository.CrudRepository
import shopstore.api.dtos.BaseParentDto
import shopstore.api.domain.AuditingEntityBaseKotlin
import shopstore.api.models.BaseModel
import java.util.*

interface BaseRepository<
        TDto : BaseParentDto<TModel>,
        TModel : BaseModel<TEntity>,
        TEntity : AuditingEntityBaseKotlin<TModel>> {
    fun create(model: TModel): TModel
    fun read(id : UUID) : TModel?
    fun update(model: TModel) : TModel
    fun delete(id: UUID)
    fun saveAll(models: List<TModel>) : List<TModel>
}

abstract class BaseRepositoryImpl<
        TDto : BaseParentDto<TModel>,
        TModel : BaseModel<TEntity>,
        TEntity : AuditingEntityBaseKotlin<TModel>,
        TRepository : CrudRepository<TEntity, UUID>> (
    protected val inner: TRepository
): BaseRepository<TDto, TModel, TEntity> {

    override fun create(model: TModel) =
        inner
            .findById(model.id)
            .orElse(null)
            ?.let { throw Exception() }
            ?: model.toEntity()
                .let(inner::save)
                .toModel()


    override fun delete(id: UUID) =
        inner
            .findById(id)
            .orElse(null)
            ?.let(inner::delete)
            ?: Unit

    override fun read(id: UUID): TModel? =
        inner
            .findById(id)
            .orElse(null)
            ?.toModel()

    override fun update(model: TModel): TModel =
        inner
            .findById(model.id)
            .orElse(null)
            ?.let { model.toEntity() }
            ?.let (inner::save)
            ?.toModel()
            ?:throw Exception()

    override fun saveAll(models: List<TModel>): List<TModel> =
        models
            .map {  it.toEntity() }
            .let(inner::saveAll)
            .map { it.toModel() }
}