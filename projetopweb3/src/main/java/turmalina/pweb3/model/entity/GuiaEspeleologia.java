package turmalina.pweb3.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
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
@Table(name = "guia_espeleologia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuiaEspeleologia extends Pessoa {

    @Column(nullable=false, name="numero_credenciamento")
    private String numeroCredenciamento;

    @Column (name="validade_certificado")
    private LocalDate validadeCertificado;

    @Column (name="expedicoes_concluidas")
    private int expedicoesConcluidas;

    @Enumerated(EnumType.STRING)
    private NivelCertificado nivelCertificado;
}
