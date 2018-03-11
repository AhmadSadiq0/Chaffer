package app.chaffer;

/**
 * Created by Mac on 11/03/2018.
 */

public class Offer {
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private  String time;
    private String offerid ;
    private String senderID ;
    private String senderName ;
    private String offerDescription ;
    private String amount ;
    private String offerStatus ;
    private Request request ;

    public String getOfferid() {
        return offerid;
    }

    public void setOfferid(String offerid) {
        this.offerid = offerid;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }



    public Offer(String offerid, String senderID, String senderName, String offerDescription, String amount, String offerStatus,String time , Request request) {
        this.offerid = offerid;
        this.senderID = senderID;
        this.senderName = senderName;
        this.offerDescription = offerDescription;
        this.amount = amount;
        this.offerStatus = offerStatus;
        this.request = request;
        this.time=time ;
    }




}
