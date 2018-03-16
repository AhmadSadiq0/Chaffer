package app.chaffer;

/**
 * Created by Mac on 15/03/2018.
 */

public class Notifications {

    private String notificationId ;
    private String description ;
    private String user_Id ;
    private String relationShipId ;
    //0 for offer 1 for order
    private String relationShipType ;
    //0 for un read 1 for read
    private String isRead ;




    public Notifications(String description){
        this.description = description;

    }


    public Notifications(String notificationId, String description, String user_Id, String relationShipId, String relationShipType, String isRead) {
        this.notificationId = notificationId;
        this.description = description;
        this.user_Id = user_Id;
        this.relationShipId = relationShipId;
        this.relationShipType = relationShipType;
        this.isRead = isRead;
    }



    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public String getRelationShipId() {
        return relationShipId;
    }

    public void setRelationShipId(String relationShipId) {
        this.relationShipId = relationShipId;
    }

    public String getRelationShipType() {
        return relationShipType;
    }

    public void setRelationShipType(String relationShipType) {
        this.relationShipType = relationShipType;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }




}
