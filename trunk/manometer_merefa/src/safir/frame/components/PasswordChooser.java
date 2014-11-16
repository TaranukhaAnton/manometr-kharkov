package safir.frame.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: anton
 * Date: 24.09.14
 */
public class PasswordChooser extends JPanel {
    private JTextField userName;
    private JPasswordField passwordField;
    private JButton okButton;
    private JButton cancelButton;
    private JDialog dialog;

    boolean ok;

    public PasswordChooser() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("User name:"));
        panel.add(userName = new JTextField());
        panel.add(new JLabel("Password:"));
        panel.add(passwordField = new JPasswordField());
        add(panel, BorderLayout.CENTER);
        okButton = new JButton("ok");
        cancelButton = new JButton("Отмена");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ok = true;
                dialog.setVisible(false);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                dialog.setVisible(false);
            }
        });
        JPanel panel2 = new JPanel();
        panel2.add(okButton);
        panel2.add(cancelButton);
        add(panel2, BorderLayout.SOUTH);


    }

    public boolean showDialog(JFrame owner) {
        ok = false;
        //	JFrame owner = null;
        if (dialog == null || dialog.getOwner() != owner) {
            dialog = new JDialog(owner, true);
            dialog.add(this);
            dialog.getRootPane().setDefaultButton(okButton);
            dialog.pack();
            dialog.setVisible(true);
        }


        return ok;
    }


    public String getPass() {
        return new String(passwordField.getPassword());

    }

    public String getName() {
        return userName.getText();
    }
}
