package br.com.caelum.tarefas.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection(){
        String user = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost/fj21";
Connection connection= null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return connection;
    }
    public static void closeConnection(){
        try {
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
