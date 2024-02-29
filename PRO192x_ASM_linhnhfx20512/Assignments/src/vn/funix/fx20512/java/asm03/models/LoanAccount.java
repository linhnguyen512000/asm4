package vn.funix.fx20512.java.asm03.models;

import vn.funix.fx20512.java.asm02.models.Account;

public class LoanAccount extends Account implements ReportService, Withdraw {
    private static final double LOAN_ACCOUNT_WITHDRAW_FEE = 0.05;
    private static final double LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE = 0.01;
    private static final double LOAN_ACCOUNT_MAX_BALANCE = 100_000_000;


    public LoanAccount(String accNum) {
        super(accNum, LOAN_ACCOUNT_MAX_BALANCE);
    }

    public double getTransactionFee() {
        if (isPremium()) {
            return LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE;
        }
        return LOAN_ACCOUNT_WITHDRAW_FEE;
    }

    public boolean isAccepted(double amount)  {
        if (getBalance() - amount - getFee(amount)>= 50_000 && amount > 0) {
            return true;
        }
        return false;
    }

    public double getFee(double amount) {
        return getTransactionFee()*amount;
    }
    public void log(double amount) {
        System.out.println(Utils.getDivider());
        System.out.printf("%30s%n", "BIEN LAI GIAO DICH LOAN");
        System.out.printf("NGAY G/D: %28s%n", Utils.getDateTime());
        System.out.printf("ATM ID: %30s%n", "DIGITAL-BANK-ATM 2023");
        System.out.printf("SO TK: %31s%n", this.getAccountNumber());
        System.out.printf("SO TIEN: %29s%n", Utils.formatBalance(amount));
        System.out.printf("SO DU: %31s%n", Utils.formatBalance(this.getBalance()));
        System.out.printf("PHI + VAT: %27s%n", Utils.formatBalance(getFee(amount)));
        System.out.println(Utils.getDivider());
    }


    public boolean withdraw(double amount) {
        if (isAccepted(amount)) {
            setBalance(getBalance() - amount - getFee(amount));
            log(amount);
            Transaction a = new Transaction(getAccountNumber(), amount, Utils.getDateTime(), true);
            getTransaction().add(a);
            return true;
        } else {
            Transaction a = new Transaction(getAccountNumber(), amount, Utils.getDateTime(), false);
            getTransaction().add(a);
            System.out.println("So tien rut khong hop le!");
        }
        return false;
    }
}
