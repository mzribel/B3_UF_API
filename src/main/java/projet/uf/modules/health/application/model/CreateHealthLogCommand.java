package projet.uf.modules.health.application.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateHealthLogCommand {
    @NotNull
    private Long catId;
    
    private BigDecimal weightInGrams;
    private BigDecimal temperatureInCelsius;
    private String appetite;
    private String hydratation;
    private String behavior;
    private String stoolQuality;
    private String urineObservations;
    private String notes;
}