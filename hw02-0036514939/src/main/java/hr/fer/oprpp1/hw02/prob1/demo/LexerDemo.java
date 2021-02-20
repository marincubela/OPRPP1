package hr.fer.oprpp1.hw02.prob1.demo;

import hr.fer.oprpp1.hw02.prob1.Lexer;
import hr.fer.oprpp1.hw02.prob1.TokenType;

public class LexerDemo {
	public static void main(String[] args) {
		String ulaz = "\\1\\2 ab\\\\\\2c\\3\\4d";
		
		Lexer l = new Lexer(ulaz);
		
		while(l.getToken().getType() != TokenType.EOF) {
			System.out.println(l.getToken());
			l.nextToken();
		}
	}
}
