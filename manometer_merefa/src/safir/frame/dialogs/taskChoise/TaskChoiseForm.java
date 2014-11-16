package safir.frame.dialogs.taskChoise;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;

import org.apache.log4j.Logger;

import safir.bdUtils.DAO;
import safir.constants.CommonConstants;
import safir.data.Group;
import safir.data.Sensor;
import safir.frame.SafirFrame;

@SuppressWarnings("serial")
public class TaskChoiseForm extends JDialog {
	private Group group;
	private JTable table;

	private JScrollPane scroll;
	private JButton nextButton, cancelButton;
	// private SafirFrame parent;
	//private List<Sensor> sensors;
	private MyTableModel tableModel;
	Logger log;

	/**
	 * 
	 */
	public TaskChoiseForm(final SafirFrame parent , final List<Sensor> sensors,
			final Group group) {
		super(parent, true);
		this.group = group;
	//	this.sensors =sensors;
		
		log = Logger.getLogger(this.getClass());
//		if (parent.clickedPath.getLastPathComponent() instanceof Group ){
//		group = (Group) parent.clickedPath.getLastPathComponent();
//		}
//		if (parent.clickedPath.getLastPathComponent() instanceof Sensor ){
//			group = (Group)((Sensor)parent.clickedPath.getLastPathComponent()).getParent();
//		};
//		
		
		
		
		setSize(500, 600);
		setLocation(50, 50);
		setResizable(false);
		getContentPane().setLayout(null);
		ContructTable();

		nextButton = new JButton("Далее");
		nextButton.setSize(150, 30);
		nextButton.setLocation(330, 20);
		nextButton.setIcon(CommonConstants.okIcon);
		nextButton.setEnabled(false);
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				List<Double> listPressures = new ArrayList<Double>();
				boolean find = false;
				boolean notBaseRowSelected = false;
				Double temperature = null;
				int newBaseRow=0;
				for (int c = 0; c < tableModel.getColumnCount(); c++) {
					for (int r = 0; r < tableModel.getRowCount(); r++) {
						if ((Boolean) tableModel.getValueAt(r, c)) {
							find = true;
							listPressures.add(group.getPressures().get(r));
							temperature = group.getTemperatures().get(c);
							
							if((r != group.getBaseRow())
									& (r != group.getZeroPressure())&(c!=group.getT23()))
									{
								notBaseRowSelected = true;
								newBaseRow = r;
									}

						}
					}
					if (find)
						break;
				}
				

				// if (find) {

				if (notBaseRowSelected) {
					int res = JOptionPane
							.showConfirmDialog(
									null,
									"Хотите изменить базовую строку? \n Данные, снятые ранее, будут потеняны!",
									"Выбрана точка не в базовой строке",
									JOptionPane.OK_CANCEL_OPTION,
									JOptionPane.WARNING_MESSAGE);
		
				

					if (res == JOptionPane.OK_OPTION) {
						{
						setVisible(false);
						dispose();
						
							log.info("Изменили базовую строку. Удалили данные, снятые ранные");
							group.setBaseRow(newBaseRow);
							try {
								DAO.INSTANSE.updateGroup(group);
							} catch (SQLException e) {
								JOptionPane
								.showConfirmDialog(
										null,
										"Нет связи с базой данных. \n Обратитесь к сисадмину.",
										"База данных недоступна.",
										JOptionPane.DEFAULT_OPTION,
										JOptionPane.WARNING_MESSAGE);
							}
							
							
							
						(new SavePoinsDialog(parent, temperature, listPressures,true,sensors,group))
								.setVisible(true);}
					}

				} else {

					setVisible(false);
					dispose();

					(new SavePoinsDialog(parent, temperature, listPressures,false,sensors,group))
							.setVisible(true);
				}

				// } else {
				// new AlertDialog(parent, "Ни одна точка не выбрана.",
				// "Ни одна точка не выбрана.",
				// "Укажите точки для съема", "");
				// }
			}
		});
		add(nextButton);

		cancelButton = new JButton("Отмена");
		cancelButton.setSize(150, 30);
		cancelButton.setLocation(330, 60);
		// cancelButton.s
		cancelButton.setIcon(CommonConstants.canselIcon);

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
				dispose();
			}
		});
		add(cancelButton);

	}

	private void ContructTable() {
		int x = group.getPointsP();
		int y = group.getPointsT();
		Boolean[][] data = new Boolean[x][y];
		String[] columnHeaders = new String[group.getTemperatures().size()];
		String[] rowHeaders = new String[group.getPressures().size()];
		// LinkedList<String> rowHeaders = new LinkedList<String>();
		int a = 0;
		for (Double d : group.getTemperatures())
			columnHeaders[a++] = d.toString();
		a = 0;
		for (Double d : group.getPressures())
			rowHeaders[a++] = d.toString();

		for (int i = 0; i < y; i++) {
			// headers[i] =group.getTemperatures().toArray();
			for (int j = 0; j < x; j++)
				data[j][i] = false;
		}

		tableModel = new MyTableModel(data, columnHeaders, group
				.getZeroPressure(), group.getBaseRow(), group.getT23());
		table = new JTable(tableModel);

		tableModel.addTableModelListener(new TableModelListener() {
		
			public void tableChanged(TableModelEvent arg0) {
				nextButton.setEnabled(true);
			}
		});

		int height = x < 7 ? 550 / x - 5 : 550 / x;
		table.setRowHeight(height);
		JList rowHeader = new JList(rowHeaders);
		rowHeader.setFixedCellWidth(60);
		rowHeader.setFixedCellHeight(table.getRowHeight()
				+ table.getRowMargin() - 1);
		rowHeader.setCellRenderer(new RowHeaderRenderer(table));
		table.getTableHeader().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				int colNum = table.columnAtPoint(event.getPoint());

				if (colNum == group.getT23()) {
					for (int j = 0; j < tableModel.getRowCount(); j++)
						tableModel.setValueAt(true, j, colNum);
				} else {
					if (group.getBaseRow() != -1)

						tableModel.setValueAt(true, group.getZeroPressure(),
								colNum);
					tableModel.setValueAt(true, group.getBaseRow(), colNum);
				}

			}
		});

		scroll = new JScrollPane(table);
		scroll.setRowHeaderView(rowHeader);
		scroll.setSize(300, table.getRowHeight() * x + 20);
		scroll.setLocation(10, 10);
		add(scroll);
	}

}

