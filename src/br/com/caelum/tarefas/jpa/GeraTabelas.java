package br.com.caelum.tarefas.jpa;

import br.com.caelum.tarefas.modelo.Tarefa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.List;

public class GeraTabelas {

    public static void main(String[] args) {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setDescricao("Comprar pão");
        tarefa.setFinalizado(true);
        tarefa.setDataFinalizacao(Calendar.getInstance());

//        It will create a database named tarefa; see the last two lines in current page;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("tarefas");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        How to create a new row in database
//        entityManager.getTransaction().begin(); // Actions that modifi the database we need to put these two line of code; begin(); - you are start the operation
//        entityManager.persist(tarefa);
//        entityManager.getTransaction().commit(); // Actions that modifi the database we need to put these two line of code; commit(); - you are confirming the alteration
//        System.out.println(tarefa);

//        How to search a row in database using ID
//        Tarefa t1 = entityManager.find(Tarefa.class, 1L);
//        System.out.println(t1);

//        How to remove row in database, 1 - first search using find(<set type of class(object) it will return (Object.class)>, id of row(use the same type of data))
//        Tarefa t2 = entityManager.find(Tarefa.class, 1L);
//        tarefa.setId(1L);
//        t2.setDescricao("1212Comprar pão111");
//        t2.setFinalizado(false);
//        t2.setDataFinalizacao(Calendar.getInstance());
//        entityManager.getTransaction().begin();
//        entityManager.merge(t2);
//        entityManager.getTransaction().commit();



//        How to remove? it's simple just find first, then remove.
//        Tarefa t3 = entityManager.find(Tarefa.class, 1L);
//        entityManager.getTransaction().begin();
//        entityManager.remove(t3);
//        entityManager.getTransaction().commit();



//        How to search using where? just create a query and add the JPQL (Is the name of the JPA query language, JPA don't use SQL they have your own language named JPQL) in string "select t from tarefa as t where t.finalizado = false; you can create your own query just use the same rule"
//        List<Tarefa> tarefas = entityManager.createQuery("select t from Tarefa as t where t.finalizado =true").getResultList();
//        for(Tarefa t: tarefas){
//            System.out.println(tarefas);
//        }



//        How to put parameters in a JPQL query? just add :paramName where you want to put it; look below
//        Query query = entityManager.createQuery("select t from Tarefa as t where t.finalizado = :paramFinalizado");
//        query.setParameter("paramFinalizado", true); // Then use it so
//        List<Tarefa> t6 = query.getResultList();
//        System.out.println(t6);



        entityManager.close(); //We need to close entityManager
        entityManagerFactory.close(); // We need to close entityManagerFactory


    }
}
