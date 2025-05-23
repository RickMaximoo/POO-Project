package ManagerProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBd {
    private static final String URL = "jdbc:mysql://localhost:3306/sys_pedidos?useSSL=false";
    private static final String USER = "root";  
    private static final String PASS = "Rickelme0512";    

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC não encontrado: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
