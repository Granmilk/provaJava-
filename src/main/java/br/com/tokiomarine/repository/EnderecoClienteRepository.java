package br.com.tokiomarine.repository;

import br.com.tokiomarine.domain.EnderecoCliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EnderecoClienteRepository extends CrudRepository<EnderecoCliente, Long> {

    @Query("SELECT ec From EnderecoCliente ec where ec.idCliente = :idCliente and ec.idEndereco = (select e.idEndereco from Endereco e where e.cep = :cep and e.rua = :rua and e.numero = :numero and e.bairro = :bairro and e.cidade = :cidade and e.estado = :estado)")
    Boolean existsEnderecoClienteByIdClienteAndEndereco(Long idCliente, String cep, String rua, String numero, String bairro, String cidade, String estado);

    EnderecoCliente findEnderecoClienteByIdEndereco(Long idEndereco);

    List<EnderecoCliente> findAllByIdCliente(Long idCliente);
}
