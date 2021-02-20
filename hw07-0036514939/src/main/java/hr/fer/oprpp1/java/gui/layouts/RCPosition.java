package hr.fer.oprpp1.java.gui.layouts;

/**
 * Klasa koja predstavlja poziciju elementa pomoću broja retka i broja stupca.
 * 
 * @author Cubi
 *
 */
public class RCPosition {
	private int row;
	private int column;

	/**
	 * Konstruktor za klasu {@link RCPosition} koji prima poziciju, broj retka i
	 * stupca.
	 * 
	 * @param row    broj retke.
	 * @param column broj stupca.
	 */
	public RCPosition(int row, int column) {
		if (row < 1 || column < 1) {
			throw new IllegalArgumentException(
					"Brojevi retka i stupca moraju biti prirodni brojevi, a bili su " + row + " i " + column + "!");
		}
		this.row = row;
		this.column = column;
	}

	/**
	 * Metoda koja vraća broj retke.
	 * 
	 * @return broj retka.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Metoda koja vraća broj stupca.
	 * 
	 * @return broj stupca.
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Javna statička metoda tvornica koja pravi objekt {@link RCPosition}
	 * parsirajući predani tekst.
	 * 
	 * @param text string koji se parsira.
	 * @return novi objekt {@link RCPosition}.
	 * @throws IllegalArgumentException baca se ako nisu primljena dva argumenta ili
	 *                                  se ne mogu pretvoriti u {@link Integer}.
	 */
	public static RCPosition parse(String text) {
		String[] split = text.split(",");
		if (split.length != 2) {
			throw new IllegalArgumentException("Očekivao sam 2 argumenta, a predana su " + split.length + "!");
		}

		int row, column;

		try {
			row = Integer.parseInt(split[0]);
			column = Integer.parseInt(split[1]);
			return new RCPosition(row, column);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(text + " se ne može parsirati u dvije dimenzije tipa integer!");
		}
	}

	@Override
	public String toString() {
		return "(" + row + ", " + column + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
}
