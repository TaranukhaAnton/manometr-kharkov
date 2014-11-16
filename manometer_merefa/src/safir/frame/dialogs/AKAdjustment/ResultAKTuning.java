package safir.frame.dialogs.AKAdjustment;

import java.awt.Color;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import safir.data.Sensor;

@SuppressWarnings("serial")
public class ResultAKTuning extends JDialog {
	private JTextPane textPane;
	private JScrollPane scrollPane;
	private JButton okButton;
	private JLabel label;

	// private JDialog parent;
	// List<Sensor> sensors;
	public ResultAKTuning(final JDialog parent, List<Sensor> sensors) {
		super(parent, true);
		// this.parent = parent;

		getContentPane().setLayout(null);
		setSize(550, 430);
		setLocation(50, 50);
		setResizable(false);
		setTitle("Настройка коэффициенов усиления датчиков.");
		textPane = new JTextPane();
		scrollPane = new JScrollPane(textPane);
		scrollPane.setSize(500, 300);
		scrollPane.setLocation(10, 50);
		add(scrollPane);
		okButton = new JButton("Ok");
		okButton.setSize(100, 30);
		okButton.setLocation(200, 360);
		add(okButton);
		label = new JLabel("Результаты настройки коэффициентов усиления");
		label.setSize(500, 30);
		label.setLocation(10, 10);
		add(label);
		initializeStyles();

		okButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				parent.setVisible(false);
				parent.dispose();
				setVisible(false);
				dispose();
			}
		});

		Document document = textPane.getDocument();
		try {
			boolean exist = false;
			
			for (Sensor sensor : sensors) {
				int ka = sensor.getKoefAmplificftion();
				if ((ka > 1) & (ka < 7)){
					
					document.insertString(document.getLength(), "Датчик "
							+ sensor.getSerNum() + " ку усиления установлен "
							+ ka + ".\n", textPane.getStyle("normal"));
					
					exist = true;
					}
			}
			if(  exist )
				
			document.insertString(document.getLength(),
					"______________________________________________________\n",
					textPane.getStyle("normal"));

			exist = false;
			for (Sensor sensor : sensors) {
				int ka = sensor.getKoefAmplificftion();
				if ((ka == 1) | (ka == 7)) {
					document.insertString(document.getLength(), "Датчик "
							+ sensor.getSerNum() + " ку усиления установлен "
							+ ka + ".\n", textPane.getStyle("red"));
					exist = true;
				}
			}

			
			if(  exist )
				document.insertString(document.getLength(),
						"______________________________________________________\n",
						textPane.getStyle("normal"));

			exist = false;
			for (Sensor sensor : sensors) {
				int ka = sensor.getKoefAmplificftion();
				if ((ka == -1)&sensor.isAvailable())
					{document.insertString(document.getLength(),
							"КУ для датчика " + sensor.getSerNum()
									+ " настроить не удалось.\n", textPane
									.getStyle("red"));
					exist = true;
					}
			}

			if(  exist )
				document.insertString(document.getLength(),
						"______________________________________________________\n",
						textPane.getStyle("normal"));
			
			exist = false;
			for (Sensor sensor : sensors) {
				if (!sensor.isAvailable())
					{document.insertString(document.getLength(),
							"Связь с датчиком " + sensor.getSerNum()
									+ " отсутствует.\n", textPane
									.getStyle("red"));
					exist = true;
					}
			}
			if(  exist )
				document.insertString(document.getLength(),
						"______________________________________________________\n",
						textPane.getStyle("normal"));
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initializeStyles() {
		StyleContext context = StyleContext.getDefaultStyleContext();
		Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
		Style newStyle = textPane.addStyle("normal", style);
		StyleConstants.setFontFamily(newStyle, "SansSerif");
		newStyle = textPane.addStyle("yellow", style);
		StyleConstants.setForeground(newStyle, Color.ORANGE);
		newStyle = textPane.addStyle("red", style);
		StyleConstants.setForeground(newStyle, Color.RED);

	}

}
