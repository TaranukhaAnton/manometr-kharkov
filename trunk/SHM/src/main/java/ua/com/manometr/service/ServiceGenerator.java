package ua.com.manometr.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 04.10.11
 * Time: 7:49
 * To change this template use File | Settings | File Templates.
 */
public class ServiceGenerator {
    public static void main(String[] args) {
        String className = "Contact";
        String package_slesh = "";
        String package_dot = "";
        String class_lower = className.toLowerCase();


        try {
            File f = new File("D:\\projects\\~SPRING\\SHM\\src\\main\\java\\ua\\com\\manometr\\service" + package_slesh + "\\" + className + "Service.java");
            PrintWriter out = new PrintWriter(new FileWriter(f));
            out.println("package ua.com.manometr.service" + package_dot + ";\n" +
                    "\n" +
                    "import ua.com.manometr.model" + package_dot + "."+className+";\n" +
                    "\n" +
                    "import java.util.List;\n" +
                    "\n" +
                    "public interface "+className+"Service {\n" +
                    "\n" +
                    "\tpublic void add"+className+"("+className+" "+class_lower+");\n" +
                    "\n" +
                    "\tpublic List<"+className+"> list"+className+"();\n" +
                    "\n" +
                    "\tpublic void remove"+className+"(Long id);\n" +
                    "\n" +
                    "}");
            out.close(); // We're done writing
        } catch (IOException e) { /* Handle exceptions */ }


        try {
            File f = new File("D:\\projects\\~SPRING\\SHM\\src\\main\\java\\ua\\com\\manometr\\service" + package_slesh + "\\" + className + "ServiceImpl.java");
            PrintWriter out = new PrintWriter(new FileWriter(f));
            out.println("package ua.com.manometr.service"+ package_slesh +";\n" +
                    "\n" +
                    "import org.springframework.beans.factory.annotation.Autowired;\n" +
                    "import org.springframework.stereotype.Service;\n" +
                    "import org.springframework.transaction.annotation.Transactional;\n" +
                    "import ua.com.manometr.dao"+ package_slesh +"."+className+"DAO;\n" +
                    "import ua.com.manometr.model"+ package_slesh +"."+className+";\n" +
                    "\n" +
                    "import java.util.List;\n" +
                    "\n" +
                    "@Service\n" +
                    "public class "+className+"ServiceImpl implements "+className+"Service {\n" +
                    "\n" +
                    "\t@Autowired\n" +
                    "\tprivate "+className+"DAO "+class_lower+"DAO;\n" +
                    "\n" +
                    "\t@Override\n" +
                    "\t@Transactional\n" +
                    "\tpublic void add"+className+"("+className+" "+class_lower+") {\n" +
                    "\t\t"+class_lower+"DAO.add"+className+"("+class_lower+");\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@Override\n" +
                    "\t@Transactional\n" +
                    "\tpublic List<"+className+"> list"+className+"() {\n" +
                    "\t\treturn "+class_lower+"DAO.list"+className+"();\n" +
                    "\t}\n" +
                    "\n" +
                    "\t@Override\n" +
                    "\t@Transactional\n" +
                    "\tpublic void remove"+className+"(Long id) {\n" +
                    "\t\t"+class_lower+"DAO.remove"+className+"(id);\n" +
                    "\t}\n" +
                    "\n" +
                    "}");
            out.close(); // We're done writing
        } catch (IOException e) { /* Handle exceptions */ }
    }

}
