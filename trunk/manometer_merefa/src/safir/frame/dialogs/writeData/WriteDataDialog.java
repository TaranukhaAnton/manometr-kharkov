package safir.frame.dialogs.writeData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultTreeModel;

import safir.bdUtils.DAO;
import safir.constants.CommonConstants;
import safir.data.Group;
import safir.data.Sensor;
import safir.frame.SafirFrame;
import safir.frame.components.RowHeaderRenderer;
import safir.frame.dialogs.ZeroizeSensors;

@SuppressWarnings("serial")
public class WriteDataDialog extends JDialog {

	// public SafirFrame parent;
	private JButton cancelButton;
	private JButton nextButton;
	private List<Sensor> sensors;
	private Group group;
	private SafirFrame parent;
	private JTable table;
	private JScrollPane tableScroll;
	private JRadioButton[] items;
	private ButtonGroup buttonGroup;
	private JProgressBar progressBar;
	private JLabel lab1, lab2, lab3;

	/**
	 * @param p
	 */
	public WriteDataDialog(final SafirFrame parent, final List<Sensor> sensors,
			final Group group) {
		super(parent, true);
		this.parent = parent;
		this.group = group;
		this.sensors = sensors;
		setTitle("Запись данных в датчики");
		lab1 = new JLabel("Количество пределов");
		lab2 = new JLabel("Предел на который");
		lab3 = new JLabel("будут настроенны все датчики");
		lab1.setSize(200, 20);
		lab2.setSize(200, 20);
		lab3.setSize(200, 20);
		lab1.setLocation(130, 20);
		lab2.setLocation(450, 10);
		lab3.setLocation(450, 30);
		add(lab1);
		add(lab2);
		add(lab3);
		// if (parent.clickedPath.getLastPathComponent() instanceof Group) {
		// group = (Group) parent.clickedPath.getLastPathComponent();
		// // maxPressure = group.getMaxPressure();
		// sensors = group.getListSensors();
		// }
		// if (parent.clickedPath.getLastPathComponent() instanceof Sensor) {
		//
		// Sensor sensor = (Sensor) parent.clickedPath.getLastPathComponent();
		// group = (Group) sensor.getParent();
		// // maxPressure = group.getMaxPressure();
		// sensors = new ArrayList<Sensor>();
		// sensors.add(sensor);
		// }

		setSize(800, 600);
		setLocation(50, 50);
		setResizable(false);
		getContentPane().setLayout(null);
		cancelButton = new JButton("Отмена");
		cancelButton.setIcon(CommonConstants.canselIcon);
		cancelButton.setSize(150, 30);
		cancelButton.setLocation(80, 510);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
				dispose();
			}
		});
		add(cancelButton);

		progressBar = new JProgressBar();
		progressBar.setSize(750, 30);
		progressBar.setLocation(20, 320);
		progressBar.setVisible(false);
		progressBar.setStringPainted(true);
		add(progressBar);

		nextButton = new JButton("Далее");
		nextButton.setIcon(CommonConstants.okIcon);
		nextButton.setSize(150, 30);
		nextButton.setLocation(250, 510);
		// final WriteDataFirst instanse = this;
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				nextButton.setEnabled(false);
				cancelButton.setEnabled(false);
				table.setEnabled(false);
				progressBar.setVisible(true);
				lab1.setEnabled(false);
				lab2.setEnabled(false);
				lab3.setEnabled(false);
				for (JRadioButton radioButton : items)
					radioButton.setEnabled(false);
				progressBar.setIndeterminate(true);
				Thread t = new Thread() {
					public void run() {
						writeData();
					}
				};
				t.start();

			}
		});
		add(nextButton);

		items = new JRadioButton[] { new JRadioButton("1"),
				new JRadioButton("2"), new JRadioButton("3"),
				new JRadioButton("4"), new JRadioButton("5"),
				new JRadioButton("6"), new JRadioButton("7"),
				new JRadioButton("8", true) };
		buttonGroup = new ButtonGroup();

		for (int j = 0; j < 8; j++) {

			buttonGroup.add(items[j]);
			items[j].setSize(200, 20);
			items[j].setLocation(450, 50 + 20 * j);
			add(items[j]);
		}

		contructTable();
		table.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent arg0) {

				for (int j = 0; j < 8; j++) {
					items[j].setEnabled(true);
					for (int i = 0; i < sensors.size(); i++) {
						items[j].setEnabled(items[j].isEnabled()
								& (Boolean) table.getValueAt(i, j));
					}

					items[j].setSelected(items[j].isEnabled());
				}

			}
		});
	}

	private void writeData() {

		String[] result = new String[sensors.size()];
		JTree tree = parent.getTree();
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		

		for (int i = 0; i < sensors.size(); i++) {
			int limitCount = 0;
			progressBar.setString("Запись данных в датчик "
					+ sensors.get(i).getSerNum());

			for (; limitCount < 8; limitCount++)
				if (!(Boolean) table.getValueAt(i, limitCount))
					break;
			
			int limit =0;
			for (; limit<items.length;limit++)
				if (items[limit].isSelected()) break;
			System.out.println("limit "+limit);
				
				
			if (sensors.get(i).writeData(limit, limitCount)& sensors.get(i).writeData(limit, limitCount)) { 
				
				sensors.get(i).setDataIsWrote(1);
				sensors.get(i).setDiapasonCount(limitCount);
				sensors.get(i).setK0diapIsWrote(0);
				sensors.get(i).setSpan(0.f);
				result[i] = "Данные в датчик " + sensors.get(i)
						+ "  записаны успешно";
			} else {
				sensors.get(i).setDataIsWrote(2);
				result[i] = "Ошибки при записи данных в датчик "
						+ sensors.get(i);
			}
			try {
				DAO.INSTANSE.updateSensor(sensors.get(i));
			} catch (SQLException e) {
				JOptionPane
				.showConfirmDialog(
						null,
						"Нет связи с базой данных. \n Обратитесь к сисадмину.",
						"База данных недоступна.",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE);
			}
			model.nodeChanged(sensors.get(i));
		}
		model.nodeChanged(group);
		model.reload(group);
		progressBar.setIndeterminate(false);
		JOptionPane.showMessageDialog(null, result);
		int res = JOptionPane.showConfirmDialog(null,
				"Хотите обнулить датчики?", "", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (res == JOptionPane.OK_OPTION) {
			(new ZeroizeSensors(parent, sensors)).setVisible(true);
			setVisible(false);
			dispose();
		} else {
			setVisible(false);
			dispose();
		}

	}

	private void contructTable() {
		String[] rowHeaders = new String[sensors.size()];// /{"1","2","3","4","5","6","7","8"};
		Boolean[][] tableData = new Boolean[sensors.size()][8];
		for (int j = 0; j < sensors.size(); j++) {
			rowHeaders[j] = Long.toString(sensors.get(j).getSerNum());
			for (int i = 0; i < 8; i++)
				tableData[j][i] = true;
		}

		MyTableModel tableModel = new MyTableModel(tableData, new String[] {
				"1", "2", "3", "4", "5", "6", "7", "8" });
		table = new JTable(tableModel);

		JList rowHeader = new JList(rowHeaders);

		rowHeader.setFixedCellWidth(60);
		rowHeader.setFixedCellHeight(table.getRowHeight()
				+ table.getRowMargin() - 1);
		rowHeader.setCellRenderer(new RowHeaderRenderer(table));
		tableScroll = new JScrollPane(table);
		tableScroll.setRowHeaderView(rowHeader);
		tableScroll.setSize(400, 3 + (rowHeaders.length + 1)
				* (table.getRowHeight() + table.getRowMargin() - 1));
		tableScroll.setLocation(30, 50);
		add(tableScroll);
	}

}

@SuppressWarnings("serial")
class MyTableModel extends AbstractTableModel {
	// public int x1;
	// public int y1;
	private Object[][] cells;
	private String[] columnNames;

	/**
	 * @param cells
	 * @param columnNames
	 */
	public MyTableModel(Object[][] cells, String[] columnNames) {
		this.cells = cells;
		this.columnNames = columnNames;

	}

	public String getColumnName(int c) {
		return columnNames[c];
	}

	@SuppressWarnings("unchecked")
	public Class getColumnClass(int c) {
		return cells.clone()[0][c].getClass();
	}

	public int getColumnCount() {
		return cells[0].length;
	};

	public int getRowCount() {
		return cells.length;
	};

	public void setValueAt(Object obj, int r, int c) {
		for (int i = 0; i < 8; i++)
			if ((Boolean) obj)
				cells[r][i] = i < (c + 1);
			else
				cells[r][i] = i < c;
		fireTableDataChanged();
	}

	public Object getValueAt(int r, int c) {
		return cells[r][c];
	}

	public boolean isCellEditable(int r, int c) {
		return c != 0;
	}

}
