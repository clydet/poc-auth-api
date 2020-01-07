package org.hcqis.model;

public class Registry {
    public String clientName;
    public String emailAddress;
    public String mfaDeviceId;

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return this.clientName;
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
        return "Registry [emailAddress=" + emailAddress + ", mfaDeviceId=" + mfaDeviceId + ", user=" + clientName + "]";
    }
}