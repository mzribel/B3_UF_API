package projet.uf.modules.auth.adapters.in.rest.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.CurrentUser;

@Component
public class CurrentUserProvider {
    public CurrentUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ApiException("Utilisateur non connecté", HttpStatus.UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CurrentUser userDetails) {
            return userDetails;
        }

        throw new ApiException("Impossible de récupérer l'identifiant utilisateur", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}