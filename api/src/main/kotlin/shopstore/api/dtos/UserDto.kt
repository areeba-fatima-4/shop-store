package shopstore.api.dtos

import shopstore.api.domain.enums.UserType
import shopstore.api.models.User
import java.util.*

data class UserDto(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val emailAddress: String, //add unique constraint
    val password: String, //add unique constraint
    val active: Boolean,
    val type: UserType,
    val jwtToken: String

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
            jwtToken
        )
}
