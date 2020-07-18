package br.com.caelum.tarefas.dao;

import br.com.caelum.tarefas.modelo.Tarefa;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.List;

//Uma entidade é um objecto feito por uma classe com a notação @Entity

@Repository // Esta é uma notação que diz ao spring que este é um bean, só que não é um bean qualquer é um bean com acesso a dados, banco de dados, arquivos, um Array com dados e etc;
public class JpaTarefaDao implements TarefaDao /*Note a explicação que dei no arquivo [br\com\caelum\tarefas\dao\TarefaDao.java] sobre implementação de Interfaces*/{

    @PersistenceContext // Tem a mesma função que o @Autowired (Injecta o Object), infelizmente não funciona com construtores, exigindo um outro ponto de injeção, esse será o spring a fazer. precisamos desse @PersistenceContext para dizer que este EntityManager é persistivel, que vamos persistir a sua informação
    EntityManager entityManager; //Precisamos desse Objecto para nos comunicarmos com o JPA, a assim podermos gravar dados no banco usando vários métodos como entityManager.persiste(<nome_do_Objecto_com_a_assinatura_@Entity>), é neste objecto que temos os métodos de persistência de dados e e outros;

    @Override
    public Tarefa buscaPorID(Long id) {
        return entityManager // Veja no atributo EntityManager com a anotação @PersistenceContext para entender o que isto é.
                .find(Tarefa.class, id); // O métodos .find( <Nome_da_Classe.class> Este é a entidade que o .find devera retornar, o find irá retornar um objecto do tipo Tarefas (por isso devemos dizer que classe é com a expressão "Tarefa.class") neste casso, note no retorno do método, Este é i ID da entidade que procuramos, o JPA usa o id para identificar o ID --veja no arquivo "src\br\com\caelum\tarefas\jpa\GeraTabelas.java" -- lá dou um explicação de como fazer pesquisas customizadas) é usado para encontrar um objecto a partir do seu ID;
    }

    @Override
    public List<Tarefa> lista() {
        return entityManager // Veja no atributo EntityManager com a anotação @PersistenceContext para entender o que isto é.
                .createQuery("select t from Tarefa t") // Este método é usado para criar queries customizadas, que vão de acordo com a nossa regra de negocio, aqui eu estou a fazer um simples select de todas as Tarefas.
                .getResultList(); // O método createQuery(<query>); retorna uma Query, um objecto do tipo Query que é uma implementação da interface Query então podemos usar um metodos que temos na InterfaceQuery, para converter as entidades retornadas em uma List<Entidade>.
    }

    @Override
    public void adiciona(Tarefa tarefa) {
        entityManager // Veja no atributo EntityManager com a anotação @PersistenceContext para entender o que isto é.
                .persist(tarefa); //Usado para persistir a entidade no banco de dados.
    }

    @Override
    public void altera(Tarefa tarefa) {
        entityManager // Veja no atributo EntityManager com a anotação @PersistenceContext para entender o que isto é.
                .merge(tarefa); // Com o mesmo sentido que no Excel, o JPA vai juntar as duas entidades, a que esta no banco vai perder todas as caraterísticas que a nova tras.
    }

    @Override
    public void remove(Tarefa tarefa) {
        Tarefa tarefa1 = buscaPorID(tarefa.getId()); // Aqui chamamos primeiro pesquisamos para nos certificar que a tarefa existe, se não existe não vamos usar o remove porque a variável tarefa1 tera valor null;
        entityManager // Veja no atributo EntityManager com a anotação @PersistenceContext para entender o que isto é.
                .remove(tarefa1); // Método para remover entidade, voçe so pode remover uma entidade se souber todos os dados;
    }

    @Override
    public void finaliza(Long id) {
        Tarefa tarefa = buscaPorID(id);
        tarefa.setFinalizado(true);
        tarefa.setDataFinalizacao(Calendar.getInstance());
        entityManager // Veja no atributo EntityManager com a anotação @PersistenceContext para entender o que isto é.
                .merge(tarefa); // Com o mesmo sentido que no Excel, o JPA vai juntar as duas entidades, a que esta no banco vai perder todas as caraterísticas que a nova tras.
    }
}
