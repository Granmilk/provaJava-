package br.com.tokiomarine.controller;

import br.com.tokiomarine.domain.Funcionario;
import br.com.tokiomarine.domain.enums.ErroEnums;
import br.com.tokiomarine.repository.FuncionarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    private FuncionarioRepository funcionarioRepository;

    public MainController(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @RequestMapping("/dashboard")
    public String mostraDashboard(HttpSession session,
                                  Model model) {
        Funcionario funcionario = funcionarioRepository.findById((Long) session.getAttribute("usuarioLogado")).get();
        model.addAttribute("funcionario", funcionario);
        return "dashboard/dashboard";
    }

    @RequestMapping("/timeout")
    public String timeout(Model model) {
        model.addAttribute("erro", ErroEnums.TIMEOUT);
        return "mensagem-generica";
    }

    @RequestMapping("/adm")
    public String telaAdm(Model model) {
       return "dashboard/pagina-adm";
    }

}
