package andrew.korzhov.ecommerce.utils;

import org.springframework.security.core.Authentication;

public final class AuthUserUtil {

    public static long getUserId(Authentication auth) {
        String principal = (String) auth.getPrincipal();
        return Long.parseLong(principal);
    }

}
