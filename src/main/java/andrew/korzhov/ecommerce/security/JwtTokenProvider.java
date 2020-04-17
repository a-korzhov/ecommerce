package andrew.korzhov.ecommerce.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public interface JwtTokenProvider {

    String createToken(String username, Set<? extends GrantedAuthority> roles);

    Authentication getAuthentication(String token);

    String resolveToken(String bearerToken);
}
