import java.util.Date;


public class Order {

    private Integer ID;
    private Integer orderNumber;

    private Integer customerID;
    private Customer customer;

    private Date orderDate;
    private Date dateOfStartExecution;
    private Date dateOfEndExecution;

    //TODO:решить проблемку с выводом описания
    private String description = "";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDateOfStartExecution() {
        return dateOfStartExecution;
    }

    public void setDateOfStartExecution(Date dateOfStartExecution) {
        this.dateOfStartExecution = dateOfStartExecution;
    }

    public Date getDateOfEndExecution() {
        return dateOfEndExecution;
    }

    public void setDateOfEndExecution(Date dateOfEndExecution) {
        this.dateOfEndExecution = dateOfEndExecution;
    }


}
