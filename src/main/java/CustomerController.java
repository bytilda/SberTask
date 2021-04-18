import java.util.HashMap;

public class CustomerController extends Controller<Customer>{
    CustomerController(DAO<Customer> dao) {
        super(dao);
    }


    @Override
    public boolean add(Customer customer){

        return dao.add(customer);
    }

}
