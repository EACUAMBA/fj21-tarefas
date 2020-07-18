package br.com.caelum.tarefas.controller;

import br.com.caelum.tarefas.dao.TarefaDao;
import br.com.caelum.tarefas.modelo.Tarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.awt.windows.ThemeReader;

import javax.validation.Valid;

@Controller // Esta notação diz ao spring que este é um controlador e ele tem métodos que são acções, e que estes métodos terão uma notação @RequestMapping("<value>") o <value> é um valor, um nome que sera usado para acessar essa ação pelo navegador, neste caso no navegador ao acessar localhost:8080/<nome_da_aplicação>/<value> acessaras esta acção que poderá estar ligada a tua regra de negocio e posteriormente vai chamar um pagina JSP ou enviará um arquivo JSON(para versões mais recentes do spring) como response(resposta ao navegador).
@Transactional
public class TarefasController {
    @Autowired // Esta anotação indica ao spring que ele deverá injetar o objecto (neste caso devera injetar um objecto do tipo TarefaDao(Mas como Tarefa é uma interface vai instanciar o jpaTarefaDao que a implementa). Mas como?? O spring vai criar um objecto e depois colocar aqui automaticamente, você com está anotação apenas já fez tudo isso), e se for a ver a tradução de auto + wire verá qeu significa amarrar automaticamente, o spring vai amarrar estas duas instâncias junto. Para funcionar o Objecto deverá ser um componente, nos não anotamos o jpaTarefaDao ou o TarefaDao explicitamente, mas  todos são componentes por causa da nossa tag no arquivo web\WEB-INF\spring-context.xml

    @Qualifier("jpaTarefaDao") // Esta anotação faz o spring instanciar um certa classe que nos acabamos de instanciar. Mas porque isto é necessário? porque temos duas classes que implementam a interface TarefaDao então o spring não sabe qual delas instanciar aqui! por isso temos de ser explicito.
    private TarefaDao dao;
//
//    @Autowired // Esta anotação indica ao spring que ele deverá injetar o objecto (neste caso devera injetar um objecto do tipo TarefaDao(Mas como Tarefa é uma interface vai instanciar o jpaTarefaDao que a implementa). Mas como?? O spring vai criar um objecto e depois colocar aqui automaticamente, você com está anotação apenas já fez tudo isso), e se for a ver a tradução de auto + wire verá qeu significa amarrar automaticamente, o spring vai amarrar estas duas instâncias junto.
//    public TarefasController(TarefaDao dao)/*Aqui estavamos a fazer uma injeção de dependências, estavamos a ligar a dependência JdbcTarefaDao com o construtor so que não é mais necessario porque */ {
//        this.dao = dao;
//    }

    @RequestMapping("home") // Para entender isto leia a informação sobre o @Controller colocada acima. UP
    public String home() {
        return "index";
    }

    @RequestMapping("novaTarefa") // Para entender isto leia a informação sobre o @Controller colocada acima. UP
    public String novaTarefa() {
        return "tarefa/formulario";
    }

    @RequestMapping("adicionaTarefa") // Para entender isto leia a informação sobre o @Controller colocada acima. UP
    public String adiciona(@Valid Tarefa tarefa, BindingResult bindingResult /*Ao fazermos o envio de descrição ela pode não satisfazer @Size que colocamos na class Tarefa então o objecto chamado BindingResult será enviado pelo spring, este objecto armazena o erro, se tiver o erro significa que deveremos retornar o utilizador para a mesma pagina um erro será exibido, va para a pagina mostra.jsp e verá como fazemos pra mostrar o erro.*/) {
        if (bindingResult.hasFieldErrors("descricao")) /*Estamos verificando se ocorreu erros, então se ocorreu levamos o usuário para a pagina com o erro que gerou! que a mesma pagina de adicionar.*/ {
            return "tarefa/formulario";
        }
        dao.adiciona(tarefa);
        return "tarefa/adicionada";
    }

