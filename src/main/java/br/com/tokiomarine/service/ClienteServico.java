package br.com.tokiomarine.service;

import br.com.tokiomarine.domain.Cliente;
import br.com.tokiomarine.domain.Endereco;
import br.com.tokiomarine.domain.dto.ClienteDTO;
import br.com.tokiomarine.exception.EmailJaCadastradoException;
import br.com.tokiomarine.repository.ClienteRepository;
import br.com.tokiomarine.repository.EnderecoClienteRepository;
import br.com.tokiomarine.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class ClienteServico {

    private ClienteRepository clienteRepository;
    private EnderecoRepository enderecoRepository;
    private EnderecoClienteRepository enderecoClienteRepository;
    private EntityManager entityManager;


    public ClienteServico(ClienteRepository clienteRepository, EnderecoRepository enderecoRepository, EnderecoClienteRepository enderecoClienteRepository, EntityManager entityManager) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.enderecoClienteRepository = enderecoClienteRepository;
        this.entityManager = entityManager;
    }

    public ClienteDTO montaClienteDTO(Long idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente).get();
        List<Endereco> listaEndereco = enderecoRepository.findListaEnderecoByIdCliente(idCliente);
        return ClienteDTO.builder().cliente(cliente).listaEndereco(listaEndereco).build();
    }

    public Cliente cadastraCliente(Cliente cliente) throws EmailJaCadastradoException {
        Cliente clienteByEmail = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteByEmail == null) return clienteRepository.save(cliente);
        else throw new EmailJaCadastradoException();
    }

    public void updateCliente(Long idCliente, String nome, String email) throws EmailJaCadastradoException {
        Cliente validaCliente = clienteRepository.findByEmail(email);
        if (!validaCliente.getId().equals(idCliente)) throw new EmailJaCadastradoException();
        Cliente cliente = clienteRepository.findById(idCliente).get();
        cliente.setEmail(email);
        cliente.setNome(nome);
        clienteRepository.save(cliente);
    }

    public void deleteCliente(Long idCliente) {
        List<Endereco> listaEndereco = enderecoRepository.findListaEnderecoByIdCliente(idCliente);
        enderecoClienteRepository.deleteAll(enderecoClienteRepository.findAllByIdCliente(idCliente));
        enderecoRepository.deleteAll(listaEndereco);
        clienteRepository.deleteById(idCliente);
    }

    public List<Cliente> findListaPaginada(Integer firstResult, Integer maxResults) {
        try {
            return (List<Cliente>) entityManager.createQuery("select c from Cliente c order by c.nome asc").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
