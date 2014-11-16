package safir.frame.dialogs;

import java.awt.Component;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;

import safir.constants.SensorConst;
import safir.data.Sensor;
import safir.exceptions.NoDataException;
import safir.frame.SafirFrame;
import safir.rs232connect.SerialConnection;

public class FixedCurrentOutput extends JDialog {
	private static final long serialVersionUID = -5469300419684904829L;
	// private Logger log;
	private JButton exitButton, setButton, onOfButton;
	boolean isFixedCurretOutpauEnabled = false;
	private JSlider slider;

	Sensor sensor;

	public FixedCurrentOutput(SafirFrame parent, final Sensor sensor) {
		super(parent, true);
		this.sensor = sensor;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(null);
		setSize(400, 400);
		setLocation(300, 100);
		setResizable(false);
		slider = new JSlider(35, 225, 35);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(5);
		Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
		labelTable.put(35, new JLabel(""));
		labelTable.put(40, new JLabel("4"));
		labelTable.put(45, new JLabel(""));
		labelTable.put(50, new JLabel("5"));
		labelTable.put(55, new JLabel(""));
		labelTable.put(60, new JLabel("6"));
		labelTable.put(65, new JLabel(""));
		labelTable.put(70, new JLabel("7"));
		labelTable.put(75, new JLabel(""));
		labelTable.put(80, new JLabel("8"));
		labelTable.put(85, new JLabel(""));
		labelTable.put(90, new JLabel("9"));
		labelTable.put(95, new JLabel(""));
		labelTable.put(100, new JLabel("10"));
		labelTable.put(105, new JLabel(""));
		labelTable.put(110, new JLabel("11"));
		labelTable.put(115, new JLabel(""));
		labelTable.put(120, new JLabel("12"));
		labelTable.put(125, new JLabel(""));
		labelTable.put(130, new JLabel("13"));
		labelTable.put(135, new JLabel(""));
		labelTable.put(140, new JLabel("14"));
		labelTable.put(145, new JLabel(""));
		labelTable.put(150, new JLabel("15"));
		labelTable.put(155, new JLabel(""));
		labelTable.put(160, new JLabel("16"));
		labelTable.put(165, new JLabel(""));
		labelTable.put(170, new JLabel("17"));
		labelTable.put(175, new JLabel(""));
		labelTable.put(180, new JLabel("18"));
		labelTable.put(185, new JLabel(""));
		labelTable.put(190, new JLabel("19"));
		labelTable.put(195, new JLabel(""));
		labelTable.put(200, new JLabel("20"));
		labelTable.put(205, new JLabel(""));
		labelTable.put(210, new JLabel("21"));
		labelTable.put(215, new JLabel(""));
		labelTable.put(220, new JLabel("22"));
		labelTable.put(225, new JLabel(""));
		
		slider.setLabelTable(labelTable);
		slider.setSize(350, 40);
		slider.setLocation(10, 100);
		slider.setSnapToTicks(true);
		add(slider);
		slider.setEnabled(false);
		exitButton = new JButton("Выход");
		setButton = new JButton("Подать");
		onOfButton = new JButton("Включить режим");
		exitButton.setSize(220, 30);
		setButton.setSize(220, 30);
		onOfButton.setSize(220, 30);
		onOfButton.setLocation(20, 20);
		setButton.setLocation(20, 160);
		exitButton.setLocation(20, 200);

		add(exitButton);
		add(setButton);
		add(onOfButton);
		setButton.setEnabled(false);
		setButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				float ioutF = slider.getValue() / 10;
				int Iout = (int) (2048 * ioutF);
				int[] b = new int[2];
				b[0] = (Iout & 0xff);
				b[1] = ((Iout >> 8) & 0xff);
				try {
					SerialConnection.INSTANSE.writeVar(SensorConst.DAC, sensor
							.getAddress(), b);
				} catch (NoDataException e) {
				} catch (InterruptedException e) {
				}

			}
		});

		onOfButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isFixedCurretOutpauEnabled){
					SerialConnection.INSTANSE.disabledFixedCurrtntOutput(sensor);
					setButton.setEnabled(false);
					onOfButton.setText("Включить режим");
					slider.setEnabled(false);
				} else {
					SerialConnection.INSTANSE.enabledFixedCurrtntOutput(sensor);
					setButton.setEnabled(true);
					onOfButton.setText("Выключить режим");
					slider.setEnabled(true);
				}
				
				isFixedCurretOutpauEnabled = !isFixedCurretOutpauEnabled;

			}
		});

		exitButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(isFixedCurretOutpauEnabled)
				SerialConnection.INSTANSE.disabledFixedCurrtntOutput(sensor);
				setVisible(false);
				dispose();
			}
		});

	}

}
