import java.util.ArrayList;
import java.util.Set;


public class Customer {

    private Integer ID;
    private String firstName;
    private String lastName;
    private String patronymic;
    private int age;
    private String email;
    private String phoneNumber;

    private ArrayList<Order> orders;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {       this.ID = ID;    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Customer(){

    }

}
