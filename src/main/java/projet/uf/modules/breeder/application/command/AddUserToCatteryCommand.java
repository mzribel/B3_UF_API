package projet.uf.modules.breeder.application.command;

public record AddUserToCatteryCommand(
        Long userId,
        String userEmail
) {
}
