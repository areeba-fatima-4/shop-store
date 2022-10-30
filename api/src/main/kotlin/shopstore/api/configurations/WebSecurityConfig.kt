package shopstore.api.configurations

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import shopstore.api.filters.JwtRequestFilter
import java.io.Serializable
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    val jwtRequestFilter: JwtRequestFilter
) {

    @Value("\${anonymous.urls}")
    private val anonymousUrls : Array<String> = arrayOf("")

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(
        authConfig: AuthenticationConfiguration
    ): AuthenticationManager =
         authConfig.authenticationManager

    @Bean
    fun passwordEncoder() : PasswordEncoder =
        BCryptPasswordEncoder()

    @Bean
    @Throws(Exception::class)
    fun  filterChain(httpSecurity : HttpSecurity): SecurityFilterChain {
        httpSecurity
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/authenticate")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
        return httpSecurity.build()
    }

}

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint, Serializable {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
    }

}