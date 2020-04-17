package andrew.korzhov.ecommerce.security;

import andrew.korzhov.ecommerce.security.model.JwtUser;
import andrew.korzhov.ecommerce.security.model.Role;
import andrew.korzhov.ecommerce.security.model.Status;
import andrew.korzhov.ecommerce.security.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtUserFactory {

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                mapToGrantedAuthority(user.getRoles()),
                user.getStatus().equals(Status.ACTIVE)
        );
    }


    private static List<GrantedAuthority> mapToGrantedAuthority(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

}
