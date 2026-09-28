package turmalina.pweb3.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import turmalina.pweb3.model.enums.NivelCertificado;

@Entity 
@Table(name = "GuiaEspeleologia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuiaEspeleologia extends Pessoa {
    private String numeroCredenciamento;
    private LocalDate validadeCertificado;
    private int expedicoesConcluidas;

    @Enumerated(EnumType.STRING)
    private NivelCertificado nivelCertificado;
}
