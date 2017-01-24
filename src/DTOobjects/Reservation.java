package DTOobjects;

import java.sql.Timestamp;

/**
 * Created by alanziberi on 26/10/2016.
 */
public class Reservation {

    private int adId;
    private int userId;
    private Timestamp timestamp;
    private Long bookIsbn;
    private String userUsername;
    private int userPhonenumber;

    public Reservation() {

    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Long getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(Long bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public int getUserPhonenumber() {
        return userPhonenumber;
    }

    public void setUserPhonenumber(int userPhonenumber) {
        this.userPhonenumber = userPhonenumber;
    }
}