package safir.frame.dialogs;

import java.awt.Choice;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.InternationalFormatter;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;

import safir.bdUtils.DAO;
import safir.constants.CommonConstants;
import safir.constants.SensorConst;
import safir.data.Group;
import safir.data.Message;
import safir.data.Sensor;
import safir.data.Termocam;
import safir.exceptions.NoDataException;
import safir.frame.SafirFrame;
import safir.rs232connect.SerialConnection;
import safir.utils.SafUtil;
import sun.swing.SwingUtilities2;

@SuppressWarnings("serial")
public class AddSensorDialog extends JDialog implements Runnable {
    private SafirFrame parent;
    private JButton okButton;
    private JButton addButton;
    private JLabel label1;
    private JLabel labCounter;
    private JFormattedTextField edit;
    private Thread thread;
    private JTree tree;
    private JProgressBar prBar;
    private JTextArea addedSensors;
    private JScrollPane addedSensorsPane;
    private Group group;
    private boolean stopThread = false;
    private Logger log;
    final AddSensorDialog INSTANSE;
    private Choice choice1;

    /**
     * @param parent
     */
    public AddSensorDialog(SafirFrame parent) {

        super(parent, true);
        INSTANSE = this;
        group = (Group) parent.clickedPath.getLastPathComponent();
        log = Logger.getLogger(this.getClass());
        this.tree = parent.getTree();
        this.parent = parent;
        getContentPane().setLayout(null);
        setSize(400, 400);
        setLocation(300, 100);
        setResizable(false);
        initComponents();
        prBar.setIndeterminate(true);
        thread = new Thread(this);
        thread.start();
        choice1 = new Choice();
        choice1.setLocation(210, 90);
        choice1.setSize(100, 18);
        add(choice1);

        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year; i >= 2006; i--) {
            choice1.add(i + "");
        }


        choice1.setVisible(false);

