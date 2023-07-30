package shopstore.api.dtos

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus

data class ResponseDto<T>(
    @JsonIgnore
    val status: HttpStatus,
    val data: T?,
    val error: ErrorDto?,
    val success: Boolean
) {

    constructor() : this(HttpStatus.OK,null, null, true)
}

fun <T> ResponseDto<T>.makeSuccessResponse(data: T) =
    this.copy(
        data = data,
        status = HttpStatus.OK,
        success = true
    )

fun <T> ResponseDto<T>.makeSuccessResponse() =
    this.copy(
        status = HttpStatus.OK,
        success = true
    )


fun <T> ResponseDto<T>.makeSuccessResponse(data: T, status: HttpStatus) =
    this.copy(
        data = data,
        status = status,
        success = true
    )

fun <T> ResponseDto<T>.makeFailedResponse(data: T, status: HttpStatus, error: ErrorDto) =
    this.copy(
        data = data,
        status = status,
        success = false,
        error = error
    )