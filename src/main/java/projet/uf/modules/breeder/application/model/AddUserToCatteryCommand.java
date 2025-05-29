package projet.uf.modules.breeder.application.model;

public record AddUserToCatteryCommand(
        Long userId,
        String userEmail
) {
}
