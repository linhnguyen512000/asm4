package vn.funix.fx20512.java.asm03.models;

import java.io.Serializable;
import java.util.UUID;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String accountNumber;
    private double amount;
    private String time;
    private String type;
    private boolean status;

    public Transaction (String accountNumber, double amount, String time, boolean status) {
     id = String.valueOf(UUID.randomUUID());
     this.accountNumber = accountNumber;
     this.amount = amount;
     this.time = time;
     this.status = status;
    }

    public Transaction (String accountNumber, double amount, String time, String type, boolean status) {
        id = String.valueOf(UUID.randomUUID());
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.time = time;
        this.type = type;
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }

    public boolean isStatus() {
        return status;
    }
}