        choice1.setFont(new Font("Dialog", 4, 18));

    }

    private void initComponents() {

        addedSensors = new JTextArea();
        addedSensors.setEditable(false);
        addedSensorsPane = new JScrollPane(addedSensors);
        addedSensorsPane.setVisible(false);
        addedSensorsPane.setSize(370, 75);
        addedSensorsPane.setLocation(15, 225);
        add(addedSensorsPane);

        prBar = new JProgressBar();
        prBar.setSize(300, 20);
        prBar.setLocation(50, 50);
        add(prBar);

        setTitle("Добавление датчиков");

        // edit = new JTextField("");
        // edit.setVisible(false);

        edit = new JFormattedTextField(new InternationalFormatter(NumberFormat
                .getIntegerInstance()) {
            @Override
            protected DocumentFilter getDocumentFilter() {
                // TODO Auto-generated method stub
                return filter;
            }

            private DocumentFilter filter = new IntFilter();
        });

        edit.setVisible(false);

        edit.setFont(new Font("Dialog", 4, 18));

        edit.setSize(80, 25);
        edit.setLocation(110, 90);
        edit.addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyCode() == 10) {
                    try {
                        edit.commitEdit();
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    addSensor();
                }
            }


            public void keyReleased(KeyEvent arg0) {

            }


            public void keyTyped(KeyEvent arg0) {
            }
        });

        add(edit);

        labCounter = new JLabel("Введине номер и год измерительного блока");
        labCounter.setSize(300, 20);
        labCounter.setVisible(false);
        labCounter.setLocation(80, 120);
        add(labCounter);

        label1 = new JLabel("Поиск датчика.");
        label1.setFont(new Font("Dialog", 1, 20));
        label1.setSize(400, 20);
        label1.setLocation(100, 5);
        add(label1);

        // %%%%%%%%%%%%%%%%%%---okButton---%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        okButton = new JButton("Выход");
        okButton.setIcon(CommonConstants.canselIcon);
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                stopThread = true;

                try {
                    thread.join();
                } catch (InterruptedException e) {
                }
                setVisible(false);
                dispose();
            }
        });
        okButton.setSize(150, 30);
        okButton.setLocation(135, 325);
        add(okButton);
        // 88888888888888888888888888888888888888888888888888888888888888888

        addButton = new JButton("Добавить");
        addButton.setIcon(CommonConstants.okIcon);
        addButton.setVisible(false);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSensor();
            }
        });
        addButton.setSize(150, 30);
        addButton.setLocation(135, 180);
        add(addButton);

		/*
         * cancelButton = new JButton("Отмена");
		 * cancelButton.addActionListener(new java.awt.event.ActionListener() {
		 * public void actionPerformed(java.awt.event.ActionEvent evt) {
		 * stopThread = true; while (thread.isAlive()) { try {
		 * Thread.sleep(100); } catch (InterruptedException e) { } }
		 * setVisible(false); dispose(); } }); cancelButton.setSize(100, 25);
		 * cancelButton.setLocation(175, 325); add(cancelButton);
		 */

    }

    private void addSensor() {

        try {
            Long sernum = (Long) edit.getValue();
            int year = Integer.valueOf(choice1.getSelectedItem());
            // проверяем существует датчик с таким номером
            // измеритльного блока в текущей базе данных
            if (DAO.INSTANSE.existSensor(sernum, year)) {
                JOptionPane.showMessageDialog(null,
                        "Датчик с таким серийным номером "
                                + "уже существует в базе данных");
                edit.setText("");
            } else {
                // проверяем существует датчик с таким номером
                // измеритльного блока в архивной базе данных
                if (DAO.INSTANSE.existSensorInArchive(sernum, year)) {
                    Object[] options1 = {"В текущ. группу",
                            "Настроить заново", "В новую группу", "Отмена"};
                    Object[] options2 = {"Настроить заново", "В новую группу",
                            "Отмена"};
                    int action = 3;
                    if (DAO.INSTANSE.groupsEqualType(sernum, year, group)) {

                        action = JOptionPane
                                .showOptionDialog(
                                        null,
                                        "Выберите действие",
                                        "Датчик с таким номером ИБ существует в архивной БД",
                                        JOptionPane.OK_CANCEL_OPTION,
                                        JOptionPane.QUESTION_MESSAGE, null,
                                        options1, options1[3]);
                    } else {
                        action = JOptionPane
                                .showOptionDialog(
                                        null,
                                        "Выберите действие",
                                        "Датчик с таким номером ИБ существует в архивной БД",
                                        JOptionPane.OK_CANCEL_OPTION,
                                        JOptionPane.QUESTION_MESSAGE, null,
                                        options2, options2[2]);
                        action++;

                    }

                    switch (action) {
                        case 0:
                            addSensor2CurrentGroup(sernum, year);
                            break;

                        case 1:
                            addNewSensor2CurrentGroup(sernum, year);
                            break;

                        case 2:
                            addSensor2NewGroup(sernum, year);
                            break;

                        default:
                            break;
                    }

                } else {
                    addNewSensor2CurrentGroup(sernum, year);
                }

            }

        } catch (Exception e) {
            log.error("Проблемы при создании или добавлении \"sensor\"", e);
            e.printStackTrace();
        }

    }

    ;

    private void addSensor2NewGroup(long sernum, int year) throws Exception {

        Termocam parentTermocam;
        parentTermocam = (Termocam) group.getParent();

        Group newGroup = DAO.INSTANSE.sensorToNewGroup(sernum, year, parentTermocam.getId());

        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

        model.insertNodeInto(newGroup, parentTermocam, parentTermocam
                .getChildCount());
        model.nodeStructureChanged(parentTermocam);

        writeDataToLog(newGroup, (Sensor) newGroup.getFirstChild());
        toOriginState();

    }

    private void addNewSensor2CurrentGroup(Long sernum, int year)
            throws Exception {
        Sensor sensor = new Sensor();
        sensor.setSerNum(sernum);
        sensor.setYear(year);
        SerialConnection.INSTANSE.writeNewAddress(sensor.getAddress());

        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        Float[][] table = new Float[group.getPointsP()][group.getPointsT()];
        Float[] temperatures = new Float[group.getPointsT()];
        Integer[] tempFlags = new Integer[group.getPointsT()];
        Arrays.fill(tempFlags, 0);
        sensor.setTempFlags(tempFlags);
        sensor.setTable(table);

        sensor.setTemperatures(temperatures);
        sensor.setAvailable(true);

        sensor.setGroup(group.getGroupNum());
        DAO.INSTANSE.insertSensor(sensor, Integer.parseInt(choice1.getSelectedItem()));
        model.insertNodeInto(sensor, group, group.getChildCount());
        model.nodeStructureChanged(group);

        writeDataToLog(group, sensor);
        toOriginState();

    }

    private void addSensor2CurrentGroup(Long sernum, int year) throws Exception {
        Sensor sensor = DAO.INSTANSE.sensorToCurrentGroup(sernum, year, group);

        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        model.insertNodeInto(sensor, group, group.getChildCount());
        model.nodeStructureChanged(group);

        writeDataToLog(group, sensor);
        toOriginState();

    }

    private void toOriginState() {
        addedSensorsPane.setVisible(true);
        edit.setVisible(false);
        choice1.setVisible(false);
        addButton.setVisible(false);
        labCounter.setVisible(false);
        prBar.setVisible(true);
        prBar.setIndeterminate(true);
        thread = new Thread(INSTANSE);
        thread.start();
        label1.setText("Поиск следующего датчика.");
        edit.setText("");
    }

    private void writeDataToLog(Group group, Sensor sensor) {

        int t = 0;
        int p = 0;
        try {
            p = SerialConnection.INSTANSE.readPressure(sensor.getAddress());
            t = SerialConnection.INSTANSE.readTemperature(sensor.getAddress());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NoDataException e) {
            e.printStackTrace();
        }

        addedSensors.append("Датчик " + sensor.getSerNum() + " добавлен. ");
        addedSensors.append("P= " + p + " T= " + t + " \n");

        log.info("Датчик с номером измерительного блока " + sensor.getSerNum()
                + " добавлен");

        parent.addLogToArea(
                "Датчик номер из.бл. " + sensor.getSerNum() + "  группа "
                        + group.getGroupNum() + " добавлен.");

        SafUtil.log(sensor, "Датчик добавлен", true);
        SafUtil.log(sensor, "Группа --" + group, false);
        SafUtil.log(sensor, "Количество точек по температуре --"
                + group.getPointsT(), false);
        SafUtil.log(sensor, "Количество точек по давлению --"
                + group.getPointsP(), false);
        SafUtil.log(sensor, "Номер измерительного блока -- "
                + sensor.getSerNum(), false);
    }

    public void run() {
        for (; ; ) {

            try {

                Message m = SerialConnection.INSTANSE.readVar(SensorConst.NOMSENS, 0x02);
                int[] b = m.getData();
                SafUtil.printHexArray(b, true);

                long n1 = SafUtil.ByteToInt(b[0], b[1]), n2 = SafUtil.ByteToInt(b[2], 0);
                n1 = n1 | (n2 << 16);

                label1.setText("Датчик с адресом 0х02 обнаружен.");
                prBar.setIndeterminate(false);
                label1.setLocation(20, 5);
                edit.setVisible(true);
                choice1.setVisible(true);
                SwingUtilities2.adjustFocus(edit);
                if (n1 == 999924)
                    edit.setText("");
                else
                    edit.setText(Long.toString(n1));
                addButton.setVisible(true);
                labCounter.setVisible(true);
                prBar.setVisible(false);
                prBar.setValue(1);
                break;

            } catch (Exception e) {
                // e.printStackTrace();
            }

            if (stopThread)
                break;

        }

    }

    class IntFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass arg0, int arg1, String arg2,
                                 AttributeSet arg3) throws BadLocationException {
            StringBuilder builder = new StringBuilder(arg2);

            for (int i = builder.length() - 1; i >= 0; i--) {
                int cp = builder.codePointAt(i);
                if (!Character.isDigit(cp)) {
                    builder.deleteCharAt(i);
                    if (Character.isSupplementaryCodePoint(cp)) {
                        i--;
                        builder.deleteCharAt(i);
                    }
                }
            }

            // TODO Auto-generated method stub
            super.insertString(arg0, arg1, builder.toString(), arg3);
        }

        @Override
        public void replace(FilterBypass arg0, int arg1, int arg2, String arg3,
                            AttributeSet arg4) throws BadLocationException {

            if (arg3 != null) {
                StringBuilder builder = new StringBuilder(arg3);
                for (int i = builder.length() - 1; i >= 0; i--) {
                    int cp = builder.codePointAt(i);
                    if (!Character.isDigit(cp)) {
                        builder.deleteCharAt(i);
                        if (Character.isSupplementaryCodePoint(cp)) {
                            i--;
                            builder.deleteCharAt(i);
                        }
                    }
                }
                arg3 = (arg0.getDocument().getLength() >= 7) ? "" : builder
                        .toString();
            }
            super.replace(arg0, arg1, arg2, arg3, arg4);
        }

    }

}
