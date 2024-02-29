package vn.funix.fx20512.java.asm03;

import vn.funix.fx20512.java.asm02.models.Customer;
import vn.funix.fx20512.java.asm03.models.DigitalBank;
import vn.funix.fx20512.java.asm03.models.DigitalCustomer;
import vn.funix.fx20512.java.asm03.models.Utils;

import java.util.Scanner;

public class Asm03 {
    public static final int EXIT_COMMAND_CODE = 0;
    public static final int EXIT_ERROR_CODE = -1;
    public static final Scanner scanner = new Scanner(System.in);
    public static final DigitalBank activeBank = new DigitalBank("A");
    public static final String CUSTOMER_ID = "001200000000";
    public static final String CUSTOMER_NAME = "FUNIX";


    public static void main(String[] args) {

        activeBank.checkCustomerId(CUSTOMER_ID, CUSTOMER_NAME);

        Scanner sc = new Scanner(System.in);
        String n;
        do {
        System.out.println(Utils.getDivider());
        System.out.println("|  NGAN HANG SO | FX20512@v3.0.0                |");
        System.out.println("| 1. Thong tin khach hang                       |");
        System.out.println("| 2. Them tai khoan ATM                         |");
        System.out.println("| 3. Them tai khoan tin dung                    |");
        System.out.println("| 4. Rut tien                                   |");
        System.out.println("| 5. Lich su giao dich                          |");
        System.out.println("| 0. Thoat                                      |");
        System.out.println(Utils.getDivider());
        System.out.println("Chuc nang:");
            n = sc.next();
            switch (n) {
                case "1":
                    showDigitalCustomer();
                    break;
                case "2":
                    activeBank.checkCccd(CUSTOMER_ID, 1);
                    break;
                case "3":
                    activeBank.checkCccd(CUSTOMER_ID, 2);
                    break;
                case "4":
                    withdraw_money();
                    break;
                case "5":
                    showTransactionHistory();
                case "0":
                    break;
                default:
                    System.out.println("Vui long nhap so hop le");
                    break;
            }
        } while (!n.equals("0"));
    }

    private static void showDigitalCustomer() {
        Customer customer = activeBank.getDiCusById(CUSTOMER_ID);
        if (customer != null) {
            customer.displayInformation();
        }
    }

    private static void withdraw_money() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Tai khoan ban muon rut tien: ");
        String a = sc.next();
        System.out.println("Hay nhap so tien ban muon rut: ");
        double b = sc.nextDouble();
        activeBank.withdraw(CUSTOMER_ID, a, b);
    }

    private static void showTransactionHistory() {
        DigitalCustomer customer = activeBank.getDiCusById(CUSTOMER_ID);
        if (customer != null) {
            customer.displayInfoAdvance();
        }
    }
}
