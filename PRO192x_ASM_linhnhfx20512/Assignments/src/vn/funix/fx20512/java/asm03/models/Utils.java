package vn.funix.fx20512.java.asm03.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static String getDivider() {
        return "+----------+-------------------------+----------+";
    }

    public static String getDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String formatBalance(double amount) {
        String a = String.format("%,.0f", amount);
        return a + "đ";
    }

}
