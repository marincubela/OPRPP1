package hr.fer.oprpp1.lsystems.impl;

import java.awt.Color;

import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.oprpp1.lsystems.impl.commands.ColorCommand;
import hr.fer.oprpp1.lsystems.impl.commands.DrawCommand;
import hr.fer.oprpp1.lsystems.impl.commands.PopCommand;
import hr.fer.oprpp1.lsystems.impl.commands.PushCommand;
import hr.fer.oprpp1.lsystems.impl.commands.RotateCommand;
import hr.fer.oprpp1.lsystems.impl.commands.ScaleCommand;
import hr.fer.oprpp1.lsystems.impl.commands.SkipCommand;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;

/**
 * Klasa koja modelira konkretnu implementaciju {@link LSystemBuilder} 
 * izgradnje Lindermayerovog sustava. Konkretan Lindermayerov sustav se može zadati iz tekstualne datoteke 
 * ili se mogu posebne postavljati sve potrebne vrijednosti preko dostupnih metoda.
 * 
 * @author Cubi
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	public double unitLength = 0.1;
	public double unitLengthDegreeScaler = 1.f;
	public Vector2D origin = new Vector2D(0, 0);
	public double angle = 0.f;
	public String axiom = "";
	private Dictionary<Character, String> productions = new Dictionary<>();
	private Dictionary<Character, Command> actions = new Dictionary<>();

	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	@Override
	public LSystemBuilder configureFromText(String[] data) {
		String[] splitted = null;
		
		for(String line : data) {
			if(line.equals("")) {
				continue;
			}
			
			splitted = line.split("\\s+", 2);
			
			if (splitted[0].equals("origin")) {
				splitted = line.split("\\s+");
				this.setOrigin(Double.parseDouble(splitted[1]), Double.parseDouble(splitted[2]));
				
			} else if (splitted[0].equals("angle")) {
				this.setAngle(Double.parseDouble(splitted[1]));
				
			} else if (splitted[0].equals("unitLength")) {
				this.setUnitLength(Double.parseDouble(splitted[1]));
				
			} else if (splitted[0].equals("unitLengthDegreeScaler")) {
				double res;
				String[] divString = splitted[1].split("/");
				if (divString.length == 2) {
					res = Double.parseDouble(divString[0]) / Double.parseDouble(divString[1]);
				} else if (divString.length == 1) {
					res = Double.parseDouble(divString[0]);
				} else {
					throw new IllegalArgumentException("Invalid unitLengthDegreeScaler: " + splitted[1]);
				}

				this.setUnitLengthDegreeScaler(res);
				
			} else if (splitted[0].equals("command")) {
				splitted = line.split("\\s+", 3);
				this.registerCommand(splitted[1].charAt(0), splitted[2]);
				
			} else if (splitted[0].equals("axiom")) {
				this.setAxiom(splitted[1]);
				
			} else if (splitted[0].equals("production")) {
				splitted = line.split("\\s+", 3);
				this.registerProduction(splitted[1].charAt(0), splitted[2]);
				
			} else {
				throw new IllegalArgumentException("Attribute is not valid " + splitted[0] + "!");
			}
		}
		
		return this;
	}

	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		Command comm = getCommandFromString(arg1);
		this.actions.put(arg0, comm);

		return this;
	}

	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		this.productions.put(arg0, arg1);
		return this;
	}

	/**
	 * Privatna metoda koja parsira string i pretvara ga u objekt klase
	 * {@link Command}.
	 * 
	 * @param arg1 string koji treba parsirati.
	 * @return akciju dobivenu iz stringa.
	 */
	private Command getCommandFromString(String arg1) {
		String[] strings = arg1.split("\\s+");
		Command comm;

		if (strings[0].equals("draw")) {
			comm = new DrawCommand(Double.parseDouble(strings[1]));
		} else if (strings[0].equals("skip")) {
			comm = new SkipCommand(Double.parseDouble(strings[1]));
		} else if (strings[0].equals("scale")) {
			comm = new ScaleCommand(Double.parseDouble(strings[1]));
		} else if (strings[0].equals("rotate")) {
			comm = new RotateCommand(Double.parseDouble(strings[1]));
		} else if (strings[0].equals("push")) {
			comm = new PushCommand();
		} else if (strings[0].equals("pop")) {
			comm = new PopCommand();
		} else if (strings[0].equals("color")) {
			comm = new ColorCommand(new Color(Integer.valueOf(strings[1], 16)));
		} else {
			throw new IllegalArgumentException("Akcija je netočno definirana " + arg1);
		}

		return comm;
	}

	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

	/**
	 * Privatna klasa koja implementira {@link LSystem}.
	 * Ova implementacija koristi se za crtanje Lindermayerovog sustava zadanog nadklasom.
	 * 
	 * @author Cubi
	 *
	 */
	public class LSystemImpl implements LSystem {

		@Override
		public void draw(int lvl, Painter painter) {
			Context ctx = new Context();
			Vector2D direction = new Vector2D(1, 0);
			direction.rotate(angle * Math.PI / 180.f);
			TurtleState ts = new TurtleState(origin, direction, Color.black,
					unitLength * Math.pow(unitLengthDegreeScaler, lvl));
			ctx.pushState(ts);
			String actionsString = generate(lvl);
			
			for(Character c : actionsString.toCharArray()) {
				if(actions.get(c) != null) {
					actions.get(c).execute(ctx, painter);
				}
			}
		}

		@Override
		public String generate(int lvl) {
			if (lvl == 0) {
				return axiom;
			}

			String result = new String("");
			String resultAtLvl = generate(lvl - 1);

			for (Character c : resultAtLvl.toCharArray()) {
				if (productions.get(c) != null) {
					result += productions.get(c);
				} else {
					result += c;
				}
			}

			return result;
			
//			Nerekurzivni način 
			
//			String res = axiom;
//			String s = new String("");
//			
//			for(int i = lvl; i > 0; i--) {
//				s = res;
//				res = new String("");
//				for (Character c : s.toCharArray()) {
//					if (productions.get(c) != null) {
//						res += productions.get(c);
//					} else {
//						res += c;
//					}
//				}
//			}
//			
//			return res;
		}
	}
}
