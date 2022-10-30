package shopstore.api.dtos

import shopstore.api.models.JwtRequestModel
import shopstore.api.models.JwtResponseModel
import java.util.Date

data class JwtResponseDto(
    val token: String,
    val identityToken: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val userId: Long,
    val creationDate: Date

) : BaseParentDto<JwtResponseModel>() {
    override fun toModel() =
        JwtResponseModel(
            token,
            identityToken,
            firstName,
            lastName,
            email,
            userId,
            creationDate
        )
}

data class JwtRequestDto(
    val email: String,
    val password: String
) : BaseParentDto<JwtRequestModel>() {
    override fun toModel() =
        JwtRequestModel(
            email,
            password
        )
}
