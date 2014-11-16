package safir.frame.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.TreeCellRenderer;

import safir.data.Group;
import safir.data.Sensor;

/**
 * @version 1.0 11/09/98
 */
@SuppressWarnings("serial")
public class MultiLineCellRenderer extends JPanel implements TreeCellRenderer {
	protected JLabel icon;
	protected TreeTextArea text;

	public MultiLineCellRenderer() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		icon = new JLabel() {
			public void setBackground(Color color) {
				if (color instanceof ColorUIResource)
					color = null;
				super.setBackground(color);
			}
		};
		add(icon);
		add(Box.createHorizontalStrut(4));
		add(text = new TreeTextArea());
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		String stringValue = tree.convertValueToText(value, isSelected,
				expanded, leaf, row, hasFocus);
		setEnabled(tree.isEnabled());
		text.setText(stringValue);
		//text.setSelect(isSelected);
	//	text.setFocus(hasFocus);
		Icon componentIcon = null;
		//DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		if (value instanceof Sensor) {
			text.setSelect(isSelected, false);
			Sensor sensor = (Sensor) value;
			componentIcon = sensor.getIcon();
		} else 
		if (value instanceof Group) {
			text.setSelect(isSelected,true);
			Group group = (Group)value;
			componentIcon = group.getIcon();
		} else text.setSelect(isSelected, false);
		
		
		if (componentIcon == null) {
			if (leaf) {
				icon.setIcon(UIManager.getIcon("Tree.leafIcon"));
			} else if (expanded) {
				icon.setIcon(UIManager.getIcon("Tree.openIcon"));
			} else {
				icon.setIcon(UIManager.getIcon("Tree.closedIcon"));
			}
		} else {
			//icon.seti
			icon.setIcon(componentIcon);
		}
		

		// icon.setIcon(MetalIconFactory.getTreeComputerIcon());

		return this;
	}

	public Dimension getPreferredSize() {
		Dimension iconD = icon.getPreferredSize();
		Dimension textD = text.getPreferredSize();
		int height = iconD.height < textD.height ? textD.height : iconD.height;
		return new Dimension(iconD.width + textD.width, height);
	}
//	@Override
//	public Dimension getSize() {
//		return new Dimension(200,	30);
//	}

	public void setBackground(Color color) {
		if (color instanceof ColorUIResource)
			color = null;
		super.setBackground(color);
	}

	class TreeTextArea extends JTextArea {
		Dimension preferredSize;

		TreeTextArea() {
			setLineWrap(true);
			setWrapStyleWord(true);
			setOpaque(true);
		}

		public void setBackground(Color color) {
			if (color instanceof ColorUIResource)
				color = null;
			super.setBackground(color);
		}

		public void setPreferredSize(Dimension d) {
			if (d != null) {
				preferredSize = d;
			}
		}

		public Dimension getPreferredSize() {
			return preferredSize;
		}

		@SuppressWarnings("deprecation")
		public void setText(String str) {
			FontMetrics fm = getToolkit().getFontMetrics(getFont());
			BufferedReader br = new BufferedReader(new StringReader(str));
			String line;
			int maxWidth = 0, lines = 0;
			try {
				while ((line = br.readLine()) != null) {
					int width = SwingUtilities.computeStringWidth(fm, line);
					if (maxWidth < width) {
						maxWidth = width;
					}
					lines++;
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			lines = (lines < 1) ? 1 : lines;
			int height = fm.getHeight() * lines;
			setPreferredSize(new Dimension(maxWidth + 6, height));
			super.setText(str);
		}

		void setSelect(boolean isSelected, boolean isGroup) {
			Color bColor;
			if (isSelected) {
				Color lineColor = UIManager.getColor("Tree.selectionBorderColor");
				setBorder(BorderFactory.createLineBorder(lineColor));
				bColor = UIManager.getColor("Tree.selectionBackground");
			} else {
				setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
				bColor = (isGroup)?(new Color(0.8f,0.6f,0.8f,0.3f)):UIManager.getColor("Tree.textBackground");
				//new Color()
			}
			super.setBackground(bColor);
		}

		void setFocus(boolean hasFocus) {
			if (hasFocus) {
				Color lineColor = UIManager.getColor("Tree.selectionBorderColor");
				setBorder(BorderFactory.createLineBorder(lineColor));
			} else {
				setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
			}
		}
	}
}
