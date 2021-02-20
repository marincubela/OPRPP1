package hr.fer.oprpp1.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CalcLayout implements LayoutManager2 {
	private static final int ROWS = 5;
	private static final int COLUMNS = 7;

	private Map<Component, RCPosition> components;
	private int space;

	public CalcLayout(int space) {
		this.space = space;
		this.components = new HashMap<>();
	}

	public CalcLayout() {
		this(0);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		this.components.remove(comp);
	}
	
	private Dimension getLayoutSize(Container cont, ISizeGetter getter) {
		Insets ins = cont.getInsets();
		
		Optional<Integer> heightOpt = Arrays.stream(cont.getComponents())
				.map(c -> getter.get(c) == null ? -1 : getter.get(c).height).max(Integer::compare);
		
		Optional<Integer> widthOpt = Arrays.stream(cont.getComponents())
				.map(c -> {
					if(components.get(c).equals(new RCPosition(1, 1))) {
						return (getter.get(c).width - 4 * space) / 5;
					} else {
						return getter.get(c) == null ? -1 : getter.get(c).width;
					}
				}).max(Integer::compare);
		
		int height = heightOpt.isPresent() && heightOpt.get() > 0 ? heightOpt.get() : 200;
		int width = widthOpt.isPresent() && widthOpt.get() > 0 ? widthOpt.get() : 500;
		
		return new Dimension(width * COLUMNS + ins.left + ins.right + space * (COLUMNS - 1),
				height * ROWS + ins.top + ins.bottom + space * (ROWS - 1));
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(parent, Component::getPreferredSize);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(parent, Component::getMinimumSize);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets ins = parent.getInsets();
		// Efektivna povrsina koja je dostupna za crtanje
		int width = parent.getWidth() - ins.left - ins.right;
		int height = parent.getHeight() - ins.top - ins.bottom;

		// Sirina i visina svih komponenti zajedno
		int wTotal = width - (COLUMNS - 1) * space;
		int hTotal = height - (ROWS - 1) * space;
		int h, w;
		Dimension dim;

		for (var entry : components.entrySet()) {
			h = getActualHeight(hTotal, entry.getValue().getRow(), entry.getValue().getColumn());
			w = getActualWidth(wTotal, entry.getValue().getRow(), entry.getValue().getColumn());

			dim = getActualPosition(ins, wTotal, hTotal, entry.getValue());
			entry.getKey().setBounds(dim.width, dim.height, w, h);
		}

	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (constraints == null) {
			throw new NullPointerException("Predano ograničenje ne smije biti null!");
		}

		if (comp == null) {
			throw new NullPointerException("Predana komponenta ne smije biti null!");
		}

		RCPosition position = null;
		try {
			if (constraints instanceof RCPosition) {
				position = (RCPosition) constraints;
			} else if (constraints instanceof String) {
				position = RCPosition.parse((String) constraints);
			} else {
				throw new IllegalArgumentException(
						"Predano ograničenje treba biti tipa RCPosition ili String, a bilo je tipa "
								+ constraints.getClass().toString() + "!");
			}
		} catch (IllegalArgumentException e) {
			throw new CalcLayoutException(e);
		}

		int r = position.getRow();
		int s = position.getColumn();

		if (r < 1 || r > 5) {
			throw new CalcLayoutException("Broj retka može biti samo broj od 1 do " + ROWS + "!");
		}

		if (s < 1 || s > 7) {
			throw new CalcLayoutException("Broj stupca može biti samo broj od 1 do " + COLUMNS + "!");
		}

		if (r == 1 && s > 1 && s < 6) {
			throw new CalcLayoutException("Pozicija " + position + " nije dopuštena!");
		}

		if (components.containsValue(position)) {
			throw new CalcLayoutException("Na poziciji " + position + " već postoji komponenta!");
		}

		components.put(comp, position);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getLayoutSize(target, Component::getMaximumSize);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {

	}

	private int getActualWidth(int w, int r, int c) {
		int baseW = w / COLUMNS;
		int mod = w % COLUMNS;

		if (r == 1 && c == 1) {
			baseW = baseW * 5 + 4 * space;
			switch (mod) {
			case 0:
				break;
			case 1:
				baseW += 1;
				break;
			case 2:
			case 3:
				baseW += 2;
				break;
			case 4:
			case 5:
				baseW += 3;
				break;
			case 6:
				baseW += 4;
				break;
			default:
				throw new IllegalArgumentException("Nešto je opasno pošlo po krivu!");
			}
			return baseW;
		}

		if (mod == 0 || (mod == 1 && c != 4) || (mod == 2 && (c != 3 && c != 5))
				|| (mod == 3 && (c != 2 && c != 4 && c != 6)) || (mod == 4 && (c == 2 || c == 4 || c == 6))
				|| (mod == 5 && (c == 3 || c == 5)) || (mod == 6 && c == 4)) {

			return baseW;
		} else {
			return baseW + 1;
		}
	}

	private int getActualHeight(int h, int r, int c) {
		int baseW = h / ROWS;
		int mod = h % ROWS;

		if (mod == 0 || (mod == 1 && r != 3) || (mod == 2 && (r != 2 && r != 4)) || (mod == 3 && (r == 2 || r == 4))
				|| (mod == 4 && r == 3)) {
			return baseW;
		} else {
			return baseW + 1;
		}
	}

	private Dimension getActualPosition(Insets ins, int w, int h, RCPosition position) {
		int x = ins.left;
		int y = ins.top;
		int c = position.getColumn();
		int r = position.getRow();

		if (r != 1) {
			for (int i = 1; i < position.getColumn(); i++) {
				x += getActualWidth(w, r, i) + space;
			}
		} else if (r == 1 && c != 1) {
			x += getActualWidth(w, 1, 1) + space;
			if (c == 7) {
				x += getActualWidth(w, 1, 6) + space;
			}
		}

		for (int j = 1; j < position.getRow(); j++) {
			y += getActualHeight(h, j, c) + space;
		}

		return new Dimension(x, y);
	}
}
