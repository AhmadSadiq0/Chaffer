package app.chaffer;

/**
 * Created by Mac on 24/04/2018.
 */

public class ReceivingOrder {


    private  String orderId ;
    private  String description ;
    private  String desLng ;
    private  String desLat ;
    private  String deliveryPersonId ;
    private  String deliveryPersonName ;
    private  String pickUpDes ;
    private  String desDes ;
    private  String pkgDes ;
    private String pickUpLat ;
    private String pickUpLng ;

    public String getPickUpLat() {
        return pickUpLat;
    }

    public void setPickUpLat(String pickUpLat) {
        this.pickUpLat = pickUpLat;
    }

    public String getPickUpLng() {
        return pickUpLng;
    }

    public void setPickUpLng(String pickUpLng) {
        this.pickUpLng = pickUpLng;
    }



    public String getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(String deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    public String getDeliveryPersonName() {
        return deliveryPersonName;
    }

    public void setDeliveryPersonName(String deliveryPersonName) {
        this.deliveryPersonName = deliveryPersonName;
    }



    public String getPickUpDes() {
        return pickUpDes;
    }

    public void setPickUpDes(String pickUpDes) {
        this.pickUpDes = pickUpDes;
    }

    public String getDesDes() {
        return desDes;
    }

    public void setDesDes(String desDes) {
        this.desDes = desDes;
    }

    public String getPkgDes() {
        return pkgDes;
    }

    public void setPkgDes(String pkgDes) {
        this.pkgDes = pkgDes;
    }



    public ReceivingOrder(String orderId, String description, String desLng, String desLat, String deliveryPersonId, String deliveryPersonName,
                          String pickUpDes, String desDes, String pkgDes,String pickUpLat,String pickUpLng) {
        this.orderId = orderId;
        this.description = description;
        this.desLng = desLng;
        this.desLat = desLat;
        this.deliveryPersonId = deliveryPersonId;
        this.deliveryPersonName = deliveryPersonName;
        this.pickUpDes=pickUpDes ;
        this.desDes=desDes ;
        this.pkgDes=pkgDes ;
        this.pickUpLat=pickUpLat ;
        this.pickUpLng=pickUpLng ;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesLng() {
        return desLng;
    }

    public void setDesLng(String desLng) {
        this.desLng = desLng;
    }

    public String getDesLat() {
        return desLat;
    }

    public void setDesLat(String desLat) {
        this.desLat = desLat;
    }






}
