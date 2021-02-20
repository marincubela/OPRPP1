package hr.fer.oprpp1.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class BarChartDemo extends JFrame {
	private static final long serialVersionUID = 1L;
	private BarChart barChart;
	private String fileName;

	public BarChartDemo(String fileName) {
		setLocation(20, 50);
		setSize(600, 300);
		setTitle("Bar chart demo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.fileName = fileName;
		this.barChart = parseFile(fileName);
		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		cp.add(new JLabel(fileName, SwingConstants.CENTER), BorderLayout.NORTH);
		cp.add(new BarChartComponent(barChart), BorderLayout.CENTER);		
	}

	public static BarChart parseFile(String fileName) {
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
			if (lines.size() < 6) {
				throw new IllegalArgumentException("Datoteka ne opisuju dijagram kako bi trebala!");
			}
			String xText = null;
			String yText = null;
			List<XYValue> list = new ArrayList<>();
			int yMin = 0;
			int yMax = 0;
			int space = 0;

			xText = lines.get(0);
			yText = lines.get(1);
			String[] split = lines.get(2).split(" ");
			for (String s : split) {
				String[] split2 = s.split(",");
				list.add(new XYValue(Integer.parseInt(split2[0]), Integer.parseInt(split2[1])));
			}

			yMin = Integer.parseInt(lines.get(3));
			yMax = Integer.parseInt(lines.get(4));
			space = Integer.parseInt(lines.get(5));
			return new BarChart(list, xText, yText, yMin, yMax, space);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	public static void main(String[] args) {
		String fileName = args[0];

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new BarChartDemo(fileName);
			frame.setVisible(true);
		});
	}
}
