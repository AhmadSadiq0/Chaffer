package app.chaffer;

/**
 * Created by Mac on 24/04/2018.
 */

public class DeliveryOrder {


    private  String orderId ;
    private  String description ;
    private  String desLng ;
    private  String desLat ;
    private  String receiverId ;
    private  String receiverName ;
    private  String pickUpDes ;
    private  String desDes ;
    private  String pkgDes ;
    private String pickUpLng ;
    private String pickUpLat ;

    public String getPickUpLng() {
        return pickUpLng;
    }

    public void setPickUpLng(String pickUpLng) {
        this.pickUpLng = pickUpLng;
    }

    public String getPickUpLat() {
        return pickUpLat;
    }

    public void setPickUpLat(String pickUpLat) {
        this.pickUpLat = pickUpLat;
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



    public DeliveryOrder(String orderId, String description, String desLng, String desLat, String receiverId, String receiverName,
                         String pickUpDes,String desDes,String pkgDes,String pickUpLat,String pickUpLng) {
        this.orderId = orderId;
        this.description = description;
        this.desLng = desLng;
        this.desLat = desLat;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
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

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }





}
