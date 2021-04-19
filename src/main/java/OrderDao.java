import java.sql.*;
import java.util.HashMap;


public class OrderDao extends  DAO<Order>{
    private HashMap<Integer, Order> orders = new HashMap<>();
    private CustomerDao customerDao;
    private static final String INSERT_NEW =
            "insert into orders (number, orderDate, dateOfStartExecution, dateOfEndExecution, idcustomer, description) values(?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL = "select * from orders";
    private static final String FIND_ONE = "select * from orders where (orders.idorder = ?)";
    private static final String DELETE_ONE = "delete from orders where orders.idorder = ?";
    private static final String UPDATE =
            "update orders set number = ?,  orderDate = ?, dateOfStartExecution = ?, " +
                    "dateOfEndExecution = ?, idcustomer = ?, description = ? where idorder = ?";

    public OrderDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
        findAll();
    }

    public HashMap<Integer, Order> findAll() {

        try {
            Statement statement = DBManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                Order order;
                int id = resultSet.getInt("idorder");

                if(orders.containsKey(id))
                    order = orders.get(id);
                else
                    order = new Order();

                fillOrder(order, resultSet);
                //добавление заказа в список покупателя, если его еще там нет
                if(!order.getCustomer().getOrders().contains(order))
                    order.getCustomer().getOrders().add(order);
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
            fillOrderStatement(order, preparedStatement);

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
        Order order;
        if(orders.containsKey(id))
            order = orders.get(id);
        else
            order = new Order();
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

    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement(DELETE_ONE);
            preparedStatement.setInt(1, id);
            int numOfAffectedRows = preparedStatement.executeUpdate();
            if (numOfAffectedRows != 1){
                throw new SQLException();
            }
            if(orders != null)
                orders.remove(id);

        }catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Order order) {
        try {
            PreparedStatement preparedStatement = DBManager.getConnection().prepareStatement(UPDATE);
            fillOrderStatement(order, preparedStatement);
            preparedStatement.setInt(7, order.getID());
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

    private void fillOrderStatement(Order order, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, order.getOrderNumber());
        preparedStatement.setDate(2, new Date(order.getOrderDate().getTime()));
        preparedStatement.setDate(3, new Date(order.getDateOfStartExecution().getTime()));
        preparedStatement.setDate(4, new Date(order.getDateOfEndExecution().getTime()));
        preparedStatement.setInt(5, order.getCustomerID());
        preparedStatement.setString(6, order.getDescription());
    }

    public boolean isCustomerExists(int id){
        return customerDao.get(id) != null;
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
