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

    //считывает дату из коммандной строки
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

    //переводит дату в строку в заданном формате
    protected String formatDate(Date date){
        if(date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(date);
        }
        else return "";
    }
}
