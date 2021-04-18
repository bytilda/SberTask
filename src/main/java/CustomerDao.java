import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class CustomerDao extends DAO<Customer>{
    private HashMap<Integer, Customer> customers = new HashMap<>();
    private static final String INSERT_NEW = "insert into customers (firstname, lastname, patronymic) values(?, ?, ?)";


    public HashMap<Integer, Customer> findAll() {
        customers = new HashMap<>();
        try {
            Statement statement = DBManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from customers");
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setID(resultSet.getInt("idcustomer"));
                customer.setFirstName(resultSet.getString("firstname"));
                customer.setLastName(resultSet.getString("lastname"));
                customer.setPatronymic(resultSet.getString("patronymic"));
                customers.put(customer.getID(), customer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customers;
    }

    public boolean add(Customer customer) {
        try {
            PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement(INSERT_NEW, Statement.RETURN_GENERATED_KEYS);
            //preparedStatement.setInt(1, customer.getID());
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getPatronymic());

            //был ли внесен покупатель
            int numOfAffectedRows = preparedStatement.executeUpdate();
            if (numOfAffectedRows != 1){
                throw new SQLException();
            }

            //получение сгенерированного бд нового ключа
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            customer.setID(rs.getInt(1));
            customers.put(customer.getID(), customer);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public HashMap<Integer, Customer> getHashMap(){
        return customers;
    }
}

