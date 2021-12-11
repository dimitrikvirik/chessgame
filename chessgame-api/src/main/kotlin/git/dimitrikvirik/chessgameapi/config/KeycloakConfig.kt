package git.dimitrikvirik.chessgameapi.config

import de.codecentric.boot.admin.server.domain.entities.Instance
import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.adapters.KeycloakConfigResolver
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties
import org.keycloak.adapters.springsecurity.KeycloakConfiguration
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.session.SessionRegistryImpl

@KeycloakConfiguration
@EnableConfigurationProperties(
    KeycloakSpringBootProperties::class
)
class KeycloakConfig {
    @Value("\${keycloak.auth-server-url}")
    private val serverUrl: String? = null

    @Value("\${auth.admin.username}")
    private val username: String? = null

    @Value("\${auth.admin.password}")
    private val password: String? = null

    @Bean
    fun keycloakBearerAuthHeaderProvider(keycloak: Keycloak): HttpHeadersProvider {
        return HttpHeadersProvider { app: Instance? ->
            val accessToken = keycloak.tokenManager().accessTokenString
            val headers = HttpHeaders()
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
            headers
        }
    }

    @Bean
    fun keycloak(): Keycloak {
        return KeycloakBuilder
            .builder()
            .serverUrl(serverUrl)
            .realm("master")
            .username(username)
            .password(password)
            .clientId("admin-cli")
            .resteasyClient(ResteasyClientBuilder().connectionPoolSize(10).build())
            .build()
    }

    @Bean
    fun keycloakConfigResolver(): KeycloakConfigResolver {
        return KeycloakSpringBootConfigResolver()
    }

    @Bean
    protected fun buildSessionRegistry(): SessionRegistry {
        return SessionRegistryImpl()
    }
}