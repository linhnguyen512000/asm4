package vn.funix.fx20512.java.asm02.models;

import vn.funix.fx20512.java.asm04.dao.CustomerDao;
import vn.funix.fx20512.java.asm04.service.TextFileService;

import java.io.IOException;
import java.util.*;

public class Bank {
    private final String id;
    private final String bankName;
    private final List<Customer> customers;

    public Bank(String bankName) {
        this.bankName = bankName;
        this.customers = CustomerDao.list();
        this.id = String.valueOf(UUID.randomUUID());
    }

    public String getBankName() {
        return bankName;
    }
    public String getId() {
        return id;
    }

    public List<Customer> getCustomer() {
        return customers;
    }

    public void addAccount(String customerId, Account account) {
        this.findCustomer(customerId).addAccount(account);
    }


//    Hàm để để nhập và check xem CCCD đã tồn tại trong hệ thống chưa
    public void checkCccd() {
        Scanner sc = new Scanner(System.in);
        String customerId = "";
        int check = 0;
        do {
            try {
                System.out.println("Nhap CCCD khach hang: ");
                customerId = sc.next();
                if (findCustomer(customerId) != null) {
                    this.checkAccountNumber(customerId);
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
    public void checkAccountNumber(String customerId) {
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
                        double balance = checkBalance();
                        Account acc = new Account(accNum, balance);
                        this.addAccount(customerId, acc);
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

//    Hàm này dùng để tìm index của khách hàng trong ArrayList customers
    public Customer findCustomer(String customerId) {
        for (int i=0; i< customers.size(); i++) {
            if (customerId.equals(customers.get(i).getCustomerId())) {
                return customers.get(i);
            }
        }
        return null;
    }

//    Hàm này để check xem STK đã có trong bank chưa
    public boolean isAccountNumberExisted(String accountNumber) {
        for (int i=0; i<customers.size(); i++) {
            for (int j=0; j<customers.get(i).getAccounts().size(); j++) {
                if (accountNumber.equals(customers.get(i).getAccounts().get(j).getAccountNumber())) {
                    return true;
                }
            }
        }
        return false;
    }

//    Hàm này để check xem số dư có phải là số và tối thiểu 50,000 VND không
    public double checkBalance() {
        boolean check;
        double balance = 0;
        do {
            Scanner sc = new Scanner(System.in);
            check = true;
            try {
                System.out.println("Nhap so du: ");
                balance = sc.nextDouble();
                if (balance < 50_000) {
                    throw new Exception();
                } else {
                    System.out.println("So du da duoc them vao tai khoan. Xin cam on!");
                }
            } catch (Exception e) {
                check = false;
                System.out.println("So du tai khoan phai phai la so va toi thieu 50,000 VND. Xin vui long nhap lai!");
                sc.nextLine();
            }
        } while (!check);
        return balance;
    }

    public void addCustomer(Customer newCustomer) {
        this.customers.add(newCustomer);
    }

    //   Hàm này dùng để kiểm tra cccd nhập đúng cú pháp hay không (dùng cho chức năng 1)
    public void checkCustomerId() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhap ten khach hang: ");
        String name = sc.next();
        String cccd;
        boolean check = false;
        int num = 0;
        int test = 0;
        Map<String, String> map = provinceCode();
        do {
            try {
                System.out.println("Vui long nhap so CCCD: ");
                cccd = sc.next();
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
                if (check && num == 12 && map.containsKey(cccd.substring(0, 3)) && findCustomer(cccd) == null) {
                    Customer cus = new Customer();
                    cus.setCustomerId(cccd);
                    cus.setName(name);
                    this.addCustomer(cus);
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

//  Hàm này chứa map lưu thông tin 3 chữ số đầu và mã tỉnh tương ứng
    public static Map<String, String> provinceCode() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("001", "Ha Noi");
        map.put("002", "Ha Giang");
        map.put("004", "Cao Bang");
        map.put("006", "Bac Kan");
        map.put("008", "Tuyen Quang");
        map.put("010", "Lao Cai");
        map.put("011", "Dien Bien");
        map.put("012", "Lai Chau");
        map.put("014", "Son La");
        map.put("015", "Yen Bai");
        map.put("017", "Hoa Binh");
        map.put("019", "Thai Nguyen");
        map.put("020", "Lang Son");
        map.put("022", "Quang Ninh");
        map.put("024", "Bac Giang");
        map.put("025", "Phu Tho");
        map.put("026", "Vinh Phuc");
        map.put("027", "Bac Ninh");
        map.put("030", "Hai Duong");
        map.put("031", "Hai Phong");
        map.put("033", "Hung Yen");
        map.put("034", "Thai Binh");
        map.put("035", "Ha Nam");
        map.put("036", "Nam Dinh");
        map.put("037", "Ninh Binh");
        map.put("038", "Thanh Hoa");
        map.put("040", "Nghe An");
        map.put("042", "Ha Tinh");
        map.put("044", "Quang Binh");
        map.put("045", "Quang Tri");
        map.put("046", "Thua Thien Hue");
        map.put("048", "Da Nang");
        map.put("049", "Quang Nam");
        map.put("051", "Quang Ngai");
        map.put("052", "Binh Dinh");
        map.put("054", "Phu Yen");
        map.put("056", "Khanh Hoa");
        map.put("058", "Ninh Thuan");
        map.put("060", "Binh Thuan");
        map.put("062", "Kon Tum");
        map.put("064", "Gia Lai");
        map.put("066", "Dac Lac");
        map.put("067", "Dac Nong");
        map.put("068", "Lam Dong");
        map.put("070", "Binh Phuoc");
        map.put("072", "Tay Ninh");
        map.put("074", "Binh Duong");
        map.put("075", "Dong Nai");
        map.put("077", "Ba Ria - Vung Tau");
        map.put("079", "TP Ho Chi Minh");
        map.put("080", "Long An");
        map.put("082", "Tien Giang");
        map.put("083", "Ben Tre");
        map.put("084", "Tra Vinh");
        map.put("086", "Vinh Long");
        map.put("087", "Dong Thap");
        map.put("089", "An Giang");
        map.put("091", "Kien Giang");
        map.put("092", "Can Tho");
        map.put("093", "Hau Giang");
        map.put("094", "Soc Trang");
        map.put("095", "Bac Lieu");
        map.put("096", "Ca Mau");
        return map;
    }

//    Hàm này để hiển thị thông tin cho chức năng 3
    public void displayInfo() {
        for (Customer cus : CustomerDao.list()) {
            cus.displayInformation();
        }
    }

//    Hàm này để chạy chức năng 4
    public void searchCustomerByCCCD() {
        Scanner sc = new Scanner(System.in);
        String customerId = "";
        int check = 0;
        do {
            try {
                System.out.println("Nhap CCCD khach hang: ");
                customerId = sc.next();
                if (findCustomer(customerId) != null) {
                    findCustomer(customerId).displayInformation();
                    check = 1;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("So CCCD khong hop le. Vui long nhap lai!");
            }
        } while (check != 1);
    }

//    Hàm này để chạy chức năng 5
    public void searchCustomerByName() {
        Scanner sc = new Scanner(System.in);
        String name;
        int check = 0;
        do {
            try {
                System.out.println("Nhap ten khach hang de tim: ");
                name = sc.next();
                Customer a = findCustomerByName(name);
                if (a != null) {
                    a.displayInformation();
                    check = 1;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Ten khach hang khong hop le. Vui long nhap lai!");
            }
        } while (check != 1);
    }

    public Customer findCustomerByName(String name)  {
        for (int i=0; i<customers.size(); i++) {
                if (customers.get(i).getName().toLowerCase().contains(name.toLowerCase())) {
                    return customers.get(i);
                }
        }
        return null;
    }

    public void showCustomer(Scanner scanner) {
        int num = 0;
        Customer cus = null;
        do {
            System.out.println("Xin moi nhap so CCCD: ");
            String customerId = scanner.next();
            cus = getCustomerById(CustomerDao.list(), customerId);
            if (cus != null) {
                num = 1;
            } else {
                System.out.println("Khach hang khong ton tai trong he thong.");
            }
        } while (num == 0);
        cus.displayTransactionInformation();
    }

    public void addCustomers(String fileName) {
        try {
            List<List<String>> parentArr = TextFileService.readFile(fileName);
            for (List<String> element : parentArr) {
                new Customer(element); // Hàm khời tạo của Customer sẽ kiểm tra điều kiện CCCD
            }
        } catch (IOException e) {

            }
    }

    public void addSavingAccount(Scanner scanner, String customerId) {
        int num = 0;
        List<Customer> customers = CustomerDao.list();
        for (Customer cus : customers) {
            if(cus.getCustomerId().equals(customerId)) {
                cus.addSavingAccount(scanner, customerId);
                num = 1;
            }
        }
        if (num == 0) {
            System.out.println("Khong tim thay khach hang " + customerId + ", tac vu khong thanh cong");
        }
    }

    public void withdraw(Scanner scanner) {
        int num = 0;
        Customer cus = null;
        do {
            System.out.println("Xin moi nhap so CCCD: ");
            String customerId = scanner.next();
             cus = getCustomerById(CustomerDao.list(), customerId);
            if (cus != null) {
                num = 1;
            } else {
                System.out.println("Khach hang khong ton tai trong he thong.");
            }
        } while (num == 0);

        cus.displayInformation();
        cus.withdraw(scanner);
    }

    public void transfers(Scanner scanner) {
        int num = 0;
        Customer cus = null;
        do {
            System.out.println("Xin moi nhap so CCCD: ");
            String customerId = scanner.next();
            cus = getCustomerById(CustomerDao.list(), customerId);
            if (cus != null) {
                num = 1;
            } else {
                System.out.println("Khach hang khong ton tai trong he thong.");
            }
        } while (num == 0);
        cus.displayInformation();
        cus.transfers(scanner);
    }

    public boolean isAccountExisted(List<Account> accountsList, Account newAccount) {
        return accountsList.stream().equals(newAccount); // Sử dụng Stream/Lambda
    }

    public boolean isCustomerExisted(List<Customer> customers, Customer newCustomer) {
        return customers.stream().equals(newCustomer); // Sử dụng Stream/Lambda
    }

    public Customer getCustomerById(List<Customer> customerList, String customerId) {
        for (Customer cus : customerList) {
            if (cus.getCustomerId().equals(customerId)) {
                return cus;
            }
        }
        return null;
    }

}
