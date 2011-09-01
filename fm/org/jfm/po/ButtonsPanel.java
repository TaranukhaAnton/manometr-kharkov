package org.jfm.po;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class ButtonsPanel extends JPanel {
    private Vector buttons = null;
    private JPanel btPanel = new JPanel();

    public ButtonsPanel(Vector buttons) {
        try {
            jbInit();
            setButtons(buttons);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ButtonsPanel() {
        this(null);
    }

    public void setButtons(Vector newButtons) {
        buttons = newButtons;
        btPanel.removeAll();
        if (buttons != null) {
            addButtons();
        }
    }

    private void addButtons() {
        btPanel.setLayout(new BoxLayout(btPanel, BoxLayout.X_AXIS));
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.elementAt(i) instanceof JButton) {
                btPanel.add((JButton) buttons.elementAt(i));
            }
        }

        //this.revalidate();
    }

    public Vector getButtons() {
        return buttons;
    }

    private void jbInit() throws Exception {
        this.setLayout(new BorderLayout());
        this.add(btPanel, BorderLayout.CENTER);
    }

}