package shopstore.api.dtos

import org.springframework.security.crypto.password.PasswordEncoder
import shopstore.api.domain.enums.UserType
import shopstore.api.models.User
import java.util.*
import javax.persistence.EnumType
import javax.persistence.Enumerated

data class UserDto(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val phone: String, //add regex
    val emailAddress: String, //add unique constraint
    val password: String?, //add unique constraint
    val active: Boolean,
    @Enumerated(EnumType.STRING)
    val type: UserType,

) : BaseParentDto<User>() {
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

data class UserSignUpRequestDto(
    val firstName: String,
    val lastName: String,
    val emailAddress: String,
    val phone: String,
    val type: UserType
)

fun UserSignUpRequestDto.toUserDto() =
        UserDto(
            UUID.randomUUID(),
            firstName,
            lastName,
            phone,
            emailAddress,
            null,
            false,
            type
        )