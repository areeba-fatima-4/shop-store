package shopstore.api.controllers

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import shopstore.api.dtos.JwtRequestDto
import shopstore.api.dtos.JwtResponseDto
import shopstore.api.dtos.ResponseDto
import shopstore.api.dtos.makeSuccessResponse
import shopstore.api.services.AuthenticationService

@RestController
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping( "/v1/authenticate")
    fun authenticate(
        @RequestBody request: JwtRequestDto
    ) :  ResponseDto<JwtResponseDto> =
        authenticationService.authenticate(request.email, request.password)
            .let { ResponseDto<JwtResponseDto>().makeSuccessResponse(it)}



}