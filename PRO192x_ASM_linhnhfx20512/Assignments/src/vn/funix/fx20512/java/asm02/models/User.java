package vn.funix.fx20512.java.asm02.models;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialNumberUID = 1L;
    private String name;
    private String customerId;

    public User() {
    }

    public User(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
