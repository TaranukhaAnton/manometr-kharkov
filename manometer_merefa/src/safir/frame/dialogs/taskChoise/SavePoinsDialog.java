package safir.frame.dialogs.taskChoise;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;

import safir.bdUtils.DAO;
import safir.constants.CommonConstants;
import safir.constants.SensorConst;
import safir.data.Group;
import safir.data.Message;
import safir.data.Sensor;
import safir.exceptions.NoDataException;
import safir.frame.SafirFrame;
import safir.frame.components.Monitor;
import safir.rs232connect.SerialConnection;
import safir.utils.SafUtil;

public class SavePoinsDialog extends Monitor {
	private JProgressBar progressBar;
	private float[][] pData;
	private float[] tData;
	private Iterator<Double> iterPressures;
	private List<Double> listPressures;
	private Double currentPresure;
	private Double currentTemp;;
	private List<Sensor> sensors;
	private JButton monitorButton;
	private boolean begin = true;
	private boolean finish = false;
	private SafirFrame parent;
	private Group group;
	private Logger log;

	/**
	 * 
	 */

	public SavePoinsDialog(SafirFrame parent, Double temp,
			List<Double> listPressures, boolean baseRowIsChanged,
			List<Sensor> sensors, Group group) {
		super(parent);
		log = Logger.getLogger(this.getClass());
		this.parent = parent;
		currentTemp = temp;
		this.listPressures = listPressures;
		this.group = group;
		this.sensors = sensors;

		for (Sensor s : sensors) {
			s.setDataIsWrote(0);
			s.setK0diapIsWrote(0);
			if (baseRowIsChanged) {

				for (int t = 0; t < group.getPointsT(); t++) {
					if (t == group.getT23())
						continue;

					for (int p = 0; p < group.getPointsP(); p++)
						s.getTable()[p][t] = null;
					s.getTemperatures()[t] = null;
					s.getTempFlags()[t] = 0;

				}
			}
		}

		address = sensors.get(0).getAddress();
		setSize(700, 600);
		// setLocation(50, 50);
		// setResizable(false);
		// setTitle("");
		// init();
		iterPressures = listPressures.iterator();

		pData = new float[sensors.size()][listPressures.size()];
		tData = new float[sensors.size()];

		init();
		startTime = System.currentTimeMillis();
		startMonitor();

		// TODO Auto-generated constructor stub
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
		nextButton.setIcon(CommonConstants.okIcon);
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (begin) {
					begin = false;
					nextButton.setText("Съем");
					if (iterPressures.hasNext()) {
						currentPresure = iterPressures.next();
						titleLabel.setText("Подайте давление " + currentPresure
								+ group.getStrMeasure());
					}

				} else {
					if (!finish) {
						nextButton.setEnabled(false);
						titleLabel.setText("Cъем данных");
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

						Thread t = new Thread() {
							public void run() {
								getAndSaveData();
							}
						};
						t.start();
					} else {
						stopMonitor();
						setVisible(false);
						dispose();
					}
				}
			}
		});

		// cancelButton.addActionListener(new java.awt.event.ActionListener() {
		// public void actionPerformed(java.awt.event.ActionEvent evt) {
		// //monitorIsEnabled = false;
		// setVisible(false);
		// dispose();
		// }
		// });

		// monitorButton.addActionListener(new java.awt.event.ActionListener() {
		// public void actionPerformed(java.awt.event.ActionEvent evt) {
		// // ImageIcon iconRun = new ImageIcon("C:\\go.PNG");
		// // ImageIcon iconStop = new ImageIcon("C:\\stop.png");
		//
		// if (monitorIsEnabled) {
		// monitorButton.setText("Старт");
		// stopMonitor();
		// // monitorButton.setIcon(iconRun);
		// } else {
		// monitorButton.setText("Стоп");
		// // monitorButton.setIcon(iconStop);
		//
		// gpPressure.clear();
		// gpTerm.clear();
		// startMonitor();
		// }
		//
		// // monitorIsEnabled = !monitorIsEnabled;
		//
		// }
		// });

	}

	// private void sendTask() {
	// // System.out.println("Отослали задачу");
	//		
	// //return true;
	// }

	private void sendGroupCommand(List<Sensor> list ){
		
		try {
			Thread.sleep(1000);
			SerialConnection.INSTANSE.writeTask(list);
		} catch (NoDataException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showConfirmDialog(null,
					"При выдаче команды на измерение \n произошла ошибка.",
					"Ошибка при выдаче команды.", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
			log.error("При выдаче команды на измерение произошла ошибка.");
			setVisible(false);
			dispose();
			return;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
		}
	}
	
	
	private void getAndSaveData() {
		stopMonitor();

		sendGroupCommand(sensors);


		List<Sensor> resList =	takeMeasurements(sensors);
		if (!resList.isEmpty())
		{
			sendGroupCommand(resList);
			List<Sensor> resList2 =	takeMeasurements(resList);
			if (!resList2.isEmpty())
			{
				sendGroupCommand(resList2);
				List<Sensor> resList3 =	takeMeasurements(resList2);
				if (!resList3.isEmpty())
				{
					for (Sensor sensor : resList3) {
						sensors.remove(sensor);
						 int tInd = group.getTemperatures().indexOf(currentTemp);
						 sensor.getTempFlags()[tInd] = 2;
					}
				}
				
				

				
			}

			
		}
		

		if (sensors.isEmpty()) {
			JOptionPane.showConfirmDialog(null,
					"Список датчиков пуст. \n Произошли ошибки.",
					"Обратитесь к технологу .", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			setVisible(false);
			dispose();

		} else {

			if (iterPressures.hasNext()) {
				nextButton.setEnabled(true);
				currentPresure = iterPressures.next();
				titleLabel.setText("Подайте давление " + currentPresure
						+ group.getStrMeasure());

				startMonitor();

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

			} else

			{
				writeDataToSensors();
				finish = true;
				progressBar.setVisible(false);
				nextButton.setText("Готово");
				nextButton.setEnabled(true);
				titleLabel.setText("Все данные сняты.");
			}
		}
		// System.out.println("приняли данные");

	}

	private void writeDataToSensors() {
		DefaultTreeModel model = (DefaultTreeModel) parent.getTree().getModel();
		for (int i = 0; i < sensors.size(); i++) {
			Sensor sensor = sensors.get(i);

			SafUtil.log(sensor, "", false);
			SafUtil.log(sensor, "Сняли данные при температуре " + currentTemp,
					true);
			int tInd = group.getTemperatures().indexOf(currentTemp);
			Float[][] tab = sensor.getTable();
			Float[] temperatures = sensor.getTemperatures();

			for (int j = 0; j < listPressures.size(); j++) {

				int pInd = group.getPressures().indexOf(listPressures.get(j));

				tab[pInd][tInd] = pData[i][j];
				SafUtil.log(sensor, "P=" + listPressures.get(j) + " cod = "
						+ pData[i][j], false);

			}

			temperatures[tInd] = tData[i] / listPressures.size();
			SafUtil.log(sensor, "Tср = " + temperatures[tInd], false);
			SafUtil.log(sensor, "", false);
			sensor.getTempFlags()[tInd] = 1;
			sensor.setDataIsWrote(0);
			sensor.setK0diapIsWrote(0);
			sensor.setSpan(0.F);
			sensor.setDiapasonCount(-1);

			model.nodeChanged(sensor);
			try {
				DAO.INSTANSE.updateSensor(sensor);
			} catch (SQLException e) {
				JOptionPane.showConfirmDialog(null,
						"Нет связи с базой данных. \n Обратитесь к сисадмину.",
						"База данных недоступна.", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE);
			}

		}

	}

	private List<Sensor> takeMeasurements(List<Sensor> sens) {
		List<Sensor> result = new LinkedList<Sensor>();

		for (int i = 0; i < sens.size(); i++) {
			Sensor sensor = sens.get(i);
			try {
				Message m = SerialConnection.INSTANSE.readVar(
						SensorConst.PTAVR, sensor.getAddress());
				int[] res = m.getData();

				SafUtil.log(sensor, "P= "
						+ currentPresure
						+ " dataP "
						+ SafUtil.printHexArray(Arrays.copyOfRange(res, 0, 4),
								false)
						+ " dataT "
						+ SafUtil.printHexArray(
								Arrays.copyOfRange(res, 12, 16), false), true);

				float p, t;
				p = SafUtil.ByteToFloat(Arrays.copyOfRange(res, 0, 4));
				t = SafUtil.ByteToFloat(Arrays.copyOfRange(res, 12, 16));

				if ((p < 10) | (p > 65400) | (t < 10) | (t > 65400))

				{
					// sensors.remove(i);
					System.out.println("p " + p);
					System.out.println("t " + t);
					System.out.println("отбраковали" + sensor.getSerNum());
					result.add(sensor);
					// int tInd = group.getTemperatures().indexOf(currentTemp);
					// sensor.getTempFlags()[tInd] = 2;

				} else {
					pData[sensors.indexOf(sensor)][listPressures
							.indexOf(currentPresure)] = p;
					tData[sensors.indexOf(sensor)] += t;
				}
			} catch (Exception e) {
				// Exception может выкинуть только SerialConnection.INSTANSE.readVar если датчик не отвечает
				// или еще что то там с ним случилось
				// на всякий случай его закидываем в список дачтиков которые плохо ответили
				result.add(sensor);
				
				//e.printStackTrace();
			}

		}
		return result;
	}

}
