package turmalina.pweb3.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import turmalina.pweb3.model.enums.CategoriaAmostra;
import turmalina.pweb3.model.enums.CondicaoConservacao;
import turmalina.pweb3.model.enums.UnidadeMedida;

@Entity
@Table(name = "amostra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Amostra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_campo", nullable = false, unique = true, length = 30)
    private String codigoCampo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategoriaAmostra categoria;

    @Column(nullable = false, precision = 12, scale = 4)
    private BigDecimal quantidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UnidadeMedida unidade;

    @Column(name = "data_acondicionamento", nullable=false)
    private LocalDateTime dataAcondicionamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "condicao_conservacao", nullable = false, length = 20)
    private CondicaoConservacao condicaoConservacao;

    @Column(name = "material_perigoso", nullable = false)
    private boolean materialPerigoso;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] fotografia;

    @Column(length = 500)
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coleta_id", nullable = false)
    private Coleta coleta;
}