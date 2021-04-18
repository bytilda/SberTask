import java.util.HashMap;

public class CustomerController extends Controller<Customer>{
    private CustomerDao customerDao;

    CustomerController(DAO<Customer> dao) {
        super(dao);
    }


    public HashMap<Integer, Customer> findAll(){
        return customerDao.findAll();
    }

    public boolean add(Customer customer){
        customerDao.add(customer);
        return true;
    }

}
