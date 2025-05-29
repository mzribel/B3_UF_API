package projet.uf.modules.breeder.application.model;

import java.util.List;

public record UserCatteries(
        List<CatteryDetails> adminOf,
        List<CatteryDetails> memberOf
        ) {
}
