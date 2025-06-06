package projet.uf.modules.auth.infrastructure.configuration;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import projet.uf.modules.auth.adapters.in.rest.security.JwtAuthenticationFilter;
import projet.uf.modules.auth.adapters.out.security.JwtService;
import projet.uf.modules.user.application.port.out.UserPersistencePort;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class AuthSecurityConfig {

    private final UserPersistencePort userPersistencePort;
    private final JwtService jwtService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(userPersistencePort, jwtService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
//             .cors(cors -> cors
//                 .configurationSource(request -> {
//                     var corsConfig = new org.springframework.web.cors.CorsConfiguration();
//                     corsConfig.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:8081"));
//                     corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
//                     corsConfig.setAllowedHeaders(List.of("*"));
//                     corsConfig.setAllowCredentials(true);
//                     return corsConfig;
//                 })
//             )
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/auth/**", "/error").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated())
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(e -> e.authenticationEntryPoint((req, res, ex) ->
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")))
            .build();
    }
}
