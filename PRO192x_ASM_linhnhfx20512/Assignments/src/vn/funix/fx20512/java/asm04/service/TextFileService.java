package vn.funix.fx20512.java.asm04.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TextFileService {
    private static final String COMMA_DELIMITER = ",";

    public static List<List<String>> readFile(String fileName) throws FileNotFoundException, InputMismatchException {

        List<List<String>> parentArr = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(fileName));
            while (sc.hasNextLine()) {
                List<String> childArr = new ArrayList<>();
                String line = sc.nextLine();
                childArr.add(line.substring(0, line.indexOf(",")));
                childArr.add(line.substring(line.indexOf(",")+1));
                parentArr.add(childArr);
            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }

        return parentArr;
    }
}
