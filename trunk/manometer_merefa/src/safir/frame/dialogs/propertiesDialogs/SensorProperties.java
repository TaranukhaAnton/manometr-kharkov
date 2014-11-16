package safir.frame.dialogs.propertiesDialogs;

import safir.bdUtils.DAO;
import safir.constants.CommonConstants;
import safir.data.Group;
import safir.data.Sensor;
import safir.data.SensorTypes;
import safir.frame.SafirFrame;
import safir.frame.components.ChartPanel;
import safir.frame.components.RowHeaderRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.NumberFormat;

public class SensorProperties extends JDialog {
    /**
     *
     */
    private Group group;
    private Sensor sensor;

    private JTable table;
    private JTextPane textPane;
    private JTextArea note;
    private JScrollPane scrollPane, tableScroll, noteScroll;

    private JButton cancelButton;
    private JButton nextButton;
    private JLabel titleLabel, noteTitle;
    String measure = "";
    private SafirFrame p;

	/*
     * private JLabel PMaxLabel; private JLabel PMinLabel; private JLabel
	 * TMaxLabel; private JLabel TMinLabel; private JLabel PBase;
	 */

    /**
     *
     */
    public SensorProperties(final SafirFrame parent, final Sensor sensor) {
        super(parent, true);


        p = parent;
        this.sensor = sensor;
        group = (Group) sensor.getParent();
        setSize(800, 600);
        setLocation(50, 50);
        setResizable(false);
        setTitle(sensor.toString());
        getContentPane().setLayout(null);
        titleLabel = new JLabel(sensor.toString());
        titleLabel.setBounds(10, 10, 210, 30);
        add(titleLabel);

        drawChart();


        initMeasure();

        textPane = new JTextPane();
        scrollPane = new JScrollPane(textPane);
        scrollPane.setSize(300, 200);
        scrollPane.setLocation(10, 50);
        add(scrollPane);
        initializeStyles();


        initText(sensor);

        noteTitle = new JLabel("Примечания");
        noteTitle.setSize(100, 20);
        noteTitle.setLocation(20, 275);
        add(noteTitle);
        note = new JTextArea();
        note.append(sensor.getNote());
        note.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent arg0) {
            }

            public void keyReleased(KeyEvent arg0) {
            }

