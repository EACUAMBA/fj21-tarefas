package br.com.caelum.tarefas.Interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AutorizadorInterceptor extends HandlerInterceptorAdapter /*Para uma classe se tornar um Interceptador devemos herdar este método, ele tem tr^s métodos que são importantes, preHandle, postHandle(estes dois são executados antes do final da requisição) e afterCompletion(Este é executado no final da requisição!)*/ {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //Então antes de uma requisição seja qualfor queremos fazer algumas coisas, para nos certificar que o utilizador que esta a fazer essa requisição é mesmo um utilizador com acesso(credenciais da nossa aplicação)

        HttpSession session = request.getSession(); // Estamos pegando a Sessão que está no navegador relacionada com esta página.
        String uri =request.getRequestURI(); // Estamos pegando o URI(Uniform Resource Identifier (Identificador de Recursos Uniforme)) que foi enviado na requisição, o nome do site praticamente só que contém mais informações.

        if(uri.endsWith("login-form")||uri.endsWith("efectuaLogin")||uri.contains("resources")) /* Estamos verificando em que site ele esta tentando entrar atraves da uri, como disse a uri é o endereço, se ele conter neste caso terminar por (login-form | efectuaLogin | resources(é onde estão nossos arquivos de css, js, imgs e outros)) elevai executar uma ação, eu explico abaixo*/{
            return true; // Se retornarmos true (significa ter aprovação de passar) para o método preHandler isso significa que ele não deve se preocupar com a interceptação então ele não vai verificar a nossa sessão e a execução deste metodo para aqui para esta requisição, note para está requisição, significa que se fizermos uma outra requisição teríamos que passar por este método novamente
        }

        if(session.getAttribute("usuarioLogado") !=null) /* Estamos verificando se existe alguma sessão, se existe ele passa se não existe veja nas três ultimas linhas de código*/{
            return true; // Se retornarmos true (significa ter aprovação de passar) para o método preHandler isso significa que ele não deve se preocupar com a interceptação então ele não vai verificar a nossa sessão e a execução deste metodo para aqui para esta requisição, note para está requisição, significa que se fizermos uma outra requisição teríamos que passar por este método novamente
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login-form");// Esta linha chama um dispatch aquele que usamos nos servlet, esse dispatch é responsável por enviar o usuário para a página de login se ele não estiver logado
        requestDispatcher.forward(request, response);// faz um redirecionamento usando forward(encaminhamento)

        return false; // se o resultado é false o usuário não pode acessar o que ele que acessar.


/*Note tudo isto fazemos antes do usuario ter o resultado de qualquer requisição, para não termos informação com quem não é user do sistema. */
    }
}
