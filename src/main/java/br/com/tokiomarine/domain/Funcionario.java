package br.com.tokiomarine.domain;

import br.com.tokiomarine.domain.enums.AcessoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFuncionario;

    @Column
    private String nome;

    @Column
    @Email
    private String email;

    @Column
    private String senha;

    @Column
    private AcessoEnum acesso;

}
