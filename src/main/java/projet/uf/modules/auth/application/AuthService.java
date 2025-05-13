package projet.uf.modules.auth.application;

import projet.uf.modules.auth.application.ports.out.LoadUserPort;
import projet.uf.modules.auth.application.ports.out.PasswordEncoderPort;
import projet.uf.modules.auth.application.ports.out.SaveUserPort;
import projet.uf.modules.user.application.mapper.UserCommandMapper;
import projet.uf.modules.user.application.ports.in.CreateUserCommand;
import projet.uf.modules.user.domain.model.User;
import projet.uf.modules.auth.application.ports.in.RegisterUseCase;

public class AuthService implements
        RegisterUseCase
{
    private final PasswordEncoderPort passwordEncoder;
    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;

    public AuthService(PasswordEncoderPort passwordEncoder, LoadUserPort loadUserPort, SaveUserPort saveUserPort) {
        this.passwordEncoder = passwordEncoder;
        this.loadUserPort = loadUserPort;
        this.saveUserPort = saveUserPort;
    }

    @Override
    public User register(CreateUserCommand command) {
        if (loadUserPort.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (!User.isStrongPassword(command.getPassword())) {
            throw new IllegalArgumentException("Password not strong enough");
        }

        User user = UserCommandMapper.fromCreateCommand(command, passwordEncoder.encode(command.getPassword()));
        return saveUserPort.save(user);
    }
}
