package shopstore.api.models

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.Id
import kotlin.collections.Collection

data class JwtUser(
    @Id val id: String,
    val userName: String,
    val passWord: String,
    val email: String,
    val creationDate: Date,
    val authoritiesList: List<GrantedAuthority>
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> = authoritiesList
    override fun getPassword(): String = passWord
    override fun getUsername(): String = userName
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}
