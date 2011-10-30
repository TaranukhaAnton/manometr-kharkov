package com.avrora.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExtendedButton extends JButton {
    private String channelAlias;
    private String data1;
    // private boolean k[] = {false, false, false, false, false, false};
    private ChannelState channelState;

    public ExtendedButton(String channelAlias, MainWindow mainPanel, Integer num, ChannelState channelState) {
        this.channelAlias = channelAlias;
        this.addActionListener(new MyActionListener(mainPanel, num));
        this.channelState = channelState;
    }

    public void setData1(String data1) {
        this.data1 = data1;
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
       g.drawRect(40,5, 50,25);

        Font f = g.getFont();
        g.setFont(new Font(f.getName(), f.getStyle(), 20));
        g.drawString(channelAlias, 45, 25);
        g.setFont(new Font(f.getName(), f.getStyle(), 30));
        if (data1 != null)
            g.drawString(data1, 5, 80);
        g.setFont(f);

//        g.setColor(channelState.isKran0() ? Color.GREEN : Color.RED);
//        g.drawOval(70, 70, 75, 75);
    }

    public void setChannelState(ChannelState channelState) {
        this.channelState = channelState;
        this.repaint();
    }
}

class MyActionListener implements ActionListener {
    MainWindow mainPanel;
    Integer num;

    MyActionListener(MainWindow mainPanel, Integer num) {
        this.mainPanel = mainPanel;
        this.num = num;
    }

    public void actionPerformed(ActionEvent e) {
        mainPanel.selectChannel(num);
    }


}