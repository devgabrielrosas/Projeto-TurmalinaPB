package turmalina.pweb3.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import turmalina.pweb3.model.enums.SituacaoOperacionalEquipamento;
import turmalina.pweb3.model.enums.TipoEquipamento;

@Entity
@Table(name = "equipamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_patrimonial", nullable = false, unique = true, length = 30)
    private String codigoPatrimonial;

    @Column(nullable = false, length = 100)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoEquipamento tipo;

    @Column(length = 60)
    private String fabricante;

    @Column(name = "valor_aquisicao", precision = 12, scale = 2)
    private BigDecimal valorAquisicao;

    @Column(name = "data_compra")
    private LocalDate dataCompra;

    @Column(name = "data_ultima_manutencao")
    private LocalDate dataUltimaManutencao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SituacaoOperacionalEquipamento situacao;

    @Column(name = "exige_calibracao", nullable = false)
    private Boolean exigeCalibracao;
}