package vn.funix.fx20512.java.asm03.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoanAccountTest {

    @Test
    public void isAccepted1() {
        LoanAccount acc = new LoanAccount("123456"); // Rút quá hạn mức
        assertEquals(false, acc.isAccepted(110_000_000));
    }

    @Test
    public void isAccepted2() {
        LoanAccount acc = new LoanAccount("123456"); // Rút dưới hạn mức
        assertEquals(true, acc.isAccepted(90_000_000));
    }

    @Test
    public void isAccepted3() {
        LoanAccount acc = new LoanAccount("123456"); // Hạn mức còn lại < 50,000
        assertEquals(false, acc.isAccepted(99_999_000));
    }

    @Test
    public void isAccepted4() {
        LoanAccount acc = new LoanAccount("123456"); // Hạn mức còn lại > 50,000
        assertEquals(true, acc.isAccepted(99_900_000));
    }

    @Test
    public void isAccepted5() {
        LoanAccount acc = new LoanAccount("123456"); // Hạn mức còn lại = 50,000
        assertEquals(true, acc.isAccepted(99_950_000));
    }

    @Test
    public void withdraw1() {
        LoanAccount acc = new LoanAccount("123456"); // Rút tiền được chấp nhận
        assertEquals(true, acc.withdraw(90000000));
    }

    @Test
    public void withdraw2() {
        LoanAccount acc = new LoanAccount("123456"); // Rút tiền không được chấp nhận
        acc.setBalance(100000);
        assertEquals(false, acc.withdraw(9000000));
    }

    @Test
    public void getFee1() {
        LoanAccount acc = new LoanAccount("123456"); // Tài khoản thường, phí là 0.05
        acc.setBalance(1000000);
        assertEquals(5000, acc.getFee(100000), 0);
    }

    @Test
    public void getFee2() {
        LoanAccount acc = new LoanAccount("123456"); // Tài khoản thường, phí là 0.05
        acc.setBalance(1000000);
        assertEquals(5000, acc.getFee(100000), 0);
    }
}