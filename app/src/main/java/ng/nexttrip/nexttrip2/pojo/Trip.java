package ng.nexttrip.nexttrip2.pojo;

public class Trip {

    int id;
    String driver_id;
    String user_id;
    String pick_location;
    String lat;
    String lng;
    String drop_location;
    String date_requested;
    String accept;
    String journey_status;
    String rate;
    String amount_made;
    String payment_method;
    String payment_made;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPick_location() {
        return pick_location;
    }

    public void setPick_location(String pick_location) {
        this.pick_location = pick_location;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDrop_location() {
        return drop_location;
    }

    public void setDrop_location(String drop_location) {
        this.drop_location = drop_location;
    }

    public String getDate_requested() {
        return date_requested;
    }

    public void setDate_requested(String date_requested) {
        this.date_requested = date_requested;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getJourney_status() {
        return journey_status;
    }

    public void setJourney_status(String journey_status) {
        this.journey_status = journey_status;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getAmount_made() {
        return amount_made;
    }

    public void setAmount_made(String amount_made) {
        this.amount_made = amount_made;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_made() {
        return payment_made;
    }

    public void setPayment_made(String payment_made) {
        this.payment_made = payment_made;
    }
}
