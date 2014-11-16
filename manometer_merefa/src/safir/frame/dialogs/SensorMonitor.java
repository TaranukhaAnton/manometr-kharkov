package safir.frame.dialogs;

import java.awt.event.ActionListener;

import safir.data.Sensor;
import safir.frame.SafirFrame;
import safir.frame.components.Monitor;

@SuppressWarnings("serial")
public class SensorMonitor extends Monitor{
	
	public SensorMonitor(SafirFrame parent, Sensor sensor) {
		super(parent);
		setTitle("Монитор");
		//Sensor sensor = (Sensor)parent.clickedPath.getLastPathComponent();
		titleLabel.setText("Монитор состояния датчика "+ sensor.getSerNum());
		address = sensor.getAddress();
		nextButton.setText("Стоп");
		startTime = System.currentTimeMillis();
		startMonitor();
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (isMonitorEnabled()) {
					
					nextButton.setText("Старт");
					stopMonitor();
				} else {
					nextButton.setText("Стоп");
					startTime = System.currentTimeMillis();
					gpPressure.clear();
					gpTerm.clear();
					startMonitor();
				}
			}
		});
		
		
		
		
		
		
		
	}
	

}
