package safir.frame.dialogs.AKAdjustment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.tree.DefaultTreeModel;

import safir.bdUtils.DAO;
import safir.constants.CommonConstants;
import safir.data.Group;
import safir.data.Sensor;
import safir.frame.SafirFrame;
import safir.frame.components.Monitor;
import safir.rs232connect.SerialConnection;
import safir.utils.SafUtil;

@SuppressWarnings("serial")
public class FormAK extends Monitor {

	private JProgressBar progressBar;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private SafirFrame parent;
	private List<Sensor> sensors;
	private Group group;

	public FormAK(SafirFrame parent, List<Sensor> sensors,
			Group group) {
		super(parent);
		setTitle("Настройка коэффициентов усиления.");
		this.parent = parent;
		this.group = group;
		this.sensors =sensors;
/*		if (parent.clickedPath.getLastPathComponent() instanceof Group) {
			group = (Group) parent.clickedPath.getLastPathComponent();
			sensors = group.getListSensors();
		}
		if (parent.clickedPath.getLastPathComponent() instanceof Sensor) {
			Sensor sensor = (Sensor) parent.clickedPath.getLastPathComponent();
			group = (Group) sensor.getParent();
			sensors = new ArrayList<Sensor>();
			sensors.add(sensor);
		}*/
		
		
		
		
		
		address = sensors.get(0).getAddress();
		titleLabel.setText("Подайте давление " + group.getMaxPressure()
				+ " кПа.");
		progressBar = new JProgressBar();
		nextButton.setText("Старт");
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);
		scrollPane.setSize(500, 250);
		progressBar.setSize(700, 20);
		scrollPane.setLocation(10, 50);
		progressBar.setLocation(0, this.getHeight() - 50);
		progressBar.setString("Идёт настройка коэффициенов усиления датчиков.");
		progressBar.setStringPainted(true);
		progressBar.setVisible(false);
		scrollPane.setVisible(false);
		add(scrollPane);
		add(progressBar);

		nextButton.setIcon(CommonConstants.okIcon);

		startMonitor();
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				stopMonitor();

				minPLabel.setVisible(false);
				maxPLabel.setVisible(false);
				PChangingLabel.setVisible(false);
				minTLabel.setVisible(false);
				maxTLabel.setVisible(false);
				TChangingLabel.setVisible(false);
				timeLabel.setVisible(false);
				PCurrentLab.setVisible(false);
				TCurrentLab.setVisible(false);
				PCurrent.setVisible(false);
				TCurrent.setVisible(false);
				titleLabel.setVisible(false);
				gpTerm.setVisible(false);
				gpPressure.setVisible(false);
				

				scrollPane.setVisible(true);
				progressBar.setIndeterminate(true);
				progressBar.setVisible(true);
				(new Thread() {
					public void run() {
						AKTune();
					}
				}).start();


			}
		});

	}

	private void AKTune() {

		DefaultTreeModel model = (DefaultTreeModel) parent.getTree().getModel();

		for (Sensor sensor : sensors) {
			int res = 0;
			int KA = 3; // сначала пишем ку=3, потом
			// больше или меньше
			sensor.setKoefAmplificftion(-1);
			sensor.setDataIsWrote(0);
			sensor.setK0diapIsWrote(0);
			Arrays.fill(sensor.getTempFlags(), 0);
			Arrays.fill(sensor.getTemperatures(), null);
			for (int i = 0; i<sensor.getTable().length;i++)
				Arrays.fill(sensor.getTable()[i], null);
			
			
			sensor.setAvailable(true);

			L: while (true) {
				//
				// System.out.println("датчик " + sensor.getAddress());
				// sensor.getAddress()
				int count = 3;
				for (;;) {

					try {
						SerialConnection.INSTANSE.writeKa(sensor.getAddress(),
								KA);
						textArea.append("Датчик  " + sensor.getSerNum()
								+ ". Записали ку  " + KA);
						res = SerialConnection.INSTANSE.readPressure(sensor
								.getAddress());
						textArea.append("  Код P канала ацп " + res + "\n");
						break;

					} catch (Exception e) {
						if (count > 0) {
							System.out.println("проблема с датчиком  "
									+ sensor.getAddress());
							System.out.println("Еще попыток" + count);
							count--;
						} else {
							System.out
									.println("Попыток больше не осталось. Выходим");
							sensor.setAvailable(false);
							break L;
						}
					}
				}

				if ((res < CommonConstants.maxAdcCode)
						& (res > CommonConstants.minAdcCode)) {
					// записать в сенсор ку
					sensor.setKoefAmplificftion(KA);
					textArea.append("КУ для датчика " + sensor.getSerNum()
							+ " принят " + KA + "\n\n");
					break;
				}
				if ((KA < 7) & (KA > 0)) {
					if (res > CommonConstants.maxAdcCode)
						KA--;
					if (res < CommonConstants.minAdcCode)
						KA++;
				} else {
					textArea.append("Для датчика " + sensor.getSerNum()
							+ " КУ настроить не удалось. \n");
					sensor.setKoefAmplificftion(KA);

					break; // это значит что пипец, ку не настроен
				}
			}

			SafUtil.log(sensor, "Записали КУ " + sensor.getKoefAmplificftion(),
					true);
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
			model.nodeChanged(sensor);

		}

		model.nodeChanged(group);
		model.reload(group);

		progressBar.setIndeterminate(false);
		progressBar.setVisible(false);
		(new ResultAKTuning(this, sensors)).setVisible(true);

		// setVisible(false);
		// dispose();

	}

}
