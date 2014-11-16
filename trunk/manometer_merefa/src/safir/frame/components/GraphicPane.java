package safir.frame.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.LinkedList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GraphicPane extends JPanel {
	// private int h, w;
	private int hh = 20;
	private int ww = 45;

	public LinkedList<Integer> data = new LinkedList<Integer>();
	public LinkedList<Long> time = new LinkedList<Long>();
	private int upperLimit;// = 32000;
	private int lowerLimit;// = 31000;
	private int step = 10;
	public int minValue;
	public int maxValue;
	// private int addedValue = 0;
	private int listLenght;
	private int delta = 100;
	public 	int delta10sec = 0;

	public GraphicPane(int width, int height) {

		this.setSize((width+5)*step, (height+2)*step);
		 hh = height;
		 ww = width;
		listLenght = ww;// width / step - 4;

	}

	public void clear() {
		upperLimit = 0;
		lowerLimit = 0;
		minValue = 0;
		maxValue =0;
		delta10sec=0;
		data.clear();
		time.clear();
	}

	public void addNode(int value) {
		// addedValue = value;
		if ((lowerLimit == 0) && (upperLimit == 0)) {
			upperLimit = value + delta;
			lowerLimit = value - delta;
			minValue = upperLimit;
			maxValue = lowerLimit;
		}
		while (upperLimit < value)
			upperLimit += delta;

		while (lowerLimit > value)
			lowerLimit -= delta;

		// int res = (int) (((value - lowerLimit) * (hh* step)) / (upperLimit -
		// lowerLimit));
		data.addFirst(value);
		time.addFirst(System.currentTimeMillis());

		if (value > maxValue)
			maxValue = value;
		if (value < minValue)
			minValue = value;

		if (data.size() > listLenght) {
			data.removeLast();
			time.removeLast();
		}
		if (data.size() > 5) {
		int min=data.get(0);
		int max=data.get(0);
		
			for (int k = 1; k<5; k++)
			{
				 if(min > data.get(k) ) min = data.get(k);
				 if(max < data.get(k) ) max = data.get(k);
				 delta10sec = max - min;
			}
		}
		
		
		
		repaint();
	}

	public void paint(Graphics g) {

		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g.setFont(new Font("arial", 4, 10));
		FontMetrics fm = g.getFontMetrics();
		int i = 160;
		setBackground(new Color(i, i, i));

		g.setColor(new Color(0xff, 0xff, 0x00));

		g2.translate(step * 4, step);
		g.drawString(Integer.toString(lowerLimit), -5
				- fm.stringWidth(Integer.toString(lowerLimit)), hh * step + 3);
		g.drawString(Integer.toString(upperLimit), -5
				- fm.stringWidth(Integer.toString(upperLimit)), 3);
		int lim = lowerLimit + (upperLimit - lowerLimit) / 2;
		g.drawString(Integer.toString(lim), -5
				- fm.stringWidth(Integer.toString(lim)), hh / 2 * step + 3);
		lim = lowerLimit + (upperLimit - lowerLimit) / 4;
		g.drawString(Integer.toString(lim), -5
				- fm.stringWidth(Integer.toString(lim)), hh * 3 / 4 * step + 3);
		lim = lowerLimit + (upperLimit - lowerLimit) * 3 / 4;
		g.drawString(Integer.toString(lim), -5
				- fm.stringWidth(Integer.toString(lim)), hh / 4 * step + 3);

		g2.setPaint(new Color(140, 140, 140));
		g2.fill(new Rectangle2D.Double(0, 0, ww * step, hh * step));
		

		i = 200;
		g.setColor(new Color(i, i, i));
		// горизонтальные
		for (int k = 1; k < hh; k++)
			g.drawLine(0, k * step, ww * step, k * step);

		// вертикальные
		for (int k = 1; k < ww; k++) {
			i = (k % 5 == 0) ? 0 : 200;
			g.setColor(new Color(i, i, i));
			g.drawLine(k * step, 0, k * step, hh * step);
		}
		g.setColor(new Color(0, 0, 0)); 
		g.drawRect(0, 0, ww * step, hh * step);
		g.drawLine(0, hh / 2 * step, ww * step, hh / 2 * step);
		g.drawLine(0, hh / 4 * step, ww * step, hh / 4 * step);
		g.drawLine(0, hh * 3 / 4 * step, ww * step, hh * 3 / 4 * step);

		// рисуем прямые минимума и максимума.
		if ((data.size() > 5)&(maxValue-minValue >30 )) {
			int min = (int) (((minValue - lowerLimit) * (hh * step)) / (upperLimit - lowerLimit));
			int max = (int) (((maxValue - lowerLimit) * (hh * step)) / (upperLimit - lowerLimit));
			g.setColor(new Color(0xff, 0x00, 0x00));
			g.drawLine(0, hh * step - max, ww * step, hh * step - max);
			g.setColor(new Color(0x00, 0x00, 0xff));
			g.drawLine(0, hh * step - min, ww * step, hh * step - min);
		}

		if (data.size() != 0) {
			int[] yArray = new int[data.size()+1];
			int[] xArray = new int[data.size()+1];

			g.setColor(new Color(0xff, 0xff, 0x00));
			xArray[0] = ww * step;
			yArray[0] = hh * step -(int) (((data.get(0) - lowerLimit) * (hh * step)) / (upperLimit - lowerLimit));

			for (int k = 1; k < yArray.length; k++) {
				xArray[k] = (ww - k-1) * step;
				yArray[k] = hh * step -(int) (((data.get(k-1) - lowerLimit) * (hh * step)) / (upperLimit - lowerLimit));
			}
			g2.setStroke(new BasicStroke(3.0f));
			g.drawPolyline(xArray, yArray, data.size());
		}

		g.setColor(new Color(0xff, 0xff, 0x00));
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(1);
		for (int a = 1; a * 5 < time.size(); a++) {
			float tt = (System.currentTimeMillis() - time.get(a * 5));
			g.drawString(nf.format(tt / 1000), ww * step - (a * 5 + 1) * step+3,
					(hh + 1) * step-2);

		}

	}

	public int getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(int upperLimit) {
		this.upperLimit = upperLimit;
	}

	public int getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(int lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

}
