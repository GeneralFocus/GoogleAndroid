package ng.nexttrip.nexttrip2.pojo;

public class User {

    private int id;
    private String name;
    private String email;
    private String phone_number;
    private String user_type;
    private String archived;
    private String payment_method;

    public User(int id, String name, String email, String phone_number, String user_type,
                String archived, String payment_method){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.user_type = user_type;
        this.archived = archived;
        this.payment_method = payment_method;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getArchived() {
        return archived;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public void setArchived(String archived) {
        this.archived = archived;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
}
