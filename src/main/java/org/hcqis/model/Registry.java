package org.hcqis.model;

public class Registry {
    public String user;
    public String emailAddress;
    public String mfaDeviceId;

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return this.user;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setMfaDeviceId(String mfaDeviceId) {
        this.mfaDeviceId = mfaDeviceId;
    }

    public String getMfaDeviceId() {
        return this.mfaDeviceId;
    }

    @Override
    public String toString() {
        return "Registry [emailAddress=" + emailAddress + ", mfaDeviceId=" + mfaDeviceId + ", user=" + user + "]";
    }
}