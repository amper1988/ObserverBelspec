package utils;

public class UserManager {
    private static UserManager instanse;
    private String mLogin;
    private String mPassword;
    private String mFullName;
    private boolean mRegistered;
    private String organization;
    private String mParking;

    private UserManager() {
        this.mLogin = "";
        this.mPassword = "";
        this.mFullName = "";
        this.organization = "";
        this.mRegistered = false;
        this.mParking = "";
    }

    public static UserManager getInstanse() {
        if (instanse == null) {
            instanse = new UserManager();
        }
        return instanse;

    }

    public void logout() {
        this.mRegistered = false;
        this.mLogin = "";
        this.mPassword = "";
        this.mFullName = "";
        this.organization = "";
        this.mParking = "";
    }

    public String getmParking() {
        return this.mParking;
    }

    public void setParking(String parking) {
        this.mParking = parking;
    }

    public String getmFullName() {
        return mFullName;
    }

    public void setmFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public static void setInstanse(UserManager instanse) {
        UserManager.instanse = instanse;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setUserData(String login, String pasword, String fullName, boolean registered, String organization) {
        this.mLogin = login;
        this.mPassword = pasword;
        this.mRegistered = registered;
        this.mFullName = fullName;
        if (organization != null) {
            this.organization = organization;
        } else {
            this.organization = "";
        }
        this.mParking = "";
    }

    public String getmLogin() {

        return mLogin;
    }

    public void setmLogin(String mLogin) {
        this.mLogin = mLogin;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public boolean ismRegistered() {
        return mRegistered;
    }

    public void setmRegistered(boolean mRegistered) {
        this.mRegistered = mRegistered;
    }
}
