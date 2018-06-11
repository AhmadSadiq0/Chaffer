package app.chaffer;

/**
 * Created by Mac on 07/05/2018.
 */

public class Inbox {
    private String userName ;
    private String boxName  ;


    public Inbox(String userName, String boxName) {
        //here username is second person username who will be in the chat
        this.userName = userName;
        this.boxName = boxName;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }
}
