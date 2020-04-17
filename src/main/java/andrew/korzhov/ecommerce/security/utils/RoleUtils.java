package andrew.korzhov.ecommerce.security.utils;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RoleUtils {

    public static List<String> getRoleNames(Set<? extends GrantedAuthority> userRoles) {
        List<String> result = new ArrayList<>();

        userRoles.forEach(role ->
                result.add(role.getAuthority()));

        return result;
    }

}