    @RequestMapping("listaTarefas") // Para entender isto leia a informação sobre o @Controller colocada acima. UP
    public String lista(Model model/*Este objecto armazena os dados, esses dados serão enviado para JSP assim que nós chamarmos a pagina jsp, então na pagina podemos chamar esses dados que ficam nesse model usando expression language( ${<nome_do_atributo>} ). Podemos usar ModelAndView criando uma instância dessa classe */) {
//        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("tarefas", dao.lista());
        return "tarefa/lista";
    }


    @ResponseBody // Esta anotação faz com que o spring envie uma response com o corpo, o que isso significa? Que a resposta terá la os dados de como foi a requisição, tipo 200 - para OK, 404 - para Not Found, 500 para Internal Issues e ainda outros códigos existentes.
    @RequestMapping("removeTarefa") // Para entender isto leia a informação sobre o @Controller colocada acima. UP
    public String remove(Tarefa tarefa){
        dao.remove(tarefa);
        return "redirect:listaTarefas"; // {[(Com o @ResponseBody nao funciona o redirecionamento!)]}É um forward o que significa que o navagador recebe do servidor um comamdo para o navegador chamar a pagina listaTarefas, então a URL vai alterar  e ficará, localhost:8080/listaTarefas e assin o navegador vai fazer um requisição pessoal mente.
    }

    @RequestMapping("mostraTarefa") // Para entender isto leia a informação sobre o @Controller colocada acima. UP
    public String mostra(Tarefa tarefa1, Model model/*Este objecto armazena os dados, esses dados serão enviado paara JSP assim que nós chamarmos a pagina jsp, entõa na pagina podemos chamar esses dados que ficam nesse model usando expression language( ${<nome_do_atributo>} ). */) {
        Tarefa tarefa = dao.buscaPorID(tarefa1.getId());
        model.addAttribute("tarefa", tarefa); // Aqui nós estamos a adicionar uma informação ao model que será carregada ao JSP para acesso seu posterior.
        return "tarefa/mostra";
    }

    @RequestMapping("actualizaTarefa")// Para entender isto leia a informação sobre o @Controller colocada acima. UP
    public String actualiza(@Valid Tarefa tarefa, BindingResult bindingResult /*Ao fazermos o envio de descrição ela pode não satisfazer @Size que colocamos na class Tarefa então o objecto chamado BindingResult será enviado pelo spring, este objecto armazena o erro, se tiver o erro significa que deveremos retornar o utilizador para a mesma pagina um erro será exibido, va para a pagina mostra.jsp e verá como fazemos pra mostrar o erro.*/){
        if (bindingResult.hasFieldErrors("descricao"))/*Estamos verificando se ocorreu erros levamos o utilizador para a pagina com o erro que gerou!*/ {
            return "tarefa/mostra";
        }
        dao.altera(tarefa);
        return "forward:listaTarefas"; // é um redirecionamento do lado do servidor o que significa que o servidor vai pegar o conteúdo da pagina listaTarefas e enviar ao invés de enviar outro tipo de conteúdo mas não vai alterar a url, a url continuará da pagina anterior com o conteúdo da pagina listaTarefas.
    }

    //    @ResponseBody este serve para retornar um html response com a resposta se é 200 sucesso ou outra coisa
    @RequestMapping("finalizaTarefa") // Para entender isto leia a informação sobre o @Controller colocada acima. UP
    public String finalizar(long id, Model model/*Este objecto armazena os dados, esses dados serão enviado paara JSP assim que nós chamarmos a pagina jsp, entõa na pagina podemos chamar esses dados que ficam nesse model usando expression language( ${<nome_do_atributo>} ). */) {
        dao.finaliza(id);
        model.addAttribute("tarefa", dao.buscaPorID(id)); // Aqui nós estamos a adicionar uma informação ao model que será carregada ao JSP para acesso seu posterior.
        return "tarefa/dataFormatada";
    }
}


