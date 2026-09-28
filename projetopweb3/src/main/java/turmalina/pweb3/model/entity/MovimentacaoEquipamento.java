package turmalina.pweb3.model.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.Instant;

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
import turmalina.pweb3.model.enums.EstadoEquipamento;

@Entity
@Table(name = "movimentacao_equipamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoEquipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "retirada_em", nullable = false)
    private Instant retiradaEm;

    @Column(name = "devolucao_prevista", nullable = false)
    private LocalDateTime devolucaoPrevista;

    @Column(name = "devolucao_efetiva")
    private Instant devolucaoEfetiva;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_saida", nullable = false, length = 20)
    private EstadoEquipamento estadoSaida;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_retorno", length = 20)
    private EstadoEquipamento estadoRetorno;

    @Column(name = "custo_avaria", precision = 12, scale = 2)
    private BigDecimal custoAvaria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "expedicao_id", nullable = false)
    private Expedicao expedicao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "responsavel_id", nullable = false)
    private Pessoa responsavel;

    public void registrarRetirada(Instant retiradaEm, LocalDateTime devolucaoPrevista) {
        if (!devolucaoPrevista.atZone(ZoneId.systemDefault()).toInstant().isAfter(retiradaEm)) {
            throw new IllegalArgumentException("A devolução prevista deve ser posterior à retirada");
        }
        this.retiradaEm = retiradaEm;
        this.devolucaoPrevista = devolucaoPrevista;
    }

    public void registrarDevolucao(Instant devolucaoEfetiva, EstadoEquipamento estadoRetorno, BigDecimal custoAvaria) {
        if (devolucaoEfetiva.isBefore(retiradaEm)) {
            throw new IllegalArgumentException("A devolução efetiva não pode ser anterior à retirada");
        }
        this.devolucaoEfetiva = devolucaoEfetiva;
        this.estadoRetorno = estadoRetorno;
        this.custoAvaria = custoAvaria;
    }
}