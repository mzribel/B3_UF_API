package projet.uf.modules.auth.application.ports.out;

public interface PasswordEncoderPort {
    String encode(String password);
    boolean matches(String password, String encodedPassword);
}
