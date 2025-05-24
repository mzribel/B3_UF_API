package projet.uf.modules.auth.application;

import org.springframework.http.HttpStatus;
import projet.uf.modules.auth.application.ports.in.AuthCommandMapper;
import projet.uf.modules.auth.application.ports.in.LoginCommand;
import projet.uf.modules.auth.application.ports.in.RegisterCommand;
import projet.uf.modules.auth.application.ports.out.UserAccessPort;
import projet.uf.modules.auth.application.ports.out.PasswordEncoder;
import projet.uf.modules.auth.exception.UserAlreadyExistsException;
import projet.uf.modules.auth.exception.WeakPasswordException;
import projet.uf.modules.auth.exception.WrongCredentialsException;
import projet.uf.modules.user.domain.model.User;
import projet.uf.modules.auth.application.ports.in.AuthUseCase;

public class AuthService implements AuthUseCase
{
    private final PasswordEncoder passwordEncoder;
    private final UserAccessPort userPersistence;

    public AuthService(PasswordEncoder passwordEncoder, UserAccessPort userPersistence) {
        this.passwordEncoder = passwordEncoder;
        this.userPersistence = userPersistence;
    }

    @Override
    public User register(RegisterCommand command) {
        if (userPersistence.existsByEmail(command.email())) {
            throw new UserAlreadyExistsException("A user with this email already exists", HttpStatus.CONFLICT);
        }
        if (!User.isStrongPassword(command.password())) {
            throw new WeakPasswordException("Password is too weak (required at least 8 characters and 1 uppercase, 1 lowercase, 1 number, 1 special character)", HttpStatus.BAD_REQUEST);
        }

        User user = AuthCommandMapper.fromCreateCommand(command, passwordEncoder.encode(command.password()));
        return userPersistence.save(user);
    }

    @Override
    public User login(LoginCommand command) {
        User user = userPersistence.getByEmail(command.email())
                .orElseThrow(() -> new WrongCredentialsException("Email not found or incorrect password", HttpStatus.BAD_REQUEST));

        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new WrongCredentialsException("Email not found or incorrect password", HttpStatus.BAD_REQUEST);
        }

        return user;
    }
}
