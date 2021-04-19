import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class OrderView extends View<Order>{

    OrderView(Controller<Order> controller) {
        super(controller);
    }

    @Override
    public void mainDialog(){
        System.out.println("1. Вывести все зазказы");
        System.out.println("2. Изменить заказ");
        System.out.println("3. Удалить заказ");
        System.out.println("4. Создать новый заказ");
        System.out.println("5. Вывести один заказ");
        System.out.println("6. Назад");
        int subOption = Integer.parseInt(scanner.nextLine());
        switch (subOption){
            case 1 -> {
                HashMap<Integer, Order> orders = controller.findAll();
                for ( Order order: orders.values()) {
                    printInformationAboutOrder(order);
                }

            }
            //TODO: заменить на изменение только одного поля
            case 2 -> {
                System.out.println("Введите id заказа для изменения информации:");
                int id = Integer.parseInt(scanner.nextLine());
                Order order = controller.get(id);
                if(fillInformation(order)){
                    if(controller.update(order))
                        System.out.println("Данные успешно обновлены.");
                    else
                        System.out.println("Не удалось обновить данные");
                }
                else System.out.println("Ошибка ввода. Отмена изменений.");
            }
            case 3->{
                System.out.println("Введите id заказа для удаления информации:");
                int id = Integer.parseInt(scanner.nextLine());
                if(controller.delete(id))
                    System.out.println("Удаление прошло успешно.");
                else
                    System.out.println("Ошибка. Не удалось удалить информацию о заказе с указанным id.");
            }
            case 4 -> {
                Order order = new Order();

                //если заполнение информации не прошло успешно, выход
                if(!fillInformation(order))
                    break;

                if(controller.add(order))
                    System.out.println("Добавление прошло успешно");
                else
                    System.out.println("Не удалось добавить");

            }

            case 5 ->{
                System.out.println("Введите id заказа:");
                int id = Integer.parseInt(scanner.nextLine());
                Order order = controller.get(id);
                if(order != null) {
                    printInformationAboutOrder(order);
                }
                else{
                    System.out.println("Не удалось найти заказ с указанным id");
                }
            }

        }
    }

    private void printInformationAboutOrder(Order order){
        System.out.println("ID: " + order.getID());
        System.out.println("Номер: " + order.getOrderNumber());
        System.out.println("Дата оформления: " + formatDate(order.getOrderDate()));
        System.out.println("Дата начала исполнения: " + formatDate(order.getDateOfStartExecution()));
        System.out.println("Дата окончания исполнения: " + formatDate(order.getDateOfEndExecution()));
        System.out.println("Описание: " + order.getDescription());
        System.out.println("ID покуателя: " + order.getCustomerID());
        System.out.println("Имя и фамилия покупателя: " + order.getCustomer().getFirstName() + " " +
                order.getCustomer().getLastName());
        System.out.println();
    }

    private boolean fillInformation(Order order){
        System.out.println("Введите номер заказа:");
        order.setOrderNumber(Integer.valueOf(scanner.nextLine()));
        System.out.println("Введите дату заказа dd/MM/yyyy:");
        Date date = readDate();
        if(date != null){
            order.setOrderDate(date);
        }
        else return false;
        System.out.println("Введите дату начала исполнения заказа dd/MM/yyyy:");
        date = readDate();
        if(date != null){
            order.setDateOfStartExecution(date);
        }
        else return false;
        System.out.println("Введите дату конца исполнения заказа dd/MM/yyyy:");
        date = readDate();
        if(date != null){
            order.setDateOfEndExecution(date);
        }
        else return false;

        System.out.println("Введите id покупателя:");
        order.setCustomerID(Integer.parseInt(scanner.nextLine()));

        System.out.println("Введите описание:");
        order.setDescription(scanner.nextLine());
        return true;
    }

}
