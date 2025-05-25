package projet.uf.modules.loof_characteristic.application.port.in;

import jakarta.validation.constraints.NotBlank;

public record CreateLoofCharacteristicCommand(
        @NotBlank(message = "Le code LOOF ne peut pas être vide")
        String code,
        @NotBlank(message = "Le nom de la caractéristique ne peut pas être vide")
        String name,
        String details){}