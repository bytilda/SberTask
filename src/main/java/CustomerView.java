import java.util.Date;
import java.util.HashMap;

public class CustomerView extends View<Customer>{

    CustomerView(Controller<Customer> controller) {
        super(controller);
    }

    @Override
    public void mainDialog() {
        System.out.println("1. Вывести данные о всех покупателей");
        System.out.println("2. Изменить данные о покупателе");
        System.out.println("3. Удалить данные о покупателе");
        System.out.println("4. Внести данные о новом покупателе");
        System.out.println("5. Вывести данные об одном покупателе");
        System.out.println("6. Назад");
        int subOption  = Integer.parseInt(scanner.nextLine());
        switch (subOption) {
            case 1 -> {
                HashMap<Integer, Customer> customers = controller.findAll();
                for (Customer customer: customers.values()) {
                    printInformationAboutCustomer(customer);
                }
            }

            case 4->{
                Customer customer = new Customer();
                System.out.println("Введите имя:");
                customer.setFirstName(scanner.nextLine());

                System.out.println("Введите фамилию:");
                customer.setLastName(scanner.nextLine());

                System.out.println("Введите отчество при наличии, иначе Enter:");
                customer.setPatronymic(scanner.nextLine());

                System.out.println("Введите номер при наличии, иначе Enter:");
                customer.setPhoneNumber(scanner.nextLine());

                System.out.println("Введите email: ");
                customer.setEmail(scanner.nextLine());

                System.out.println("Введите дату рождения: ");
                Date date = readDate();
                if(date != null)
                    customer.setDateOfBirth(date);
                else{
                    System.out.println("Дата была введена неверно");
                    break;
                }
                if(controller.add(customer))
                    System.out.println("Добавление прошло успешно");
                else
                    System.out.println("Не удалось добавить");

            }
            case 5 -> {
                System.out.println("Введите id покупателя:");
                int id = Integer.parseInt(scanner.nextLine());
                Customer customer = controller.get(id);
                if(customer != null){
                    printInformationAboutCustomer(customer);
                }
                else{
                    System.out.println("Покупатель с указанным id не найден");
                }
            }
        }
    }

    private void printInformationAboutCustomer(Customer customer){
        System.out.println("ID: " + customer.getID());
        System.out.println("Имя: " + customer.getFirstName());
        System.out.println("Фамилия: " + customer.getLastName());
        System.out.println("Отчество: " + customer.getPatronymic());
        System.out.println("E-mail: " + customer.getEmail());
        System.out.println("Номер телефона: " + customer.getPhoneNumber());
        System.out.println("Дата рождения: " + formatDate(customer.getDateOfBirth()));
        System.out.println();
    }


}
