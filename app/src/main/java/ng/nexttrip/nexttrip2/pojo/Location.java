package ng.nexttrip.nexttrip2.pojo;

public class Location {

    int id;
    String driver_id;
    String lat;
    String lng;
    String online;

    public Location(int id,
            String driver_id,
            String lat,
            String lng,
            String online) {

        this.id = id;
        this.driver_id = driver_id;
        this.lat = lat;
        this.lng = lng;
        this.online = online;
    }

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

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}
