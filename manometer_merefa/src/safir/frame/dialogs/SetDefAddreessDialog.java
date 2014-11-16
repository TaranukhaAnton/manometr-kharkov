package safir.frame.dialogs;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.apache.log4j.Logger;

import safir.constants.CommonConstants;
import safir.constants.SensorConst;
import safir.data.Message;
import safir.frame.SafirFrame;
import safir.rs232connect.SerialConnection;

@SuppressWarnings("serial")
public class SetDefAddreessDialog extends JDialog implements Runnable {
	// private SafirFrame parent;
	private JLabel label1, label2, label3, label4;
	private JButton cancelButton, startSearchButton, stopSearchButton,
			write0x02But;
	private JProgressBar prBar;
	private int direction = 1;
	private boolean stopThread;
	private Thread thread;
	private int ad;
	private Logger log;

	public SetDefAddreessDialog(SafirFrame parent) {
		super(parent, true);
		log = Logger.getLogger(this.getClass());
		// this.parent = parent;
		getContentPane().setLayout(null);
		setSize(400, 400);
		setLocation(300, 100);
		setResizable(false);
		initComponents();
	}

	private void initComponents() {
		final SetDefAddreessDialog nd = this;
		prBar = new JProgressBar();
		prBar.setSize(300, 20);
		prBar.setLocation(50, 100);
		prBar.setMaximum(20);
		prBar.setVisible(false);
		add(prBar);

		label1 = new JLabel("Подключите к системе ");
		label1.setSize(195, 20);
		label1.setLocation(50, 10);
		label1.setFont(new Font("Dialog", 4, 18));
		add(label1);

		label4 = new JLabel();
		label4.setSize(195, 20);
		label4.setLocation(50, 70);
		// label4.setFont(new Font("Dialog", 4, 18));
		add(label4);
		// final ImageIcon icon = new ImageIcon("c:\\abp3.png");
		label2 = new JLabel("один");
		label2.setSize(120, 20);
		label2.setLocation(245, 10);
		label2.setFont(new Font("Dialog", 3, 20));
		// label2.setIcon(icon);
		add(label2);

		label3 = new JLabel("датчик и нажмите кнопку \"Начать поиск\" ");
		label3.setSize(350, 20);
		label3.setLocation(20, 30);
		label3.setFont(new Font("Dialog", 4, 18));
		add(label3);

		// %%%%%%%%%%%%%%%%%%---exitButton---%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		cancelButton = new JButton("Выход");
		cancelButton.setIcon(CommonConstants.canselIcon);
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stopThread = true;
				if (thread != null)
					while (thread.isAlive()) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
						}
					}
				setVisible(false);
				dispose();
			}
		});
		cancelButton.setSize(100, 25);
		cancelButton.setLocation(150, 325);
		add(cancelButton);
		// 88888888888888888888888888888888888888888888888888888888888888888

		// %%%%%%%%%%%%%%%%%%---Начать поиск---%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		startSearchButton = new JButton("Начать поиск");
		startSearchButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						stopSearchButton.setEnabled(true);
						startSearchButton.setEnabled(false);
						prBar.setVisible(true);
						thread = new Thread(nd);
						stopThread = false;
						thread.start();

					}
				});
		startSearchButton.setSize(120, 25);
		startSearchButton.setLocation(50, 225);
		add(startSearchButton);
		// 88888888888888888888888888888888888888888888888888888888888888888

		// %%%%%%%%%%%%%%%%%%---Остановить
		// поиск---%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		stopSearchButton = new JButton("Остановить поиск");
		stopSearchButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startSearchButton.setEnabled(true);
				stopSearchButton.setEnabled(false);
				stopThread = true;
				prBar.setVisible(false);
			}
		});
		stopSearchButton.setEnabled(false);
		stopSearchButton.setSize(120, 25);
		stopSearchButton.setLocation(200, 225);
		add(stopSearchButton);
		// 88888888888888888888888888888888888888888888888888888888888888888

		// %%%%%%%%%%%%%%%%%%---Записать адрес 0х02 в
		// датчик.---%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		write0x02But = new JButton("Записать адрес 0х02 в датчик.");
		write0x02But.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// if(ad==null)
				if (SerialConnection.INSTANSE.writeDefAddress(ad)) {
					label3.setText("Адрес 0х02 записан успешно.");
					log.info("Записали адрес 0х02 в датчик с адресом " + ad
							+ " успешно");
					
					
				} else {
					label3.setText("Ошибка при записи адреса 0х02.");
					log.error("При записи адреса 0х02 в датчик с адресом " + ad
							+ " произошла ошибка");
				}
				write0x02But.setEnabled(false);

			}
		});
		write0x02But.setSize(250, 25);
		write0x02But.setLocation(70, 275);
		write0x02But.setVisible(false);
		add(write0x02But);
		// 88888888888888888888888888888888888888888888888888888888888888888

	}

	public void run() {
		// SerialConnection conn = parent.getConnection();
		for (;;) {
			int i = prBar.getValue();
			i += direction;
			prBar.setValue(i);
			if ((i == 20) | (i == 0))
				direction *= -1;

			try {
				Message m = SerialConnection.INSTANSE.readVar(
						SensorConst.ADDRESS, 0x03);
				ad = m.getData()[0];
				label3.setText("Датчик с адресом "
						+ ((ad > 0x0a) ? Integer.toHexString(ad)
								: ("0" + Integer.toHexString(ad)))
						+ " обнаружен.");
				label1.setVisible(false);
				label2.setVisible(false);
				startSearchButton.setVisible(false);
				stopSearchButton.setVisible(false);
				prBar.setVisible(false);
				prBar.setValue(1);
				write0x02But.setVisible(true);
				break;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}

			if (stopThread)
				break;
		}
	}

}
