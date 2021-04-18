import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBManager {
    private final String connectionURL = "jdbc:mysql://localhost:3306/sberdb";
    private final String DBUsername = "root";
    private static Connection connection = null;
    private static DBManager instance;

    private DBManager(){
        try {
            Scanner sc = new Scanner(Main.class.getResourceAsStream("password.txt"));
            String password = sc.nextLine();
            connection = DriverManager.getConnection(connectionURL, DBUsername, password);

        } catch (SQLException e){
            System.out.println("Failed to establish a connection to the database.");
            e.printStackTrace();
        }

    }
    public static DBManager getInstance(){
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }
    public static Connection getConnection(){
        if (instance == null) {
            instance = new DBManager();
        }
        return connection;
    }

}
