package br.com.caelum.tarefas.modelo;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Entity // Esta notação serve para mostrar ao JPA e ao Hibernate que este dados deve ou pode ser persistível(significa armazenado, a entidade persistir em ficar no banco de dados, espero que entendas) no banco de dados
public class Tarefa implements Serializable {
    @Id //Esta notação diz ao JPA e Hibernate que o atributo (private int id) é um ID, identificador único de cada entidade ou objecto.
    @GeneratedValue //Esta notação diz que os valores do atributos serão definidos aleatoriamente pelo banco de dados.
    private Long id;

    @Size(min = 5 , max = 200, message = "{tarefa.descricao.curta}") //Esta notação diz ao validation que este campo deve obedecer as limitações impostas dentro de parentesis, como min=5(significa no mínimo so aceita 5 caracteres o mesmo acontece com max=200), o message indica ao validation que deve apresentar esta mensagem, dentro da mensagem podemos colocar um {min} isso significa que esse {min} sera substituido pelo valo do min=5. Este arquivo fica na pasta root do projecto (src/ValidationMessages.properties)
    private String descricao;
    private boolean finalizado;

    @Temporal(TemporalType.DATE) // Esta notação diz ao JPA e ao Hibernate como mapear o calendar para o banco de dados. Se tiver a trabalha com horas use TemporalType.TIME, e tambem temos o TemporalType.TIMESTAMP
    @DateTimeFormat(pattern = "dd/MM/yyyy") // Esta notação diz ao springframework como trabalhar com a data, diz o formato que ela deve ter ao ser gravada.
    private Calendar dataFinalizacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public Calendar getDataFinalizacao() {
        return dataFinalizacao;
    }


    public void setDataFinalizacao(Calendar dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", finalizado=" + finalizado +
                ", dataFinalizacao=" + dataFinalizacao +
                '}';
    }
}
