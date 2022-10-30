package shopstore.api.exceptions

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import shopstore.api.dtos.*

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(ex: Exception): ResponseEntity<ResponseDto<Unit>> =
        ResponseEntity(
            ResponseDto<Unit>()
                .makeFailedResponse(
                    Unit,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorDto().make(Error.INTERNAL_SERVER_ERROR)
                ),
            HttpStatus.OK
        )

    @ExceptionHandler(
        HttpMessageConversionException::class,
        MethodArgumentNotValidException::class,
        MissingRequestHeaderException::class,
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(ex: Exception): ResponseEntity<ResponseDto<Unit>> =
        ResponseEntity(
            ResponseDto<Unit>()
                .makeFailedResponse(
                    Unit,
                    HttpStatus.BAD_REQUEST,
                    ErrorDto().make(Error.BAD_REQUEST)
                ),
            HttpStatus.OK
        )

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    fun handleException(ex: HttpRequestMethodNotSupportedException): ResponseEntity<ResponseDto<Unit>> =
        ResponseEntity(
            ResponseDto<Unit>()
                .makeFailedResponse(
                    Unit,
                    HttpStatus.METHOD_NOT_ALLOWED,
                    ErrorDto().make(Error.METHOD_NOT_ALLOWED)
                ),
            HttpStatus.OK
        )

    @ExceptionHandler(AccessDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleException(ex: AccessDeniedException): ResponseEntity<ResponseDto<Unit>> =
        ResponseEntity(
            ResponseDto<Unit>()
                .makeFailedResponse(
                    Unit,
                    HttpStatus.FORBIDDEN,
                    ErrorDto().make(Error.FORBIDDEN)
                ),
            HttpStatus.OK
        )

    @ExceptionHandler(DataIntegrityViolationException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleException(ex: DataIntegrityViolationException): ResponseEntity<ResponseDto<Unit>> =
        ResponseEntity(
            ResponseDto<Unit>()
                .makeFailedResponse(
                    Unit,
                    HttpStatus.FORBIDDEN,
                    ErrorDto().make(Error.DATA_INTEGRITY_VIOLATED)
                ),
            HttpStatus.OK
        )

}