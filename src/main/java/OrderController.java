public class OrderController extends Controller<Order>{
    OrderController(DAO<Order> dao) {
        super(dao);
    }

    @Override
    public boolean add(Order order) {
        if (order.getDateOfStartExecution().after(order.getDateOfEndExecution())) {
            System.out.println("Ошибка. Дата начала исполнения заказа позже даты окончания исполнения.");
            return false;
        } else if (!((OrderDao) dao).isCustomerExists(order.getCustomerID())) {
            System.out.println("Ошибка. Не существует покупателя с указазным id.");
            return false;
        }
        else if(dao.findAll().containsKey(order.getID())){
            System.out.println("Ошибка. Заказ с указанным номером уже существует.");
            return false;
        }
        else return dao.add(order);
    }


}
