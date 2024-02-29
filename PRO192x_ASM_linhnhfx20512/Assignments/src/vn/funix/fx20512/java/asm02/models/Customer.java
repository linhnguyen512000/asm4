package vn.funix.fx20512.java.asm02.models;

import vn.funix.fx20512.java.asm03.models.SavingAccount;
import vn.funix.fx20512.java.asm03.models.Utils;
import vn.funix.fx20512.java.asm04.dao.AccountDao;
import vn.funix.fx20512.java.asm04.dao.CustomerDao;
import vn.funix.fx20512.java.asm04.exception.CustomerIdNotValidException;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Customer extends User implements Serializable {
    private static final long serialNumberUID = 1L;
    private List<Account> accounts;

    public Customer() {
        this.accounts = new ArrayList<Account>();
    }

    public Customer(List<String> values)  {
        String customerId = values.get(0);
        String name = values.get(1);
        boolean check = false;
        int num = 0;
        Map<String, String> map = provinceCode();
        try {
            if (customerId.length() == 12) {
                check = true;
            }
            num = 0;
            for (int i = 0; i < customerId.length(); i++) {
                boolean a = Character.isDigit(customerId.charAt(i));
                if (a) {
                    num++;
                }
            }
            if (check && num == 12 && map.containsKey(customerId.substring(0, 3)) && findCustomer(customerId) == null) {
                Customer cus = new Customer(customerId, name);
                CustomerDao.addCustomer(cus);
                System.out.println("Da them khach hang " + customerId + " vao danh sach");
            } else  {
                throw new CustomerIdNotValidException("Khach hang " + customerId + " da ton tai hoac so CCCD khong hop le!");
            }
        } catch (CustomerIdNotValidException e) {
            System.out.println(e.getMessage());
        }
    }

    public Customer(String customerId, String name) {
        super(customerId, name);
        accounts = getAccs();
    }

    public List<Account> getAccs() { // Sử dụng Stream/Lambda
        return AccountDao.list().stream().filter(acc -> acc.getCustomerId().equals(this.getCustomerId())).collect(Collectors.toList());
    }

    public List<Account> getAccounts() {
        return accounts = getAccs();
    }

    public boolean isPremium() {
        for ( int i=0; i < getAccounts().size(); i++) {
            if (getAccounts().get(i).isPremium()) {
                return true;
            }
        }
        return false;
    }

    public void addAccount(Account account) {
            this.accounts.add(account);
    }

    public double getBalance() {
        double balance = 0;
        for (int i=0; i<getAccounts().size(); i++) {
            balance += getAccounts().get(i).getBalance();
        }
        return balance;
    }

    public Account getAccountByAccountNumber(List<Account> accounts, String accountNumber) {
        for(Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null;
    }

    public void displayTransactionInformation() {
        displayInformation();
        for (Account acc : getAccs()) {
            acc.displayTransactionList();
        }
    }

    public void withdraw(Scanner scanner) {
        List<Account> accounts = getAccs();
        if (!accounts.isEmpty()) {
            Account account;
            double amount;
            do {
                System.out.println("Nhap so tai khoan: ");
                String a = scanner.next();
                account = getAccountByAccountNumber(accounts, a);
                if (account == null) {
                    System.out.println("So tai khoan khong dung hoac khong ton tai");
                }
            } while (account == null);

            do {
                System.out.println("Nhap so tien rut: ");
                String c = scanner.next();
                amount = Double.parseDouble(c);
                if (!isAccepted(account, amount)) {
                    System.out.println("So tien rut khong hop le");
                }
            } while (!isAccepted(account, amount));

            if (account instanceof SavingAccount) {
                System.out.println("Rut tien thanh cong, in bien lai giao dich");
                ((SavingAccount)account).withdraw(amount);
            }
        } else {
            System.out.println("Khach hang khong co tai khoan nao, rut tien khong thanh cong");
        }
    }

    public void transfers(Scanner scanner) {
        List<Account> accounts = getAccs();
        if (!accounts.isEmpty()) {
            Account account;
            Account receiveAccount;
            double amount;
            do {
                System.out.println("Nhap so tai khoan chuyen tien: ");
                String a = scanner.next();
                account = getAccountByAccountNumber(AccountDao.list(), a);
                if (account == null) {
                    System.out.println("So tai khoan khong dung hoac khong ton tai");
                }
            } while (account == null);

            do {
                System.out.println("Nhap so tai khoan nhan tien: ");
                String b = scanner.next();
                receiveAccount = getAccountByAccountNumber(AccountDao.list(), b);
                if (receiveAccount == null || receiveAccount.getAccountNumber().equals(account.getAccountNumber())) {
                    System.out.println("So tai khoan khong dung hoac khong ton tai");
                }
            } while (receiveAccount == null || receiveAccount.getAccountNumber().equals(account.getAccountNumber()));
            System.out.println("Gui tien den tai khoan " + receiveAccount.getAccountNumber() + " | " + receiveAccount.getCustomer().getName());

            do {
                System.out.println("Nhap so tien rut: ");
                String c = scanner.next();
                amount = Double.parseDouble(c);
                if (!isAccepted(account, amount)) {
                    System.out.println("So tien rut khong hop le");
                }
            } while (!isAccepted(account, amount));

            if (account instanceof SavingAccount) {
                String a = String.format("%,.0f", amount);
                System.out.println("Xac nhan chuyen " + a + "đ tu tai khoan " + account.getAccountNumber() + " toi tai khoan " + receiveAccount.getAccountNumber() + " (Y/N)");
                String b = scanner.next();
                if (b.toLowerCase().equals("y")) {
                    System.out.println("Chuyen tien thanh cong, bien lai giao dich: ");
                    ((SavingAccount)account).transfers(receiveAccount, amount);
                }
            }
        } else {
            System.out.println("Khach hang khong co tai khoan nao, rut tien khong thanh cong");
        }
    }

    public boolean isAccepted(Account account, double amount) {
        if ((this.getBalance() - amount) >= 50000) {
            if (amount >= 50000 && amount%10000 == 0) {
                if(!account.isPremium() && amount <= 5_000_000) {
                    return true;
                } else if (isPremium()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void displayInformation() {
        String a;
        if (isPremium()) {
            a = "Premium";
        } else {
            a = "Normal";
        }

        System.out.print(getCustomerId() + " |");
        System.out.printf("%-25s", getName());
        System.out.printf("|%10s", a + " |");
        String b = String.format("%,.0f", getBalance());
        System.out.printf("%15s", b + "đ\n");

        for (int i=0; i<accounts.size(); i++) {
            String c = String.format("%,.0f",accounts.get(i).getBalance());
            System.out.print((i+1) + "     " + accounts.get(i).getAccountNumber());
            System.out.printf(" |"+ "%-24s", a);
            System.out.printf(" | %24s", c + "đ" + "\n");
        }
    }

    public Customer findCustomer(String customerId) {
        if (CustomerDao.list().size() == 0) {
            return null;
        } else {
            for (Customer cus : CustomerDao.list()) {
                if (customerId.equals(cus.getCustomerId())) {
                    return cus;
                }
            }
        }
        return null;
    }

    public void addSavingAccount(Scanner scanner, String customerId) {
        String accNum;
        int num = 0;
        int check = 0;
        do {
            try {
                System.out.println("Vui long nhap ma STK gom 6 chu so: ");
                accNum = scanner.next();
                num = 0;
                for (int i = 0; i < accNum.length(); i++) {
                    boolean a = Character.isDigit(accNum.charAt(i));
                    if (a) {
                        num++;
                    }
                }
                if (accNum.length() == 6 && num == 6 && !isAccountNumberExisted(accNum)) {
                    double amount = checkBalance(scanner);
                    SavingAccount acc = new SavingAccount(customerId, accNum, amount);
                    acc.createTransaction2(amount, Utils.getDateTime(), true, "DEPOSIT");
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
}
