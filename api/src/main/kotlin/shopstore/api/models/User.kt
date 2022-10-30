package shopstore.api.models

import shopstore.api.domain.UserEntity
import shopstore.api.domain.enums.UserType
import shopstore.api.dtos.UserDto
import java.util.UUID

data class User(
    override val id: UUID,
    val firstName: String,
    val lastName: String,
    val phone: String, //add regex for pak phone
    val emailAddress: String, //add regex
    val password: String,
    val active: Boolean,
    val type: UserType,
    val jwtToken: String
) : BaseCompleteModel<UserDto, UserEntity>() {

    init {
        validate()
    }
    override fun toEntity(): UserEntity =
        UserEntity(
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

    override fun toDto(): UserDto =
        UserDto(
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
