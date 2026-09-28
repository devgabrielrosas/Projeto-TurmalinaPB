package turmalina.pweb3.model.entity;

import java.time.LocalDate;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import turmalina.pweb3.model.enums.SituacaoAutorizacao;

@Entity 
@Table(name="autorizacao_ambiental")
public class AutorizacaoAmbiental {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="numero", nullable = false, unique = true)
    private String numero;

    @Column(name="orgao_emissor", nullable = false)
    private String orgaoEmissor;

    @Column(name="data_emissor", nullable = false)
    private LocalDate dataEmissor;

    @Column(name="data_validade", nullable = false)
    private LocalDate dataValidade;

    @Enumerated(EnumType.STRING)
    @Column(name="situacao")
    private SituacaoAutorizacao situacaoAutorizacao;

    @Column(name="observacoes")
    private String observacoes;

    @Lob 
    @Basic(fetch = FetchType.LAZY)
    @Column(name="arquivo_pdf", nullable = false)
    private byte[] arquivoPdf;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="expedicao_id", nullable = false, unique = true)
    private Expedicao expedicao;
}