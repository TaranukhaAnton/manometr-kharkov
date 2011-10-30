package com.avrora.GUI;

import com.avrora.bdutils.DAO;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 10.08.11
 * Time: 8:35
 * To change this template use File | Settings | File Templates.
 */
public class TechSchema extends JPanel {
    int currentChannel = 0;
    List<ChannelState> channelStates;
    private ImageIcon greenControlledValve, redControlledValve;
    private ImageIcon greenControlledValveG, redControlledValveG;
    private ImageIcon img;
    private String temperature = "";
    private JButton[] buttons;


    public TechSchema() {
        initPane();
    }

    private void initPane() {

        try {
//            img = new ImageIcon(getClass().getResource("/pic/main.png"));
            img = new ImageIcon(getClass().getResource("/pic/VK1.png"));
            greenControlledValve = new ImageIcon(getClass().getResource("/pic/a.png"));
            redControlledValve = new ImageIcon(getClass().getResource("/pic/b.png"));
            greenControlledValveG = new ImageIcon(getClass().getResource("/pic/green_kran_g.png"));
            redControlledValveG = new ImageIcon(getClass().getResource("/pic/red_kran_g.png"));
        } catch (Throwable t) {
            greenControlledValve = new ImageIcon("./pic/a.png");
            redControlledValve = new ImageIcon("./pic/b.png");
            greenControlledValveG = new ImageIcon("./pic/green_kran_g.png");
            redControlledValveG = new ImageIcon("./pic/red_kran_g.png");
           // img = new ImageIcon("./pic/main.png");
            img = new ImageIcon("./pic/VK1.png");
        }
        Point[] points =
                {
                        new Point(94,81),//71
                        new Point(181,120),
                        new Point(262, 120),
                        new Point(341, 120),
                        new Point(440, 120),
                        new Point(526, 120)
                };

        buttons = new JButton[points.length];
        channelStates = DAO.getChannelStates();
        for (int i = 0; i < points.length; i++) {


            Point point = points[i];
            boolean state = channelStates.get(0).getKran(i);

            ImageIcon ico;
            if (state) {
                ico = (i == 0) ? greenControlledValveG : greenControlledValve;
            } else {
                ico = (i == 0) ? redControlledValveG : redControlledValve;
            }
            final int num = i;
            buttons[i] = ((JButton) add(this, new JButton(ico), ico.getIconWidth(), ico.getIconHeight(), point.x, point.y));

            if (i == 0) {
                buttons[i].addActionListener(new KranButtonActionListener(channelStates, num, 0, greenControlledValveG, redControlledValveG));
            } else {
                buttons[i].addActionListener(new KranButtonActionListener(channelStates, num, 0, greenControlledValve, redControlledValve));
            }
        }


        Dimension size = new Dimension(img.getIconWidth(), img.getIconHeight());
        setPreferredSize(size);
        // setMinimumSize(size);
        // setMaximumSize(size);
        //  setSize(size);
        setLayout(null);


    }

    Component add(Container parent, Component c, int w, int h, int x, int y) {
        c.setSize(w, h);
        c.setLocation(x, y);
        parent.add(c);
        return c;
    }

    public void addData(String temperature) {
        this.temperature = temperature;
        repaint();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img.getImage(), 0, 0, null);
        Font font = g.getFont();
        g.setFont(new Font(font.getName(), Font.BOLD, 20));
        g.drawString(temperature, 412, 35);


    }


    public void changeChannel(int channel) {
        ChannelState channelState = channelStates.get(channel);


        for (int i = 0; i < buttons.length; i++) {
            boolean state = channelState.getKran(i);
            ImageIcon ico;
            if (state) {
                ico = (i == 0) ? greenControlledValveG : greenControlledValve;
            } else {
                ico = (i == 0) ? redControlledValveG : redControlledValve;
            }
            JButton button = buttons[i];
            button.setIcon(ico);
            ((KranButtonActionListener) button.getActionListeners()[0]).setChannelNum(channel);

        }


    }
}


class KranButtonActionListener implements ActionListener {
    List<ChannelState> channelStates;
    Integer kranNum;
    Integer channelNum;
    ImageIcon g, r;

    KranButtonActionListener(List<ChannelState> channelStates, Integer kranNum, Integer channelNum, ImageIcon g, ImageIcon r) {
        this.channelStates = channelStates;
        this.kranNum = kranNum;
        this.channelNum = channelNum;
        this.g = g;
        this.r = r;
    }

    public void setChannelNum(Integer channelNum) {
        this.channelNum = channelNum;
    }

    public void actionPerformed(ActionEvent e) {
        Integer res = (new ActChoiceDialog((JButton) e.getSource(), 0, 1)).getResult();
        System.out.println("res = " + res);
        ChannelState channelState = channelStates.get(channelNum);
        if (res == 1) {
            channelState.setKran(kranNum, true);
            DAO.saveChannelState(channelState);
            MainWindow.buttons[channelNum].setChannelState(channelState);
            ((JButton) e.getSource()).setIcon(g);
        }
        if (res == 2) {
            channelState.setKran(kranNum, false);
            DAO.saveChannelState(channelState);
            MainWindow.buttons[channelNum].setChannelState(channelState);
            ((JButton) e.getSource()).setIcon(r);
        }
    }


}


