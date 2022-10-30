package shopstore.api.dtos

data class ErrorDto(
    val code: String,
    val message: String,
    val details: String
) {
    constructor() : this("","","")
}

fun ErrorDto.make(error : Error) =
    this.copy(
        code = error.code,
        message = error.message
    )


enum class Error(val code: String, val message: String) {
    INTERNAL_SERVER_ERROR("E-500", "Something went wrong!"),
    BAD_REQUEST("E-400", "Bad request"),
    DATA_INTEGRITY_VIOLATED("E-1", "Data integrity violated"),
    FORBIDDEN("E-2", "Forbidden"),
    METHOD_NOT_ALLOWED("E-3", "Method not allowed")
}
