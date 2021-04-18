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

            }
            case 4->{

            }
        }
    }


}
