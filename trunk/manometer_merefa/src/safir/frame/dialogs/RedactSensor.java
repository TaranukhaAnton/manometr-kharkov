package safir.frame.dialogs;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;

import safir.bdUtils.DAO;
import safir.constants.CommonConstants;
import safir.data.Group;
import safir.data.Sensor;
import safir.data.SensorType;
import safir.data.SensorTypes;
import safir.data.Termocam;
import safir.frame.SafirFrame;
import safir.frame.components.RowHeaderRenderer;
import safir.rs232connect.SerialConnection;

public class RedactSensor extends JDialog {
	private SafirFrame parent;
	private JTree tree;
	private JPanel pan1, pan2;
	private JButton nextButton, cancelButton;
	private Choice choice1, choice2, choice3, choice4, choice5;
	private Choice choice6, choice7, choice8, choice9, choice10;
	private JLabel label1, label2, label3, label4, label5;
	private JLabel label6, label7, label8, label9, label10, label11, label12;
	private JTable table;
	private int lastchoice = 0;
	private Logger log;

	private JScrollPane scroll;
	private List<Double> pressures, temperatures;

	private int ind;

	Group newGroup;
	Group oldGroup;
	Sensor sensor;

	public RedactSensor(SafirFrame parent) {

		super(parent, true);
		log = Logger.getLogger(this.getClass());
		this.parent = parent;

		getContentPane().setLayout(null);
		setSize(1000, 600);
		setLocation(50, 50);
		setResizable(false);
		initComponents();

		sensor = (Sensor) parent.clickedPath.getLastPathComponent();
		oldGroup = (Group) parent.clickedPath.getParentPath()
				.getLastPathComponent();

		createTableSensor();
		choice1.select(oldGroup.getType());
		typeChange(SensorTypes.INSTANSE.sensorTypes.get(oldGroup.getType()));
		refreshTable();
	}

	private void createTableSensor() {
		Float[][] tableData = null;
		String[] columnHeaders = null;
		String[] rowHeaders = null;
		Float minP = oldGroup.getMinPressure();
		Float maxP = oldGroup.getMaxPressure();
		Integer minT = oldGroup.getMinTemp();
		Integer maxT = oldGroup.getMaxTemp();
		int pP = oldGroup.getPointsP();
		int pT = oldGroup.getPointsT();
		List<Double> tmp = Group.computeTemperatures(minT, maxT, pT);
		columnHeaders = new String[tmp.size()];
		for (int i = 0; i < tmp.size(); i++) {
			columnHeaders[i] = tmp.get(i).toString();
		}
		tmp = Group.computePressures(minP, maxP, pP);
		rowHeaders = new String[tmp.size() + 1];
		int a = 1;
		rowHeaders[0] = "Код T";
		for (Double d : tmp)
			rowHeaders[a++] = d.toString();
		Float[] temps = sensor.getTemperatures();
		Float[][] tmpTable = sensor.getTable();

		tableData = new Float[tmpTable.length + 1][tmpTable[0].length];

		tableData[0] = temps;

		for (int i = 0; i < tmpTable.length; i++) {
			tableData[i + 1] = tmpTable[i];
		}
		DefaultTableModel tableModel = new DefaultTableModel(tableData,
				columnHeaders);
		JTable tableOld = new JTable(tableModel);
		// table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// table.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tableOld.setRowSelectionAllowed(false);

		// table.setCellSelectionEnabled(false);
		JList rowHeader = new JList(rowHeaders);
		rowHeader.setFixedCellWidth(60);
		rowHeader.setFixedCellHeight(tableOld.getRowHeight()
				+ tableOld.getRowMargin() - 1);
		rowHeader.setCellRenderer(new RowHeaderRenderer(tableOld));
		tableOld.setDragEnabled(true);
		JScrollPane tableScroll = new JScrollPane(tableOld);
		tableScroll.setRowHeaderView(rowHeader);
		tableScroll.setSize(380, 130);
		tableScroll.setLocation(600, 10);
		add(tableScroll);
	}

