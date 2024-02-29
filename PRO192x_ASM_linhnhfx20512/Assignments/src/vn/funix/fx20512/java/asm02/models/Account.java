package vn.funix.fx20512.java.asm02.models;

import vn.funix.fx20512.java.asm03.models.Transaction;
import vn.funix.fx20512.java.asm04.dao.AccountDao;
import vn.funix.fx20512.java.asm04.dao.CustomerDao;
import vn.funix.fx20512.java.asm04.dao.TransactionDao;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private String customerId;
    private String accountNumber;
    private double balance;
    private List<Transaction> transaction;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = 0;
    }

    public Account(String customerId, String accountNumber, double balance) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = 0;
    }

    public void setTransaction() {
        this.transaction = new ArrayList<Transaction>();
    }

    public List<Transaction> getTransaction() {
        return transaction;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isPremium() {
        if (this.balance >= 10_000_000) {
            return true;
        }
        return false;
    }

    public List<Transaction> getTransactions() { // Sử dụng Stream/Lambda
        return TransactionDao.list().stream().filter(trans -> trans.getAccountNumber().equals(this.accountNumber)).collect(Collectors.toList());
    }

    public String getCustomerId() {
        return customerId;
    }

    public Customer getCustomer() {
        for (Customer cus : CustomerDao.list()) {
            if (cus.getCustomerId().equals(this.customerId)) {
                return cus;
            }
        }
        return null;
    }

    public void displayTransactionList() {
        for (Transaction trans : getTransactions()) {
//         Chỉ in ra các giao dịch thành công
            if (trans.isStatus()) {
                String a = this.getAccountNumber();
                String b = trans.getType();
                String c = String.format("%,.0f", trans.getAmount());
                String d = trans.getTime();
                String e;
                if (trans.getType().equals("DEPOSIT")) {
                    e = "";
                } else {
                    e = "-";
                }
                System.out.print("[GD]  ");
                System.out.print(a);
                System.out.printf(" | %-15s", b);
                System.out.printf(" | %-14s", e + c);
                System.out.println("đ |   " + d);
            }
        }
    }

//    Hàm này tạo Transaction và update AccountDao cho các giao dịch thông thường
    public void createTransaction1(double amount, String time, boolean status, String type) throws IOException {
        Transaction trans = new Transaction(getAccountNumber(), amount, time, type, status);
        TransactionDao.addTrans(trans);
        this.transaction = getTransactions();
//      Chỉ thực hiện chuyển tiền với các giao dịch thành công
        if (status) {
            if (trans.getType().equals("DEPOSIT")) {
                this.balance = getBalance() + amount;
                AccountDao.update(this);
            } else {
                this.balance = getBalance() - amount;
                AccountDao.update(this);
            }
        }
    }

//   Hàm này tạo  Transaction và update AccountDao khi tạo balance cho tài khoản mới
    public void createTransaction2(double amount, String time, boolean status, String type) throws IOException {
        Transaction trans = new Transaction(getAccountNumber(), amount, time, type, status);
        TransactionDao.addTrans(trans);
        this.transaction = getTransactions();
//      Chỉ thực hiện chuyển tiền với các giao dịch thành công
        if (status) {
            this.balance = amount;
            AccountDao.update(this);
        }
    }

    public boolean isAccountNumberExisted(String accNum) {
        for (Account acc : AccountDao.list()) {
            if (acc.getAccountNumber().equals(accNum)) {
                return true;
            }
        }
        return false;
    }

    public double checkBalance(Scanner scanner) {
        boolean check;
        double balance = 0;
        do {
            check = true;
            try {
                System.out.println("Nhap so du: ");
                balance = scanner.nextDouble();
                if (balance < 50_000) {
                    throw new Exception();
                } else {
                    System.out.println("So du da duoc them vao tai khoan. Xin cam on!");
                }
            } catch (Exception e) {
                check = false;
                System.out.println("So du tai khoan phai phai la so va toi thieu 50,000 VND. Xin vui long nhap lai!");
                scanner.nextLine();
            }
        } while (!check);
        return balance;
    }
}
