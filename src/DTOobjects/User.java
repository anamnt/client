package DTOobjects;

/**
 * Created by Emma og Thomas on 17-10-2016.
 */
public class User {

    // Attributter oprettes
    private int userId;
    private String username;
    private String password;
    private int phonenumber;
    private String address;
    private String email;
    private int mobilepay;
    private int cash;
    private int transfer;
    private int type;


    //Construtor oprettes
    public User() {

    }

    // Getters og setters oprettes
    public int getId() {
        return userId;
    }

    public void setId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getPhonenumber() { return phonenumber; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMobilepay() { return mobilepay; }

    public void setMobilepay(int mobilepay) { this.mobilepay = mobilepay; }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getTransfer() {
        return transfer;
    }

    public void setTransfer(int transfer) {
        this.transfer = transfer;
    }

    public int getType() { return type; }

    public void setType(int type) {
        this.type = type;
    }

    public void setMobilePay(int mobilePay) {
        this.mobilepay = mobilePay;
    }



}


