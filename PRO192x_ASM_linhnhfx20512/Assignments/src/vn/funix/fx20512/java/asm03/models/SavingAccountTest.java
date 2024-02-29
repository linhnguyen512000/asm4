package vn.funix.fx20512.java.asm03.models;

import static org.junit.Assert.assertEquals;

public class SavingAccountTest {

    @org.junit.Test
    public void isAccepted1() {
        SavingAccount acc = new SavingAccount("123456", 10000000); // Số rút = 50,000
        assertEquals(true, acc.isAccepted(50000));
    }

    @org.junit.Test
    public void isAccepted2() {
        SavingAccount acc = new SavingAccount("123456", 10000000); // Số rút < 50,000
        assertEquals(false, acc.isAccepted(40000));
    }

    @org.junit.Test
    public void isAccepted3() {
        SavingAccount acc = new SavingAccount("123456", 10000000); // Số rút > 50,000
        assertEquals(true, acc.isAccepted(60000));
    }

    @org.junit.Test
    public void isAccepted4() {
        SavingAccount acc = new SavingAccount("123456", 9000000); // Tài khoản thường và rút trên 5,000,000
        assertEquals(false, acc.isAccepted(6000000));
    }

    @org.junit.Test
    public void isAccepted5() {
        SavingAccount acc = new SavingAccount("123456", 9000000); // Tài khoản thường và rút dưới 5,000,000
        assertEquals(true, acc.isAccepted(4000000));
    }

    @org.junit.Test
    public void isAccepted6() {
        SavingAccount acc = new SavingAccount("123456", 9000000); // Tài khoản thường và rút = 5,000,000
        assertEquals(true, acc.isAccepted(5000000));
    }

    @org.junit.Test
    public void isAccepted7() {
        SavingAccount acc = new SavingAccount("123456", 100000); // Số dư còn lại dưới 50,000
        assertEquals(false, acc.isAccepted(60000));
    }

    @org.junit.Test
    public void isAccepted8() {
        SavingAccount acc = new SavingAccount("123456", 100000); // Số dư còn lại = 50,000
        assertEquals(true, acc.isAccepted(50000));
    }

    @org.junit.Test
    public void isAccepted9() {
        SavingAccount acc = new SavingAccount("123456", 110000); // Số dư còn lại > 50,000
        assertEquals(true, acc.isAccepted(50000));
    }

    @org.junit.Test
    public void isAccepted10() {
        SavingAccount acc = new SavingAccount("123456", 200000); // Số rút là bội số của 10,000
        assertEquals(true, acc.isAccepted(60000));
    }

    @org.junit.Test
    public void isAccepted11() {
        SavingAccount acc = new SavingAccount("123456", 200000); // Số rút không là bội số của 10,000
        assertEquals(false, acc.isAccepted(55000));
    }

    @org.junit.Test
    public void withdraw1() {
        SavingAccount acc = new SavingAccount("123456", 200000); // Giao dịch được chấp nhận
        assertEquals(true, acc.withdraw(70000));
    }

    @org.junit.Test
    public void withdraw2() {
        SavingAccount acc = new SavingAccount("123456", 200000); // Giao dịch không được chấp nhận
        assertEquals(false, acc.withdraw(40000));
    }
}