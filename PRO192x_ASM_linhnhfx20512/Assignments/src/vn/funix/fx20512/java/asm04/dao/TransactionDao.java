package vn.funix.fx20512.java.asm04.dao;

import vn.funix.fx20512.java.asm03.models.Transaction;
import vn.funix.fx20512.java.asm04.service.BinaryFileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {
    private final static String FILE_PATH = "src\\vn\\funix\\fx20512\\java\\asm04\\store\\transaction.dat";

    public static void save(List<Transaction> transactions) throws IOException {
        BinaryFileService.writeFile(FILE_PATH, transactions);
    }

    public static List<Transaction> list() {
        return BinaryFileService.readFile(FILE_PATH);
    }

    public static void addTrans(Transaction trans) throws IOException {
        List<Transaction> transactions = new ArrayList<>(list());
        transactions.add(trans);
        save(transactions);
    }
}
