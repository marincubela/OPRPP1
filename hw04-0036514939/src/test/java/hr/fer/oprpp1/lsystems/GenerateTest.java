package hr.fer.oprpp1.lsystems;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.lsystems.impl.LSystemBuilderImpl;

public class GenerateTest {
	@Test
	public void testGenerate() {
		LSystemBuilderImpl ls = new LSystemBuilderImpl();
		ls.setAxiom("F").registerProduction('F', "F+F-F+F");
		
		assertEquals("F", ls.build().generate(0));	
		assertEquals("F+F-F+F", ls.build().generate(1));
		assertEquals("F+F-F+F+F+F-F+F-F+F-F+F+F+F-F+F", ls.build().generate(2));
	}
}
