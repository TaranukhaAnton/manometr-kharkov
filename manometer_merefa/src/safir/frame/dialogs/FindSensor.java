package safir.frame.dialogs;

import safir.bdUtils.DAO;
import safir.constants.CommonConstants;
import safir.frame.SafirFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.List;

public class FindSensor extends JDialog {

    private JTextField serialNumberField;
    private JTextArea leftTextPanel, rightTextPanel;
    private JScrollPane leftPanel, rightPanel;
    private JComboBox leftYearsComboBox, rightYearsComboBox;
    private JLabel leftYearLabel, rightYearLabel;
    private JScrollPane leftTableScrollPane, rightTableScrollPane;


    public FindSensor(final SafirFrame parent) {


        super(parent, true);
        setSize(1000, 750);
        setLocation(50, 50);
        setResizable(false);
        setTitle("Поиск датчика");
        getContentPane().setLayout(null);

        serialNumberField = new JTextField();
        serialNumberField.setSize(200, 25);
        serialNumberField.setLocation(20, 30);
        add(serialNumberField);


        JLabel lab1 = new JLabel("Введите номер измерительного блока");
        lab1.setSize(300, 20);
        lab1.setLocation(20, 10);
        add(lab1);


        JButton findButton = new JButton("Поиск");
        findButton.setIcon(CommonConstants.okIcon);
        findButton.setSize(100, 25);
        findButton.setLocation(250, 30);
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                find();
            }
        });
        add(findButton);

        JButton okButton = new JButton("Ok");
        okButton.setSize(150, 30);
        okButton.setLocation(250, 675);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
                dispose();
            }
        });
        add(okButton);


        initRight();
        initLeft();

    }

    private void initRight() {
        int h = 70;


        rightYearLabel = new JLabel("Укажите год");
        rightYearLabel.setSize(300, 20);
        rightYearLabel.setLocation(510, h);
        rightYearLabel.setVisible(false);
        add(rightYearLabel);

        rightYearsComboBox = new JComboBox();
        rightYearsComboBox.setSize(100, 20);
        rightYearsComboBox.setLocation(590, h);
        rightYearsComboBox.setVisible(false);

        rightYearsComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    long serNum = Long.parseLong(serialNumberField.getText());
                    String year = (String) e.getItem();
                    findInArchive(serNum, year);
                }
            }
        });

        add(rightYearsComboBox);


        JLabel lab4 = new JLabel("Архивная БД");
        lab4.setSize(300, 20);
        lab4.setLocation(510, h + 30);
        add(lab4);

        rightTextPanel = new JTextArea();
        rightPanel = new JScrollPane(rightTextPanel);
        rightPanel.setSize(400, 250);
        rightPanel.setLocation(500, h + 50);
        add(rightPanel);
    }

    private void initLeft() {
        int h = 70;

        leftYearLabel = new JLabel("Укажите год");
        leftYearLabel.setSize(300, 20);
        leftYearLabel.setLocation(20, h);
        leftYearLabel.setVisible(false);
        add(leftYearLabel);

        leftYearsComboBox = new JComboBox();
        leftYearsComboBox.setVisible(false);
        leftYearsComboBox.setSize(100, 20);
        leftYearsComboBox.setLocation(100, h);
        leftYearsComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    long serNum = Long.parseLong(serialNumberField.getText());
                    String year = (String) e.getItem();
                    findInCurrentDB(serNum, year);
                }
            }
        });
        add(leftYearsComboBox);


        JLabel lab3 = new JLabel("Текущая БД");
        lab3.setSize(300, 20);
        lab3.setLocation(20, h + 30);
        add(lab3);

        leftTextPanel = new JTextArea();
        leftPanel = new JScrollPane(leftTextPanel);
        leftPanel.setSize(400, 250);
        leftPanel.setLocation(10, h + 50);
        add(leftPanel);


    }

    private void find() {
        rightYearsComboBox.setVisible(false);
        rightYearLabel.setVisible(false);
        leftYearsComboBox.setVisible(false);
        leftYearLabel.setVisible(false);

        try {
            long serNum = Long.parseLong(serialNumberField.getText());

            List<String> yearsInSensors = DAO.INSTANSE.findYearsInSensors(serNum);
            List<String> yearsInArchive = DAO.INSTANSE.findYearsInArchive(serNum);


            switch (yearsInArchive.size()) {
                case 0:
                    rightTextPanel.setText("");
                    if (rightTableScrollPane != null) {
                        remove(rightTableScrollPane);
                        repaint();
                    }
                    rightTextPanel.append("Датчик в архивной БД не найден ");
                    break;
                case 1:
                    String year = yearsInArchive.get(0);
                    findInArchive(serNum, year);
                    break;
                default:
                    rightYearLabel.setVisible(true);
                    rightYearsComboBox.setVisible(true);
                    rightYearsComboBox.removeAllItems();
                    for (String str : yearsInArchive) {
                        rightYearsComboBox.addItem(str);
                    }
            }

            switch (yearsInSensors.size()) {
                case 0:
                    leftTextPanel.setText("");
                    if (leftTableScrollPane != null) {
                        remove(leftTableScrollPane);
                        repaint();
                    }
                    leftTextPanel.append("Датчик в текущей БД не найден ");
                    break;
                case 1:
                    String year = yearsInSensors.get(0);
                    findInCurrentDB(serNum, year);

                    break;
                default:
                    leftYearLabel.setVisible(true);
                    leftYearsComboBox.setVisible(true);
                    leftYearsComboBox.removeAllItems();
                    for (String str : yearsInSensors) {
                        leftYearsComboBox.addItem(str);
                    }
            }


        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void findInCurrentDB(long serNum, String year) {
        leftTextPanel.setText("");
        if (leftTableScrollPane != null) {
            remove(leftTableScrollPane);
            repaint();
        }

        leftTableScrollPane = DAO.INSTANSE.getInfoFromDB(leftTextPanel, year, serNum);
        leftTableScrollPane.setSize(400, 250);
        leftTableScrollPane.setLocation(10, 70 + 320);
        add(leftTableScrollPane);
    }

    private void findInArchive(long serNum, String year) {
        rightTextPanel.setText("");
        if (rightTableScrollPane != null) {
            remove(rightTableScrollPane);
            repaint();
        }
        rightTableScrollPane = DAO.INSTANSE.getInfoFromArchive(rightTextPanel, year, serNum);
        rightTableScrollPane.setSize(400, 250);
        rightTableScrollPane.setLocation(500, 70 + 320);
        add(rightTableScrollPane);
    }

}
