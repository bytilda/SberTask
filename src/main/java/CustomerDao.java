import java.sql.*;
import java.util.HashMap;


public class CustomerDao extends DAO<Customer>{
    private HashMap<Integer, Customer> customers = new HashMap<>();
    private static final String INSERT_NEW =
            "insert into customers (firstname, lastname, patronymic, email, dateofbirth, phonenumber) " +
                    "values(?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL = "select * from customers";
    private static final String FIND_ONE = "select * from customers where idcustomer = ?";
    private static final String DELETE_ONE = "delete from customers where idcustomer = ?";
    private static final String UPDATE =
            "update customers set firstname = ?, lastname = ?, patronymic = ?, email = ?," +
                    " dateofbirth = ?, phonenumber = ? where idcustomer = ?";


    public CustomerDao(){
        findAll();
    }

    public HashMap<Integer, Customer> findAll() {

        try {
            Statement statement = DBManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                int id = resultSet.getInt("idcustomer");
                Customer customer;

                //если уже есть ссылка покупателя с указанным id
                //то перезаписываем в нее (возможно) новую информацию из бд
                //таким образом сохраняем ссылки между заказом и покупателем
                if(customers.containsKey(id))
                    customer = customers.get(id);
                else
                    customer = new Customer();

                fillCustomer(customer, resultSet);
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

            fillCustomerStatement(customer, preparedStatement);

            //был ли внесен покупатель
            int numOfAffectedRows = preparedStatement.executeUpdate();
            if (numOfAffectedRows != 1){
                throw new SQLException();
            }

            //получение сгенерированного бд нового ключа
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            customer.setID(rs.getInt(1));
            if(customers == null)
                customers = new HashMap<>();
            customers.put(customer.getID(), customer);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Customer get(int id) {
        Customer customer;

        //если уже есть ссылка покупателя с указанным id
        //то перезаписываем в нее (возможно) новую информацию из бд
        if(customers.containsKey(id))
            customer = customers.get(id);
        else
         customer = new Customer();

        try {
            PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement(FIND_ONE);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            fillCustomer(customer, resultSet);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }

    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement(DELETE_ONE);
            preparedStatement.setInt(1, id);
            int numOfAffectedRows = preparedStatement.executeUpdate();
            if (numOfAffectedRows != 1){
                throw new SQLException();
            }
            customers.remove(id);

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Customer customer) {
        try {
            PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement(UPDATE);
            fillCustomerStatement(customer, preparedStatement);
            preparedStatement.setInt(7, customer.getID());
            int numOfAffectedRows = preparedStatement.executeUpdate();
            if (numOfAffectedRows != 1){
                throw new SQLException();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    private void fillCustomerStatement(Customer customer, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, customer.getFirstName());
        preparedStatement.setString(2, customer.getLastName());
        preparedStatement.setString(3, customer.getPatronymic());
        preparedStatement.setString(4, customer.getEmail());
        preparedStatement.setDate(5, new Date(customer.getDateOfBirth().getTime()));
        preparedStatement.setString(6, customer.getPhoneNumber());
    }

    public HashMap<Integer, Customer> getHashMap(){
        return customers;
    }

    private void fillCustomer(Customer customer, ResultSet resultSet) throws SQLException {
        customer.setID(resultSet.getInt("idcustomer"));
        customer.setFirstName(resultSet.getString("firstname"));
        customer.setLastName(resultSet.getString("lastname"));
        customer.setPatronymic(resultSet.getString("patronymic"));
        customer.setEmail(resultSet.getString("email"));
        customer.setPhoneNumber(resultSet.getString("phonenumber"));
        customer.setDateOfBirth(resultSet.getDate("dateofbirth"));
    }
}

