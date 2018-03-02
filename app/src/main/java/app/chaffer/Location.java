package app.chaffer;

/**
 * Created by Mac on 01/03/2018.
 */

public class Location {


    private String lat ;
    private String lng ;
    private String locationName ;
    private String locationAddress ;

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }



    public Location(String locationName, String locationAddress, String lat, String lng) {
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.lat = lat;
        this.lng = lng;
    }








}
