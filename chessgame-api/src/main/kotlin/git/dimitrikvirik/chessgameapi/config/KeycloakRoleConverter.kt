package git.dimitrikvirik.chessgameapi.config

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import java.util.stream.Collectors

class KeycloakRoleConverter :
    Converter<Jwt?, Collection<GrantedAuthority?>?> {
    override fun convert(source: Jwt): Collection<GrantedAuthority>? {
        val realmAccess = source.claims["realm_access"] as Map<String, Any>?
        if (realmAccess == null || realmAccess.isEmpty()) {
            return ArrayList()
        }
        val roles = (realmAccess["roles"] as List<String>?)!!.stream()
            .map { roleName: String -> "ROLE_$roleName" }
            .map { role: String? ->
                SimpleGrantedAuthority(
                    role
                )
            }.collect(Collectors.toList())
        return ArrayList<GrantedAuthority>(roles)
    }
}