            public void keyTyped(KeyEvent arg0) {
                nextButton.setEnabled(true);
            }
        });

        noteScroll = new JScrollPane(note);
        noteScroll.setSize(300, 100);

        noteScroll.setLocation(10, 300);
        add(noteScroll);

        contructTable();

        cancelButton = new JButton("Отмена");
        cancelButton.setIcon(CommonConstants.canselIcon);
        cancelButton.setSize(130, 30);
        cancelButton.setLocation(30, 510);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
                dispose();
            }
        });
        add(cancelButton);

        nextButton = new JButton("Применить");
        nextButton.setEnabled(false);
        nextButton.setIcon(CommonConstants.okIcon);
        nextButton.setSize(130, 30);
        nextButton.setLocation(180, 510);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                sensor.setNote(note.getText());
                JTree tree = p.getTree();
                DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                model.nodeChanged(sensor);
                try {
                    DAO.INSTANSE.updateSensor(sensor);
                } catch (SQLException e) {
                    JOptionPane
                            .showConfirmDialog(
                                    null,
                                    "Нет связи с базой данных. \n Обратитесь к сисадмину.",
                                    "База данных недоступна.",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE);
                }
                setVisible(false);
                dispose();
            }
        });
        add(nextButton);

    }

    private void initText(Sensor sensor) {
        Document document = textPane.getDocument();
        try {
            document.insertString(document.getLength(), "P max = "
                    + group.getMaxPressure() + measure, textPane
                    .getStyle("normal"));
            document.insertString(document.getLength(), "   P min = "
                    + group.getMinPressure() + measure + "\n", textPane
                    .getStyle("normal"));
            document.insertString(document.getLength(), "T max = "
                    + group.getMaxTemp(), textPane.getStyle("normal"));
            document.insertString(document.getLength(), "    T min = "
                    + group.getMinTemp() + "\n", textPane.getStyle("normal"));

            document.insertString(document.getLength(), "P баз = "
                    + group.getPressures().get(group.getBaseRow()).floatValue() + measure
                    + "\n\n", textPane.getStyle("normal"));
            if ((sensor.getKoefAmplificftion() == -1)
                    | (sensor.getKoefAmplificftion() == 1)
                    | (sensor.getKoefAmplificftion() == 7)) {
                if (sensor.getKoefAmplificftion() == -1)
                    document.insertString(document.getLength(),
                            "Коэффициент усиления не настроен.\n", textPane
                            .getStyle("normal"));
                else
                    document
                            .insertString(
                                    document.getLength(),
                                    "При настройке коэффициента усиления произошли ошибки.\n",
                                    textPane.getStyle("normal"));
            } else {
                document.insertString(document.getLength(),
                        "Коэффициент усиления настроен.\n", textPane
                        .getStyle("green"));
                for (int i = 0; i < sensor.getTempFlags().length; i++)
                    switch (sensor.getTempFlags()[i]) {
                        case 0:
                            document.insertString(document.getLength(), "Точки на "
                                    + group.getTemperatures().get(i)
                                    + " градусах не сняты.\n", textPane
                                    .getStyle("normal"));
                            break;
                        case 1:
                            document.insertString(document.getLength(), "Точки на "
                                    + group.getTemperatures().get(i)
                                    + " градусах сняты.\n", textPane
                                    .getStyle("green"));
                            break;
                        case 2:
                            document.insertString(document.getLength(),
                                    "При снятии данных на "
                                            + group.getTemperatures().get(i)
                                            + " градусов возникли проблемы.\n",
                                    textPane.getStyle("red"));
                            break;
                    }
                switch (sensor.getDataIsWrote()) {
                    case 0:
                        document.insertString(document.getLength(),
                                "Данные в датчик не записаны.\n", textPane
                                .getStyle("normal"));
                        break;
                    case 1:
                        document.insertString(document.getLength(),
                                "Данные в датчик записаны.\n", textPane
                                .getStyle("green"));
                        break;
                    case 2:
                        document.insertString(document.getLength(),
                                "При записи данных в датчик возникли проблемы.\n",
                                textPane.getStyle("red"));
                        break;
                }
                switch (sensor.getK0diapIsWrote()) {
                    case 0:
                        document.insertString(document.getLength(),
                                "Константы нуля диапазона в датчик не записаны.",
                                textPane.getStyle("normal"));
                        break;
                    case 1:
                        document.insertString(document.getLength(),
                                "Константы нуля диапазона в датчик записаны.",
                                textPane.getStyle("green"));
                        break;
                    case 2:
                        document
                                .insertString(
                                        document.getLength(),
                                        "При записи констант нуля диапазона в датчик возникли проблемы.",
                                        textPane.getStyle("red"));
                        break;
                }
            }
        } catch (BadLocationException e) {
            // TODO: handle exception
        }
    }

    private void initMeasure() {
        switch (SensorTypes.INSTANSE.sensorTypes.get(group.getType()).measure) {
            case 0:
                measure = " Па";
                break;
            case 1:
                measure = " кПа";
                break;
            case 2:
                measure = " MПа";
                break;
            default:
                break;
        }
    }

    private void drawChart() {

        int t23 = ((Group) sensor.getParent()).getT23();
        java.util.List<Double> pressures = ((Group) sensor.getParent()).getPressures();
        Float[][] data = sensor.getTable();
        float[] adcCodes = new float[data.length];
        for (int i = 0; i < data.length; i++) {
            Float[] floats = data[i];

            Float aFloat = floats[t23];
            if (aFloat == null) {
                return;
            }
            adcCodes[i] = aFloat;
        }

        ChartPanel chartPanel = new ChartPanel(pressures, adcCodes);
        chartPanel.setLocation(330, 300);
        add(chartPanel);
    }


    private void contructTable() {
        String[] columnHeaders = new String[group.getTemperatures().size()];
        String[] rowHeaders = new String[group.getPressures().size() + 1];
        // LinkedList<String> rowHeaders = new LinkedList<String>();
        int a = 0;
        for (Double d : group.getTemperatures())
            columnHeaders[a++] = d.toString();
        a = 1;
        rowHeaders[0] = "Код T";
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);// после запятой максимум один разряд

        for (Double d : group.getPressures())
            rowHeaders[a++] = nf.format(d);//String.valueOf(d.floatValue());

        Float[][] tableData = new Float[sensor.getTable().length + 1][sensor
                .getTable()[0].length];
        tableData[0] = sensor.getTemperatures();

        for (int i = 0; i < sensor.getTable().length; i++) {
            tableData[i + 1] = sensor.getTable()[i];
        }
        DefaultTableModel tableModel = new DefaultTableModel(tableData,
                columnHeaders);
        table = new JTable(tableModel);

        JList rowHeader = new JList(rowHeaders);

        rowHeader.setFixedCellWidth(60);
        rowHeader.setFixedCellHeight(table.getRowHeight()
                + table.getRowMargin() - 1);
        rowHeader.setCellRenderer(new RowHeaderRenderer(table));
        tableScroll = new JScrollPane(table);
        tableScroll.setRowHeaderView(rowHeader);


        int height = 3 + (rowHeaders.length + 1)
                * (table.getRowHeight() + table.getRowMargin() - 1);

        height = (height > 230) ? 230 : height;

        tableScroll.setSize(400, height);
//        tableScroll.setSize(400, 250);

        tableScroll.setLocation(330, 50);
        add(tableScroll);
    }

    private void initializeStyles() {
        StyleContext context = StyleContext.getDefaultStyleContext();
        Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
        Style newStyle = textPane.addStyle("normal", style);
        StyleConstants.setFontFamily(newStyle, "SansSerif");
        newStyle = textPane.addStyle("red", style);
        StyleConstants.setForeground(newStyle, Color.RED);
        newStyle = textPane.addStyle("green", style);
        StyleConstants.setForeground(newStyle, new Color(0, 160, 0));

    }

}
