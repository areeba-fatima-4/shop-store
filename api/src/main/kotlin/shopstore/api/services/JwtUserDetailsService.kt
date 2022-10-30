package shopstore.api.services

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import shopstore.api.models.JwtUser
import shopstore.api.repositories.UserRepository
import java.util.*
import javax.persistence.EntityNotFoundException


@Service
@Transactional
class JwtUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails =
        userRepository.findByEmailAddress(email)
            ?.let { JwtUser(
                it.id.toString(),
                it.firstName+"."+it.lastName,
                it.password,
                it.emailAddress,
                Date(), // change to user created date
                emptyList()
            ) }
            ?: throw EntityNotFoundException()

}