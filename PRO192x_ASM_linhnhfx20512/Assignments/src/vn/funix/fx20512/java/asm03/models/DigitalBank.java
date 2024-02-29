package vn.funix.fx20512.java.asm03.models;

import vn.funix.fx20512.java.asm02.models.Bank;
import vn.funix.fx20512.java.asm04.dao.CustomerDao;

import java.util.Map;
import java.util.Scanner;

public class DigitalBank extends Bank {

    public DigitalBank(String bankName) {
        super(bankName);
    }

    public DigitalCustomer getDiCusById(String customerId) {
        for (int i=0; i< getCustomer().size(); i++) {
            if (customerId.equals(getCustomer().get(i).getCustomerId())) {
                return (DigitalCustomer) getCustomer().get(i);
            }
        }
        return null;
    }

    public void addDiCus(String customerId, String name) {
        DigitalCustomer cus = new DigitalCustomer();
        cus.setCustomerId(customerId);
        cus.setName(name);
        this.getCustomer().add(cus);
    }

    public void withdraw(String customerId, String accountNumber, double amount) {
        if (getDiCusById(customerId) != null) {
            getDiCusById(customerId).withdraw(accountNumber, amount);
        } else {
            System.out.println("Khong the thuc hien lenh rut tien do nguoi dung khong ton tai!");
        }
    }

    //   Hàm này dùng để kiểm tra cccd nhập đúng cú pháp hay không
    public void checkCustomerId(String cccd, String name) {
        boolean check = false;
        int num = 0;
        int test = 0;
        Map<String, String> map = provinceCode();
        do {
            try {
                if (cccd.length() == 12) {
                    check = true;
                }
                num = 0;
                for (int i = 0; i < cccd.length(); i++) {
                    boolean a = Character.isDigit(cccd.charAt(i));
                    if (a) {
                        num++;
                    }
                }
                if (check && num == 12 && map.containsKey(cccd.substring(0, 3)) && getDiCusById(cccd) == null) {
                    this.addDiCus(cccd,name);
                    System.out.println("Da them khach hang " + cccd + " vao danh sach");
                    test = 1;
                } else if (cccd.equalsIgnoreCase("no")) {
                    test = 1;
                } else if (!cccd.equalsIgnoreCase("no")) {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("So CCCD khong hop le. Vui long nhap lai hoac nhan 'No' de thoat");
            }
        } while (test != 1);
    }


    //    Hàm để để nhập và check xem CCCD đã tồn tại trong hệ thống chưa
    public void checkCccd(String customerId, int option) {
        Scanner sc = new Scanner(System.in);
        int check = 0;
        do {
            try {
                if (getDiCusById(customerId) != null) {
                    this.checkAccountNumber(customerId, option);
                    check = 1;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("So CCCD khong hop le. Vui long nhap lai!");
            }
        } while (check != 1);
    }


    //    Hàm này để check xem STK co dung 6 chu so hay khong
    public void checkAccountNumber(String customerId, int option) {
        Scanner sc = new Scanner(System.in);
        String accNum;
        int num = 0;
        int check = 0;
        do {
            try {
                System.out.println("Vui long nhap ma STK gom 6 chu so: ");
                accNum = sc.next();
                num = 0;
                for (int i = 0; i < accNum.length(); i++) {
                    boolean a = Character.isDigit(accNum.charAt(i));
                    if (a) {
                        num++;
                    }
                }
                if (accNum.length() == 6 && num == 6 && !isAccountNumberExisted(accNum)) {
                    if (option == 1) {
                        double balance = checkBalance();
                        SavingAccount a = new SavingAccount(accNum, balance);
                        this.addAccount(customerId, a);
                    } else if (option == 2) {
                        LoanAccount a = new LoanAccount(accNum);
                        this.addAccount(customerId, a);
                    }
                    System.out.println("So tai khoan da duoc them. Xin cam on quy khach!");
                    check = 1;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("So tai khoan khong hop le. Xin vui long nhap lai!");
            }
        } while (check != 1);
    }

    public void showCustomer() {
        CustomerDao.list();
    }
}
