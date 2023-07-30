package shopstore.api.domain

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import shopstore.api.domain.enums.UserType
import shopstore.api.models.User
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "users")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType::class)
data class UserEntity (
    @Id val id: UUID,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val emailAddress: String,
    val password: String?,
    val active: Boolean,
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    val type: UserType

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
        )
}