	private void initComponents() {

		init1pan();
		pan1.setLocation(10, 10);
		add(pan1);

		init2pan();
		pan2.setLocation(10, 150);
		add(pan2);
		tree = parent.getTree();
		setTitle("Добавить группу");

		nextButton = new JButton("Создать");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				button1ActionPerformed(evt);
			}
		});
		nextButton.setSize(150, 30);
		nextButton.setIcon(CommonConstants.okIcon);
		nextButton.setLocation(50, 400);
		add(nextButton);

		cancelButton = new JButton("Отмена");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
				dispose();
			}
		});
		cancelButton.setSize(150, 30);
		cancelButton.setIcon(CommonConstants.canselIcon);
		cancelButton.setLocation(50, 450);
		add(cancelButton);

		choice10 = new Choice();
		choice9 = new Choice();
		choice10.setSize(80, 20);
		choice10.setLocation(100, 300);
		choice9.setSize(80, 20);
		choice9.setLocation(100, 330);
		add(choice9);
		add(choice10);
		for (int i = 0; i < 8; i++)
			choice9.add(String.valueOf(i));
		label11 = new JLabel("КУ");
		label12 = new JLabel("Баз. строка");
		label11.setSize(100, 20);
		label12.setSize(100, 20);
		label11.setLocation(10, 330);
		label12.setLocation(10, 300);
		add(label11);
		add(label12);

		createTable();

	}

	private void init2pan() {
		// панель на которой находятся температурные настройки
		pan2 = new JPanel();
		pan2.setLayout(null);
		pan2.setSize(220, 110);
		pan2.setBackground(new Color(210, 210, 210));
		int h = 3;
		label7 = new JLabel("Темп. исп.");
		label7.setBounds(5, h, 5 + 100, h + 15);
		pan2.add(label7);
		choice4 = new Choice();// темп. исполнение
		choice7 = new Choice();// мин. темп
		choice8 = new Choice();// макс темп
		choice6 = new Choice();// колво точек по Т

		choice4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ae) {
				switch (choice4.getSelectedIndex()) {
				case 0:
					choice7.setEnabled(true);
					choice8.setEnabled(false);
					choice8.removeAll();
					choice8.add("50");
					choice7.removeAll();
					choice7.add("-30");
					choice7.add("-40");
					break;
				case 1:
					choice7.setEnabled(false);
					choice8.setEnabled(true);
					choice8.removeAll();
					choice8.add("50");
					choice8.add("80");
					choice7.removeAll();
					choice7.add("5");
					break;
				case 2:
					choice7.setEnabled(false);
					choice8.setEnabled(true);
					choice8.removeAll();
					choice8.add("50");
					choice8.add("80");
					choice7.removeAll();
					choice7.add("-5");
					break;
				default:
					break;
				}
				refreshTable();

			}
		});
		choice4.add("У2");
		choice4.add("УХЛ 3.1");
		choice4.add("Т3");
		choice4.setSize(80, 20);
		choice4.setLocation(110, h);
		pan2.add(choice4);
		h += 25;
		label6 = new JLabel("min температура");
		label6.setSize(110, 20);
		label6.setLocation(5, h);
		pan2.add(label6);

		choice7.add("-30");
		choice7.add("-40");
		choice7.setSize(80, 20);
		choice7.setLocation(110, h);
		choice7.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ae) {
				refreshTable();
			}
		});
		pan2.add(choice7);
		h += 25;
		label5 = new JLabel("max температура");
		label5.setSize(110, 20);
		label5.setLocation(5, h);
		pan2.add(label5);

		choice8.add("50");
		choice8.setEnabled(false);
		choice8.setSize(80, 20);
		choice8.setLocation(110, h);
		choice8.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ae) {
				refreshTable();
			}
		});
		pan2.add(choice8);
		h += 25;

		label8 = new JLabel("Кол-во точек");
		label8.setSize(100, 20);
		label8.setLocation(5, h);
		pan2.add(label8);

		// for (int i = 3; i < 10; i++)
		choice6.add("3");
		choice6.add("4");
		choice6.setSize(80, 20);
		choice6.setLocation(110, h);
		choice6.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ae) {
				refreshTable();
			}
		});

		pan2.add(choice6);
	}

	private void refreshBaseRowChoise() {
		choice10.removeAll();
		int count = Integer.parseInt(choice3.getSelectedItem());
		for (int i = 0; i < count; i++)
			choice10.add(String.valueOf(i));

	}

	private void init1pan() {
		// Панель на которой находятся настройки по давлению
		pan1 = new JPanel();
		choice1 = new Choice(); // типы датчиков
		choice2 = new Choice(); // максимальное давление
		choice5 = new Choice(); // минимальное давление
		choice3 = new Choice(); // количество точек

		label4 = new JLabel("Кол-во точек");
		label1 = new JLabel("Тип датчиков");
		label2 = new JLabel("max абc. P");
		label9 = new JLabel("кПа");
		label3 = new JLabel("min абc. P");
		label10 = new JLabel("кПа");

		// две строки выбора типа датчиков
		pan1.setLayout(null);
		pan1.setSize(220, 130);
		pan1.setBackground(new Color(210, 210, 210));
		int h = 3;
		label1.setBounds(5, h, 5 + 100, h + 15);
		pan1.add(label1);
		h += 20;
		choice1.setBounds(5, h, 180, h + 20);
		pan1.add(choice1);

		// строка выбора максимального давления
		h += 25;
		label2.setSize(100, 20);
		label2.setLocation(5, h);
		pan1.add(label2);
		choice2.setSize(60, 20);
		choice2.setLocation(105, h);
		pan1.add(choice2);
		label9.setSize(50, 20);
		label9.setLocation(170, h);
		pan1.add(label9);

		// строка выбора минимального давления
		h += 25;
		label3.setSize(100, 20);
		label3.setLocation(5, h);
		pan1.add(label3);
		choice5.setSize(60, 20);
		choice5.setLocation(105, h);
		pan1.add(choice5);
		label10.setSize(50, 20);
		label10.setLocation(170, h);
		pan1.add(label10);

		// строка выбора количества точек
		h += 25;
		label4.setSize(100, 20);
		label4.setLocation(5, h);
		pan1.add(label4);
		choice3.setSize(60, 20);
		choice3.setLocation(105, h);
		pan1.add(choice3);

		for (SensorType senType : SensorTypes.INSTANSE.sensorTypes)
			choice1.add(senType.name);

		typeChange(SensorTypes.INSTANSE.sensorTypes.getFirst());

		choice1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ae) {

				typeChange(SensorTypes.INSTANSE.sensorTypes.get(choice1
						.getSelectedIndex()));

				refreshTable();

			}
		});
		choice3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ae) {
				refreshTable();
				// refreshBaseRowChoise();

			}
		});
		choice5.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ae) {
				SensorType st = SensorTypes.INSTANSE.sensorTypes.get(choice1
						.getSelectedIndex());
				choice3.removeAll();
				StringBuffer sb = new StringBuffer(choice5.getSelectedItem());
				sb.deleteCharAt(0);
				for (String points : st.limits.get(sb.toString()))

					choice3.add(points);

				choice3.setEnabled(true);
				refreshTable();
				// refreshBaseRowChoise();

			}
		});

		choice2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ae) {
				SensorType st = SensorTypes.INSTANSE.sensorTypes.get(choice1
						.getSelectedIndex());
				StringBuffer sb = new StringBuffer(choice2.getSelectedItem());
				if (lastchoice == 3) {
					if ((Float.parseFloat(choice2.getSelectedItem()) > 100)
							&& (st.measure == 1)) {
						choice5.removeAll();
						choice5.add("-100");
					} else {
						choice5.removeAll();
						choice5.add("-" + choice2.getSelectedItem());
					}

				}

				int ind = sb.indexOf("-");
				if (ind != -1)
					sb.deleteCharAt(ind);

				choice3.removeAll();

				for (String points : st.limits.get(sb.toString()))
					choice3.add(points);
				choice3.setEnabled(true);
				// refreshBaseRowChoise();
				refreshTable();
			}
		});

	}

	private void button1ActionPerformed(ActionEvent evt) {
		newGroup = new Group();
		Termocam termocam = (Termocam) parent.clickedPath.getParentPath()
				.getParentPath().getLastPathComponent();

		int t = ((DefaultTableModel) table.getModel()).getColumnCount();
		int p = ((DefaultTableModel) table.getModel()).getRowCount();
		Float[] temps = new Float[t];
		Float[][] data = new Float[p - 1][t];

		for (int i = 0; i < t; i++) {

			try {
				temps[i] = Float.valueOf((String) table.getValueAt(0, i));
			} catch (Exception e) {
				temps[i] = 0f;
			}

			for (int j = 1; j < p; j++) {
				try {
					data[j - 1][i] = Float.valueOf((String) table.getValueAt(j,
							i));
				} catch (Exception e) {
					data[j - 1][i] = 0f;
				}
			}
		}
		;

		sensor.setTable(data);
		sensor.setTemperatures(temps);

		newGroup.setCamera(termocam.getId());
		newGroup.setMaxPressure(Float.parseFloat(choice2.getSelectedItem()));
		newGroup.setMaxTemp(Integer.parseInt(choice8.getSelectedItem()));
		newGroup.setMinPressure(Float.parseFloat(choice5.getSelectedItem()));
		newGroup.setMinTemp(Integer.parseInt(choice7.getSelectedItem()));
		newGroup.setPointsP(Integer.parseInt(choice3.getSelectedItem()));
		newGroup.setPointsT(Integer.parseInt(choice6.getSelectedItem()));
		newGroup.setType(choice1.getSelectedIndex());
		newGroup.setTermType(choice4.getSelectedIndex());

		// refreshTable();

		// SensorType st = SensorTypes.INSTANSE.sensorTypes.get(choice1
		// .getSelectedIndex());

		// if (st.type == 2) {
		// int baseRow =
		// pressures.indexOf(Double.parseDouble(choice2.getSelectedItem()));
		newGroup.setBaseRow(Integer.parseInt(choice10.getSelectedItem()));
		sensor.setKoefAmplificftion(Integer
				.parseInt(choice9.getSelectedItem()));

		try {
			
			System.out.println("ka"+sensor.getKoefAmplificftion());
			SerialConnection.INSTANSE.writeKa(sensor.getAddress(), sensor
					.getKoefAmplificftion());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// } else {
		// int baseRow = pressures.indexOf(Double.parseDouble(choice2
		// .getSelectedItem()));
		// newGroup.setBaseRow(baseRow);
		// }

		Integer[] mas =new  Integer[Integer.parseInt(choice6.getSelectedItem())];
		
		for (int j = 0; j < mas.length; j++) {
			mas[j]=1;
			
		}
		
		sensor.setTempFlags(mas);
		
		try {
			DAO.INSTANSE.insertGroup(newGroup);
			// ((DefaultTreeModel) tree.getModel()).insertNodeInto(newGroup,
			// termocam, termocam.getChildCount());

			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			model.insertNodeInto(newGroup, termocam, termocam.getChildCount());

			model.insertNodeInto(sensor, newGroup, 0);
			// System.out.println("old "+sensor.getGroup());

			sensor.setGroup(newGroup.getGroupNum());
			// System.out.println("new "+sensor.getGroup());

			DAO.INSTANSE.updateSensor(sensor);
			model.nodeStructureChanged(termocam);
			// oldGroup.remove(sensor);
			model.nodeStructureChanged(newGroup);
			// model.nodeStructureChanged(oldGroup);
			// model.reload(oldGroup);
			model.reload(newGroup);
			log.info("Добавили группу: " + newGroup);

		} catch (Exception e) {
			log.error("При попытке добавить новую группу произошла ошибка ");
		}

		setVisible(false);
		dispose();
	}

	private void createTable() {

		table = new JTable(
				new DefaultTableModel(new Float[1][1], new String[1]));

		scroll = new JScrollPane(table);
		scroll.setLocation(250, 10);
		// scroll.setSize(300, 200);
		add(scroll);
		refreshTable();
	}

	private void typeChange(SensorType st) {
		switch (st.measure) {
		case 0:
			label9.setText("Па");
			label10.setText("Па");
			break;
		case 1:
			label10.setText("кПа");
			label9.setText("кПа");
			break;
		case 2:
			label10.setText("МПа");
			label9.setText("МПа");
			break;
		}

		switch (st.type) {
		case 0:
			lastchoice = 0;
			choice2.removeAll();
			for (String lim : st.limits.keySet())
				choice2.add(lim);
			label2.setText("max абc. P");
			label3.setText("min абc. P");
			choice5.removeAll();
			choice5.add("0");
			choice5.setEnabled(false);
			choice2.setEnabled(true);
			break;
		case 1:
			lastchoice = 1;
			choice2.removeAll();
			for (String lim : st.limits.keySet())
				choice2.add(lim);
			label2.setText("max отн. P");
			label3.setText("min отн. P");
			choice5.removeAll();
			choice5.add("0");
			choice5.setEnabled(false);
			choice2.setEnabled(true);
			break;
		case 2:
			lastchoice = 2;
			choice2.removeAll();
			choice5.removeAll();
			for (String lim : st.limits.keySet())
				choice2.add("-" + lim);
			label2.setText("max отн. P");
			label3.setText("min отн. P");
			// choice2.removeAll();
			choice5.add("0");
			choice2.setEnabled(true);
			choice5.setEnabled(false);
			break;
		case 3:
			lastchoice = 3;
			choice2.removeAll();
			choice5.removeAll();

			for (String lim : st.limits.keySet()) {
				choice2.add(lim);
				if ((Float.parseFloat(lim) > 100) && (st.measure == 1))
					choice5.add("-100");
				else
					choice5.add("-" + lim);
			}

			label2.setText("max отн. P");
			label3.setText("min отн. P");
			choice5.setEnabled(false);
			choice2.setEnabled(true);
			break;
		}

		choice3.removeAll();
		LinkedList<String> list = st.limits.firstEntry().getValue();
		for (String str : list)
			choice3.add(str);

	}

	private void refreshTable() {
		refreshBaseRowChoise();
		SensorType st = SensorTypes.INSTANSE.sensorTypes.get(choice1
				.getSelectedIndex());

		int pointT = Integer.parseInt(choice6.getSelectedItem());
		int pointP = Integer.parseInt(choice3.getSelectedItem());
		pressures = new LinkedList<Double>();
		temperatures = new LinkedList<Double>();
		double Pmin;
		double Pmax;
		switch (st.type) {
		case 2:
			StringBuffer sb = new StringBuffer(choice2.getSelectedItem());
			sb.deleteCharAt(0);
			Pmax = -Double.parseDouble(st.map.get(sb.toString()));
			Pmin = 0;
			break;
		case 3:
			Pmax = Double.parseDouble(st.map.get(choice2.getSelectedItem()));

			Pmin = ((Pmax > 100) && (st.measure == 1)) ? -100 : -Pmax;

			break;

		default:
			Pmax = Double.parseDouble(st.map.get(choice2.getSelectedItem()));
			Pmin = 0;
			break;
		}

		double stepP = (Pmax - Pmin) / (pointP - 1);
		int Tmax = Integer.parseInt(choice8.getSelectedItem());
		int Tmin = Integer.parseInt(choice7.getSelectedItem());
		int stepT = (Tmax - Tmin) / (pointT - 1);

		LinkedList<String> upperHeaders = new LinkedList<String>();
		LinkedList<String> rowHeaderNames = new LinkedList<String>();

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(1);// после запятой максимум один разряд
		rowHeaderNames.add("Код T");
		for (int i = 0; i < (pointP - 1); i++) {
			pressures.add(Pmin + stepP * i);
			rowHeaderNames.add(nf.format(Pmin + stepP * i));

		}

		/**/rowHeaderNames.add(nf.format(Pmax));
		pressures.add(Pmax);

		for (int i = 0; i < (pointT - 1); i++) {
			upperHeaders.add(Integer.toString(Tmin + stepT * i));
			temperatures.add(Double.valueOf(Tmin + stepT * i));

		}
		/**/upperHeaders.add(Integer.toString(Tmax));
		temperatures.add(Double.valueOf(Tmax));

		Double delta = Double.valueOf(java.lang.Math.abs(Tmin - 23));
		Double val = Double.valueOf(Tmin);
		for (Double d : temperatures)
			if (delta > java.lang.Math.abs(d - 23)) {
				delta = java.lang.Math.abs(d - 23);
				val = d;
			}

		ind = temperatures.indexOf(val);

		temperatures.set(ind, 23.);
		upperHeaders.set(ind, "23");

		remove(scroll);

		repaint();

		// upperHeaders.toArray()

		Float[][] mas = new Float[pointP + 1][pointT];

		table = new JTable(new DefaultTableModel(mas, upperHeaders.toArray()));
		table.setRowSelectionAllowed(false);
		scroll = new JScrollPane(table);
		scroll.setLocation(250, 10);
		// ((MyTableModel1) table.getModel()).setRowCount(pointP + 1);

		JList rowHeader = new JList(new MyListModel3(rowHeaderNames));
		rowHeader.setFixedCellWidth(60);
		rowHeader.setFixedCellHeight(table.getRowHeight()
				+ table.getRowMargin() - 1);
		rowHeader.setCellRenderer(new RowHeaderRenderer1(table));
		scroll.setRowHeaderView(rowHeader);
		scroll.setSize(335, 3 + (rowHeaderNames.size() + 1)
				* (table.getRowHeight() + table.getRowMargin() - 1));
		add(scroll);
		// TableColumnModel cm = table.getColumnModel();
		/*
		 * int count = upperHeaders.size() - cm.getColumnCount(); if (count > 0) {
		 * for (int i = 0; i < count; i++) { cm.addColumn(new TableColumn()); } }
		 * else { for (int i = 0; i < -count; i++) {
		 * cm.removeColumn(cm.getColumn(0)); } } Enumeration<TableColumn>
		 * columns = cm.getColumns(); for (String col : upperHeaders) {
		 * columns.nextElement().setHeaderValue(col); } TableColumn col2 = new
		 * TableColumn(); cm.addColumn(col2); cm.removeColumn(col2);
		 */

	}
}

