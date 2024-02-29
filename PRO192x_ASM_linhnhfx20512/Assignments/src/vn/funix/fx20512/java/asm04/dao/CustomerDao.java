package vn.funix.fx20512.java.asm04.dao;

import vn.funix.fx20512.java.asm02.models.Customer;
import vn.funix.fx20512.java.asm04.service.BinaryFileService;

import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    private final static String FILE_PATH = "src\\vn\\funix\\fx20512\\java\\asm04\\store\\customer.dat";

    public static void save(List<Customer> customers)  {
            BinaryFileService.writeFile(FILE_PATH, customers);
    }

    public static List<Customer> list() {
        return BinaryFileService.readFile(FILE_PATH);
    }

    public static void addCustomer(Customer cus) {
        try {
            List<Customer> customers = new ArrayList<>(list());
            customers.add(cus);
            save(customers);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}

