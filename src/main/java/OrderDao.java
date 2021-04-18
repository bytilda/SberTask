import java.sql.*;
import java.util.HashMap;


public class OrderDao extends  DAO<Order>{
    private HashMap<Integer, Order> orders;
    private CustomerDao customerDao;
    private static final String INSERT_NEW =
            "insert into orders (number, orderDate, dateOfStartExecution, dateOfEndExecution, idcustomer, description) values(?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL = "select * from orders";
    private static final String FIND_ONE = "select * from orders where (orders.idorder = ?)";
    public OrderDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public HashMap<Integer, Order> findAll() {
        orders = new HashMap<Integer, Order>();
        try {
            Statement statement = DBManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                Order order = new Order();
                fillOrder(order, resultSet);

                orders.put(order.getID(), order);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return orders;
    }

    @Override
    public boolean add(Order order) {
        try {
            PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement(INSERT_NEW, Statement.RETURN_GENERATED_KEYS);
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

    @Override
    public Order get(int id){
        Order order = new Order();
        try {
            PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement(FIND_ONE);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            fillOrder(order, resultSet);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return order;

    }

    public boolean isCustomerExists(int id){
        //TODO: change to getID
        return customerDao.findAll().containsKey(id);
    }

    private void fillOrder(Order order, ResultSet resultSet) throws SQLException {
        order.setID(resultSet.getInt("idorder"));
        order.setCustomerID(resultSet.getInt("idcustomer"));
        order.setOrderDate(resultSet.getDate("orderDate"));
        order.setCustomer(customerDao.getHashMap().get(resultSet.getInt("idcustomer")));
        order.setOrderNumber(resultSet.getInt("number"));
        order.setDateOfStartExecution(resultSet.getDate("dateOfStartExecution"));
        order.setDateOfEndExecution(resultSet.getDate("dateOfEndExecution"));
    }
}
