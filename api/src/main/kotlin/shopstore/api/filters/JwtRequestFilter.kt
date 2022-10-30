package shopstore.api.filters

import lombok.AllArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import shopstore.api.models.JwtUser
import shopstore.api.services.JwtUserDetailsService
import shopstore.api.utils.JwtTokenUtil
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
@Component
@AllArgsConstructor
class JwtRequestFilter(
    val jwtTokenUtil: JwtTokenUtil,
    val jwtUserDetailsService: JwtUserDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        SecurityContextHolder.getContext().authentication
            ?.let { getTokenFromRequest(request) }
            ?.let { token ->
                val email = jwtTokenUtil.getEmailFromToken(token)
                val jwtUser = jwtUserDetailsService.loadUserByUsername(email) as JwtUser
                if (jwtTokenUtil.validateToken(token, jwtUser))
                    updateSecurityContext(getAuthToken(jwtUser, request))
            } ?: throw Exception()

        filterChain.doFilter(request, response)
    }

    private fun updateSecurityContext(authToken: UsernamePasswordAuthenticationToken): UsernamePasswordAuthenticationToken {
        SecurityContextHolder.getContext().authentication = authToken
        return authToken
    }

    private fun getAuthToken(jwtUser: JwtUser, request: HttpServletRequest): UsernamePasswordAuthenticationToken {
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
            jwtUser,
            null,
            jwtUser.authoritiesList
        )
        usernamePasswordAuthenticationToken.details =
            WebAuthenticationDetailsSource().buildDetails(request)

        return usernamePasswordAuthenticationToken

    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? =
        request.getHeader("Authorization")
            ?.let {
                if (it.startsWith("Bearer"))
                    it.substring(7)
                it
            }

}