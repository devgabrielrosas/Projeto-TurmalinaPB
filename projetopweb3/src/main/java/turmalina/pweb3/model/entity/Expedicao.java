package turmalina.pweb3.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;


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
import turmalina.pweb3.model.enums.SituacaoExpedicao;

@Entity 
@Table(name="Expedicao")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 

public class Expedicao {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="codigo", nullable = false, unique = true, length = 10)
    private String codigo;

    @Column(name="titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name="objetivo", nullable = false, length = 300)
    private String objetivo;

    @Column(name="inicio_previsto", nullable = false)
    private LocalDateTime inicioPrevisto;

    @Column(name="termino_previsto", nullable = false)
    private LocalDateTime terminoPrevisto;

    @Column(name="orcamento_aprovado", nullable = false, precision = 14, scale = 2)
    private BigDecimal orcamentoAprovado;

    @Column(name="custo_realizado", precision = 14, scale = 2)
    private BigDecimal custoRealizado;

    @Column(name="max_participantes", nullable = false)
    private int maxParticipantes;

    @Enumerated(EnumType.STRING)
    @Column(name="situacao", length = 20, nullable = false)
    private SituacaoExpedicao situacao;

    @Column(name="cancelamento_emergencial", nullable = false)
    private boolean cancelamentoEmergencial;
    
}
