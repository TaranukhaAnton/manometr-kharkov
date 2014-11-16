package safir.frame.components;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import safir.constants.CommonConstants;
import safir.exceptions.NoDataException;
import safir.frame.SafirFrame;
import safir.rs232connect.SerialConnection;

@SuppressWarnings("serial")
public class Monitor extends JDialog implements Runnable {
	protected JLabel minPLabel;
	protected JLabel maxPLabel;
	protected JLabel timeLabel;
	protected JLabel PChangingLabel;
	protected JLabel minTLabel;
	protected JLabel maxTLabel;
	protected JLabel TChangingLabel;

	protected JLabel PCurrentLab;
	protected JLabel TCurrentLab;
	protected JLabel titleLabel;
	protected JLabel PCurrent;
	protected JLabel TCurrent;
	protected JButton nextButton;
	protected JButton cancelButton;
	protected GraphicPane gpPressure;
	protected GraphicPane gpTerm;
	// protected boolean monitorEnabled = false;
	protected long startTime;

	private Thread monitor;
	protected Integer address;

	public Monitor(SafirFrame parent) {
		super(parent, true);
		getContentPane().setLayout(null);
		setSize(700, 500);
		setLocation(50, 50);
		setResizable(false);
		// setTitle("Монитор");
		init();

		// DefaultMutableTreeNode node
		// =(DefaultMutableTreeNode)parent.clickedPath.getLastPathComponent();

		// Sensor sensor = (Sensor) node;
		// address = sensor.getAddress();

	}

	protected synchronized void startMonitor() {
		// monitorEnabled = true;
		// monitor.interrupt();
		startTime = System.currentTimeMillis();
		monitor = new Thread(this);
		monitor.start();
	};

