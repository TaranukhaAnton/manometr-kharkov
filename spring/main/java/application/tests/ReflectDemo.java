package application.tests;

import sun.reflect.Reflection;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.reflect.*;

public class ReflectDemo extends JFrame {

    private JTextField fld = new JTextField(30);
    private JTextArea  msg = new JTextArea(8, 40);

    ReflectDemo() {
     super("Демонстрация работы рефлексии");

     try  {
       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
     }
     catch(Exception e) {
     }

     setSize(600, 350);
     Container c = getContentPane();
     JPanel pn = new JPanel();
     c.add(pn, BorderLayout.NORTH);
     pn.add(new Label("Имя класса"));
     pn.add(fld);
     fld.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
             reflectInfo();
         }
     });
     JScrollPane pane = new JScrollPane(msg);
     c.add(pane, BorderLayout.CENTER);
     WindowListener wndCloser = new WindowAdapter() {
       public void windowClosing(WindowEvent e) {
         System.exit(0);
       }
     };
     addWindowListener(wndCloser);

     setVisible(true);
    }

    void reflectInfo() {
        String className = fld.getText();
        try {
            Class cls = Class.forName(className);
            Method[] m = cls.getDeclaredMethods();

            int lastLine = msg.getLineCount()-1;
            if ( lastLine >= 0 )
                msg.replaceRange("", 0, msg.getLineEndOffset(lastLine));
            msg.append("Методы класса "+className+":\n");
            for(int i = 0; i < m.length; i++)
                msg.append(m[i].toString()+"\n");
        } catch (Exception ex) {
            msg.append("Ошибка при поиске класса "+className+":\n");
            msg.append(ex.toString()+"\n");
        }
    }

    public static void main(String args[]) {
        new ReflectDemo();
    }

}