package app.chaffer;

/**
 * Created by Mac on 12/02/2018.
 */

public class Messages {

    private String userName,messageDescription ;
    private String senderImage ;


    public Messages(String userName, String messageDescription, String senderImage){

            this.userName=userName ;
            this.messageDescription=messageDescription ;
            this.senderImage=senderImage ;

    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getmessageDescription() {
        return messageDescription;
    }

    public void setmessageDescription(String messageDescription) {
        this.messageDescription = messageDescription;
    }


    public String getsenderImage() {
        return senderImage;
    }

    public void setsenderImage(String senderImage) {
        this.senderImage = senderImage;
    }




}