/*
 * @SuppressWarnings("serial") class MyTableModel1 extends AbstractTableModel {
 * public int x1; public int y1; Float[][] data;
 * 
 * public MyTableModel1(int x, int y) { this.x1 = x; this.y1 = y; data = new
 * Float[x][y]; System.out.println("1. x="+x+" y="+y);
 *  }
 * 
 * public int getColumnCount() { return x1; }
 * 
 * public int getRowCount() { return y1; }
 * 
 * public void setRowCount(int rowCount) { y1 = rowCount; data = new
 * Float[rowCount][rowCount]; //System.out.println(); System.out.println("3.
 * x="+x1+" y="+y1);
 *  }
 * 
 * public Object getValueAt(int x, int y) { System.out.println("2. x="+x+"
 * y="+y); return data[x][y];
 * 
 *  } @Override public void setValueAt(Object arg0, int x, int y) {
 * 
 * System.out.println("4. x="+x+" y="+y); data[x][y] = Float.valueOf((String)
 * arg0);
 * 
 * //}
 *  }
 * 
 * public boolean isCellEditable(int r, int c) { return true; }
 *  }
 */

@SuppressWarnings("serial")
class RowHeaderRenderer1 extends JLabel implements ListCellRenderer {

	RowHeaderRenderer1(JTable table) {
		JTableHeader header = table.getTableHeader();
		setOpaque(true);
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setHorizontalAlignment(CENTER);
		setForeground(header.getForeground());
		setBackground(header.getBackground());
		setFont(header.getFont());
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		setText((value == null) ? "" : value.toString());
		return this;
	}
}

@SuppressWarnings("serial")
class MyListModel3 extends AbstractListModel {
	LinkedList<String> headers;

	public int getSize() {
		return headers.size();
	}

	public Object getElementAt(int index) {
		return headers.get(index);
	}

	/**
	 * @param headers
	 * 
	 */
	public MyListModel3(LinkedList<String> headers) {
		this.headers = headers;
	}

	public MyListModel3() {

	}

}
