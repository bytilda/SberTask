import java.util.Date;

public class CustomerController extends Controller<Customer>{
    CustomerController(DAO<Customer> dao) {
        super(dao);
    }


    @Override
    public boolean add(Customer customer){
        Date dateNow = new Date();
        long years = (dateNow.getTime() - customer.getDateOfBirth().getTime()) / (1000L*60*60*24*365);
        if (years < 18){
            System.out.println("Ошибка. Пользователю меньше 18 лет.");
            return false;
        }
        return dao.add(customer);
    }

}
