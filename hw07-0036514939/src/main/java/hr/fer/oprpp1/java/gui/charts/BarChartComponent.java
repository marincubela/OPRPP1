package hr.fer.oprpp1.java.gui.charts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class BarChartComponent extends JComponent {
	private static final long serialVersionUID = 1L;
	private final int SPACE = 8;

	private BarChart chart;

	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Insets ins = getInsets();
		int h = getHeight() - ins.top - ins.bottom;
		int w = getWidth() - ins.left - ins.right;

		int stringHeight = g.getFontMetrics().getHeight();
		int bottomHeight = 2 * (stringHeight + SPACE) + SPACE;
		int leftWidth = g.getFontMetrics().getHeight() + SPACE * 3
				+ g.getFontMetrics().stringWidth(Integer.toString(chart.getyMax()));

		int space = chart.getSpace();
		int yMax = chart.getyMax();
		int yMin = chart.getyMin();

		int rows = (yMax - yMin) / space;
		int columns = chart.getList().size();

		@SuppressWarnings("unused")
		int rectHeight = (h - bottomHeight - SPACE) / rows / space;
		int rectWidth = (w - leftWidth - SPACE) / columns;

		// Osi
		g.drawLine(leftWidth, h - bottomHeight, w, h - bottomHeight);
		g.drawLine(leftWidth, h - bottomHeight, leftWidth, 0);

		// Left axis text
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform defaultAt = g2.getTransform();

		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		g2.setTransform(at);
		int a = -(h - bottomHeight) / 2 - g2.getFontMetrics().stringWidth(chart.getxText()) / 2;
		int b = stringHeight + SPACE;
		g2.drawString(chart.getyText(), a, b);

		g2.setTransform(defaultAt);

		// Bottom axis text
		a = leftWidth + (w - leftWidth) / 2 - g.getFontMetrics().stringWidth(chart.getxText()) / 2;
		b = h - g.getFontMetrics().getMaxDescent() - SPACE;
		g.drawString(chart.getxText(), a, b);

		// Brojevi sa strane
		while ((yMax - yMin) % space != 0) {
			space++;
		}

		String number = null;
		for (int i = 0; i <= rows; i++) {
			number = Integer.toString(yMin + space * i);
			g.drawString(number, stringHeight + SPACE * 3 - g.getFontMetrics().stringWidth(number),
					h - bottomHeight - i * (h - bottomHeight - SPACE) / rows
							+ (g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent()) / 2);
		}

		// Brojevi na dnu
		List<XYValue> sorted = new ArrayList<>(chart.getList());
		sorted.sort((v1, v2) -> v1.getX() - v2.getX());

		for (int i = 0; i < sorted.size(); i++) {
			number = Integer.toString(sorted.get(i).getX());
			int sw = g.getFontMetrics().stringWidth(number);
			int ww = leftWidth + i * (w - leftWidth - SPACE) / sorted.size()
					+ (w - leftWidth - SPACE) / sorted.size() / 2 - sw / 2;
			g.drawString(number, ww, h - SPACE * 2 - stringHeight);
		}

		// Fill columns
		int j = 0;
		g.setColor(Color.orange);
		for (XYValue xy : sorted) {
			g.fillRect(leftWidth + j * (w - leftWidth - SPACE) / columns,
					h - bottomHeight - xy.getY() * (h - bottomHeight - SPACE) / rows / space,
					rectWidth, xy.getY() * (h - bottomHeight - SPACE) / rows / space);
			j++;
		}
		// Draw horizontal gridLines
		g.setColor(Color.orange);
		for (int i = 1; i <= rows; i++) {
			g.drawLine(leftWidth, h - bottomHeight - i * (h - bottomHeight - SPACE) / rows, w,
					h - bottomHeight - i * (h - bottomHeight - SPACE) / rows);
		}

		// Draw little horizontal lines
		g.setColor(Color.black);
		for (int i = 0; i <= rows; i++) {
			g.drawLine(leftWidth, h - bottomHeight - i * (h - bottomHeight - SPACE) / rows, leftWidth - SPACE,
					h - bottomHeight - i * (h - bottomHeight - SPACE) / rows);
		}

		// Draw vertical gridLines
		g.setColor(Color.white);
		for (int i = 1; i <= columns; i++) {
			g.drawLine(leftWidth + i * (w - leftWidth - SPACE) / columns, h - bottomHeight,
					leftWidth + i * (w - leftWidth - SPACE) / columns, 0);
		}

		// Draw little vertical lines
		g.setColor(Color.black);
		for (int i = 0; i < columns; i++) {
			g.drawLine(leftWidth + i * (w - leftWidth - SPACE) / columns, h - bottomHeight,
					leftWidth + i * (w - leftWidth - SPACE) / columns, h - bottomHeight + SPACE);
		}

		// Strjelice
		g.setColor(Color.black);
		g.drawLine(leftWidth, 0, leftWidth - 3, 5);
		g.drawLine(leftWidth, 0, leftWidth + 3, 5);

		g.drawLine(w, h - bottomHeight, w - 5, h - bottomHeight - 3);
		g.drawLine(w, h - bottomHeight, w - 5, h - bottomHeight + 3);
	}

}
