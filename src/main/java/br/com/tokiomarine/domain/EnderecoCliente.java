package br.com.tokiomarine.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name="id_endereco")
    private Long idEndereco;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_endereco",insertable=false,updatable=false, foreignKey = @ForeignKey(name="fk_endereco_cliente_id_endereco"))
    private Endereco endereco;

    @Column(name="id_cliente")
    private Long idCliente;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_cliente",insertable=false,updatable=false, foreignKey = @ForeignKey(name="fk_endereco_cliente_id_cliente"))
    private Cliente cliente;

}
