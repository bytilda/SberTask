import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class OrderDao extends  DAO<Order>{
    private HashMap<Integer, Order> orders;
    private CustomerDao customerDao;
    //private static final String INSERT_NEW = "insert into orders (firstname, lastname, patronymic) values(?, ?, ?)";

    public OrderDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public HashMap<Integer, Order> findAll() {
        orders = new HashMap<Integer, Order>();
        try {
            Statement statement = DBManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from orders");
            while (resultSet.next()) {
                Order order = new Order();
                order.setID(resultSet.getInt("idorder"));
                order.setCustomerID(resultSet.getInt("idcustomer"));
                order.setOrderDate(resultSet.getDate("orderDate"));

                //если коллекция покупателей не была заполнена
                if(customerDao.getHashMap() == null) customerDao.findAll();
                order.setCustomer(customerDao.getHashMap().get(resultSet.getInt("idcustomer")));

                orders.put(order.getID(), order);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orders;
    }

    @Override
    public boolean add(Order order) {
        try {
            //
            PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement("insert into orders (number, orderDate, dateOfStartExecution, dateOfEndExecution, idcustomer, description) values(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, order.getOrderNumber());
            preparedStatement.setDate(2, new Date(order.getOrderDate().getTime()));
            preparedStatement.setDate(3, new Date(order.getDateOfStartExecution().getTime()));
            preparedStatement.setDate(4, new Date(order.getDateOfEndExecution().getTime()));
            preparedStatement.setInt(5, order.getCustomerID());
            preparedStatement.setString(6, order.getDescription());

            //был ли внесен заказ
            int numOfAffectedRows = preparedStatement.executeUpdate();
            if (numOfAffectedRows != 1){
                throw new SQLException();
            }

            //получение сгенерированного бд нового ключа
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            order.setID(rs.getInt(1));
            if (orders == null)
                orders = new HashMap<Integer, Order>();
            orders.put(order.getID(), order);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isCustomerExists(int id){
        //TODO: change to getID
        return customerDao.findAll().containsKey(id);
    }
}
