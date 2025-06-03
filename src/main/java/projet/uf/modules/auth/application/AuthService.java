package projet.uf.modules.auth.application;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.modules.auth.adapters.in.rest.dto.AuthenticatedUserDto;
import projet.uf.modules.auth.adapters.out.security.JwtService;
import projet.uf.modules.auth.application.ports.in.LoginCommand;
import projet.uf.modules.auth.application.ports.in.RegisterCommand;
import projet.uf.modules.auth.application.ports.out.PasswordEncoder;
import projet.uf.modules.auth.exception.UserAlreadyExistsException;
import projet.uf.modules.auth.exception.WeakPasswordException;
import projet.uf.modules.auth.exception.WrongCredentialsException;
import projet.uf.modules.user.application.dto.UserDto;
import projet.uf.modules.user.application.port.out.UserPersistencePort;
import projet.uf.modules.user.domain.model.User;
import projet.uf.modules.auth.application.ports.in.AuthUseCase;

@AllArgsConstructor
public class AuthService implements AuthUseCase
{
    private final PasswordEncoder passwordEncoder;
    private final UserPersistencePort userPersistencePort;
    final JwtService jwtService;

    @Override
    public AuthenticatedUserDto register(RegisterCommand command) {
        if (userPersistencePort.existsByEmail(command.email())) {
            throw new UserAlreadyExistsException("Un utilisateur avec cette adresse email existe déjà", HttpStatus.CONFLICT);
        }
        if (!User.isStrongPassword(command.password())) {
            throw new WeakPasswordException("Le mot de passe est trop faible (au moins 8 caractères dont 1 majuscule, 1 minuscule, 1 chiffre et 1 caractère spécial)", HttpStatus.BAD_REQUEST);
        }

        User user = RegisterCommand.toModel(command, passwordEncoder.encode(command.password()));
        User saved = userPersistencePort.save(user);

        return new AuthenticatedUserDto(
                UserDto.fromUser(saved),
                jwtService.generateToken(saved)
        );
    }

    @Override
    public AuthenticatedUserDto login(LoginCommand command) {
        User user = userPersistencePort.getByEmail(command.email())
                .orElseThrow(() -> new WrongCredentialsException("Email ou mot de passe invalide", HttpStatus.BAD_REQUEST));

        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new WrongCredentialsException("Email ou mot de passe invalide", HttpStatus.BAD_REQUEST);
        }

        return new AuthenticatedUserDto(
                UserDto.fromUser(user),
                jwtService.generateToken(user)
        );
    }
}
