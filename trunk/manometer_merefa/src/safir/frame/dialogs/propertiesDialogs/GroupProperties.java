package safir.frame.dialogs.propertiesDialogs;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.tree.DefaultTreeModel;

import safir.bdUtils.DAO;
import safir.constants.CommonConstants;
import safir.data.Group;
import safir.data.Sensor;
import safir.data.SensorTypes;
import safir.frame.SafirFrame;

@SuppressWarnings("serial")
public class GroupProperties extends JDialog {
	private Group group;
	private JTextPane textPane;
	private JTextArea note;
	private JScrollPane scrollPane, noteScroll;
	private JButton cancelButton;
	private JButton nextButton;
	private JLabel titleLabel;
//	private SafirFrame parent;
	/**
	 * 
	 */
	public GroupProperties(final SafirFrame parent, final Group group) {
		super(parent, true);
	//	this.parent = parent;
	
		//group = (Group) parent.clickedPath.getLastPathComponent();
		this.group =group;
		setSize(500, 470);
		setLocation(50, 50);
		setResizable(false);
		getContentPane().setLayout(null);
		titleLabel = new JLabel(group.toString());
		titleLabel.setBounds(10, 10, 210, 30);
		add(titleLabel);
		String measure = "";

		switch (SensorTypes.INSTANSE.sensorTypes.get(group.getType()).measure) {
		case 0:
			measure = " Па";
			break;
		case 1:
			measure = " кПа";
			break;
		case 2:
			measure = " MПа";
			break;
		default:
			break;
		}
		textPane = new JTextPane();
		scrollPane = new JScrollPane(textPane);
		scrollPane.setSize(400, 200);
		scrollPane.setLocation(10, 40);
		add(scrollPane);
		int count = group.getChildCount();
		// System.out.println(count);

		int KA = 4;
		int TempFlags[] = new int[group.getTemperatures().size()];
		Arrays.fill(TempFlags, (byte) 1);
		// byte //SensTempFlags[];

		byte DataIsWrote = 1;
		byte K0diapIsWrote = 1;

		Sensor sensor;
		for (int i = 0; i < count; i++) {
			sensor = (Sensor) group.getChildAt(i);

			int ska = sensor.getKoefAmplificftion(); // коэф усиления
														// текущего сенсора
			if ((ska == 1) | (ska == 7)) {
				KA = ska;
			} else {
				if ((ska == -1) & (KA != 1) & (KA != 7))
					KA = -1;
			}

			int sdiw = sensor.getDataIsWrote();
			if (sdiw == 2) {
				DataIsWrote = 2;
			} else {
				if((DataIsWrote!=2)&(sdiw==0))
					DataIsWrote =0;
			}
			
			int sk0 = sensor.getK0diapIsWrote();
			
			if (sk0 == 2) {
				K0diapIsWrote = 2;
			} else {
				if((K0diapIsWrote!=2)&(sk0==0))
					K0diapIsWrote =0;
			}
			

			for (int j = 0; j < TempFlags.length; j++) {

				if (sensor.getTempFlags()[j] == (byte) 2) {
				

					TempFlags[j] = 2;

				} else {
					if (TempFlags[j] != 2) {
						TempFlags[j] = sensor.getTempFlags()[j];
					}
				}

			}

		}

	//	System.out.println(Arrays.toString(TempFlags));

		fillPropPane(measure, count, KA, TempFlags, DataIsWrote, K0diapIsWrote);

		note = new JTextArea();
		note.append(group.getNote());
		note.addKeyListener(new KeyListener() {

			
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void keyTyped(KeyEvent arg0) {
				nextButton.setEnabled(true);

			}
		});

		noteScroll = new JScrollPane(note);
		noteScroll.setSize(400, 100);

		noteScroll.setLocation(10, 260);
		add(noteScroll);

		// contructTable();

		cancelButton = new JButton("Отмена");
		cancelButton.setIcon(CommonConstants.canselIcon);
		cancelButton.setSize(150, 30);
		cancelButton.setLocation(80, 380);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
				dispose();
			}
		});
		add(cancelButton);

		nextButton = new JButton("Применить");
		nextButton.setEnabled(false);
		nextButton.setIcon(CommonConstants.okIcon);
		nextButton.setSize(150, 30);
		nextButton.setLocation(250, 380);
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				group.setNote(note.getText());
				
				try {
					DAO.INSTANSE.updateGroup(group);
				} catch (SQLException e) {
					JOptionPane
					.showConfirmDialog(
							null,
							"Нет связи с базой данных. \n Обратитесь к сисадмину.",
							"База данных недоступна.",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE);
				}
				
				
				((DefaultTreeModel)parent.getTree().getModel()).nodeChanged(group);
				// p.getTree().revalidate();
				// p.getTree().
				// /
				// sensor.
				// DAO.INSTANSE.updateSensor(sensor);
				setVisible(false);
				dispose();
			}
		});
		add(nextButton);

	}

	private void initializeStyles() {
		StyleContext context = StyleContext.getDefaultStyleContext();
		Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
		Style newStyle = textPane.addStyle("normal", style);
		StyleConstants.setFontFamily(newStyle, "SansSerif");
		newStyle = textPane.addStyle("red", style);
		StyleConstants.setForeground(newStyle, Color.RED);
		newStyle = textPane.addStyle("green", style);
		StyleConstants.setForeground(newStyle, new Color(0, 160, 0));

	}

	private void fillPropPane(String measure, int count, int KA,
			int TempFlags[], byte DataIsWrote, byte K0diapIsWrote) {
		initializeStyles();
		Document document = textPane.getDocument();
		try {
			document.insertString(document.getLength(), "P max = "
					+ group.getMaxPressure() + measure, textPane
					.getStyle("normal"));
			document.insertString(document.getLength(), "   P min = "
					+ group.getMinPressure() + measure + "  ", textPane
					.getStyle("normal"));
			
			document.insertString(document.getLength(), group.getPointsP() + "  точек. \n", textPane
					.getStyle("normal"));
			
			document.insertString(document.getLength(), "T max = "
					+ group.getMaxTemp(), textPane.getStyle("normal"));
			document.insertString(document.getLength(), "    T min = "
					+ group.getMinTemp() + "   ", textPane.getStyle("normal"));
			
			document.insertString(document.getLength(), group.getPointsT() + "  точек. \n", textPane
					.getStyle("normal"));
			
			
			
			

			document.insertString(document.getLength(), "P баз = "
					+ group.getPressures().get(group.getBaseRow()) + measure
					+ "\n\n", textPane.getStyle("normal"));

			if (count == 0) {
				document.insertString(document.getLength(),
						"В группу не добавленно ни одного датчика.", textPane
								.getStyle("normal"));
				return;
			}

			if ((KA == -1) | (KA == 1) | (KA == 7)) {

				if (KA == -1) {
					document.insertString(document.getLength(),
							"Коэффициент усиления не настроен.\n", textPane
									.getStyle("normal"));
					return;
				} else {
					document
							.insertString(
									document.getLength(),
									"При настройке коэффициента усиления произошли ошибки.\n",
									textPane.getStyle("normal"));
					return;
				}
			}

			document.insertString(document.getLength(),
					"Коэффициент усиления настроен.\n", textPane
							.getStyle("green"));
			for (int i = 0; i < TempFlags.length; i++)
				switch (TempFlags[i]) {
				case 0:
					document.insertString(document.getLength(), "Точки на "
							+ group.getTemperatures().get(i)
							+ " градусах не сняты.\n", textPane
							.getStyle("normal"));
					break;
				case 1:
					document.insertString(document.getLength(), "Точки на "
							+ group.getTemperatures().get(i)
							+ " градусах сняты.\n", textPane.getStyle("green"));
					break;
				case 2:
					document.insertString(document.getLength(),
							"При снятии данных на "
									+ group.getTemperatures().get(i)
									+ " градусов возникли проблемы.\n",
							textPane.getStyle("red"));
					break;
				}
			switch (DataIsWrote) {
			case 0:
				document.insertString(document.getLength(),
						"Данные в датчик не записаны.\n", textPane
								.getStyle("normal"));
				break;
			case 1:
				document.insertString(document.getLength(),
						"Данные в датчики записаны.\n", textPane
								.getStyle("green"));
				break;
			case 2:
				document.insertString(document.getLength(),
						"При записи данных в датчики возникли проблемы.\n",
						textPane.getStyle("red"));
				break;
			}
			switch (K0diapIsWrote) {
			case 0:
				document.insertString(document.getLength(),
						"Константы нуля диапазона в датчики не записаны.",
						textPane.getStyle("normal"));
				break;
			case 1:
				document.insertString(document.getLength(),
						"Константы нуля диапазона в датчики записаны.",
						textPane.getStyle("green"));
				break;
			case 2:
				document
						.insertString(
								document.getLength(),
								"При записи констант нуля диапазона в датчик возникли проблемы.",
								textPane.getStyle("red"));
				break;
			}

		} catch (BadLocationException e) {
			// TODO: handle exception
		}

	}

}
