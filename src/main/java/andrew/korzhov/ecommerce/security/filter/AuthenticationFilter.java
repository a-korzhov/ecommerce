package andrew.korzhov.ecommerce.security.filter;

import andrew.korzhov.ecommerce.security.JwtTokenProvider;
import andrew.korzhov.ecommerce.security.model.JwtUser;
import andrew.korzhov.ecommerce.web.dto.AuthRequestUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationFilter(JwtTokenProvider jwtTokenProvider,
                                AuthenticationManager authenticationManager) {
        this.setAuthenticationManager(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /*
        Read user from JSON login request(username and password) and
        create Authentication object that will be authenticated by AuthenticationManager
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) {
        AuthRequestUser user = new ObjectMapper()
                .readValue(request.getInputStream(), AuthRequestUser.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                ));
    }

    /*
        Taking Authentication(user form SecurityContext) and get principal as JwtUser.
        Then create token by username and authorities of user as Role.
        Then add token to Authorization header.
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) {
        JwtUser user = (JwtUser) authResult.getPrincipal();
        String token = jwtTokenProvider.createToken(user.getId(), new HashSet<>(user.getAuthorities()));
        response.addHeader("Authorization", "Bearer_" + token);
    }

}
