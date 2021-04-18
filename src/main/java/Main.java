import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        CustomerDao customerDao = new CustomerDao();
        OrderDao orderDao = new OrderDao(customerDao);
        CustomerController customerController = new CustomerController(customerDao);
        OrderController orderController = new OrderController(orderDao);
        OrderView orderView = new OrderView(orderController);
        CustomerView customerView = new CustomerView(customerController);

        int option;
        Scanner scanner = new Scanner(System.in);
        do{
            System.out.println("1. Операции с заказами");
            System.out.println("2. Операции с покупателями");
            System.out.println("3. Выход");
            option = scanner.nextInt();
            switch (option){
                case 1 ->{
                    orderView.mainDialog();
                }
                case 2 ->{
                    customerView.mainDialog();
                }
            }

        }while(option != 3);

    }

}
