package turmalina.pweb3.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import turmalina.pweb3.model.embeddable.Endereco;


// Classe abstrata que vai implementar mapeamento de herança e vai utilizar estratégia JOINED;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 70)
    private String nome;

    @Column(nullable=false, length=11, unique=true)
    private String cpf;

    @Column(name="data_nascimento")
    private LocalDate dataNascimento;
    
    private String email;
    private String telefone;
    
    @Column(columnDefinition="Boolean DEFAULT TRUE")
    private Boolean ativa;

    @Embedded
    private Endereco endereco;
    
}
