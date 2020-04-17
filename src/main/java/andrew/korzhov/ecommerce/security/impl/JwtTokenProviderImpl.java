package andrew.korzhov.ecommerce.security.impl;

import andrew.korzhov.ecommerce.security.JwtAuthenticationException;
import andrew.korzhov.ecommerce.security.JwtTokenProvider;
import andrew.korzhov.ecommerce.security.utils.RoleUtils;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProviderImpl implements JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    /*
        Encode secret key from env.
     */
    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    /*
        Build token by username, roles, secret key
        and time of validity in milliseconds.
     */
    @Override
    public String createToken(String username, Set<? extends GrantedAuthority> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("aud", RoleUtils.getRoleNames(roles));

        Date now = new Date();
        Date validity = new Date(now.getTime() + (validityInMilliseconds * 24));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /*
        Identify token. Check username and roles to return Authentication.
     */
    @Override
    public Authentication getAuthentication(String token) {
        Jws<Claims> claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }

        if (!claims.getBody().getExpiration().before(new Date())) {
            Claims body = claims.getBody();
            return new UsernamePasswordAuthenticationToken(
                    token,
                    null,
                    parseRoles(body.getAudience().substring(7)).stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList())
            );
        } else return null;
    }


    /*
        If request exists token with Authorization header and
        starts with 'Bearer_', then cut 'Bearer_' and
        return clean token.
     */
    @Override
    public String resolveToken(String bearerToken) {
        return (bearerToken != null && bearerToken.startsWith("Bearer_"))
                ? bearerToken.substring(7)
                : null;
    }

    private List<String> parseRoles(String aud) {
        String[] roles = aud.replaceAll("[\\[\\]\"]", "").split(", ");
        return new ArrayList<>(Arrays.asList(roles));
    }


}
