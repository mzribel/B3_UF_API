package projet.uf.modules.breeder.application.dto;

import java.util.List;

public record UserCatteriesDto(
        List<CatteryDetailsDto> adminOf,
        List<CatteryDetailsDto> memberOf
        ) {}
