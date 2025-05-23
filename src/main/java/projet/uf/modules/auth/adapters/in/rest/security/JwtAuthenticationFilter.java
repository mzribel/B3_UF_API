package projet.uf.modules.auth.adapters.in.rest.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import projet.uf.modules.auth.adapters.out.security.HeaderAuthenticationTokenAdapter;
import projet.uf.modules.auth.adapters.out.security.JwtService;
import projet.uf.modules.auth.application.model.CurrentUser;
import projet.uf.modules.auth.application.ports.out.LoadUserPort;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final LoadUserPort loadUserPort;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        final String email;

        try {
            email = jwtService.extractUsername(token);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        loadUserPort.getByEmail(email).ifPresent(user -> {
            CurrentUser currentUser = new CurrentUser(user.getId(), user.isAdmin());
            Authentication auth = new HeaderAuthenticationTokenAdapter(currentUser);
            SecurityContextHolder.getContext().setAuthentication(auth);
        });

        filterChain.doFilter(request, response);
    }
}
