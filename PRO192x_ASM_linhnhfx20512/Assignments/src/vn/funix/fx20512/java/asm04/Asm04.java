package vn.funix.fx20512.java.asm04;

import vn.funix.fx20512.java.asm02.models.Bank;
import vn.funix.fx20512.java.asm02.models.Customer;
import vn.funix.fx20512.java.asm03.models.Utils;
import vn.funix.fx20512.java.asm04.dao.CustomerDao;

import java.io.IOException;
import java.util.Scanner;

public class Asm04 {
    public static void main(String[] args) throws IOException {
        Bank bank = new Bank("TPBank");
        String customerPath = "src\\vn\\funix\\fx20512\\java\\asm04\\store\\customers.csv";
        String addPath = "src\\vn\\funix\\fx20512\\java\\asm04\\store\\";
        Scanner sc = new Scanner(System.in);
        String n;
        do {
            System.out.println(Utils.getDivider());
            System.out.println("|  NGAN HANG SO | FX20512@v4.0.0                |");
            System.out.println("| 1. Xem danh sach khach hang                   |");
            System.out.println("| 2. Nhap danh sach khach hang                  |");
            System.out.println("| 3. Them tai khoan ATM                         |");
            System.out.println("| 4. Chuyen tien                                |");
            System.out.println("| 5. Rut tien                                   |");
            System.out.println("| 6. Tra cuu lich su giao dich                  |");
            System.out.println("| 0. Thoat                                      |");
            System.out.println(Utils.getDivider());
            System.out.println("Chuc nang:");
            n = sc.next();
            switch (n) {
                case "1":
                    bank.displayInfo();
                    break;
                case "2":
                    int num = 0;
                    do {
                        try {
                            System.out.println("Nhap duong dan den tep: ");

                            String path = sc.next();
                            String path2 = addPath + path;
                            if (path2.equals(customerPath)) {
                                bank.addCustomers(customerPath);
                                num = 1;
                            } else {
                                throw new Exception("Tep khong ton tai!");
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } while (num != 1);
                    break;
                case "3":
                    System.out.println("Nhap ma so khach hang: ");
                    String a = sc.next();
                    Customer cus = bank.getCustomerById(CustomerDao.list(), a);
                    if (cus != null) {
                        bank.addSavingAccount(sc, a);
                    } else {
                        System.out.println("Khong tim thay khach hang " + a + ", tac vu khong thanh cong");
                    }
                    break;
                case "4":
                    bank.transfers(sc);
                    break;
                case "5":
                    bank.withdraw(sc);
                    break;
                case "6":
                    bank.showCustomer(sc);
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Vui long nhap so hop le");
                    break;
            }
        } while (!n.equals("0"));
    }


}
