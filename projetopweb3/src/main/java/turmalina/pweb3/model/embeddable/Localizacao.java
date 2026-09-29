package turmalina.pweb3.model.embeddable;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import turmalina.pweb3.model.enums.Datum;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Localizacao {

    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Datum datum;

}
