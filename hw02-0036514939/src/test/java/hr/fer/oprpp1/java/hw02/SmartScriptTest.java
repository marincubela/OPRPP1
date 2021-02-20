package hr.fer.oprpp1.java.hw02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.DocumentNode;
import hr.fer.oprpp1.custom.scripting.elems.EchoNode;
import hr.fer.oprpp1.custom.scripting.elems.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.elems.TextNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;

public class SmartScriptTest {

	@Test
	public void testExample1() {
		SmartScriptParser s = new SmartScriptParser(readExample(1));
		assertTrue((s.getDocumentNode().getChild(0)) instanceof TextNode);
		assertEquals(1, s.getDocumentNode().numberOfChildren());

		SmartScriptParser parser1 = new SmartScriptParser(readExample(1));
		DocumentNode document = parser1.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		assertTrue(document2.equals(document));

	}

	@Test
	public void testExample2() {
		SmartScriptParser s = new SmartScriptParser(readExample(2));
		assertTrue((s.getDocumentNode().getChild(0)) instanceof TextNode);
		assertEquals(1, s.getDocumentNode().numberOfChildren());

		SmartScriptParser parser1 = new SmartScriptParser(readExample(2));
		DocumentNode document = parser1.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		assertTrue(document2.equals(document));
	}

	@Test
	public void testExample3() {
		SmartScriptParser s = new SmartScriptParser(readExample(3));
		assertTrue((s.getDocumentNode().getChild(0)) instanceof TextNode);
		assertEquals(1, s.getDocumentNode().numberOfChildren());

		SmartScriptParser parser1 = new SmartScriptParser(readExample(3));
		DocumentNode document = parser1.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		assertTrue(document2.equals(document));
	}

	@Test
	public void testExample4() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(readExample(4)));
	}

	@Test
	public void testExample5() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(readExample(5)));
	}

	@Test
	public void testExample6() {
		SmartScriptParser s = new SmartScriptParser(readExample(6));
		assertTrue((s.getDocumentNode().getChild(0)) instanceof TextNode);
		assertTrue((s.getDocumentNode().getChild(1)) instanceof EchoNode);
		assertTrue((s.getDocumentNode().getChild(2)) instanceof TextNode);
		assertEquals(3, s.getDocumentNode().numberOfChildren());

		SmartScriptParser parser1 = new SmartScriptParser(readExample(6));
		DocumentNode document = parser1.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		assertTrue(document2.equals(document));

	}

	@Test
	public void testExample7() {
		SmartScriptParser s = new SmartScriptParser(readExample(7));
		assertTrue((s.getDocumentNode().getChild(0)) instanceof TextNode);
		assertTrue((s.getDocumentNode().getChild(1)) instanceof EchoNode);
		assertTrue((s.getDocumentNode().getChild(2)) instanceof TextNode);
		assertEquals(3, s.getDocumentNode().numberOfChildren());

		SmartScriptParser parser1 = new SmartScriptParser(readExample(7));
		DocumentNode document = parser1.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		assertTrue(document2.equals(document));
	}

	@Test
	public void testExample8() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(readExample(8)));
	}

	@Test
	public void testExample9() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(readExample(9)));
	}

	@Test
	public void testExample10() {
		SmartScriptParser s = new SmartScriptParser(readExample(10));
		assertTrue((s.getDocumentNode().getChild(0)) instanceof TextNode);
		assertTrue((s.getDocumentNode().getChild(1)) instanceof ForLoopNode);
		assertTrue((s.getDocumentNode().getChild(2)) instanceof TextNode);
		assertTrue((s.getDocumentNode().getChild(3)) instanceof ForLoopNode);
		assertTrue((s.getDocumentNode().getChild(4)) instanceof TextNode);
		assertTrue((s.getDocumentNode().getChild(1).getChild(0)) instanceof TextNode);
		assertTrue((s.getDocumentNode().getChild(1).getChild(1)) instanceof EchoNode);
		assertTrue((s.getDocumentNode().getChild(1).getChild(2)) instanceof TextNode);
		assertTrue((s.getDocumentNode().getChild(3).getChild(0)) instanceof TextNode);
		assertTrue((s.getDocumentNode().getChild(3).getChild(1)) instanceof EchoNode);
		assertTrue((s.getDocumentNode().getChild(3).getChild(2)) instanceof TextNode);
		assertTrue((s.getDocumentNode().getChild(3).getChild(3)) instanceof EchoNode);
		assertTrue((s.getDocumentNode().getChild(3).getChild(4)) instanceof TextNode);
		assertEquals(5, s.getDocumentNode().numberOfChildren());

		SmartScriptParser parser1 = new SmartScriptParser(readExample(10));
		DocumentNode document = parser1.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		assertTrue(document2.equals(document));
	}

	private String readExample(int n) {
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer" + n + ".txt")) {
			if (is == null)
				throw new RuntimeException("Datoteka extra/primjer" + n + ".txt je nedostupna.");
			byte[] data = is.readAllBytes();
			String text = new String(data, StandardCharsets.UTF_8);
			return text;
		} catch (IOException ex) {
			throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		}
	}
}
