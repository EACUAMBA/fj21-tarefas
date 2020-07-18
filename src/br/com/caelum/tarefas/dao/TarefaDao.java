package br.com.caelum.tarefas.dao;

import br.com.caelum.tarefas.modelo.Tarefa;

import java.util.List;

public interface TarefaDao
        /*Para diminuir o acoplamento dependências usamos uma interface, assim podemos criar uma outra classe que implementam esta interface e ele pode ser usado onde chamamos uma instância do tipo TarefaDao. Mas como assim?
     *Está interface assina a classe dizendo que ela se qualifica para entrar aqui, onde? onde nos so queremos classe que assinaram este contrato. Note quando nos colocamos uma classe implementando uma interface ela é obrigada a ter métodos, isso significa que todas as classe que tem essa interface tem coisas em comum, tem métodos implementados, esses métodos são os que nos precisamos para usar... Veja o TarefaController, ela quer uma classe que tem os métodos busca, lista e etc. então todas as classes que implementarem esta interface serão assim terão estes métodos.

     Foi um texto exaustivo mas espero que compreendas.
*/
{
    Tarefa buscaPorID(Long id);

    List<Tarefa> lista();

    void adiciona(Tarefa tarefa);

    void altera(Tarefa tarefa);

    void remove(Tarefa tarefa);

    void finaliza(Long id);
}
