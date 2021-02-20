package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.elems.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {
	public static void main(String[] args) {
		String docBody = new String("This is sample text.\r\n" + "{$ FOR i 1 10 1 $}\r\n"
				+ " This is {$= i $}-th time this message is generated.\r\n" + "{$END$}\r\n" + "{$FOR i 0 10 2 $}\r\n"
				+ " sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" + "{$END$}\r\n" + "");

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
			System.out.println(parser.getDocumentNode());
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		SmartScriptParser parser1 = new SmartScriptParser(docBody);
		DocumentNode document = parser1.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		boolean same = document.equals(document2); // ==> "same" must be true
		System.out.println(same);
		
		
	}
}
