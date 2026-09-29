package turmalina.pweb3.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name="plano_seguranca")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class PlanoSeguranca {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="versao", nullable = false)
    private String versao;

    @Column(name="data_elaboracao", nullable = false)
    private LocalDate dataElaboracao;

    @Column(name="procedimentos_emergencia", nullable = false)
    private String procedimentosEmergencia;

    @Column(name="pontos_encontro", nullable = false)
    private String pontosEncontro;

    @Column(name="contato_emergencia", nullable = false)
    private String contatoEmergencia;

    @Column(name="tempo_max_sem_comunicacao", nullable = false)
    private Integer tempoMaxSemComunicacao;

    @Column(name="equipe_medica_necessaria", nullable = false)
    private Boolean equipeMedicaNecessaria;

    @Lob
    @Basic(fetch= FetchType.LAZY)
    @Column(name="mapa_rota")
    private byte[] mapaRota;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="expedicao_id", nullable = false, unique = true)
    private Expedicao expedicao;


}
