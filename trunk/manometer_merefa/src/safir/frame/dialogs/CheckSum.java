package safir.frame.dialogs;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import safir.exceptions.SerialConnectionException;
import safir.rs232connect.SerialConnection;

public class CheckSum extends JFrame{

	public CheckSum() {
		this.setSize(400, 200);
		this.setLocation(50, 50);
		this.setTitle("Манометр-Харьков. Проверка контрольной суммы.");
		getContentPane().setLayout(null);
		setVisible(true);
		JButton button = new JButton("Прочитать");
		button.setLocation(140, 50);
		button.setSize(100, 30);
		add(button);
		final JLabel  label = new JLabel();
		label.setSize(200, 30);
		label.setLocation(120, 100);
		add(label);
		try {
			SerialConnection.INSTANSE.openConnection();
		} catch (SerialConnectionException e) {
			button.setEnabled(false);
		}
		label.setFont(new Font("Dialog", 4, 24));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//JFrame INSTANSE
		button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				String checkSum = SerialConnection.INSTANSE.getCheckSum(3);
				StringBuilder sb = new StringBuilder(checkSum);
			//	sb.reverse();
				label.setText(sb.toString());
//				JOptionPane.showConfirmDialog(INSTANSE, "Контрольная сумма \n"
//						+ sb, "Контрольная сумма.", JOptionPane.DEFAULT_OPTION,
//						JOptionPane.INFORMATION_MESSAGE);
			
			}
		});
		
		
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CheckSum sf = new CheckSum();
		sf.setVisible(true);
		sf.repaint();

	}

}
