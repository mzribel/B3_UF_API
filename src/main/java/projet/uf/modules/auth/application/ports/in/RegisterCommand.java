package projet.uf.modules.auth.application.ports.in;

public record RegisterCommand(String email, String password, String displayName, boolean isAdmin) {}
