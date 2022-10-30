package shopstore.api.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import shopstore.api.domain.UserEntity
import shopstore.api.dtos.UserDto
import shopstore.api.models.User
import java.util.UUID

interface UserRepository : BaseRepository<UserDto, User, UserEntity> {
    fun findByEmailAddress(email: String) : User?
}

@Component
class UserRepositoryImpl(
    inner: UserEntityRepository
) : BaseRepositoryImpl<UserDto, User, UserEntity, UserEntityRepository>(inner),
    UserRepository {
    override fun findByEmailAddress(email: String) : User? =
        inner.findByEmailAddress(email)
            ?.let(UserEntity::toModel)

}

@Repository
interface UserEntityRepository : JpaRepository<UserEntity, UUID> {
    fun findByEmailAddress(email: String) : UserEntity?
}
