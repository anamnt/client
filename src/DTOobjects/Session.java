package DTOobjects;

/**
 * Created by alanziberi on 21/10/2016.
 */
public class Session {

    private String sessionId;
    private int userId;
    private int userType;

    public Session() {

    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

}

