package shopstore.api.models

import java.util.*

data class JwtResponseModel(
    val token: String,
    val identityToken: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val userId: Long,
    val creationDate: Date
) : BaseParentModel()

data class JwtRequestModel(
    val email: String,
    val password: String
) : BaseParentModel()
