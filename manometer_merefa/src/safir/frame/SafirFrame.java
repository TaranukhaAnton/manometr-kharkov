package safir.frame;

import org.apache.log4j.Logger;
import safir.bdUtils.DAO;
import safir.data.Group;
import safir.data.Sensor;
import safir.data.Termocam;
import safir.exceptions.NoDataException;
import safir.exceptions.SerialConnectionException;
import safir.frame.components.MultiLineCellRenderer;
import safir.frame.components.PasswordChooser;
import safir.frame.dialogs.AKAdjustment.FormAK;
import safir.frame.dialogs.*;
import safir.frame.dialogs.propertiesDialogs.GroupProperties;
import safir.frame.dialogs.propertiesDialogs.SensorProperties;
import safir.frame.dialogs.taskChoise.TaskChoiseForm;
import safir.frame.dialogs.writeData.WriteDataDialog;
import safir.rs232connect.SerialConnection;
import safir.utils.SafUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@SuppressWarnings("serial")
public class SafirFrame extends JFrame {
    private Logger log = Logger.getLogger(SafirFrame.class);

    private JTree tree;
    private JTextArea logArea;
    private JScrollPane treeScroll, logAreaScroll;
    private JPopupMenu popupSensor, popupGroup, popupTermocam;

    private JMenuBar menuBar;

    public TreePath clickedPath;
    private final SafirFrame INSTANSE;
    private boolean isLogin;
    private Integer selectedGroup = -1;
    private DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Map<Integer, String> messages;


    public SafirFrame() {


        loadMessages();

        if (messages == null) {
            messages = new HashMap<Integer, String>();
        }


        this.setSize(700, 500);
        this.setLocation(50, 50);
        this.setTitle("ДДТ МХ тестовая версия (21.09.20014) ");
        INSTANSE = this;
        setExtendedState(Frame.MAXIMIZED_BOTH);


        log.info("Открыли прогрaмму");


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logArea = new JTextArea();
        logArea.setEditable(false);
        logAreaScroll = new JScrollPane(logArea);

        popupSensor = constructPopupSensor();
        popupGroup = constructPopupGroup();
        popupTermocam = constructPopupTermocam();
        menuBar = constructMenuBar();
        setJMenuBar(menuBar);


        setVisible(true);
        try {
            SerialConnection.INSTANSE.openConnection();
        } catch (SerialConnectionException e) {
            log.error("Ошибка открытия порта");
            addLogToArea("Ошибка открытия соединения.");
            JOptionPane.showConfirmDialog(null,
                    "COM порт не открывается. \n Обратитесь к технологу.",
                    "Ошибка открытия COM порта .", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);

        }
        constructTree();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                treeScroll, logAreaScroll);

        splitPane.setContinuousLayout(true);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(300);


        add(splitPane, BorderLayout.CENTER);


