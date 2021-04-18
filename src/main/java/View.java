import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public abstract class View<T> {
    Controller<T> controller;
    Scanner scanner;
    View(Controller<T> controller){
        this.controller = controller;
        scanner = new Scanner(System.in);
    }

    public abstract void mainDialog();

    protected Date readDate(){
        Date date = null;
        String sdate = scanner.nextLine();
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = s.parse(sdate);
        } catch (ParseException e) {
            System.out.println("Дата была введена в неверном формате");
        }
        return date;
    }

    protected String formatDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
}
