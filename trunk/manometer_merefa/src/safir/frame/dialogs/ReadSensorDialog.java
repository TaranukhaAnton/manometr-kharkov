package safir.frame.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import safir.constants.CommonConstants;
import safir.constants.SensorConst;
import safir.data.Group;
import safir.data.Sensor;
import safir.data.SensorTypes;
import safir.exceptions.NoDataException;
import safir.frame.SafirFrame;
import safir.frame.components.RowHeaderRenderer;
import safir.rs232connect.SerialConnection;
import safir.utils.SafUtil;

public class ReadSensorDialog extends JDialog {

	private Group group;
	private Sensor sensor;
	private JTable table, SensorTable;
	//private JTextPane textPane;
	private JScrollPane  tableScroll, SensorTableScroll;
	private JButton nextButton;
	private JLabel title1, title2, leftTitle, rightTitle;
	private JTextArea area1 , area2;
	private JScrollPane pane1, pane2;
	private	String measure;
	

	// private SafirFrame p;
	public ReadSensorDialog(final SafirFrame parent, final Sensor sensor) {
		super(parent, true);
		// p = parent;
		this.sensor = sensor;
		group = (Group) sensor.getParent();
		setSize(1000, 600);
		setLocation(0, 0);
		setResizable(false);
		setTitle(sensor.toString());
		getContentPane().setLayout(null);
		title1 = new JLabel(sensor.toString());
		title1.setBounds(10, 10, 210, 30);
		add(title1);
		 measure = "";
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
		contructTable();
		contructSensorTable();
		nextButton = new JButton("Применить");
		nextButton.setIcon(CommonConstants.okIcon);
		nextButton.setSize(150, 30);
		nextButton.setLocation(250, 510);
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
				dispose();
			}
		});
		add(nextButton);
		leftTitle = new JLabel("Из датчика");
		rightTitle= new JLabel("Из программы");
		leftTitle.setSize(100, 30);
		rightTitle.setSize(100, 30);
		leftTitle.setLocation(200, 20);
		rightTitle.setLocation(600, 20);
		add(rightTitle);
		add(leftTitle);
		
		
		

	}

	private void contructTable() {
		area2 = new JTextArea();
		pane2 = new JScrollPane(area2);
		pane2.setSize(400, 140);
		pane2.setLocation(500, 190);
		add(pane2);
		area2.append("P min = "+  group.getMinPressure() + measure+"\n");
		area2.append("P max = "+  group.getMaxPressure() + measure+"\n");
		area2.append("T max = "	+ group.getMaxTemp()+"\n");
		area2.append("T min = "	+ group.getMinTemp()+"\n");
		area2.append("КУ = "	+ sensor.getKoefAmplificftion()+"\n");
		area2.append("span = "+   sensor.getSpan()+"\n");
		
		
		
		String[] columnHeaders = new String[group.getTemperatures().size()];
		String[] rowHeaders = new String[group.getPressures().size() + 1];
		// LinkedList<String> rowHeaders = new LinkedList<String>();
		int a = 0;
		for (Double d : group.getTemperatures())
			columnHeaders[a++] = d.toString();
		a = 1;
		rowHeaders[0] = "Код T";
		for (Double d : group.getPressures())
			rowHeaders[a++] = d.toString();
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
		//tableScroll.setSize(400, 3 + (rowHeaders.length + 1)
		//		* (table.getRowHeight() + table.getRowMargin() - 1));
		tableScroll.setSize(400, 130);

		tableScroll.setLocation(500, 50);
		add(tableScroll);
		Float[][] table = sensor.getTable();
		Float[] iout = new Float[table.length];
		Float step = (float) 16 / (table.length - 1);
		for (int i = 0; i < table.length; i++) {
			iout[i] = 4 + i * step;
		}
		
		float[] coef;
		try {
			coef = SafUtil.CalkAprox(table, iout, sensor.getTemperatures(), group
					.getZeroPressure(), group.getBaseRow(), group.getT23());


		
		Float[][] array = new Float[6][4];
		for (int j = 0;j<6;j++)
		
			for (int i = 0;i<4;i++)
		{  //if (i==3) continue; 
			array[j][i] = coef[i+j*4];
		}
		
	
		
		
		
		JTable table2 = new JTable(array,new String[] {"0","1","2","3"});
		JScrollPane pane = new JScrollPane(table2);
		pane.setSize(400, 130);
		pane.setLocation(500, 350);
		add(pane);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		
		
		
		
		
		
		
	}

	private void contructSensorTable() {
		
		area1 = new JTextArea();
		pane1 = new JScrollPane(area1);
		pane1.setSize(400, 140);
		pane1.setLocation(10, 190);
		add(pane1);
		
		
		String[] columnHeaders = { "0", "1", "2", "3", "4", "5", "6", "7", "8",
				"9", "10", "11", "12", "13", "14", "15" };
		String[] rowHeaders = { "Код T", "0", "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15" };
		try {
			Float[][] array;
			int[] t = SerialConnection.INSTANSE.readVar(
					SensorConst.temperatures, sensor.getAddress()).getData();
			int passport = SerialConnection.INSTANSE.readVar(
					SensorConst.PASSPORT, sensor.getAddress()).getData()[0];
			
			int y = passport & 15;
			int x = (passport >> 4) & 15;
			x++;
			y++;
			array = new Float[y + 1][x];
			for (int j = 0; j < x; j++) {
				array[0][j] = SafUtil.ByteToFloat(Arrays.copyOfRange(t, j * 4,
						(j + 1) * 4));
				;
			}
			for (int i = 0; i < x; i++) {

				int[] m = SerialConnection.INSTANSE.readVar(
						SensorConst.table[i], sensor.getAddress()).getData();
				for (int j = 0; j < y; j++) {
					array[j + 1][i] = SafUtil.ByteToFloat(Arrays.copyOfRange(m,
							j * 4, (j + 1) * 4));
					;
				}
			}
			DefaultTableModel tableModel = new DefaultTableModel(array, Arrays
					.copyOf(columnHeaders, x));
			SensorTable = new JTable(tableModel);
			JList rowHeader = new JList(Arrays.copyOf(rowHeaders, y + 1));
			rowHeader.setFixedCellWidth(60);
			rowHeader.setFixedCellHeight(SensorTable.getRowHeight()
					+ SensorTable.getRowMargin() - 1);
			rowHeader.setCellRenderer(new RowHeaderRenderer(SensorTable));
			SensorTableScroll = new JScrollPane(SensorTable);
			SensorTableScroll.setSize(400, 130);
//			SensorTableScroll.setSize(400, 3 + (y + 2)
//					* (SensorTable.getRowHeight() + SensorTable.getRowMargin() - 1));
			
			SensorTableScroll.setRowHeaderView(rowHeader);
			SensorTableScroll.setLocation(10, 50);
			add(SensorTableScroll);
			int[] mas = SerialConnection.INSTANSE.readVar(
					SensorConst.PminPmaxTminTmax, sensor.getAddress()).getData();
			
			
			area1.append("Pmin = "+SafUtil.ByteToFloat(Arrays.copyOfRange(mas,0,4))+"\n");
			area1.append("Pmax = "+SafUtil.ByteToFloat(Arrays.copyOfRange(mas,4,8))+"\n");
			area1.append("Tmax = "+SafUtil.ByteToFloat(Arrays.copyOfRange(mas,12,16))+"\n");
			area1.append("Tmin = "+SafUtil.ByteToFloat(Arrays.copyOfRange(mas,8,12))+"\n");
			
			mas = SerialConnection.INSTANSE.readVar(
					SensorConst.coefAmplification, sensor.getAddress()).getData();
			int ka = mas[0]>>3;
			area1.append("КУ ="+ka +"\n");
			
			mas = SerialConnection.INSTANSE.readVar(
					SensorConst.MENUP, sensor.getAddress()).getData();
			area1.append("MENUP = ");
			for (int i = 0; i < mas.length; i++) {
				area1.append(Integer.toHexString(mas[i])+" ");
				
			}
			area1.append("\n");
			
			
			
			
			title2 = new JLabel("Коэффициенты нелинейности и апроксимации");
			title2.setSize(300,20);
			title2.setLocation(10, 330);
			add(title2);
			
			
			
			
			array = new Float[6][4];
			mas = SerialConnection.INSTANSE.readVar(
					SensorConst.APROX, sensor.getAddress()).getData();	

			
			for (int i = 0;i<4;i++)
			{
				array[0][i] = SafUtil.ByteToFloat(Arrays.copyOfRange(mas, i * 4,
						(i + 1) * 4));
			}
			
			for (int i = 0;i<4;i++)
			{
				array[1][i] = SafUtil.ByteToFloat(Arrays.copyOfRange(mas, 16+(i * 4),
						16+(i + 1) * 4));
			}
			for (int i = 0;i<4;i++)
			{
				array[2][i] = SafUtil.ByteToFloat(Arrays.copyOfRange(mas, 32+(i * 4),
						32+(i + 1) * 4));
			}
			
			for (int i = 0;i<4;i++)
			{
				array[3][i] = SafUtil.ByteToFloat(Arrays.copyOfRange(mas, 48+(i * 4),
						48+(i + 1) * 4));
			}
			
			
			
			mas = SerialConnection.INSTANSE.readVar(
					SensorConst.NOLINE, sensor.getAddress()).getData();	
			for (int i = 0;i<4;i++)
			{
				array[4][i] = SafUtil.ByteToFloat(Arrays.copyOfRange(mas, i * 4,
						(i + 1) * 4));
			}
			
			for (int i = 0;i<4;i++)
			{
				array[5][i] = SafUtil.ByteToFloat(Arrays.copyOfRange(mas, 16+(i * 4),
						16+(i + 1) * 4));
			}
			
			
			
			JTable table = new JTable(array,new String[] {"0","1","2","3"});
			JScrollPane pane = new JScrollPane(table);
			pane.setSize(400, 130);
			pane.setLocation(10, 350);
			add(pane);
			
			
			
			
			
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	private void initializeStyles() {
//		StyleContext context = StyleContext.getDefaultStyleContext();
//		Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
//		Style newStyle = textPane.addStyle("normal", style);
//		StyleConstants.setFontFamily(newStyle, "SansSerif");
//		newStyle = textPane.addStyle("red", style);
//		StyleConstants.setForeground(newStyle, Color.RED);
//		newStyle = textPane.addStyle("green", style);
//		StyleConstants.setForeground(newStyle, new Color(0, 160, 0));
//
//	}

}
