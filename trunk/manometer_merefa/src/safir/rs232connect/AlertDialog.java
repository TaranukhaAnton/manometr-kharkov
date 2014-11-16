package safir.rs232connect;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;





public class AlertDialog extends Dialog implements ActionListener {


    /**
	 * 
	 */
	private static final long serialVersionUID = 774273038289613051L;

	public AlertDialog(Frame parent, 
		       String title, 
		       String lineOne, 
		       String lineTwo,
		       String lineThree) {
	super(parent, title, true);

	Panel labelPanel = new Panel();
	labelPanel.setLayout(new GridLayout(3, 1));
	labelPanel.add(new Label(lineOne, Label.CENTER));
	labelPanel.add(new Label(lineTwo, Label.CENTER));
	labelPanel.add(new Label(lineThree, Label.CENTER));
	add(labelPanel, "Center");

	Panel buttonPanel = new Panel();
	Button okButton = new Button("OK");
	okButton.addActionListener(this);
	buttonPanel.add(okButton);
	add(buttonPanel, "South");

//	FontMetrics fm = getFontMetrics(getFont());
//	int width = Math.max(fm.stringWidth(lineOne), 
//		 Math.max(fm.stringWidth(lineTwo), fm.stringWidth(lineThree)));

	setSize(300, 150);
	setLocation(100,100);
	setVisible(true);
    }

    /**
    Handles events from the OK button. When OK is pressed the dialog becomes
    invisible, disposes of its self, and retruns.
    */
    public void actionPerformed(ActionEvent e) {
	setVisible(false);
	dispose();
    }
}
