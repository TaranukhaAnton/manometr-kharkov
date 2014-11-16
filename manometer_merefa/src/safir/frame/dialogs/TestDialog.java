package safir.frame.dialogs;

import safir.constants.SensorConst;
import safir.data.Message;
import safir.data.Sensor;
import safir.exceptions.NoDataException;
import safir.frame.SafirFrame;
import safir.rs232connect.SerialConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: anton
 * Date: 15.11.14
 * Time: 18:10
 * To change this template use File | Settings | File Templates.
 */
public class TestDialog extends JDialog {


    Integer address;

    public TestDialog(SafirFrame parent, final Sensor sensor) {
        super(parent, true);
        getContentPane().setLayout(null);
        setSize(600, 300);
        setLocation(50, 50);
        setResizable(false);
        address = sensor.getAddress();

        addReadButton(20, 50, "read ZERO", address, SensorConst.ZERO);
        addReadButton(20, 100, "read SPAN", address, SensorConst.SPAN);
        addReadButton(20, 150, "read kDIV", address, SensorConst.kDIV);

        addWriteButton(250, 50, "write ZERO", address, SensorConst.ZERO);
        addWriteButton(250, 100, "write SPAN", address, SensorConst.SPAN);
        addWriteButton(250, 150, "write kDIV", address, SensorConst.kDIV);

    }


    private void addReadButton(int x, int y, String title, final int address, final int[] param) {
        JButton jButton;
        final JLabel label = new JLabel("");
        label.setSize(120, 20);
        label.setLocation(x + 150, y);
        add(label);

        jButton = new JButton(title);
        jButton.setSize(120, 20);
        jButton.setLocation(x, y);
        jButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Message message = SerialConnection.INSTANSE.readVar(param, address);
                    String text = "";
                    text += getString(message.getData()[0]) + " ";
                    text += getString(message.getData()[1]) + " ";
                    text += getString(message.getData()[2]) + " ";
                    text += getString(message.getData()[3]) + " ";
                    label.setText(text);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (NoDataException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

            private String getString(int i) {
                String s = Integer.toHexString(i);

                return (s.length() == 1) ? "0" + s : s;
            }
        });
        add(jButton);


    }


    private void addWriteButton(int x, int y, String title, final int address, final int[] param) {
        JButton jButton;
        final JTextField textField = new JTextField();
        textField.setSize(120, 20);
        textField.setLocation(x + 150, y);
        add(textField);

        jButton = new JButton(title);
        jButton.setSize(120, 20);
        jButton.setLocation(x, y);
        jButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int[] var = {0, 0, 0, 0};
                    String[] split = textField.getText().split(" ");
                    if (split.length != 4) {
                        return;
                    }
                    for (int i = 0; i < split.length; i++) {
                        String hex = split[i];
                        var[i] = Integer.parseInt(hex, 16);
                    }

                    Message message = SerialConnection.INSTANSE.writeVar(param, address, var);

                } catch (InterruptedException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (NoDataException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

            private String getString(int i) {
                String s = Integer.toHexString(i);

                return (s.length() == 1) ? "0" + s : s;
            }
        });
        add(jButton);


    }


}
