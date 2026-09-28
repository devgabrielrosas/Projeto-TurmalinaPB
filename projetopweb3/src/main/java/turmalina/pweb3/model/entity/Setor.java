package turmalina.pweb3.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import turmalina.pweb3.model.enums.CondicaoSetor;
import turmalina.pweb3.model.enums.NivelDificuldade;

@Entity
@Table(name = "setor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String denominacao;

    @Column(length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_dificuldade", nullable = false, length = 20)
    private NivelDificuldade nivelDificuldade;

    @Column(name = "profundidade_maxima", precision = 7, scale = 2)
    private BigDecimal profundidadeMaxima;

    @Column(name = "extensao_aproximada", precision = 10, scale = 2)
    private BigDecimal extensaoAproximada;

    @Column(name = "risco_inundacao", nullable = false)
    private Boolean riscoInundacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CondicaoSetor condicao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "caverna_id", nullable = false)
    private Caverna caverna;
}