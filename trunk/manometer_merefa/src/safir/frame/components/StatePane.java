package safir.frame.components;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class StatePane extends JPanel {
	// private JFrame parent;
	private JLabel label;
	public JProgressBar bar;

	public StatePane(JFrame par) {
		setLayout(null);
		setBackground(new Color(210, 210, 210));
		bar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
		bar.setSize(300, 18);
		bar.setVisible(false);
		bar.setLocation(230, 3);
		bar.setStringPainted(true);
		//bar.setIndeterminate(true);
		add(bar);

		setSize(par.getSize().width, 25);
		setLocation(0, par.getSize().height - 75);
		//ImageIcon icon = new ImageIcon("C:\\alertYellow.PNG");
		label = new JLabel("");
		//label.setIcon(icon);
		label.setSize(230, 26);
		label.setLocation(0, 0);
		add(label);
	}

	public void setIndeterminate(boolean b) {
		bar.setIndeterminate(b);
	}

	public void setBarText(String s) {
		bar.setString(s);	
	}
	
	public void setLabelText(String s) {
		label.setText(s);	
	}
	
	
	
	

}
