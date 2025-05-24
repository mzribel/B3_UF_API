package projet.uf.modules.auth.adapters.out.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import projet.uf.modules.auth.domain.model.CurrentUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class HeaderAuthenticationToken extends AbstractAuthenticationToken {

    private final CurrentUser principal;

    public HeaderAuthenticationToken(CurrentUser principal) {
        super(getAuthorities(principal));
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

//    @Override
//    public String getName() {
//        return String.valueOf(principal.userId());
//    }

    private static Collection<GrantedAuthority> getAuthorities(CurrentUser user) {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("USER"));
        if (user.isAdmin()) {
            roles.add(new SimpleGrantedAuthority("ADMIN"));
        }
        return roles;
    }
}
