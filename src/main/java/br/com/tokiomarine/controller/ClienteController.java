package br.com.tokiomarine.controller;

import br.com.tokiomarine.domain.Cliente;
import br.com.tokiomarine.domain.Endereco;
import br.com.tokiomarine.domain.EnderecoCliente;
import br.com.tokiomarine.domain.Funcionario;
import br.com.tokiomarine.domain.dto.ClienteDTO;
import br.com.tokiomarine.domain.enums.AcessoEnum;
import br.com.tokiomarine.domain.enums.ErroEnums;
import br.com.tokiomarine.exception.EmailJaCadastradoException;
import br.com.tokiomarine.repository.ClienteRepository;
import br.com.tokiomarine.repository.EnderecoClienteRepository;
import br.com.tokiomarine.repository.EnderecoRepository;
import br.com.tokiomarine.repository.FuncionarioRepository;
import br.com.tokiomarine.service.ClienteServico;
import br.com.tokiomarine.service.EnderecoServico;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final ClienteServico clienteServico;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoClienteRepository enderecoClienteRepository;
    private final FuncionarioRepository funcionarioRepository;

    public ClienteController(ClienteRepository clienteRepository, ClienteServico clienteServico, EnderecoRepository enderecoRepository, EnderecoClienteRepository enderecoClienteRepository, EnderecoServico enderecoServico, FuncionarioRepository funcionarioRepository) {
        this.clienteRepository = clienteRepository;
        this.clienteServico = clienteServico;
        this.enderecoRepository = enderecoRepository;
        this.enderecoClienteRepository = enderecoClienteRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    /**
     * Mostra a tela de clientes com a lista carregada definindo o primeiro resultado e o maximo de resultados
     *
     * @param model
     * @return
     */
    //TODO metodo que busca mais usuarios a partir do ultimo resultado
    @RequestMapping("/clientes")
    public String telaClientes(Model model) {
        List<Cliente> listaClientes = clienteServico.findListaPaginada(0, 10);
        model.addAttribute("listaClientes", listaClientes);
        return "dashboard/clientes/lista-clientes";
    }

    /**
     * Exibe a tela para adicionar os clientes
     *
     * @return
     */
    @RequestMapping("/adicionar-cliente")
    public String telaAdicionaCliente() {
        return "dashboard/clientes/adicionar-cliente";
    }

    /**
     * Insere o cliente na base, caso o email ja esteja cadastrado retorna uma mensagem de erro
     *
     * @param nome
     * @param email
     * @param model
     * @return
     */
    @RequestMapping("/insere-cliente")
    public String insereCliente(@RequestParam("nome") String nome,
                                @RequestParam("email") String email,
                                Model model) {
        Cliente cliente = null;
        try {
            cliente = clienteServico.cadastraCliente(Cliente.builder().nome(nome).email(email).build());
        } catch (EmailJaCadastradoException e) {
            model.addAttribute("erro", ErroEnums.EMAIL_JA_CADASTRADO);
            return "mensagem-generica";
        }
        ClienteDTO clienteDTO = ClienteDTO.builder().cliente(cliente).listaEndereco(new ArrayList<>()).build();
        model.addAttribute("clienteDTO", clienteDTO);
        return "dashboard/clientes/cadastra-endereco";
    }

    /**
     * insere um endereco para o cliente X
     *
     * @param idCliente
     * @param cep
     * @param rua
     * @param numero
     * @param bairro
     * @param estado
     * @param model
     * @return
     */

    @RequestMapping("/insere-endereco/{idCliente}")
    public String telaAdicionaEndereco(@PathVariable("idCliente") Long idCliente,
                                       @RequestParam("cep") String cep,
                                       @RequestParam("rua") String rua,
                                       @RequestParam("numero") String numero,
                                       @RequestParam("bairro") String bairro,
                                       @RequestParam("estado") String estado,
                                       Model model) {

        Endereco endereco = enderecoRepository.save(Endereco.builder().cep(cep).rua(rua).numero(numero).bairro(bairro).estado(estado).build());
        enderecoClienteRepository.save(EnderecoCliente.builder().idEndereco(endereco.getIdEndereco()).idCliente(idCliente).build());

        ClienteDTO clienteDTO = clienteServico.montaClienteDTO(idCliente);

        model.addAttribute("clienteDTO", clienteDTO);
        return "dashboard/clientes/lista-enderecos";
    }

    /**
     * Exclui o cliente junto com todos os seus enderecos da base fisicamente, somente o admin pode deletar
     *
     * @param idCliente
     * @return
     */

    @RequestMapping("/excluir-cliente/{idCliente}")
    public String telaExcluiCliente(HttpSession session,
                                    @PathVariable("idCliente") Long idCliente) {
        Funcionario funcionario = funcionarioRepository.findById((Long) session.getAttribute("usuarioLogado")).get();
        if (funcionario.getAcesso().equals(AcessoEnum.ADMIN)) clienteServico.deleteCliente(idCliente);
        return "redirect:/clientes";
    }

    /**
     * Mostra a tela de edicao do cliente
     *
     * @param session
     * @param idCliente
     * @param model
     * @return
     */
    @RequestMapping("/editar-cliente/{idCliente}")
    public String telaEditaCliente(HttpSession session,
                                   @PathVariable("idCliente") Long idCliente,
                                   Model model) {
        Funcionario funcionario = funcionarioRepository.findById((Long) session.getAttribute("usuarioLogado")).get();
        ClienteDTO clienteDTO = clienteServico.montaClienteDTO(idCliente);

        model.addAttribute("funcionario", funcionario);
        model.addAttribute("clienteDTO", clienteDTO);
        return "dashboard/clientes/editar-cliente";
    }

    /**
     * Edita as informacoes do cliente
     *
     * @param idCliente
     * @param nome
     * @param email
     * @param model
     * @return
     */

    @RequestMapping("/edita-cliente/{idCliente}")
    public String editaCliente(@PathVariable("idCliente") Long idCliente,
                               @RequestParam("nome") String nome,
                               @RequestParam("email") String email,
                               Model model) {
        try {
            clienteServico.updateCliente(idCliente, nome, email);
        } catch (EmailJaCadastradoException e) {
            model.addAttribute("erro", ErroEnums.EMAIL_JA_CADASTRADO);
            return "mensagem-generica";
        }
        return "redirect:/editar-cliente/" + idCliente;
    }

	/*@RequestMapping("/editar-endereco/{idEndereco}")
	public String editaCliente(@PathVariable("idEndereco")Long idEndereco,
							   @RequestParam("cep")String cep,
							   @RequestParam("rua")String rua,
							   @RequestParam("numero")String numero,
							   @RequestParam("bairro")String bairro,
							   @RequestParam("cidade")String cidade,
							   @RequestParam("estado")String estado,
							   Model model) {

		try{
			Endereco endereco = enderecoServico.editaEndereco(idEndereco,cep,rua,numero,cidade,bairro,estado);
			model.addAttribute("endereco", endereco);
			return "dashboard/clientes/";
		}catch (EnderecoJaCadastradoException e) {
			model.addAttribute("erro", ErroEnums.ENDERECO_JA_CADASTRADO);
			return "mensagem-generica";
		}
	}*/
}
