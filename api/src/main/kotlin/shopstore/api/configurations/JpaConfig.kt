package shopstore.api.configurations

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
class JpaConfig {

    @Bean
    fun auditorAware(): AuditorAware<Int> =
        AuditorAwareImpl()
}

class AuditorAwareImpl : AuditorAware<Int> {
    override fun getCurrentAuditor(): Optional<Int> {
        return Optional.of(0)
        // Can use Spring Security to return currently logged in user
        // return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()
    }
}