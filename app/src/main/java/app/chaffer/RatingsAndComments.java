package app.chaffer;

/**
 * Created by Mac on 08/05/2018.
 */

public class RatingsAndComments {

    private String ratings ;
    private String comments ;


    public RatingsAndComments(String ratings, String comments) {
        this.ratings = ratings;
        this.comments = comments;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }




}
