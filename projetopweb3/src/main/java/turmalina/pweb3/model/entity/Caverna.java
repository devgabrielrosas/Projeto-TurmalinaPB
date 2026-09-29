package turmalina.pweb3.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import turmalina.pweb3.model.embeddable.Localizacao;

@Entity
@Table(name = "caverna")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Caverna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "codigo_ambiental", nullable = false, unique = true, length = 30)
    private String codigoAmbiental;

    @Column(nullable = false, length = 60)
    private String municipio;

    @Column(nullable = false, length = 2)
    private String uf;

    @Embedded
    private Localizacao localizacao;

    @Column(precision = 8, scale = 2)
    private BigDecimal altitude;

    @Column(name = "extensao_conhecida", precision = 10, scale = 2)
    private BigDecimal extensaoConhecida;

    @Column(name = "data_ultima_inspecao")
    private LocalDate dataUltimaInspecao;

    @Column(name = "acesso_permitido", nullable = false)
    private boolean acessoPermitido;

    @OneToMany(mappedBy = "caverna", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Setor> setores = new HashSet<>();

}