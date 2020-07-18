package br.com.caelum.tarefas.dao;

import br.com.caelum.tarefas.Connection.ConnectionFactory;
import br.com.caelum.tarefas.modelo.Tarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcTarefaDao implements TarefaDao{
    private Connection connection;

    @Autowired // Esta anotação indica ao spring que ele deverá injetar o objecto (neste caso devera injetar um objecto do tipo DataSource. Mas como?? O spring vai criar um objecto e depois colocar aqui automaticamente, você com está anotação apenas já fez tudo isso), e se for a ver a tradução de auto + wire verá qeu significa amarrar automaticamente, o spring vai amarrar estas duas instâncias junto.
    public JdbcTarefaDao(DataSource dataSource /*Mas o que é DataSource, como o nome diz data source (fonte de dados) é de onde vem os dados de conexão que tanto queremos, este datasource foi definido no arquivo (web\WEB-INF\spring-context.xml)*/){
        try {
            this.connection = dataSource.getConnection(); // Aqui estou a pegar a conexão com o método .getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void adiciona(Tarefa tarefa) {
        String sql = "INSERT INTO tarefas (descricao) VALUES (?)";

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, tarefa.getDescricao());
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Numero de linhas afetadas! " + affectedRows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Tarefa> lista() {

        List<Tarefa> tarefaList = new ArrayList<>();
        String sql = "SELECT * FROM tarefas";

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Tarefa tarefa = new Tarefa();
                tarefa.setId((resultSet.getLong("id")));
                tarefa.setDescricao(resultSet.getString("descricao"));
                tarefa.setFinalizado(resultSet.getBoolean("finalizado"));

                if (resultSet.getDate("dataFinalizacao") != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(resultSet.getDate("dataFinalizacao"));
                    tarefa.setDataFinalizacao(calendar);
                }
                tarefaList.add(tarefa);
            }
            return tarefaList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void remove(Tarefa tarefa) {
        String sql = "DELETE FROM tarefas where tarefas.id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setLong(1, tarefa.getId());

            System.out.println("Linhas afectadas! " + preparedStatement.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Tarefa buscaPorID(Long id) {
        String sql = "SELECT * from tarefas where id = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Tarefa tarefa = new Tarefa();
                tarefa.setId(resultSet.getLong("id"));
                tarefa.setDescricao(resultSet.getString("descricao"));

                if (resultSet.getDate("dataFinalizacao") == null){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(resultSet.getDate("dataFinalizacao"));
                    tarefa.setDataFinalizacao(calendar);
                }
                tarefa.setFinalizado(resultSet.getBoolean("finalizado"));

                return tarefa;
            }
        } catch (SQLException e) {
            new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void altera(Tarefa tarefa){
        String sql = "UPDATE tarefas SET descricao=?, finalizado=?, dataFinalizacao=? WHERE id=?";

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(sql)){
            preparedStatement.setString(1, tarefa.getDescricao());
            preparedStatement.setBoolean(2, tarefa.isFinalizado());

            preparedStatement.setDate(3, new Date(tarefa.getDataFinalizacao().getTime().getTime()));
            Calendar calendar = Calendar.getInstance();

            preparedStatement.setLong(4, tarefa.getId());

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows);
        }catch (SQLException e){
            new RuntimeException(e);
        }

    }

    @Override
    public void finaliza(Long id){
        String sql = "UPDATE tarefas SET finalizado=?, dataFinalizacao=? WHERE id=?";

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(sql)){
            preparedStatement.setBoolean(1, true);

            preparedStatement.setDate(2, new Date(Calendar.getInstance().getTime().getTime()));
            Calendar calendar = Calendar.getInstance();

            preparedStatement.setLong(3, id);

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows);
        }catch (SQLException e){
            new RuntimeException(e);
        }

    }

}