@SuppressWarnings("serial")
class RowHeaderRenderer extends JLabel implements ListCellRenderer {

	RowHeaderRenderer(JTable table) {
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
class MyTableModel extends AbstractTableModel {
	// public int x1;
	// public int y1;
	private Object[][] cells;
	private String[] columnNames;

	private int p0, pBase, t23;

	/**
	 * @param cells
	 * @param columnNames
	 */
	public MyTableModel(Object[][] cells, String[] columnNames, int p0,
			int pBase, int t23) {
		this.cells = cells;
		this.columnNames = columnNames;
		this.p0 = p0;
		this.pBase = pBase;
		this.t23 = t23;
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
		if ((Boolean) obj) {
			for (int rowN = 0; rowN < cells.length; rowN++)
				for (int colN = 0; colN < cells[0].length; colN++)
					cells[rowN][colN] = false;

			if (c == t23) {
				for (int rowN = 0; rowN < cells.length; rowN++)
					cells[rowN][c] = true;
			} else {
				if (r == p0) {
					cells[r][c] = true;
					cells[pBase][c] = true;
				} else {
					cells[r][c] = true;
					cells[p0][c] = true;

				}
			}

			// cells[r][c] = true;

		} else {
			cells[r][c] = false;

			cells[pBase][c] = true;
		}
		// cells[r][c] = obj;

		fireTableDataChanged();
	}

	public Object getValueAt(int r, int c) {
		return cells[r][c];
	}

	public boolean isCellEditable(int r, int c) {

		if (c != t23)
			if (r != p0)
				return true;
			else
				return !(Boolean) cells[r][c];
		else
			return !(Boolean) cells[r][c];
	}

}
