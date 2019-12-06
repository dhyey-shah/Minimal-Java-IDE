package my.java.editor.compiler;

import java.util.ArrayList;
import java.util.List;

import my.java.editor.compiler.lexer.Lexer;
import my.java.editor.compiler.lexer.TokenParser;
import my.java.editor.compiler.parser.Parser;

public class Compiler {
	public static void main(String args[]) {
		Lexer lex = new Lexer();
		TokenParser tok = new TokenParser();
		Parser<Lexer.Token<Lexer.Codes>> parse = new Parser<>();
		
		List<ArrayList<String>> list = tok.parseTokens();

		lex.setKeywords(list.get(0));
		lex.setPrimitives(list.get(1));
		lex.setOperators(list.get(2));
		lex.setBoolean(list.get(3));
		lex.setSpecialKeywords(list.get(4));
		
		parse.setTokenStream(lex.generateTokens("a(x,y)"));
		parse.parse();
		
	}
}
