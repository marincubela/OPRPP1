
package hr.fer.oprpp1.hw01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ComplexNumberTest {

	@Test
	public void testConstructor() {
		ComplexNumber c = new ComplexNumber(1.1, 1.5);
		assertEquals(1.1, c.getReal());
		assertEquals(1.5, c.getImaginary());
		assertEquals(new String("1.1+1.5i"), c.toString());

		c = new ComplexNumber(0, 1);
		assertEquals(0, c.getReal());
		assertEquals(1, c.getImaginary());

		c = new ComplexNumber(1, 0);
		assertEquals(1, c.getReal());
		assertEquals(0, c.getImaginary());
	}

	@Test
	public void testFromRealShouldWork() {
		ComplexNumber c = new ComplexNumber(1, 0);
		ComplexNumber c2 = ComplexNumber.fromReal(1);
		assertEquals(true, c.equals(c2));
	}

	@Test
	public void testFromImaginaryShouldWork() {
		ComplexNumber c = new ComplexNumber(0, 1);
		ComplexNumber c2 = ComplexNumber.fromImaginary(1);
		assertEquals(true, c.equals(c2));
	}

	@Test
	public void testFromMagnitudeAndAngleShouldWork() {
		ComplexNumber c = new ComplexNumber(1, 0);
		ComplexNumber c2 = ComplexNumber.fromMagnitudeAndAngle(1, 0);
		assertEquals(true, c.equals(c2));

		// TO DO
	}

	@Test
	public void testParseShouldWork() {
		ComplexNumber c = new ComplexNumber(2, 3);
		String s = new String("2+3i");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(2, 3);
		s = new String("+2+3i");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(2, -3);
		s = new String("2-3i");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(2, -3);
		s = new String("+2-3i");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(-2, -3);
		s = new String("-2-3i");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(-2, 3);
		s = new String("-2+3i");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(2, 1);
		s = new String("2+i");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(2, -1);
		s = new String("2-i");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(-2, 1);
		s = new String("-2+i");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(-2, -1);
		s = new String("-2-i");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(2.5, -3.4);
		s = new String("2.5-3.4i");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(0, 1);
		s = new String("i");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(0, -1);
		s = new String("-i");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(3, 0);
		s = new String("3");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(3, 0);
		s = new String("+3");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(-3, 0);
		s = new String("-3");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(0, 3);
		s = new String("3i");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(0, 3);
		s = new String("+3i");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));

		c = new ComplexNumber(0, -3);
		s = new String("-3i");
		assertEquals(true, c.equals(ComplexNumber.parse(s)));
	}

	@Test
	public void testParseShouldNotWork() {
		assertThrows(Exception.class, () -> ComplexNumber.parse(" 3-2i "));

		assertThrows(Exception.class, () -> ComplexNumber.parse("3+2"));

		assertThrows(Exception.class, () -> ComplexNumber.parse(""));

		assertThrows(Exception.class, () -> ComplexNumber.parse(" 3asi"));

		assertThrows(Exception.class, () -> ComplexNumber.parse("asdads"));
	}

	@Test
	public void testGetRealShouldWork() {
		ComplexNumber c = new ComplexNumber(2, 3);
		assertEquals(2, c.getReal());
	}

	@Test
	public void testGetImaginaryShouldWork() {
		ComplexNumber c = new ComplexNumber(2, 3);
		assertEquals(3, c.getImaginary());
	}

	@Test
	public void testGetMagnitudeShouldWork() {
		ComplexNumber c = new ComplexNumber(3, 4);
		assertEquals(5, c.getMagnitude());
	}

	@Test
	public void testGetAngleShouldWork() {
		ComplexNumber c = new ComplexNumber(1, 1);
		assertEquals(Math.PI / 4., c.getAngle());

		c = new ComplexNumber(-1, 1);
		assertEquals(3. * Math.PI / 4., c.getAngle());

		c = new ComplexNumber(-1, -1);
		assertEquals(5. * Math.PI / 4., c.getAngle());

		c = new ComplexNumber(1, -1);
		assertEquals(7. * Math.PI / 4., c.getAngle());

		c = new ComplexNumber(1, 0);
		assertEquals(0., c.getAngle());

		c = new ComplexNumber(0, 1);
		assertEquals(Math.PI / 2., c.getAngle());

		c = new ComplexNumber(0, -1);
		assertEquals(3 * Math.PI / 2., c.getAngle());

		c = new ComplexNumber(-1, 0);
		assertEquals(Math.PI, c.getAngle());
	}

	@Test
	public void testAddShouldWork() {
		ComplexNumber c = new ComplexNumber(3, 1);
		ComplexNumber c2 = new ComplexNumber(2, 3);
		ComplexNumber c3 = new ComplexNumber(5, 4);
		assertEquals(true, c3.equals(c.add(c2)));

		c = new ComplexNumber(3, -1);
		c2 = new ComplexNumber(-2, 3);
		c3 = new ComplexNumber(1, 2);
		assertEquals(true, c3.equals(c.add(c2)));
	}

	@Test
	public void testAddShouldThrowNullPointer() {
		ComplexNumber c = new ComplexNumber(3, 1);
		ComplexNumber c3 = null;
		assertThrows(NullPointerException.class, () -> c.add(c3));
	}

	@Test
	public void testSubShouldWork() {
		ComplexNumber c = new ComplexNumber(3, 1);
		ComplexNumber c2 = new ComplexNumber(2, 3);
		ComplexNumber c3 = new ComplexNumber(1, -2);
		assertEquals(true, c3.equals(c.sub(c2)));

		c = new ComplexNumber(3, -1);
		c2 = new ComplexNumber(-2, 3);
		c3 = new ComplexNumber(5, -4);
		assertEquals(true, c3.equals(c.sub(c2)));
	}

	@Test
	public void testSubShouldThrowNullPointer() {
		ComplexNumber c = new ComplexNumber(3, 1);
		ComplexNumber c3 = null;
		assertThrows(NullPointerException.class, () -> c.sub(c3));
	}

	@Test
	public void testMulShouldWork() {
		ComplexNumber c = new ComplexNumber(3, 1);
		ComplexNumber c2 = new ComplexNumber(2, 3);
		ComplexNumber c3 = new ComplexNumber(3, 11);
		assertEquals(true, c3.equals(c.mul(c2)));

		c = new ComplexNumber(3, -1);
		c2 = new ComplexNumber(-2, 3);
		c3 = new ComplexNumber(-3, 11);
		assertEquals(true, c3.equals(c.mul(c2)));
	}

	@Test
	public void testMulShouldThrowNullPointer() {
		ComplexNumber c = new ComplexNumber(3, 1);
		ComplexNumber c3 = null;
		assertThrows(NullPointerException.class, () -> c.mul(c3));
	}

	@Test
	public void testDivShouldWork() {
		ComplexNumber c = new ComplexNumber(3, 1);
		ComplexNumber c2 = new ComplexNumber(2, 3);
		ComplexNumber c3 = new ComplexNumber(9. / 13., -7. / 13.);
		assertEquals(true, c3.equals(c.div(c2)));

		c = new ComplexNumber(3, -1);
		c2 = new ComplexNumber(-2, 3);
		c3 = new ComplexNumber(-9. / 13., -7. / 13.);
		assertEquals(true, c3.equals(c.div(c2)));
	}

	@Test
	public void testDivShouldThrowNullPointer() {
		ComplexNumber c = new ComplexNumber(3, 1);
		ComplexNumber c3 = null;
		assertThrows(NullPointerException.class, () -> c.div(c3));		
	}

	@Test
	public void testPowerShouldWork() {
		ComplexNumber c = new ComplexNumber(2, 2);
		ComplexNumber c2 = new ComplexNumber(0, 8);
		assertEquals(true, c2.equals(c.power(2)));

		c = new ComplexNumber(3, -1);
		c2 = new ComplexNumber(18, -26);
		assertEquals(true, c2.equals(c.power(3)));
		
		assertEquals(ComplexNumber.fromReal(1), c.power(0));
	}

	@Test
	public void testPowerShouldThrowNullPointer() {
		ComplexNumber c = new ComplexNumber(3, 1);
		assertThrows(IllegalArgumentException.class, () -> c.power(-1));		
	}

	@Test
	public void testRootShouldWork() {
		ComplexNumber c = new ComplexNumber(2, 2);
		ComplexNumber c1 = new ComplexNumber(0.5 + Math.sqrt(0.75), -0.5 + Math.sqrt(0.75));
		ComplexNumber c2 = new ComplexNumber(-1, 1);
		ComplexNumber c3 = new ComplexNumber(0.5 - Math.sqrt(0.75), -0.5 - Math.sqrt(0.75));
		ComplexNumber[] comp = new ComplexNumber[] {c1, c2, c3};
		//Nema poklapanja s metodom ispod zbog greške zaokruživanja.
		//assertArrayEquals(comp, c.root(3)); 

		assertEquals(true, Math.abs(c.root(3)[0].sub(comp[0]).getReal()) < 10.E-10);
		assertEquals(true, Math.abs(c.root(3)[0].sub(comp[0]).getImaginary()) < 10.E-10);
		
		assertEquals(true, Math.abs(c.root(3)[1].sub(comp[1]).getReal()) < 10.E-10);
		assertEquals(true, Math.abs(c.root(3)[1].sub(comp[1]).getImaginary()) < 10.E-10);

		assertEquals(true, Math.abs(c.root(3)[2].sub(comp[2]).getReal()) < 10.E-10);
		assertEquals(true, Math.abs(c.root(3)[2].sub(comp[2]).getImaginary()) < 10.E-10);
	}

	@Test
	public void testRootShouldThrowNullPointer() {
		ComplexNumber c = new ComplexNumber(3, 1);
		assertThrows(IllegalArgumentException.class, () -> c.root(-1));		
	}
}
