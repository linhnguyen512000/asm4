package vn.funix.fx20512.java.asm02;

import vn.funix.fx20512.java.asm02.models.Bank;

import java.util.Scanner;

public class Asm02 {
    public static final Bank bank = new Bank("A");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String n;
        do {
        System.out.println("+----------+-------------------------+----------+");
        System.out.println("|  NGAN HANG SO | FX20512@v2.0.0                |");
        System.out.println("| 1. Them khach hang                            |");
        System.out.println("| 2. Them tai khoan cho khach hang              |");
        System.out.println("| 3. Hien thi danh sach khach hang              |");
        System.out.println("| 4. Tim theo CCCD                              |");
        System.out.println("| 5. Tim theo ten khach hang                    |");
        System.out.println("| 0. Thoat                                      |");
        System.out.println("+----------+-------------------------+----------+");
        System.out.println("Chuc nang:");
            n = sc.next();
            switch (n) {
                case "1":
                    bank.checkCustomerId();
                    break;
                case "2":
                    bank.checkCccd();
                    break;
                case "3":
                    bank.displayInfo();
                    break;
                case "4":
                    bank.searchCustomerByCCCD();
                    break;
                case "5":
                    bank.searchCustomerByName();
                case "0":
                    break;
                default:
                    System.out.println("Vui long nhap so hop le");
                    break;
            }
        } while (!n.equals("0"));
    }
}
