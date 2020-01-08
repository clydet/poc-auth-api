package org.hcqis.model;

public class Registry {
    public String clientName;

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return this.clientName;
    }

    @Override
    public String toString() {
        return "Registry [clientName=" + clientName + "]";
    }
}