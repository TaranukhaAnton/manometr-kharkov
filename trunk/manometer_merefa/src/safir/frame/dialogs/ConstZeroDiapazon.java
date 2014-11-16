/*package safir.frame.dialogs;

//public class constZeroDiapazon {
//
//}
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import safir.bdUtils.DAO;
import safir.constants.CommonConstants;
import safir.constants.SensorConst;
import safir.data.Group;
import safir.data.Message;
import safir.data.Sensor;
import safir.data.SensorTypes;
import safir.frame.SafirFrame;
import safir.frame.components.GraphicPane;
import safir.rs232connect.SerialConnection;
import safir.utils.SafUtil;

@SuppressWarnings("serial")
public class constZeroDiapazon extends JDialog implements Runnable {

	private JLabel minLabel;
	private JLabel maxLabel;
	private JLabel timeLabel;
	private JLabel PChangingLabel;
	private JLabel PCurrent;
	private JLabel TCurrent;
	private JButton monitorButton;
	private JButton nextButton;
	private JButton cancelButton;
	private GraphicPane gpPressure;
	private GraphicPane gpTerm;
	private boolean monitorIsEnabled = false;
	private long startTime;
	private int address;
	private List<Sensor> sensors;
	private JLabel titleLabel;
	private JProgressBar progressBar;
	private Group group;
	private String unitOfMeasure;
	private Thread monitor;
	private SafirFrame parent;
	private int state = 0;

	public constZeroDiapazon(SafirFrame parent, List<Sensor> sensors,
			Group group) {
		super(parent, true);
		this.parent = parent;
		this.sensors = sensors;
		this.group = group;
		getContentPane().setLayout(null);
		setSize(800, 600);
		setLocation(50, 50);
		setResizable(false);
		setTitle("");
		init();
		List<Double> list = new LinkedList<Double>();
		list.add(0.);
		list.add(group.getPressures().get(group.getPressures().size() - 1));

		switch (SensorTypes.INSTANSE.sensorTypes.get(group.getType()).measure) {
		case 0:
			unitOfMeasure = " Па";
			break;
		case 1:
			unitOfMeasure = " кПа";
			break;
		case 2:
			unitOfMeasure = " МПа";
			break;
		}
		startMonitor();

	}

	private void startMonitor() {
		monitorIsEnabled = true;
		startTime = System.currentTimeMillis();
		monitor = new Thread(this);
		monitor.start();
	};

	private void stopMonitor() {
		monitorIsEnabled = false;
		try {
			monitor.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void init() {
		minLabel = new JLabel();
		maxLabel = new JLabel();
		timeLabel = new JLabel();
		PChangingLabel = new JLabel();
		PCurrent = new JLabel();
		TCurrent = new JLabel();
		titleLabel = new JLabel("Проверьте систему на травление.");
		titleLabel.setFont(new Font("Dialog", 4, 24));
		titleLabel.setSize(400, 25);
		titleLabel.setLocation(10, 10);
		add(titleLabel);

		progressBar = new JProgressBar();
		progressBar.setSize(600, 15);
		progressBar.setLocation(20, 320);
		progressBar.setVisible(false);
		add(progressBar);

		timeLabel.setFont(new Font("Dialog", 4, 24));
		PChangingLabel
				.setToolTipText("Изменение кода давления за поледние 10 секунд.");
		PCurrent.setToolTipText("Текущее знаечение кода давления.");
		TCurrent.setToolTipText("Текущее знаечение кода температуры.");
		minLabel.setSize(200, 20);
		maxLabel.setSize(200, 20);
		timeLabel.setSize(200, 20);
		PChangingLabel.setSize(200, 20);
		PCurrent.setSize(200, 20);
		TCurrent.setSize(200, 20);

		maxLabel.setLocation(530, 120);
		minLabel.setLocation(530, 140);
		timeLabel.setLocation(530, 80);
		PChangingLabel.setLocation(530, 180);
		PCurrent.setLocation(530, 160);
		TCurrent.setLocation(530, 300);
		add(minLabel);
		add(maxLabel);
		add(timeLabel);
		add(PChangingLabel);
		add(PCurrent);
		add(TCurrent);
		// ImageIcon iconRun = new ImageIcon("C:\\go.PNG");

		monitorButton = new JButton("Старт");
		// monitorButton.setIcon(new ImageIcon(".\\resources\\delete.png"));
		monitorButton.setSize(150, 30);
		monitorButton.setLocation(530, 10);
		add(monitorButton);

		cancelButton = new JButton("Выход");
		cancelButton.setSize(150, 30);
		cancelButton.setLocation(250, 500);

		cancelButton.setIcon(CommonConstants.canselIcon);
		add(cancelButton);

		nextButton = new JButton("Далее");
		nextButton.setIcon(CommonConstants.okIcon);
		nextButton.setSize(150, 30);
		nextButton.setLocation(450, 500);

		add(nextButton);

		gpPressure = new GraphicPane(45, 20);
		gpPressure.setLocation(10, 50);
		add(gpPressure);

		gpTerm = new GraphicPane(45, 16);
		gpTerm.setLocation(10, 280);
		add(gpTerm);

		nextButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				Thread t;
				switch (state) {
				case 0:

					titleLabel.setText("Подайте давление 0 " + unitOfMeasure);

					break;
				case 1:
					t = new Thread() {
						public void run() {
							zeroizeSensors();
						}
					};
					t.start();
					break;
				case 2:
					t = new Thread() {
						public void run() {
							spanSensors();
						}
					};
					t.start();
					break;
				case 3:
					JTree tree = parent.getTree();
					DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

					for (Sensor sensor : sensors) {
						sensor.setK0diapIsWrote(1);
						
						model.nodeChanged(sensor);
				
						DAO.INSTANSE.updateSensor(sensor);
					}
					model.nodeChanged(group);

					monitorIsEnabled = false;
					setVisible(false);
					dispose();
					break;
				}

				state++;

			}
		});

		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				monitorIsEnabled = false;
				setVisible(false);
				dispose();
			}
		});

		monitorButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// ImageIcon iconRun = new ImageIcon("C:\\go.PNG");
				// ImageIcon iconStop = new ImageIcon("C:\\stop.png");

				if (monitorIsEnabled) {
					monitorButton.setText("Старт");
					stopMonitor();
					// monitorButton.setIcon(iconRun);
				} else {
					monitorButton.setText("Стоп");
					// monitorButton.setIcon(iconStop);

					gpPressure.clear();
					gpTerm.clear();
					startMonitor();
				}

				// monitorIsEnabled = !monitorIsEnabled;

			}
		});

	}

	private void zeroizeSensors() {
		stopMonitor();
		nextButton.setEnabled(false);
		titleLabel.setText("");
		progressBar.setVisible(true);
		progressBar.setIndeterminate(true);
		gpPressure.setVisible(false);
		gpTerm.setVisible(false);
		maxLabel.setVisible(false);
		minLabel.setVisible(false);
		timeLabel.setVisible(false);
		TCurrent.setVisible(false);
		PCurrent.setVisible(false);
		PChangingLabel.setVisible(false);
		monitorButton.setVisible(false);
		List<String> result = new ArrayList<String>();
		for (Sensor sensor : sensors) {
			SerialConnection.INSTANSE.zeroizeSensor(sensor.getAddress());
			try {
				SerialConnection.INSTANSE.readVar(SensorConst.ESPAN, sensor
						.getAddress());
				Message message = SerialConnection.INSTANSE.readVar(
						SensorConst.ZERO, sensor.getAddress());

				float ZERO = SafUtil.ByteToFloat(message.getData());
				result.add("Датчик " + sensor + " ZERO = " + ZERO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		titleLabel.setText("Подайте давление " + group.getMaxPressure()
				+ unitOfMeasure);

		gpPressure.setVisible(true);
		progressBar.setVisible(false);
		progressBar.setIndeterminate(false);
		gpTerm.setVisible(true);
		maxLabel.setVisible(true);
		minLabel.setVisible(true);
		timeLabel.setVisible(true);
		TCurrent.setVisible(true);
		PCurrent.setVisible(true);
		PChangingLabel.setVisible(true);
		monitorButton.setVisible(true);
		JOptionPane.showMessageDialog(null, result.toArray());
		startMonitor();
		nextButton.setEnabled(true);
	}

	private void spanSensors() {
		stopMonitor();
		nextButton.setEnabled(false);
		titleLabel.setText("");
		progressBar.setVisible(true);
		progressBar.setIndeterminate(true);
		gpPressure.setVisible(false);
		gpTerm.setVisible(false);
		maxLabel.setVisible(false);
		minLabel.setVisible(false);
		timeLabel.setVisible(false);
		TCurrent.setVisible(false);
		PCurrent.setVisible(false);
		PChangingLabel.setVisible(false);
		monitorButton.setVisible(false);
		ByteArrayOutputStream os;
		int[] tmp;
		List<String> result = new ArrayList<String>();
		for (Sensor sensor : sensors) {
			SerialConnection.INSTANSE.spanSensor(sensor.getAddress());
			try {
				SerialConnection.INSTANSE.readVar(SensorConst.SPAN, sensor.getAddress());
				Message message = SerialConnection.INSTANSE.readVar(SensorConst.SPAN, sensor.getAddress());
						

				 float SPAN = SafUtil.ByteToFloat(message.getData());
				 sensor.setSpan(SPAN);
				 result.add("Датчик " + sensor + " SPAN = " + SPAN);
				 message = SerialConnection.INSTANSE.readVar(SensorConst.KDIAP, sensor.getAddress());
				 float[] KDIAP = new float[8];
				 int[] mas = message.getData();
				 for (int i = 0; i < KDIAP.length; i++) {
					 KDIAP[i] = SPAN*SafUtil.ByteToFloat(Arrays.copyOfRange(mas, i*4, (i+1)*4));
				}
				 
				 	os =new ByteArrayOutputStream();
					for (int i = 0; i < KDIAP.length; i++) {
						os.write(SafUtil.floatToByteArray(KDIAP[i]));
					}
					tmp = new int[os.size()];
					for (int i = 0; i < tmp.length; i++) 
					 tmp[i]=os.toByteArray()[i];
					SerialConnection.INSTANSE.writeVar(SensorConst.KDIAP, sensor.getAddress(), tmp);
				 
				 
				 
				 
				 
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		titleLabel.setText("Коэф. нуля диапазона настроенны.");

		gpPressure.setVisible(true);
		progressBar.setVisible(false);
		progressBar.setIndeterminate(false);
		gpTerm.setVisible(true);
		maxLabel.setVisible(true);
		minLabel.setVisible(true);
		timeLabel.setVisible(true);
		TCurrent.setVisible(true);
		PCurrent.setVisible(true);
		PChangingLabel.setVisible(true);
		monitorButton.setVisible(true);
		// JOptionPane.showMessageDialog(null, result.toArray());
		// startMonitor();
		nextButton.setText("Применить");
		nextButton.setEnabled(true);
		JOptionPane.showMessageDialog(null, result.toArray());
	}

	public void run() {

		if (sensors.iterator().hasNext()) {
			address = sensors.iterator().next().getAddress();

			for (;;) {

				try {
					int[] dataP = SerialConnection.INSTANSE.readVar(
							SensorConst.PRS1DAT, address).getData();
					int[] dataT = SerialConnection.INSTANSE.readVar(
							SensorConst.TMPDAT, address).getData();
					int res = SafUtil.ByteToInt(dataP[0], dataP[1]);
					PCurrent.setText("P текущ. = " + res);
					gpPressure.addNode(res);
					res = SafUtil.ByteToInt(dataT[0], dataT[1]);
					gpTerm.addNode(res);
					TCurrent.setText("T текущ. = " + res);
					minLabel.setText("Pmin = " + gpPressure.minValue);
					maxLabel.setText("Pmax = " + gpPressure.maxValue);
					String text = (new SimpleDateFormat("mm:ss"))
							.format(new Date(System.currentTimeMillis()
									- startTime));
					timeLabel.setText(text);
					PChangingLabel.setText("delta P = "
							+ Integer.toString(gpPressure.delta10sec));
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!monitorIsEnabled)
					break;
			}
		}
	}

}*/