	protected synchronized void stopMonitor() {
		
		try {
			if (monitor != null)
				{
				
				monitor.interrupt();
				monitor.join();
				
				}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//System.out.println(monitor.isAlive()?"жив":"мертв");
	}

	private void init() {
		minPLabel = new JLabel();
		maxPLabel = new JLabel();
		PChangingLabel = new JLabel();
		minTLabel = new JLabel();
		maxTLabel = new JLabel();
		TChangingLabel = new JLabel();
		timeLabel = new JLabel();
		PCurrentLab = new JLabel();
		TCurrentLab = new JLabel();
		PCurrent = new JLabel();
		TCurrent = new JLabel();
		titleLabel = new JLabel();

		PCurrent.setFont(new Font("Dialog", 4, 28));
		TCurrent.setFont(new Font("Dialog", 4, 28));
		timeLabel.setFont(new Font("Dialog", 4, 24));
		titleLabel.setFont(new Font("Dialog", 4, 24));
		PChangingLabel
				.setToolTipText("Изменение кода давления за поледние 10 секунд.");
		PCurrentLab.setToolTipText("Текущее знаечение кода давления.");
		TCurrentLab.setToolTipText("Текущее знаечение кода температуры.");
		PCurrent.setToolTipText("Текущее знаечение кода давления.");
		TCurrent.setToolTipText("Текущее знаечение кода температуры.");
		minPLabel.setSize(200, 20);
		titleLabel.setSize(650, 28);
		maxPLabel.setSize(200, 20);
		timeLabel.setSize(200, 20);
		TCurrent.setSize(200, 28);
		PCurrent.setSize(200, 28);
		PChangingLabel.setSize(200, 20);
		PCurrentLab.setSize(100, 20);
		TCurrentLab.setSize(100, 20);
		minTLabel.setSize(200, 20);
		maxTLabel.setSize(200, 20);
		TChangingLabel.setSize(200, 20);
		maxPLabel.setLocation(530, 120);
		minPLabel.setLocation(530, 140);
		timeLabel.setLocation(530, 80);
		titleLabel.setLocation(10, 10);
		PChangingLabel.setLocation(530, 160);
		PCurrentLab.setLocation(530, 185);
		PCurrent.setLocation(585, 180);

		TCurrent.setLocation(585, 340);
		TCurrentLab.setLocation(530, 345);
		minTLabel.setLocation(530, 300);
		maxTLabel.setLocation(530, 280);
		TChangingLabel.setLocation(530, 320);
		add(minPLabel);
		add(maxPLabel);
		add(timeLabel);
		add(PChangingLabel);
		add(PCurrentLab);
		add(TCurrentLab);
		add(PCurrent);
		add(TCurrent);
		add(minTLabel);
		add(maxTLabel);
		add(TChangingLabel);
		add(titleLabel);
		// ImageIcon iconRun = new ImageIcon("C:\\go.PNG");
		// monitorButton = new JButton("Старт");
		// monitorButton.setIcon(iconRun);
		// monitorButton.setSize(130, 30);
		// monitorButton.setLocation(530, 10);
		// add(monitorButton);

		nextButton = new JButton();
		nextButton.setSize(130, 30);
		nextButton.setLocation(530, 40);
		add(nextButton);

		cancelButton = new JButton("Выход");
		cancelButton.setIcon(CommonConstants.canselIcon);
		cancelButton.setSize(130, 30);
		cancelButton.setLocation(530, 420);
		add(cancelButton);
		gpPressure = new GraphicPane(45, 20);
		gpPressure.setLocation(10, 40);
		add(gpPressure);
		gpTerm = new GraphicPane(45, 16);
		gpTerm.setLocation(10, 280);
		add(gpTerm);
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stopMonitor();
				setVisible(false);
				dispose();
			}
		});

		// monitorButton.addActionListener(new java.awt.event.ActionListener() {
		// public void actionPerformed(java.awt.event.ActionEvent evt) {
		// ImageIcon iconRun = new ImageIcon("C:\\go.PNG");
		// ImageIcon iconStop = new ImageIcon("C:\\stop.png");
		//
		// if (monitorIsEnabled) {
		// monitorButton.setText("Старт");
		// monitorButton.setIcon(iconRun);
		// } else {
		// monitorButton.setText("Стоп");
		// monitorButton.setIcon(iconStop);
		// startTime = System.currentTimeMillis();
		// gpPressure.clear();
		// gpTerm.clear();
		// startThread();
		// }
		//
		// monitorIsEnabled = !monitorIsEnabled;
		//
		// }
		// });

	}

	/*
	 * public void run() {
	 * 
	 * if (address == null) return;
	 * 
	 * for (;;) {
	 * 
	 * try {
	 * 
	 * int[] dataP = SerialConnection.INSTANSE.readVar( SensorConst.PRS1DAT,
	 * address).getData(); int[] dataT = SerialConnection.INSTANSE.readVar(
	 * SensorConst.TMPDAT, address).getData(); PCurrentLab.setText("P текущ. =
	 * "); TCurrentLab.setText("T текущ. = ");
	 * 
	 * int resP = SafUtil.ByteToInt(dataP[0], dataP[1]);
	 * 
	 * PCurrent.setText(" " + resP);
	 * 
	 * gpPressure.addNode(resP);
	 * 
	 * int resT = SafUtil.ByteToInt(dataT[0], dataT[1]); gpTerm.addNode(resT);
	 * TCurrent.setText(" " + resT);
	 * 
	 * minPLabel.setText("Pmin = " + gpPressure.minValue);
	 * maxPLabel.setText("Pmax = " + gpPressure.maxValue);
	 * minTLabel.setText("Tmin = " + gpTerm.minValue); maxTLabel.setText("Tmax = " +
	 * gpTerm.maxValue);
	 * 
	 * String text = (new SimpleDateFormat("mm:ss")).format(new Date(
	 * System.currentTimeMillis() - startTime)); timeLabel.setText(text);
	 * PChangingLabel.setText("delta P = " +
	 * Integer.toString(gpPressure.delta10sec)); TChangingLabel.setText("delta T = " +
	 * Integer.toString(gpTerm.delta10sec)); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * if (!monitorEnabled) break; } }
	 */

	public void run() {

		if (address == null)
			return;
		try {
			while (true) {

				// int[] dataP = SerialConnection.INSTANSE.readVar(
				// SensorConst.PRS1DAT, address).getData();
				// int[] dataT = SerialConnection.INSTANSE.readVar(
				// SensorConst.TMPDAT, address).getData();

				PCurrentLab.setText("P текущ. = ");
				TCurrentLab.setText("T текущ. = ");

				int resP;
				int resT;
				try {
					resP = SerialConnection.INSTANSE.readPressure(address);
					resT = SerialConnection.INSTANSE.readTemperature(address);
					PCurrent.setText(" " + resP);
					gpPressure.addNode(resP);

					gpTerm.addNode(resT);
					TCurrent.setText(" " + resT);

				} catch (NoDataException e) {
					System.err.println("Возникла ошибочка. Но не беда -- моряки не сдаются!!!");
					e.printStackTrace();
				} // SafUtil.ByteToInt(dataP[0], dataP[1]);
				// SafUtil.ByteToInt(dataT[0], dataT[1]);

				minPLabel.setText("Pmin = " + gpPressure.minValue);
				maxPLabel.setText("Pmax = " + gpPressure.maxValue);
				minTLabel.setText("Tmin = " + gpTerm.minValue);
				maxTLabel.setText("Tmax = " + gpTerm.maxValue);

				String text = (new SimpleDateFormat("mm:ss")).format(new Date(
						System.currentTimeMillis() - startTime));
				timeLabel.setText(text);
				PChangingLabel.setText("delta P = "
						+ Integer.toString(gpPressure.delta10sec));
				TChangingLabel.setText("delta T = "
						+ Integer.toString(gpTerm.delta10sec));
				Thread.sleep(500);
				// if (!monitorEnabled)
				// break;
			}

		}

		catch (InterruptedException e) {
			return;
		}

	}

	public boolean isMonitorEnabled() {
		if (monitor != null) {
			return monitor.isAlive();
		} else
			return false;

	}

}