package br.com.tokiomarine.service;

import br.com.tokiomarine.domain.Endereco;
import br.com.tokiomarine.exception.EnderecoJaCadastradoException;
import br.com.tokiomarine.repository.ClienteRepository;
import br.com.tokiomarine.repository.EnderecoClienteRepository;
import br.com.tokiomarine.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServico {

    private EnderecoRepository enderecoRepository;
    private EnderecoClienteRepository enderecoClienteRepository;
    private ClienteRepository clienteRepository;

    public EnderecoServico(EnderecoRepository enderecoRepository, EnderecoClienteRepository enderecoClienteRepository, ClienteRepository clienteRepository) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoClienteRepository = enderecoClienteRepository;
        this.clienteRepository = clienteRepository;
    }

    public Endereco editaEndereco(Long idEndereco, String cep, String rua, String numero, String bairro, String cidade, String estado) throws EnderecoJaCadastradoException {
        Endereco endereco = enderecoRepository.findById(idEndereco).get();
        if(existeEndereco(idEndereco, cep,rua,numero,bairro,cidade,estado)) throw new EnderecoJaCadastradoException();
        endereco.setCep(cep);
        endereco.setRua(rua);
        endereco.setNumero(numero);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        return enderecoRepository.save(endereco);
    }

    private Boolean existeEndereco(Long idEndereco, String cep, String rua, String numero, String bairro, String cidade, String estado) {
        return enderecoClienteRepository.existsEnderecoClienteByIdClienteAndEndereco(
                enderecoClienteRepository.findEnderecoClienteByIdEndereco(idEndereco).getIdCliente(),
                cep,
                rua,
                numero,
                bairro,
                cidade,
                estado);
    }


}
