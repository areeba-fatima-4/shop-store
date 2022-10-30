package shopstore.api.domain

import shopstore.api.domain.enums.UserType
import shopstore.api.models.User
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class UserEntity (
    @Id val id: UUID,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val emailAddress: String,
    val password: String,
    val active: Boolean,
    val type: UserType,
    val jwtToken: String

    ) : AuditingEntityBaseKotlin<User>() {
    override fun toModel() =
        User(
            id,
            firstName,
            lastName,
            phone,
            emailAddress,
            password,
            active,
            type,
            jwtToken
        )
}