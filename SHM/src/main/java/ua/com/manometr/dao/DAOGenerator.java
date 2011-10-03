package ua.com.manometr.dao;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 03.10.11
 * Time: 7:59
 * To change this template use File | Settings | File Templates.
 */
public class DAOGenerator {

    public static void main(String[] args) {
        String className = "Contact";

        generateIterface(className, className.toLowerCase());
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println();
        generateImplementation(className, className.toLowerCase());
    }

    public static void generateIterface(String className, String fieldName) {
        System.out.println("import java.util.List;\n" +
                "public interface " + className + "DAO {\n" +
                "\n" +
                "\tpublic void add" + className + "(" + className + " " + fieldName + ");\n" +
                "\n" +
                "\tpublic List<" + className + "> list" + className + "();\n" +
                "\n" +
                "\tpublic void remove" + className + "(Long id);\n" +
                "\n" +
                "}");

    }

    public static void generateImplementation(String className, String fieldName) {
        System.out.println("import org.hibernate.SessionFactory;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.stereotype.Repository;\n" +
                "import java.util.List;\n" +
                "@Repository\n" +
                "public class " + className + "DAOImpl implements " + className + "DAO {\n" +
                "\n" +
                "    @Autowired\n" +
                "    private SessionFactory sessionFactory;\n" +
                "\n" +
                "    @Override\n" +
                "    public void add" + className + "(" + className + " " + fieldName + ") {\n" +
                "        sessionFactory.getCurrentSession().save(" + fieldName + ");\n" +
                "    }\n" +
                "\n" +
                "    @SuppressWarnings(\"unchecked\")\n" +
                "    @Override\n" +
                "    public List<" + className + "> list" + className + "() {\n" +
                "        return sessionFactory.getCurrentSession().createQuery(\"from " + className + "\").list();\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void remove" + className + "(Long id) {\n" +
                "        " + className + " " + fieldName + " = (" + className + ") sessionFactory.getCurrentSession().load(" + className + ".class, id);\n" +
                "        if (" + fieldName + " != null) {\n" +
                "            sessionFactory.getCurrentSession().delete(" + fieldName + ");\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "}");
    }


}
