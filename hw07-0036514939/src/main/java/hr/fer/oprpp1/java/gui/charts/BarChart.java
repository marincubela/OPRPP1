package hr.fer.oprpp1.java.gui.charts;

import java.util.ArrayList;
import java.util.List;

public class BarChart {
	private List<XYValue> list;
	private String xText;
	private String yText;
	private int yMin;
	private int yMax;
	private int space;

	public BarChart(List<XYValue> list, String xText, String yText, int yMin, int yMax, int space) {
		if (yMin < 0) {
			throw new IllegalArgumentException("Ymin ne smije biti negativan!");
		}

		if (yMax <= yMin) {
			throw new IllegalArgumentException("Ymax mora biti strogo veći od Ymin");
		}

		for (XYValue v : list) {
			if (v.getY() < yMin) {
				throw new IllegalArgumentException("Y ne smije biti manji od ymin!");
			}
		}

		this.list = new ArrayList<>(list);
		this.xText = xText;
		this.yText = yText;
		this.yMin = yMin;
		this.yMax = yMax;
		this.space = space;
	}

	public List<XYValue> getList() {
		return list;
	}

	public String getxText() {
		return xText;
	}

	public String getyText() {
		return yText;
	}

	public int getyMin() {
		return yMin;
	}

	public int getyMax() {
		return yMax;
	}

	public int getSpace() {
		return space;
	}
}
