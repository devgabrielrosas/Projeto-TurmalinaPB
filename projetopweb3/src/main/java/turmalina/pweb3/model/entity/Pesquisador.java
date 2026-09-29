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

    @Column(name = "registro_institucional", nullable = false, length = 30)
    private String registroInstitucional;

    @Column(name = "area_principal", nullable = false, length = 100)
    private String areaPrincipal;

    @Column(name = "valor_diario_bolsa", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorDiarioBolsa; 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Titulacao titulacao;

}
