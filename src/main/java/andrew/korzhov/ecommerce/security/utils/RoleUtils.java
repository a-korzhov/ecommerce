package andrew.korzhov.ecommerce.security.utils;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleUtils {

    public static List<String> getRoleNames(Set<? extends GrantedAuthority> userRoles) {
        return userRoles.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

}
