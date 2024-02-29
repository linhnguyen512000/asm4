package vn.funix.fx20512.java.asm01;

import java.util.*;

public class Asm01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String n;
        do {
        System.out.println("+----------+-------------------------+----------+");
        System.out.println("|  NGAN HANG SO | FX20512@v1.0.0                |");
        System.out.println("| 1. Nhap CCCD                                  |");
        System.out.println("| 0. Thoat                                      |");
        System.out.println("+----------+-------------------------+----------+");
        System.out.println("Chuc nang:");
        n = sc.next();
        switch (n) {
            case "1":
                securityCheck();
                break;
            case "0":
                break;
            default:
                System.out.println("Vui long nhap so hop le");
                break;
        }
        } while (!n.equals("0"));
    }

//  Hàm này kiểm tra xem có phải người không trước khi cho phép nhập cccd
    public static void securityCheck(){
        Scanner sc = new Scanner(System.in);
        boolean check = false;
        do {
            System.out.println("Hay chon ma kiem tra phu hop (easy/hard): ");
            String mode = sc.next().toLowerCase();
        switch (mode) {
            case "easy":
                easyCode();
                check = true;
                break;
            case "hard":
                hardCheck();
                check = true;
                break;
            default:
                System.out.println("Loai ma khong dung. Vui long thu lai.");
                break;
        }
        } while (!check);
        checkId();
    }

//  Hàm này để tạo và check mã kiểm tra easy
    public static void easyCode() {
        Random random = new Random();
        Scanner sc = new Scanner(System.in);
        int n = random.nextInt(900)+100;
        int valid;
        do {
            valid = 1;
            try {
                System.out.println("Nhap ma xac thuc: " + n);
                int m = sc.nextInt();
                if (m != n) {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Ma xac thuc khong dung. Vui long thu lai.");
                sc.nextLine();
                valid = 0;
            }
        } while (valid == 0);
    }

//  Hàm này để tạo và check mã hard
    public static void hardCheck() {
        Random random = new Random();
        Scanner sc = new Scanner(System.in);
        String a = random.ints(48, 123).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(6).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        int valid;
        do {
            valid = 1;
            try {
                System.out.println("Nhap ma xac thuc: " + a);
                String b = sc.nextLine();
                if (!b.equals(a)) {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Ma xac thuc khong dung. Vui long thu lai.");
                valid = 0;
            }
        } while (valid == 0);
    }

//  Hàm này dùng để kiểm tra cccd nhập đúng cú pháp hay không
    public static void checkId() {
        Scanner sc = new Scanner(System.in);
        String cccd;
        boolean check = false;
        int num = 0;
        Map<String, String> map = provinceCode();
        do {
            System.out.print("Vui long nhap so CCCD: ");
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
            if (check && num == 12 && map.containsKey(cccd.substring(0, 3))) {
                infoCheck(map, cccd);

            } else if (!cccd.equalsIgnoreCase("no")) {
                System.out.println("So CCCD khong hop le. Vui long nhap lai hoac nhan 'No' de thoat");
            }
        } while (!((check && num == 12 && map.containsKey(cccd.substring(0, 3))) || cccd.equalsIgnoreCase("no")));

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

//  Hàm này để kiểm tra nơi sinh, tuổi, giới tính và số ngẫu nhiên
    public static void infoCheck(Map<String, String> map, String cccd) {
        String a;
        do {
        Scanner sc = new Scanner(System.in);
        System.out.println("    | 1. Kiem tra noi sinh");
        System.out.println("    | 2. Kiem tra tuoi, gioi tinh");
        System.out.println("    | 3. Kiem tra so ngau nhien");
        System.out.println("    | 0. Thoat");
        System.out.println("Chuc nang: ");
        a = sc.next();
        switch (a) {
            case "1":
                System.out.println("Noi sinh: " + map.get(cccd.substring(0,3)));
                break;
            case "2":
                int b = Integer.parseInt(cccd.substring(3, 4));
                    if (b%2 == 0) {
                        System.out.println("Gioi tinh: Nam | " +(19+b/2) + cccd.substring(4, 6));
                    } else {
                        System.out.println("Gioi tinh: Nu | " + (19+b/2) + cccd.substring(4, 6));
                    }
                break;
            case "3":
                System.out.println("So ngau nhien: " + cccd.substring(6));
                break;
            case "0":
                break;
            default:
                System.out.println("Vui long nhap so phu hop");
                break;
        }
        } while (!a.equals("0"));
    }
}
