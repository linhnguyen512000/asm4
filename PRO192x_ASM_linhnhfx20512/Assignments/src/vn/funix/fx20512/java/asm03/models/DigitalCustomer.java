package vn.funix.fx20512.java.asm03.models;

import vn.funix.fx20512.java.asm02.models.Customer;

public class DigitalCustomer extends Customer {
    public void withdraw(String accountNumber, double amount) {
        int count = 0;
        for (int i=0; i<getAccounts().size(); i++) {
            if (getAccounts().get(i).getAccountNumber().equals(accountNumber)) {
                if (getAccounts().get(i) instanceof SavingAccount) {
                    ((SavingAccount)getAccounts().get(i)).withdraw(amount);
                    count++;
                } else if (getAccounts().get(i) instanceof LoanAccount) {
                    ((LoanAccount)getAccounts().get(i)).withdraw(amount);
                    count++;
                }
            }
        }
        if (count != 1) {
                System.out.println("Khong the thuc hien lenh rut tien do so TK khong ton tai.");
        }
    }


    @Override
    public void displayInformation() {
        String a;
        if (isPremium()) {
            a = "Premium";
        } else {
            a = "Normal";
        }

        System.out.print(getCustomerId() + " |");
        System.out.printf("%12s", getName() + " |");
        System.out.printf("%10s", a + " |");
        String b = String.format("%,.0f", getBalance());
        System.out.printf("%15s", b + "đ\n");
        String d = "";
        for (int i=0; i<getAccounts().size(); i++) {
            if (getAccounts().get(i) instanceof SavingAccount) {
                d = "SAVINGS";
            } else if (getAccounts().get(i) instanceof LoanAccount) {
                d = "LOAN";
            }
            String c = String.format("%,.0f",getAccounts().get(i).getBalance());
            System.out.printf((i+1) + "     " + getAccounts().get(i).getAccountNumber() + " |"+ "%12s %24s", d + " |", c + "đ" + "\n");
        }
    }

    public void displayInfoAdvance() {
        displayInformation();
        for (int i=0; i<getAccounts().size(); i++) {
            for (int j=0; j<getAccounts().get(i).getTransaction().size();j++) {
                if (getAccounts().get(i).getTransaction().get(j).isStatus()) {
                    String a = getAccounts().get(i).getAccountNumber();
                    String b = String.format("%,.0f", getAccounts().get(i).getTransaction().get(j).getAmount());
                    String c = getAccounts().get(i).getTransaction().get(j).getTime();
                    System.out.printf("[GD]  " + a + " |");
                    System.out.printf("%-19s", "-" + b);
                    System.out.println("đ |   " + c);
                }
            }
        }
    }
}
