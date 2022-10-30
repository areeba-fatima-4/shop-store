package shopstore.api.services

import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import shopstore.api.dtos.JwtResponseDto
import shopstore.api.models.JwtUser
import shopstore.api.utils.JwtTokenUtil
import javax.transaction.Transactional

interface AuthenticationService {
    fun authenticate(
        email : String,
        password: String
    ) : JwtResponseDto

}

@Service
@Transactional
@Primary
class AuthenticationDtoServiceImpl(
    private val inner : AuthenticationServiceImpl
) : AuthenticationService {
    override fun authenticate(email: String, password: String): JwtResponseDto =
        inner.authenticate(email, password)

}

@Service
@Transactional
class AuthenticationServiceImpl(
    private val authenticationManager: AuthenticationManager,
    private val jwtUserDetailService: JwtUserDetailsService,
    private val jwtTokenUtil: JwtTokenUtil
) : AuthenticationService {
    override fun authenticate(email: String, password: String): JwtResponseDto =
        authenticationManager
            .authenticate(UsernamePasswordAuthenticationToken(email, password))
            .let { jwtUserDetailService.loadUserByUsername(email) as JwtUser }
            .let { jwtUser ->
                generateToken(jwtUser)
                    .let {
                        JwtResponseDto(
                            it,
                            it,
                            jwtUser.userName.split(".").first(),
                            jwtUser.userName.split(".").last(),
                            jwtUser.email,
                            jwtUser.id.toLong(),
                            jwtUser.creationDate
                            )
                    }
            }

    fun generateToken(jwtUser: JwtUser): String =
            jwtTokenUtil.generateToken(jwtUser)


}