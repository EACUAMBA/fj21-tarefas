package br.com.caelum.tarefas.dao;

import br.com.caelum.tarefas.Connection.ConnectionFactory;
import br.com.caelum.tarefas.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository// Esta é uma notação que diz ao spring que este é um bean, só que não é um bean qualquer é um bean com acesso a dados, banco de dados, arquivos, um Array com dados e etc;
public class JdbcUsuarioDao  {
    private Connection connection;

    @Autowired // Esta anotação indica ao spring que ele deverá injetar o objecto (neste caso devera injetar um objecto do tipo DataSource. Mas como?? O spring vai criar um objecto e depois colocar aqui automaticamente, você com está anotação apenas já fez tudo isso), e se for a ver a tradução de auto + wire verá qeu significa amarrar automaticamente, o spring vai amarrar estas duas instâncias junto.
    public JdbcUsuarioDao(DataSource dataSource /*Mas o que é DataSource, como o nome diz data source (fonte de dados) é de onde vem os dados de conexão que tanto queremos, este datasource foi definido no arquivo (web\WEB-INF\spring-context.xml)*/){
        try {
            this.connection = dataSource.getConnection(); // Aqui estou a pegar a conexão com o método .getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> listarUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()){

            while(resultSet.next()){
                Usuario usuario = new Usuario();
                usuario.setUsername(resultSet.getString("username"));
                usuario.setPassword(resultSet.getString("password"));
                usuario.setAdmin(resultSet.getBoolean("admin"));
                usuarios.add(usuario);
                System.out.println(usuario);
            }
            System.out.println("Lista de usuarios obtida com sucesso!");
            return usuarios;

        }catch (SQLException e){
            new RuntimeException(e);
        }
return null;
    }
    public boolean existeUsuario(Usuario usuario){
        List<Usuario> usuarios = listarUsuarios();
        boolean very = false;
        if(usuarios != null)
             very = usuarios.contains(usuario);

        return very;
    }




}
