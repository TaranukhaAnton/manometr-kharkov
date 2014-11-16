package safir.frame.dialogs;

import java.util.List;

import safir.constants.CommonConstants;
import safir.data.Sensor;
import safir.frame.SafirFrame;
import safir.frame.components.Monitor;
import safir.rs232connect.SerialConnection;
import safir.utils.SafUtil;

@SuppressWarnings("serial")
public class ZeroizeSensors extends Monitor{
//	private List<Sensor> sensors;
	public ZeroizeSensors(SafirFrame parent, final List<Sensor> sensors) {
		super(parent);
		address = sensors.get(0).getAddress();
		titleLabel.setText("Подайте давление " + 0 + " кПа.");
		nextButton.setText("Старт");
		
		startMonitor();
		nextButton.setIcon(CommonConstants.okIcon);
		nextButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stopMonitor();
				for (Sensor sensor:sensors)
					{
					SerialConnection.INSTANSE.zeroizeSensor(sensor.getAddress());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
					SerialConnection.INSTANSE.zeroizeSensor(sensor.getAddress());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
					SerialConnection.INSTANSE.zeroizeSensor(sensor.getAddress());
					
					
					SafUtil.log(sensor, "Датчик обнулён", true);
					}
				
				setVisible(false);
				dispose();
				

			}
		});
		
		
		// TODO Auto-generated constructor stub
	}

}
