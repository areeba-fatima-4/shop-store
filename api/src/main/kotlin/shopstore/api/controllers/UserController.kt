package shopstore.api.controllers

import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import shopstore.api.dtos.ResponseDto
import shopstore.api.dtos.UserSignUpRequestDto
import shopstore.api.dtos.makeSuccessResponse
import shopstore.api.dtos.toUserDto
import shopstore.api.services.UserDtoService
import javax.validation.Valid

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val service: UserDtoService,
    private val bcryptEncoder: PasswordEncoder
) : BaseController() {

    @PostMapping("/signUp")
    fun signUp(@RequestBody @Valid request: UserSignUpRequestDto
    ) =
        service.create(request.toUserDto())
            .let { ResponseDto<Void>().makeSuccessResponse() }


}