package safir.frame.dialogs;


import safir.bdUtils.DAO;
import safir.constants.CommonConstants;
import safir.constants.SensorConst;
import safir.data.Group;
import safir.data.Message;
import safir.data.Sensor;
import safir.frame.SafirFrame;
import safir.frame.components.Monitor;
import safir.rs232connect.SerialConnection;
import safir.utils.SafUtil;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class ConstZeroDiapazon extends Monitor {
	private JButton monitorButton;
	private List<Sensor> sensors;
	private Group group;
	private SafirFrame parent;
	private int state = 0;
	private JProgressBar progressBar;

	public ConstZeroDiapazon(SafirFrame parent, List<Sensor> sensors,
                             Group group) {
		super(parent);
		this.parent = parent;
		this.sensors = sensors;
		this.group = group;
		
		
		address = sensors.get(0).getAddress();
		setSize(700, 600);
		

		init();
//		List<Double> list = new LinkedList<Double>();
//		list.add(0.);
//		list.add(group.getPressures().get(group.getPressures().size() - 1));


		startMonitor();

	}



	private void init() {
		
		titleLabel.setText("Проверьте систему на травление.");
		progressBar = new JProgressBar();
		progressBar.setSize(600, 15);
		progressBar.setLocation(20, 320);
		progressBar.setVisible(false);
		add(progressBar);
		monitorButton = new JButton("Стоп");
		monitorButton.setSize(150, 30);
		monitorButton.setLocation(530, 10);
		add(monitorButton);
		monitorButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isMonitorEnabled()) {
					monitorButton.setText("Старт");
					stopMonitor();
				} else {
					monitorButton.setText("Стоп");
					startTime = System.currentTimeMillis();
					gpPressure.clear();
					gpTerm.clear();
					startMonitor();
				}
			}
		});
		cancelButton.setLocation(250, 500);
		cancelButton.setIcon(CommonConstants.canselIcon);
		nextButton.setText("Далее");
		nextButton.setLocation(450, 500);
		nextButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				Thread t;
				switch (state) {
				case 0:

					titleLabel.setText("Подайте давление 0 " + group.getStrMeasure());

					break;
				case 1:
					nextButton.setEnabled(false);
					titleLabel.setText("");
					progressBar.setVisible(true);
					progressBar.setIndeterminate(true);
					gpPressure.setVisible(false);
					gpTerm.setVisible(false);
					minPLabel.setVisible(false);
					maxPLabel.setVisible(false);
					minTLabel.setVisible(false);
					maxTLabel.setVisible(false);
					timeLabel.setVisible(false);
					TCurrent.setVisible(false);
					PCurrent.setVisible(false);
					PChangingLabel.setVisible(false);
					TChangingLabel.setVisible(false);
					PCurrentLab.setVisible(false);
					TCurrentLab.setVisible(false);
					monitorButton.setVisible(false);
					t = new Thread() {
						public void run() {
							
							zeroizeSensors();
						}
					};
					t.start();
					break;
				case 2:
					nextButton.setEnabled(false);
					titleLabel.setText("");
					progressBar.setVisible(true);
					progressBar.setIndeterminate(true);
					gpPressure.setVisible(false);
					gpTerm.setVisible(false);
					minPLabel.setVisible(false);
					maxPLabel.setVisible(false);
					minTLabel.setVisible(false);
					maxTLabel.setVisible(false);
					timeLabel.setVisible(false);
					TCurrent.setVisible(false);
					PCurrent.setVisible(false);
					PChangingLabel.setVisible(false);
					TChangingLabel.setVisible(false);
					PCurrentLab.setVisible(false);
					TCurrentLab.setVisible(false);
					monitorButton.setVisible(false);
					t = new Thread() {
						public void run() {
							spanSensors();
						}
					};
					t.start();
					break;
				case 3:
					JTree tree = parent.getTree();
					DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

					for (Sensor sensor : sensors) {
						sensor.setK0diapIsWrote(1);
						
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
					}
					model.nodeChanged(group);

					stopMonitor();
					setVisible(false);
					dispose();
					break;
				}

				state++;

			}
		});
		
		
		
		
		
		
		
		
