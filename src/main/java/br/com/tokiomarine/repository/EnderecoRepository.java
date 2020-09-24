package br.com.tokiomarine.repository;

import br.com.tokiomarine.domain.Endereco;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EnderecoRepository extends CrudRepository<Endereco, Long> {

    @Query("SELECT e FROM Endereco e where e.idEndereco in (select ec.idEndereco from EnderecoCliente ec where ec.idCliente = :idCliente)")
    List<Endereco> findListaEnderecoByIdCliente(Long idCliente);

    Boolean existsEnderecoByCepAndRuaAndNumeroAndBairroAndCidadeAndEstado(String cep, String rua, String numero, String bairro, String cidade, String estado);
}
