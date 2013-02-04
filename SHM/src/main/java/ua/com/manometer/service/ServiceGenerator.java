package ua.com.manometer.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 04.10.11
 * Time: 7:49
 * To change this template use File | Settings | File Templates.
 */
public class ServiceGenerator {
    public static void main(String[] args) {
        List<String> className = Arrays.asList("OptionsPrice","PriceFirstPart","ProductionPrice");
        String package_slesh = "\\price";
        String package_dot = ".price";


        for (String s : className) {
            String class_lower = s.toLowerCase();
            print(s, package_slesh, package_dot, class_lower);
        }

    }

    private static void print(String className, String package_slesh, String package_dot, String class_lower) {
        try {
            File f = new File("D:\\projects\\~SPRING\\SHM\\src\\main\\java\\ua\\com\\manometer\\service" + package_slesh + "\\" + className + "Service.java");
            PrintWriter out = new PrintWriter(new FileWriter(f));
            out.println("package ua.com.manometr.service" + package_dot + ";\n" +
                    "\n" +
                    "import ua.com.manometr.model" + package_dot + "." + className + ";\n" +
                    "\n" +
                    "import java.util.List;\n" +
                    "\n" +
                    "public interface " + className + "Service {\n" +
                    "\n" +
                    "\tpublic void add" + className + "(" + className + " " + class_lower + ");\n" +
                    "\n" +
                    "\tpublic List<" + className + "> list" + className + "();\n" +
                    "\n" +
                    "\tpublic void remove" + className + "(Integer id);\n" +
                    "\n" +
                    "}");
            out.close(); // We're done writing
        } catch (IOException e) { /* Handle exceptions */ }


        try {
            File f = new File("D:\\projects\\~SPRING\\SHM\\src\\main\\java\\ua\\com\\manometer\\service" + package_slesh + "\\" + className + "ServiceImpl.java");
            PrintWriter out = new PrintWriter(new FileWriter(f));
            out.println("package ua.com.manometr.service" + package_dot + ";\n" +
                    "\n" +
                    "import org.springframework.beans.factory.annotation.Autowired;\n" +
                    "import org.springframework.stereotype.Service;\n" +
                    "import org.springframework.transaction.annotation.Transactional;\n" +
                    "import ua.com.manometr.dao" + package_dot + "." + className + "DAO;\n" +
                    "import ua.com.manometr.model" + package_dot + "." + className + ";\n" +
                    "\n" +
                    "import java.util.List;\n" +
                    "\n" +
                    "@Service\n" +
                    "public class " + className + "ServiceImpl implements " + className + "Service {\n" +
                    "\n" +
                    "\t@Autowired\n" +
                    "\tprivate " + className + "DAO " + class_lower + "DAO;\n" +
                    "\n" +
                    "\t@Override\n" +
                    "\t@Transactional\n" +
                    "\tpublic void add" + className + "(" + className + " " + class_lower + ") {\n" +
                    "\t\t" + class_lower + "DAO.add" + className + "(" + class_lower + ");\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@Override\n" +
                    "\t@Transactional\n" +
                    "\tpublic List<" + className + "> list" + className + "() {\n" +
                    "\t\treturn " + class_lower + "DAO.list" + className + "();\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@Override\n" +
                    "\t@Transactional\n" +
                    "\tpublic void remove" + className + "(Integer id) {\n" +
                    "\t\t" + class_lower + "DAO.remove" + className + "(id);\n" +
                    "\t}\n" +
                    "\n" +
                    "}");
            out.close(); // We're done writing
        } catch (IOException e) { /* Handle exceptions */ }
    }

}
