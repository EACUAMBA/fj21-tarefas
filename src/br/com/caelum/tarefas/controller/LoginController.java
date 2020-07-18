package br.com.caelum.tarefas.controller;

import br.com.caelum.tarefas.dao.JdbcUsuarioDao;
import br.com.caelum.tarefas.dao.TarefaDao;
import br.com.caelum.tarefas.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller // Esta notação diz ao spring que este é um controlador e ele tem metodos que são acções, e que estes metodos terão uma notação @RequestMapping("<value>") o <value> é um valor, um nome que sera usado para acessar essa ação pelo navegador, neste caso no navegador ao acessar localhost:8080/<nome_da_aplicação>/<value> acessaras esta acção que poderá estar ligada a tua regra de negocio e posteriormente vai chamar um pagina JSP ou enviará um arquivo JSON(para versões mais recentes do spring) como response(resposta ao navegador).
public class LoginController {
    private JdbcUsuarioDao jdbcUsuarioDao;

    @Autowired // Esta anotação indica ao spring que ele deverá injetar o objecto (neste caso devera injetar um objecto do tipo JdbcUsuarioDao. Mas como?? O spring vai criar um objecto e depois colocar aqui automaticamente, você com está anotação apenas já fez tudo isso), e se for a ver a tradução de auto + wire verá qeu significa amarrar automaticamente, o spring vai amarrar estas duas instâncias junto.
    public LoginController(JdbcUsuarioDao jdbcUsuarioDao){
        this.jdbcUsuarioDao=jdbcUsuarioDao;
    }

    @RequestMapping("login-form") // Para entender isto leia a informação sobre o @Controller colocada acima. UP
    public String loginForm(){
        return "formulario-login";
    }

    @RequestMapping("efectuaLogin") // Para entender isto leia a informação sobre o @Controller colocada acima. UP
    public String efectuaLogin(Usuario usuario , HttpSession session /*Se você nao sabe o que é uma Sessão vai ser difícil entender, mas ok, vamos lá: Uma sessão é um tipo de token ou pacote que o servidor cria quando um usuário se cadastra na pagina, esse arquivo ou código tem um tempo de duração e quando esse tempo expira o servidor não aceita mais esse código, o servidor pedirá que o navegador actualiza as informações sobre o usuário. */){
        if(jdbcUsuarioDao.existeUsuario(usuario)){
            session.setAttribute("usuarioLogado", usuario); // Estamos adicionando uma sessão ao session, assim esse usuário poderá acessar desde que tenha essa sessão;
            return "forward:home"; // encaminhamos o usuário para a pagina home com as operações
        }
        return "redirect:login-form"; // encaminhamos o usuário para a de login porque ele não existe, esse usuário não existe, note que o enedereco não mudou mas o conteudo é da pagina login-form
    }

    @RequestMapping("logout") // Para entender isto leia a informação sobre o @Controller colocada acima. UP
    public String logoutSair(HttpSession session /* Recebemos a sessão e invalidamos ela o usuario não poderá acessar mais o sistema até fazer um novo log in*/ ){
        session.invalidate(); // estamos a invalidar.
        return "redirect:login-form";
    }
}
