package com.avrora.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActChoiceDialog extends JDialog implements ActionListener {
    ///   JLabel labelName;
    ///  JLabel labelPass;
    // JTextField textName;
    // JPasswordField passField;

    Integer result;
    JButton openButton;
    JButton closeButton;
    JButton cancelButton;

    JDialog dialog;

    public ActChoiceDialog(Component relativeTo, Integer state, int type) {

        dialog = new JDialog();
        openButton = (JButton) add(dialog, new JButton("Вiдкрити"), 100, 30, 20, 40);
        closeButton = (JButton) add(dialog, new JButton("Закрти"), 100, 30, 20, 80);
        cancelButton = (JButton) add(dialog, new JButton("Cкасувати "), 100, 30, 20, 120);

        if (state.equals(2)) {
            closeButton.setEnabled(false);
        } else if (state.equals(1)) {
            openButton.setEnabled(false);
        }


        openButton.addActionListener(this);
        closeButton.addActionListener(this);
        cancelButton.addActionListener(this);

        dialog.setResizable(false);
        //   dialog.getContentPane().add(openButton);
        //   dialog.getContentPane().add(closeButton);
        //  dialog.getContentPane().add(cancelButton);

        //   dialog.getContentPane().add(panelOne);
//          dialog.getContentPane().add(panelTwo);
//          dialog.getContentPane().add(panelThree);
        if (type == 0) {
            dialog.setTitle("Оберiть дiю:");
             add(dialog, new JLabel("Керований кран."), 150, 20, 20, 8);
        } else if (type == 1) {
            dialog.setTitle("Оберiть стан:");
            add(dialog, new JLabel("Некерований кран."), 150, 20, 20, 8);
        }
        dialog.getContentPane().setLayout(null);
        dialog.setSize(150, 200);
        dialog.setLocationRelativeTo(relativeTo); // place in center of screen
        dialog.setModal(true);
        dialog.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {

            result = 1;
            dialog.dispose();
        } else if (e.getSource() == closeButton) {
            result = 2;
            dialog.dispose();
        } else if (e.getSource() == cancelButton) {
            result = 0;
            dialog.dispose();
        }
    }

    public Integer getResult() {
        return result;
    }

    Component add(Container parent, Component c, int w, int h, int x, int y) {
        c.setSize(w, h);
        c.setLocation(x, y);
        parent.add(c);
        return c;
    }


}