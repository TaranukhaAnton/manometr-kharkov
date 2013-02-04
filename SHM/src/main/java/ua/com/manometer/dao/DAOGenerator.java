package ua.com.manometer.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 03.10.11
 * Time: 7:59
 * To change this template use File | Settings | File Templates.
 */
public class DAOGenerator {

    public static void main(String[] args) {
        List<String> className = Arrays.asList("Area","City","Country","Region");
        String package_slesh = "\\address";
        String package_dot = ".address";


        for (String s : className) {
            String class_lower = s.toLowerCase();
            print1(s, package_slesh, package_dot, class_lower);
            print2(s, package_slesh, package_dot, class_lower);
            print(s, package_slesh, package_dot, class_lower);
        }
    }

    public static void print1(String className, String package_slesh, String package_dot, String class_lower) {


        try {
            File f = new File("D:\\projects\\~SPRING\\SHM\\src\\main\\java\\ua\\com\\manometer\\dao" + package_slesh + "\\" + className + "DAO.java");
            PrintWriter out = new PrintWriter(new FileWriter(f));
            out.println("package ua.com.manometr.dao" + package_dot + ";\n" +
                    "import ua.com.manometr.model" + package_dot + "." + className + ";\n" +
                    "\n" +
                    "import java.util.List;\n" +
                    "\n" +
                    "public interface " + className + "DAO {\n" +
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


    }

    public static void print2(String className, String package_slesh, String package_dot, String class_lower) {
        try {
            File f = new File("D:\\projects\\~SPRING\\SHM\\src\\main\\java\\ua\\com\\manometer\\dao" + package_slesh + "\\" + className + "DAOImpl.java");
            PrintWriter out = new PrintWriter(new FileWriter(f));
            out.println("package ua.com.manometr.dao" + package_dot + ";\n" +
                    "import ua.com.manometr.model" + package_dot + "." + className + ";\n" +

                    "\n" +
                    "import org.hibernate.SessionFactory;\n" +
                    "import org.springframework.beans.factory.annotation.Autowired;\n" +
                    "import org.springframework.stereotype.Repository;\n" +

                    "\n" +
                    "import java.util.List;\n" +
                    "\n" +
                    "@Repository\n" +
                    "public class " + className + "DAOImpl implements " + className + "DAO {\n" +
                    "\n" +
                    "    @Autowired\n" +
                    "    private SessionFactory sessionFactory;\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public void add" + className + "(" + className + " " + class_lower + ") {\n" +
                    "        sessionFactory.getCurrentSession().save(" + class_lower + ");\n" +
                    "    }\n" +
                    "\n" +
                    "    @SuppressWarnings(\"unchecked\")\n" +
                    "    @Override\n" +
                    "    public List<" + className + "> list" + className + "() {\n" +
                    "        return sessionFactory.getCurrentSession().createQuery(\"from " + className + "\").list();\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public void remove" + className + "(Integer id) {\n" +
                    "        " + className + " " + class_lower + " = (" + className + ") sessionFactory.getCurrentSession().load(" + className + ".class, id);\n" +
                    "        if (" + class_lower + " != null) {\n" +
                    "            sessionFactory.getCurrentSession().delete(" + class_lower + ");\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "}");
            out.close(); // We're done writing
        } catch (IOException e) { /* Handle exceptions */ }
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
