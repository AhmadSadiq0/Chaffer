package app.chaffer;

import android.media.Image;

import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mac on 12/02/2018.
 */

public class Request {

    private String userId ;
    private String requestId ;
    private String userName ;
    private String offerDescription ;
    private String timeToDeliver ;
    private String pickUplongitude ;
    private String pickUplatitude ;
    private String deliverLongitude ;
    private String deliverLatitude ;
    private String pickUpLocationDescription ;
    private String drofOffLocationDescription ;
    private String packageDesription ;
    private String status ;

    //    private String orderplaceImage ;



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getTimeToDeliver() {
        return timeToDeliver;
    }

    public void setTimeToDeliver(String timeToDeliver) {
        this.timeToDeliver = timeToDeliver;
    }

    public String getPickUplongitude() {
        return pickUplongitude;
    }

    public void setPickUplongitude(String pickUplongitude) {
        this.pickUplongitude = pickUplongitude;
    }

    public String getPickUplatitude() {
        return pickUplatitude;
    }

    public void setPickUplatitude(String pickUplatitude) {
        this.pickUplatitude = pickUplatitude;
    }

    public String getDeliverLongitude() {
        return deliverLongitude;
    }

    public void setDeliverLongitude(String deliverLongitude) {
        this.deliverLongitude = deliverLongitude;
    }

    public String getDeliverLatitude() {
        return deliverLatitude;
    }

    public void setDeliverLatitude(String deliverLatitude) {
        this.deliverLatitude = deliverLatitude;
    }

    public String getPickUpLocationDescription() {
        return pickUpLocationDescription;
    }

    public void setPickUpLocationDescription(String pickUpLocationDescription) {
        this.pickUpLocationDescription = pickUpLocationDescription;
    }

    public String getDrofOffLocationDescription() {
        return drofOffLocationDescription;
    }

    public void setDrofOffLocationDescription(String drofOffLocationDescription) {
        this.drofOffLocationDescription = drofOffLocationDescription;
    }

    public String getPackageDesription() {
        return packageDesription;
    }

    public void setPackageDesription(String packageDesription) {
        this.packageDesription = packageDesription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public Request(String userId, String requestId, String userName, String offerDescription, String timeToDeliver, String pickUplatitude, String pickUplongitude, String deliverLatitude, String deliverLongitude, String pickUpLocationDescription, String drofOffLocationDescription, String packageDesription, String status) {
        this.userId = userId;
        this.requestId = requestId;
        this.userName = userName;
        this.offerDescription = offerDescription;
        this.timeToDeliver = timeToDeliver;
        this.pickUplatitude = pickUplatitude;
        this.pickUplongitude = pickUplongitude;
        this.deliverLongitude = deliverLongitude;
        this.deliverLatitude = deliverLatitude;
        this.pickUpLocationDescription = pickUpLocationDescription;
        this.drofOffLocationDescription = drofOffLocationDescription;
        this.packageDesription = packageDesription;
        this.status = status;
    }




    public Request( String requestId,  String offerDescription, String timeToDeliver, String pickUplatitude, String pickUplongitude, String deliverLatitude, String deliverLongitude, String pickUpLocationDescription, String drofOffLocationDescription, String packageDesription, String status) {
       // this.userId = userId;
        this.requestId = requestId;
       // this.userName = userName;
        this.offerDescription = offerDescription;
        this.timeToDeliver = timeToDeliver;
        this.pickUplatitude = pickUplatitude;
        this.pickUplongitude = pickUplongitude;
        this.deliverLongitude = deliverLongitude;
        this.deliverLatitude = deliverLatitude;
        this.pickUpLocationDescription = pickUpLocationDescription;
        this.drofOffLocationDescription = drofOffLocationDescription;
        this.packageDesription = packageDesription;
        this.status = status;
    }






}
