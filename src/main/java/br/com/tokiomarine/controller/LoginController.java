package br.com.tokiomarine.controller;

import br.com.tokiomarine.domain.Funcionario;
import br.com.tokiomarine.repository.FuncionarioRepository;
import br.com.tokiomarine.service.GeralServico;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private FuncionarioRepository funcionarioRepository;

    public LoginController(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @RequestMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("senha") String senha,
                        HttpSession session) {

        Funcionario funcionario = funcionarioRepository.findFuncionarioByEmail(email);


        if (GeralServico.validaConfirmaSenha(funcionario.getSenha(),GeralServico.gerarHash(senha))){
            session.setMaxInactiveInterval(60 * 5);
            session.setAttribute("usuarioLogado", funcionario.getIdFuncionario());
            return "redirect:/dashboard";
        }

        return "redirect:/";
    }
}