        isLogin = false;
    }

    private void loadMessages() {
        try {
            FileInputStream fis = new FileInputStream("map.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            messages = (Map) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void constructTree() {
        Vector<Group> groups;
        Vector<Termocam> termocams;
        Vector<Sensor> sensors;
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Система");
        try {
            termocams = DAO.INSTANSE.getTermocams();
            for (Termocam t : termocams) {
                groups = DAO.INSTANSE.getGroups(t.getId());
                for (Group g : groups) {
                    sensors = DAO.INSTANSE.getSensors(g.getGroupNum());
                    for (Sensor s : sensors) {
                        g.add(s);
                    }
                    t.add(g);
                }
                top.add(t);
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null,
                    "Нет связи с базой данных. \n Обратитесь к сисадмину.",
                    "База данных недоступна.", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        tree = new JTree(top);
        tree.addMouseListener(new PopupTrigger(tree, INSTANSE, popupSensor, popupGroup, popupTermocam));
        tree.setCellRenderer(new MultiLineCellRenderer());
        treeScroll = new JScrollPane(tree);
        tree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
        // tree.setEnabled(false);

        // (TreeSelectionModel.SINGLE_TREE_SELECTION)
        // tree.setDropMode(DropMode.ON);
        // tree.setDropTarget(arg0)
        // tree.setDragEnabled(true);
        // tree.setTransferHandler(new TreeTransferHandler());
        // tree.setd

    }

    private JMenuBar constructMenuBar() {
        JMenu menu;
        JMenuBar menuBar = new JMenuBar();
        JMenuItem menuItem;
        menu = new JMenu("File");
        menuBar.add(menu);
        menuItem = menu.add(new JMenuItem("Exit"));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SerialConnection.INSTANSE.closeConnection();
                System.exit(0);
            }
        });


        menu = new JMenu("Технолог");
        menuItem = menu.add(new JMenuItem("Вход/выход"));
        menuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (isLogin) {

                    isLogin = false;
                    INSTANSE.setTitle("ДДТ МХ 1.0 24");

                } else {
                    PasswordChooser dialog = new PasswordChooser();
                    if (dialog.showDialog((JFrame) INSTANSE)) {
                        if (dialog.getName().equals("Технолог") && dialog.getPass().equals("16777216")) {
                            isLogin = true;
                            INSTANSE.setTitle("Вход выполнен: Технолог    ДДТ МХ 1.0 24");
                        }
                    }


                }


            }
        });


        menuItem = menu.add(new JMenuItem("Записать адрес 0х02"));
        menuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new SetDefAddreessDialog(INSTANSE).setVisible(true);
            }
        });

        menuItem = menu.add(new JMenuItem("Поиск датчика"));
        menuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new FindSensor(INSTANSE).setVisible(true);
            }
        });


        menuBar.add(menu);
        return menuBar;
    }

    private JMenu constructMenuTasks() {
        JMenu tasks = new JMenu("Задачи");
        Action a1;
        a1 = new AbstractAction("Настроить КУ") {
            public void actionPerformed(ActionEvent e) {

                if (DAO.INSTANSE.AvailableConnection()) {

                    Group group = null;
                    List<Sensor> sensors = new LinkedList<Sensor>();
                    List<Sensor> resultSensors = new LinkedList<Sensor>();
                    if (clickedPath.getLastPathComponent() instanceof Group) {
                        group = (Group) clickedPath.getLastPathComponent();
                        sensors = group.getListSensors();
                    }
                    if (clickedPath.getLastPathComponent() instanceof Sensor) {
                        Sensor sensor = (Sensor) clickedPath.getLastPathComponent();
                        group = (Group) sensor.getParent();
                        sensors = new LinkedList<Sensor>();
                        sensors.add(sensor);
                    }
                    for (Sensor sensor : sensors) {
                        if (!sensor.isEnabled()) {
                            continue;
                        }
                        sensor.StateScan();
                        if (sensor.isAvailable())
                            resultSensors.add(sensor);
                    }
                    if (resultSensors.isEmpty()) {
                        JOptionPane
                                .showConfirmDialog(
                                        null,
                                        "Нет датчиков, для которых, \n доступно данная операция.",
                                        "Нет доступных датчиков.",
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.WARNING_MESSAGE);
                    } else {
                        if (sensors.size() != resultSensors.size())
                            if (JOptionPane
                                    .showConfirmDialog(
                                            INSTANSE,
                                            "Не для всех датчиков операция доступна. \n Продолжить?",
                                            "Выберите действие",
                                            JOptionPane.OK_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION)
                                return;
                        (new FormAK(INSTANSE, resultSensors, group)).setVisible(true);

                    }
                } else {
                    JOptionPane
                            .showConfirmDialog(
                                    null,
                                    "Нет связи с базой данных. \n Обратитесь к сисадмину.",
                                    "База данных недоступна.",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE);
                }

            }
        };
        tasks.add(a1);
        a1 = new AbstractAction("Снять точки") {
            public void actionPerformed(ActionEvent e) {
                if (DAO.INSTANSE.AvailableConnection()) {
                    Group group = null;
                    List<Sensor> sensors = new LinkedList<Sensor>();
                    List<Sensor> resultSensors = new LinkedList<Sensor>();
                    if (clickedPath.getLastPathComponent() instanceof Group) {
                        group = (Group) clickedPath.getLastPathComponent();
                        sensors = group.getListSensors();
                    }
                    if (clickedPath.getLastPathComponent() instanceof Sensor) {
                        Sensor sensor = (Sensor) clickedPath.getLastPathComponent();
                        group = (Group) sensor.getParent();
                        sensors = new LinkedList<Sensor>();
                        sensors.add(sensor);
                    }
                    for (Sensor sensor : sensors) {
                        if (!sensor.isEnabled()) {
                            continue;
                        }
                        sensor.StateScan();
                        if (sensor.isAvailable() & (sensor.getKAFlag() == 1))
                            resultSensors.add(sensor);
                    }
                    if (resultSensors.isEmpty()) {
                        JOptionPane
                                .showConfirmDialog(
                                        null,
                                        "Нет датчиков, для которых, \n доступнa данная операция.",
                                        "Нет доступных датчиков.",
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.WARNING_MESSAGE);
                    } else {
                        if (sensors.size() != resultSensors.size())
                            if (JOptionPane
                                    .showConfirmDialog(
                                            INSTANSE,
                                            "Не для всех датчиков операция доступна. \n Продолжить?",
                                            "Выберите действие",
                                            JOptionPane.OK_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION)
                                return;
                        (new TaskChoiseForm(INSTANSE, resultSensors, group))
                                .setVisible(true);
                    }
                } else {
                    JOptionPane
                            .showConfirmDialog(
                                    null,
                                    "Нет связи с базой данных. \n Обратитесь к сисадмину.",
                                    "База данных недоступна.",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE);
                }
            }
        };
        tasks.add(a1);
        a1 = new AbstractAction("Выч. и зап. коэффициенты") {
            public void actionPerformed(ActionEvent e) {

                if (DAO.INSTANSE.AvailableConnection()) {
                    Group group = null;
                    List<Sensor> sensors = new LinkedList<Sensor>();
                    List<Sensor> resultSensors = new LinkedList<Sensor>();
                    if (clickedPath.getLastPathComponent() instanceof Group) {
                        group = (Group) clickedPath.getLastPathComponent();
                        sensors = group.getListSensors();
                    }
                    if (clickedPath.getLastPathComponent() instanceof Sensor) {
                        Sensor sensor = (Sensor) clickedPath.getLastPathComponent();
                        group = (Group) sensor.getParent();
                        sensors = new LinkedList<Sensor>();
                        sensors.add(sensor);
                    }
                    for (Sensor sensor : sensors) {
                        if (!sensor.isEnabled()) {
                            continue;
                        }
                        sensor.StateScan();
                        boolean savedata = true;
                        for (Integer flag : sensor.getTempFlags())
                            if (flag != 1)
                                savedata = false;

                        if (sensor.isAvailable() & (sensor.getKAFlag() == 1)
                                & savedata)
                            resultSensors.add(sensor);
                    }
                    if (resultSensors.isEmpty()) {
                        JOptionPane
                                .showConfirmDialog(
                                        null,
                                        "Hет датчиков, для которых, \n доступнa данная операция.",
                                        "Нет доступных датчиков.",
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.WARNING_MESSAGE);
                    } else {
                        if (sensors.size() != resultSensors.size())
                            if (JOptionPane
                                    .showConfirmDialog(
                                            INSTANSE,
                                            "Не для всех датчиков операция доступна. \n Продолжить?",
                                            "Выберите действие",
                                            JOptionPane.OK_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION)
                                return;
                        (new WriteDataDialog(INSTANSE, resultSensors, group))
                                .setVisible(true);
                    }
                } else {
                    JOptionPane
                            .showConfirmDialog(
                                    null,
                                    "Нет связи с базой данных. \n Обратитесь к сисадмину.",
                                    "База данных недоступна.",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE);
                }
            }
        };
        tasks.add(a1);
        a1 = new AbstractAction("Настроить конст. 0 диап.") {
            public void actionPerformed(ActionEvent e) {
                if (DAO.INSTANSE.AvailableConnection()) {
                    Group group = null;
                    List<Sensor> sensors = new LinkedList<Sensor>();
                    List<Sensor> resultSensors = new LinkedList<Sensor>();
                    if (clickedPath.getLastPathComponent() instanceof Group) {
                        group = (Group) clickedPath.getLastPathComponent();
                        sensors = group.getListSensors();
                    }
                    if (clickedPath.getLastPathComponent() instanceof Sensor) {
                        Sensor sensor = (Sensor) clickedPath.getLastPathComponent();
                        group = (Group) sensor.getParent();
                        sensors = new LinkedList<Sensor>();
                        sensors.add(sensor);
                    }
                    for (Sensor sensor : sensors) {
                        if (!sensor.isEnabled()) {
                            continue;
                        }
                        sensor.StateScan();
                        boolean savedata = true;
                        for (Integer flag : sensor.getTempFlags())
                            if (flag != 1)
                                savedata = false;

                        if (sensor.isAvailable() & (sensor.getKAFlag() == 1)
                                & savedata & (sensor.getDataIsWrote() == 1))
                            resultSensors.add(sensor);
                    }
                    if (resultSensors.isEmpty()) {
                        JOptionPane
                                .showConfirmDialog(
                                        null,
                                        "Hет датчиков, для которых, \n доступнa данная операция.",
                                        "Нет доступных датчиков.",
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.WARNING_MESSAGE);
                    } else {
                        if (sensors.size() != resultSensors.size())

                            if (JOptionPane
                                    .showConfirmDialog(
                                            INSTANSE,
                                            "Не для всех датчиков операция доступна. \n Продолжить?",
                                            "Выберите действие",
                                            JOptionPane.OK_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION)
                                return;

                        (new ConstZeroDiapazon(INSTANSE, resultSensors, group))
                                .setVisible(true);
                    }
                } else {
                    JOptionPane
                            .showConfirmDialog(
                                    null,
                                    "Нет связи с базой данных. \n Обратитесь к сисадмину.",
                                    "База данных недоступна.",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE);
                }
            }
        };
        tasks.add(a1);
        return tasks;
    }

    // Создается контекстное меню привязанное к датчику
    private JPopupMenu constructPopupSensor() {
        JPopupMenu menu = new JPopupMenu();
        // final SafirFrame f = this;

        Action a1;



        a1= new AbstractAction("Опросить") {
            public void actionPerformed(ActionEvent e) {

                Sensor sensor = (Sensor) clickedPath.getLastPathComponent();
                //sensor.StateScan();
                try {

                    int P = SerialConnection.INSTANSE.readPressure(sensor.getAddress());
                    int T = SerialConnection.INSTANSE.readTemperature(sensor.getAddress());
                    addLogToArea("Датчик " + sensor.getSerNum() + " ответил. P= " + P + " T= " + T);
                    sensor.setAvailable(true);
                } catch (InterruptedException e1) {

                } catch (NoDataException e1) {
                    sensor.setAvailable(false);
                    addLogToArea("Датчик " + sensor.getSerNum() + " не ответил. ");
                }


                DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                ((DefaultTreeModel) tree.getModel()).nodeChanged(sensor);
                Group group = (Group) sensor.getParent();

                model.reload(group);

            }
        };

        menu.add(a1);

        a1 = new AbstractAction("Монитор") {
            public void actionPerformed(ActionEvent e) {
                (new SensorMonitor(INSTANSE, (Sensor) clickedPath
                        .getLastPathComponent())).setVisible(true);
            }
        };
        menu.add(a1);
        menu.add(constructMenuTasks());
        a1 = new AbstractAction("Свойства") {
            public void actionPerformed(ActionEvent e) {
                (new SensorProperties(INSTANSE, (Sensor) clickedPath
                        .getLastPathComponent())).setVisible(true);
            }
        };
        menu.add(a1);
        a1 = new AbstractAction("Контрольная сумма") {
            public void actionPerformed(ActionEvent e) {
                Sensor s = (Sensor) clickedPath.getLastPathComponent();
                String checkSum = SerialConnection.INSTANSE.getCheckSum(s.getAddress());

                StringBuilder sb = new StringBuilder(checkSum);
                //	sb.reverse();
                JOptionPane.showConfirmDialog(INSTANSE, "Контрольная сумма \n"
                        + sb, "Контрольная сумма.", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        };
        menu.add(a1);

        a1 = new AbstractAction("Фиксированный ток") {
            public void actionPerformed(ActionEvent e) {

                (new FixedCurrentOutput(INSTANSE, (Sensor) clickedPath
                        .getLastPathComponent())).setVisible(true);
            }
        };
        menu.add(a1);

        Action a5 = new AbstractAction("Читать датчик") {
            public void actionPerformed(ActionEvent e) {
                (new ReadSensorDialog(INSTANSE, (Sensor) clickedPath
                        .getLastPathComponent())).setVisible(true);
            }
        };
        menu.add(a5);

        menu.add(new AbstractAction("Отключить датчик") {
            public void actionPerformed(ActionEvent e) {
                Sensor sensor = (Sensor) clickedPath.getLastPathComponent();
                boolean enabled = sensor.isEnabled();
                sensor.setEnabled(!enabled);
            }
        });

        menu.addSeparator();

        a1 = new AbstractAction("test") {
            public void actionPerformed(ActionEvent e) {
                (new TestDialog(INSTANSE, (Sensor) clickedPath
                        .getLastPathComponent())).setVisible(true);
            }
        };
        menu.add(a1);

        Action a4 = new AbstractAction("Симулировать") {
            public void actionPerformed(ActionEvent e) {
                Sensor s = (Sensor) clickedPath
                        .getLastPathComponent();
                Group g = (Group) s.getParent();
                SafUtil.simulateDataSensor(INSTANSE, s, g);


            }
        };
        menu.add(a4);


        a4 = new AbstractAction("Редактировать датчик") {
            public void actionPerformed(ActionEvent e) {
//				Sensor s = (Sensor) clickedPath
//				.getLastPathComponent();
//				Group g = (Group)s.getParent();
//				SafUtil.simulateDataSensor(INSTANSE,s, g);


                (new RedactSensor(INSTANSE)).setVisible(true);


            }
        };
        menu.add(a4);
        a4 = new AbstractAction("Удалить без сохранения в архиве") {
            public void actionPerformed(ActionEvent e) {
                if (DAO.INSTANSE.AvailableConnection()) {

                    int res = JOptionPane.showConfirmDialog(null,
                            "Действительно хотите удалить датчик без сохранения в архиве?",
                            "Удаление датчика", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (res == JOptionPane.OK_OPTION) {
                        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                        Sensor sensor = (Sensor) clickedPath.getLastPathComponent();
                        Group group = (Group) sensor.getParent();
                        if (sensor.deleteSensor(false))
                            addLogToArea("Датчик удален. Адрес 0x02 записан успешно.");
                        else
                            addLogToArea("Датчик удален. При записи адреса 0x02 произошла ошибка.");
                        model.removeNodeFromParent(sensor);
                        model.reload(group);
                    }
                } else {
                    JOptionPane
                            .showConfirmDialog(
                                    null,
                                    "Нет связи с базой данных. \n Обратитесь к сисадмину.",
                                    "База данных недоступна.",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE);
                }

            }
        };
        menu.add(a4);

        menu.addSeparator();


        Action a2 = new AbstractAction("Удалить") {
            public void actionPerformed(ActionEvent e) {
                if (DAO.INSTANSE.AvailableConnection()) {

                    int res = JOptionPane.showConfirmDialog(null,
                            "Действительно хотите удалить датчик?",
                            "Удаление датчика", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (res == JOptionPane.OK_OPTION) {
                        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                        Sensor sensor = (Sensor) clickedPath.getLastPathComponent();
                        Group group = (Group) sensor.getParent();
                        if (sensor.deleteSensor(true))
                            addLogToArea("Датчик удален. Адрес 0x02 записан успешно.");
                        else
                            addLogToArea("Датчик удален. При записи адреса 0x02 произошла ошибка.");
                        model.removeNodeFromParent(sensor);
                        model.reload(group);
                    }
                } else {
                    JOptionPane
                            .showConfirmDialog(
                                    null,
                                    "Нет связи с базой данных. \n Обратитесь к сисадмину.",
                                    "База данных недоступна.",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE);
                }

            }
        };
        menu.add(a2);
        return menu;
    }

    // Создается контекстное меню привязанное к термокамере
    private JPopupMenu constructPopupTermocam() {

        JPopupMenu menu = new JPopupMenu();
        // final SafirFrame f = this;
        Action a1 = new AbstractAction("Добавить группу") {

            public void actionPerformed(ActionEvent e) {
                (new NewGroupDialog(INSTANSE)).setVisible(true);
            }
        };
        menu.add(a1);
        return menu;


    }

    // Создается контекстное меню привязанное к группе
    private JPopupMenu constructPopupGroup() {
        JPopupMenu menu = new JPopupMenu();
        Action a1 = new AbstractAction("Добавить датчики") {
            public void actionPerformed(ActionEvent e) {
                if (DAO.INSTANSE.AvailableConnection()) {
                    new AddSensorDialog(INSTANSE).setVisible(true);
                } else {
                    JOptionPane
                            .showConfirmDialog(
                                    null,
                                    "Нет связи с базой данных. \n Обратитесь к сисадмину.",
                                    "База данных недоступна.",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE);
                }
            }
        };
        menu.add(a1);

        Action a3 = new AbstractAction("Опросить датчики") {
            public void actionPerformed(ActionEvent e) {
                Group group = (Group) clickedPath.getLastPathComponent();

                if (SerialConnection.INSTANSE.isOpen()) {
                    for (Sensor sensor : group.getListSensors()) {
                        if (!sensor.isEnabled()) {
                            continue;
                        }
                        try {
                            int P = SerialConnection.INSTANSE.readPressure(sensor.getAddress());
                            int T = SerialConnection.INSTANSE.readTemperature(sensor.getAddress());
                            addLogToArea("Датчик " + sensor.getSerNum() + " ответил. P= " + P + " T= " + T);
                            sensor.setAvailable(true);
                        } catch (InterruptedException e1) {

                        } catch (NoDataException e1) {
                            sensor.setAvailable(false);
                            addLogToArea("Датчик " + sensor.getSerNum() + " не ответил.");
                        }
                        //sensor.StateScan();

                    }

                }

                //	group.sensorsStateScan();
                ((DefaultTreeModel) tree.getModel()).nodeChanged(group);
            }
        };
        menu.add(a3);
        menu.addSeparator();
        menu.add(constructMenuTasks());
        a1 = new AbstractAction("Свойства") {
            public void actionPerformed(ActionEvent e) {
                (new GroupProperties(INSTANSE, (Group) clickedPath
                        .getLastPathComponent())).setVisible(true);
            }
        };
        menu.add(a1); //
        menu.addSeparator();


        Action a4 = new AbstractAction("Симулировать") {
            public void actionPerformed(ActionEvent e) {
            }
        };
        menu.add(a4);

        menu.addSeparator();

        Action a2 = new AbstractAction("Удалить") {
            public void actionPerformed(ActionEvent e) {

                if (DAO.INSTANSE.AvailableConnection()) {

                    int res = JOptionPane.showConfirmDialog(null,
                            "Действительно хотите удалить группу?",
                            "Удаление группы", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (res == JOptionPane.OK_OPTION) {
                        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                        Group group = (Group) clickedPath.getLastPathComponent();

                        for (Sensor sensor : group.getListSensors()) {
                            if (sensor.deleteSensor(true))
                                addLogToArea("Датчик удален. Адрес 0x02 записан успешно.");
                            else
                                addLogToArea("Датчик удален. При записи адреса 0x02 произошла ошибка.");
                            model.removeNodeFromParent(sensor);
                        }
                        group.deleteGroup();
                        model.removeNodeFromParent(group);
                        messages.remove(group.getGroupNum());
                        saveMessages();


                    }

                } else {
                    JOptionPane
                            .showConfirmDialog(
                                    null,
                                    "Нет связи с базой данных. \n Обратитесь к сисадмину.",
                                    "База данных недоступна.",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE);
                }

            }
        };
        menu.add(a2);


        return menu;
    }


    public static void main(String[] args) {

        SafirFrame sf = new SafirFrame();
        sf.setVisible(true);
        sf.repaint();
    }

    public JTree getTree() {
        return tree;
    }


    public void setClickedPath(TreePath clickedPath) {
        this.clickedPath = clickedPath;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void addLogToArea(String log) {

        Calendar cal = Calendar.getInstance();
        String string = log + "      " + sdf.format(cal.getTime()) + "\n";
        logArea.append(string);


        if (selectedGroup != -1) {
            String text = messages.get(selectedGroup);
            if (text == null) {
                text = "";
            }
            text += string;
            messages.put(selectedGroup, text);


            saveMessages();


        }

    }

    private void saveMessages() {
        try {
            FileOutputStream fos = new FileOutputStream("map.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(messages);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void setSelectedGroupId(int id) {
        selectedGroup = id;
        String text = messages.get(selectedGroup);
        if (text != null) {
            logArea.setText(text);
        } else {
            logArea.setText("");
        }


    }
}