/*		minLabel = new JLabel();
		maxLabel = new JLabel();
		timeLabel = new JLabel();
		PChangingLabel = new JLabel();
		PCurrent = new JLabel();
		TCurrent = new JLabel();
		titleLabel = new JLabel("Проверьте систему на травление.");
		titleLabel.setFont(new Font("Dialog", 4, 24));
		titleLabel.setSize(400, 25);
		titleLabel.setLocation(10, 10);
		add(titleLabel);

		progressBar = new JProgressBar();
		progressBar.setSize(600, 15);
		progressBar.setLocation(20, 320);
		progressBar.setVisible(false);
		add(progressBar);

		timeLabel.setFont(new Font("Dialog", 4, 24));
		PChangingLabel
				.setToolTipText("Изменение кода давления за поледние 10 секунд.");
		PCurrent.setToolTipText("Текущее знаечение кода давления.");
		TCurrent.setToolTipText("Текущее знаечение кода температуры.");
		minLabel.setSize(200, 20);
		maxLabel.setSize(200, 20);
		timeLabel.setSize(200, 20);
		PChangingLabel.setSize(200, 20);
		PCurrent.setSize(200, 20);
		TCurrent.setSize(200, 20);

		maxLabel.setLocation(530, 120);
		minLabel.setLocation(530, 140);
		timeLabel.setLocation(530, 80);
		PChangingLabel.setLocation(530, 180);
		PCurrent.setLocation(530, 160);
		TCurrent.setLocation(530, 300);
		add(minLabel);
		add(maxLabel);
		add(timeLabel);
		add(PChangingLabel);
		add(PCurrent);
		add(TCurrent);
		// ImageIcon iconRun = new ImageIcon("C:\\go.PNG");

		monitorButton = new JButton("Старт");
		// monitorButton.setIcon(new ImageIcon(".\\resources\\delete.png"));
		monitorButton.setSize(150, 30);
		monitorButton.setLocation(530, 10);
		add(monitorButton);

		cancelButton = new JButton("Выход");
		cancelButton.setSize(150, 30);
		cancelButton.setLocation(250, 500);

		cancelButton.setIcon(CommonConstants.canselIcon);
		add(cancelButton);

		nextButton = new JButton("Далее");
		nextButton.setIcon(CommonConstants.okIcon);
		nextButton.setSize(150, 30);
		nextButton.setLocation(450, 500);

		add(nextButton);

		gpPressure = new GraphicPane(45, 20);
		gpPressure.setLocation(10, 50);
		add(gpPressure);

		gpTerm = new GraphicPane(45, 16);
		gpTerm.setLocation(10, 280);
		add(gpTerm);

		nextButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				Thread t;
				switch (state) {
				case 0:

					titleLabel.setText("Подайте давление 0 " + unitOfMeasure);

					break;
				case 1:
					t = new Thread() {
						public void run() {
							ZeroizeSensors();
						}
					};
					t.start();
					break;
				case 2:
					t = new Thread() {
						public void run() {
							spanSensors();
						}
					};
					t.start();
					break;
				case 3:
					JTree tree = parent.getTree();
					DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

					for (Sensor sensor : sensors) {
						sensor.setK0diapIsWrote(1);
						
						model.nodeChanged(sensor);
				
						DAO.INSTANSE.updateSensor(sensor);
					}
					model.nodeChanged(group);

					monitorIsEnabled = false;
					setVisible(false);
					dispose();
					break;
				}

				state++;

			}
		});

		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				monitorIsEnabled = false;
				setVisible(false);
				dispose();
			}
		});

		monitorButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// ImageIcon iconRun = new ImageIcon("C:\\go.PNG");
				// ImageIcon iconStop = new ImageIcon("C:\\stop.png");

				if (monitorIsEnabled) {
					monitorButton.setText("Старт");
					stopMonitor();
					// monitorButton.setIcon(iconRun);
				} else {
					monitorButton.setText("Стоп");
					// monitorButton.setIcon(iconStop);

					gpPressure.clear();
					gpTerm.clear();
					startMonitor();
				}

				// monitorIsEnabled = !monitorIsEnabled;

			}
		});*/

	}

	private void zeroizeSensors() {
		stopMonitor();

		
		
		List<String> result = new ArrayList<String>();
		for (Sensor sensor : sensors) {
			SerialConnection.INSTANSE.zeroizeSensor(sensor.getAddress());
			try {
				SerialConnection.INSTANSE.readVar(SensorConst.ESPAN, sensor
						.getAddress());
				Message message = SerialConnection.INSTANSE.readVar(
						SensorConst.ZERO, sensor.getAddress());

				float ZERO = SafUtil.ByteToFloat(message.getData());
				result.add("Датчик " + sensor + " ZERO = " + ZERO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		titleLabel.setText("Подайте давление " + group.getMaxPressure()+" "+group.getStrMeasure());

		gpPressure.setVisible(true);
		progressBar.setVisible(false);
		progressBar.setIndeterminate(false);
		gpTerm.setVisible(true);
		maxPLabel.setVisible(true);
		minPLabel.setVisible(true);
		maxTLabel.setVisible(true);
		minTLabel.setVisible(true);
		TChangingLabel.setVisible(true);
		PCurrentLab.setVisible(true);
		TCurrentLab.setVisible(true);
		timeLabel.setVisible(true);
		TCurrent.setVisible(true);
		PCurrent.setVisible(true);
		PChangingLabel.setVisible(true);
		monitorButton.setVisible(true);
		
		JOptionPane.showMessageDialog(null, result.toArray());
		startMonitor();
		nextButton.setEnabled(true);
	}

	private void spanSensors() {
		stopMonitor();

		ByteArrayOutputStream os;
		int[] tmp;
		List<String> result = new ArrayList<String>();
		for (Sensor sensor : sensors) {
			SerialConnection.INSTANSE.spanSensor(sensor.getAddress());
			try {
				SerialConnection.INSTANSE.readVar(SensorConst.SPAN, sensor.getAddress());
				Message message = SerialConnection.INSTANSE.readVar(SensorConst.SPAN, sensor.getAddress());
						

				 float SPAN = SafUtil.ByteToFloat(message.getData());
				 sensor.setSpan(SPAN);
				 result.add("Датчик " + sensor + " SPAN = " + SPAN);
				 message = SerialConnection.INSTANSE.readVar(SensorConst.KDIAP, sensor.getAddress());
				 float[] KDIAP = new float[8];
				 int[] mas = message.getData();
				 for (int i = 0; i < KDIAP.length; i++) {
					 KDIAP[i] = SPAN*SafUtil.ByteToFloat(Arrays.copyOfRange(mas, i*4, (i+1)*4));
			//	System.out.println(KDIAP[i]);	
				}
				 
				 	os =new ByteArrayOutputStream();
					for (int i = 0; i < KDIAP.length; i++) {
						os.write(SafUtil.floatToByteArray(KDIAP[i]));
					}
					tmp = new int[os.size()];
					for (int i = 0; i < tmp.length; i++) 
					 tmp[i]=os.toByteArray()[i];
					SerialConnection.INSTANSE.writeVar(SensorConst.KDIAP, sensor.getAddress(), tmp);
				 
				 
				 
				 
				 
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		titleLabel.setText("Коэф. нуля диапазона настроенны.");

		gpPressure.setVisible(true);
		progressBar.setVisible(false);
		progressBar.setIndeterminate(false);
		gpTerm.setVisible(true);
		maxPLabel.setVisible(true);
		minPLabel.setVisible(true);
		maxTLabel.setVisible(true);
		minTLabel.setVisible(true);
		TChangingLabel.setVisible(true);
		PCurrentLab.setVisible(true);
		TCurrentLab.setVisible(true);
		timeLabel.setVisible(true);
		TCurrent.setVisible(true);
		PCurrent.setVisible(true);
		PChangingLabel.setVisible(true);
		monitorButton.setVisible(true);
		
		nextButton.setText("Применить");
		nextButton.setEnabled(true);
		JOptionPane.showMessageDialog(null, result.toArray());
	}



}