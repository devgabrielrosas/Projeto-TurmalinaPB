package turmalina.pweb3.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import turmalina.pweb3.model.enums.Titulacao;

@Entity 
@Table(name = "pesquisador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pesquisador extends Pessoa {

    @Column(nullable=false)
    private String registroInstitucional;
    private String areaPrincipal;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorDiarioBolsa; 

    @Enumerated(EnumType.STRING)
    private Titulacao titulacao;

}
