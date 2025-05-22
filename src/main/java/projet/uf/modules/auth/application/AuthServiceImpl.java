package projet.uf.modules.auth.application;

import projet.uf.modules.auth.adapters.out.security.JwtService;
import projet.uf.modules.auth.application.ports.in.AuthCommandMapper;
import projet.uf.modules.auth.application.ports.in.LoginCommand;
import projet.uf.modules.auth.application.ports.in.RegisterCommand;
import projet.uf.modules.auth.application.ports.out.LoadUserPort;
import projet.uf.modules.auth.application.ports.out.PasswordEncoderPort;
import projet.uf.modules.auth.application.ports.out.SaveUserPort;
import projet.uf.modules.user.application.mapper.UserCommandMapper;
import projet.uf.modules.user.application.ports.in.CreateUserCommand;
import projet.uf.modules.user.domain.model.User;
import projet.uf.modules.auth.application.ports.in.AuthService;

import java.util.Map;

public class AuthServiceImpl implements AuthService
{
    private final PasswordEncoderPort passwordEncoder;
    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;
    private final JwtService jwtService;

    public AuthServiceImpl(PasswordEncoderPort passwordEncoder, LoadUserPort loadUserPort, SaveUserPort saveUserPort, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.loadUserPort = loadUserPort;
        this.saveUserPort = saveUserPort;
        this.jwtService = jwtService;
    }

    @Override
    public User register(RegisterCommand command) {
        if (loadUserPort.existsByEmail(command.email())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (!User.isStrongPassword(command.password())) {
            throw new IllegalArgumentException("Password not strong enough");
        }

        User user = AuthCommandMapper.fromCreateCommand(command, passwordEncoder.encode(command.password()));
        return saveUserPort.save(user);
    }

    @Override
    public String login(LoginCommand command) {
        User user = loadUserPort.getByEmail(command.email())
                .orElseThrow(() -> new IllegalArgumentException("Email not found or incorrect password"));

        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new IllegalArgumentException("Email not found or incorrect password");
        }

        Map<String, Object> claims = Map.of(
            "userId", user.getId(),
            "admin", user.isAdmin()
        );

        return jwtService.generateToken(user.getEmail(), claims);
    }
}
