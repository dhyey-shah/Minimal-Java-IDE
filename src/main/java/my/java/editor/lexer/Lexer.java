package my.java.editor.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

	private final List<String> keywords;
	private final List<String> primitives;

	public Lexer() {
		keywords = new ArrayList<>();
		primitives = new ArrayList<>();
	}

	public static enum Codes {
		KEYWORDS, PRIMITIVE, SEMI, EQU,TYPE, IDENTIFIER, OPERATORS, NUMERIC;
	}

	public static class Token<T> {
		public final T t;
		public final String value;

		private int s, e;

		public Token(T t, String value) {
			this.t = t;
			this.value = value;
		}

		public Token(T t, String value, int s, int e) {
			this.t = t;
			this.value = value;
			this.s = s;
			this.e = e;
		}

		protected void setIndex(int s, int e) {
			this.s = s;
			this.e = e;
		}

		public int getStartIndex() {
			return s;
		}

		public int getEndIndex() {
			return e;
		}

		public T getCodes() {
			return t;
		}

		public String getValue() {
			return value;
		}
		
		@Override
		public String toString() {
			return t.toString() + " " + String.valueOf(s);
		}
	}

	public void setKeywords(List<String> list) {
		keywords.addAll(list);
	}
	
	public void setPrimitives(List<String> list) {
		primitives.addAll(list);
	}
	
	public void setOperators(List<String> list) {
		primitives.addAll(list);
	}
	
	public Token<Codes> createToken(Codes c, String val) {
		return new Token<Codes>(c, val);
	}

	public Token<Codes> createToken(Codes c, String val, int s, int e) {
		return new Token<Codes>(c, val, s, e);
	}

	public List<Token<Codes>> generateTokens(String input) {
		List<Token<Codes>> tokens = new ArrayList<>();
		String identifier = "";
		String numVal = "";

		for (int i = 0; i < input.length(); i++) {
			Character currChar = input.charAt(i);

			switch (currChar) {
			case ' ':
				break;
			case '+':
				tokens.add(createToken(Codes.OPERATORS, "+"));
				break;
			case '-':
				tokens.add(createToken(Codes.OPERATORS, "-"));
				break;
			case '*':
				tokens.add(createToken(Codes.OPERATORS, "*"));
				break;
			case '/':
				tokens.add(createToken(Codes.OPERATORS, "/"));
				break;
			case '=':
				tokens.add(createToken(Codes.EQU, "="));
				break;
			case ';':
				tokens.add(createToken(Codes.SEMI, ";"));
				break;
			default:
				if (Character.isLetter(currChar)) {
					int startIndex = i, endIndex;
					// System.out.println(i);
					while (i < input.length() && Character.isLetter(input.charAt(i))) {
						identifier += input.charAt(i++);
					}

					if (keywords.contains(identifier)) {
						endIndex = i;
						tokens.add(createToken(Codes.KEYWORDS, identifier, startIndex, endIndex));
					} else if (primitives.contains(identifier)) {
						endIndex = i;
						tokens.add(createToken(Codes.PRIMITIVE, identifier, startIndex, endIndex));

					} else {
						endIndex = i;
						tokens.add(createToken(Codes.IDENTIFIER, identifier, startIndex, endIndex));
					}

					identifier = "";
					i--;
				} else if (Character.isDigit(currChar)) {
					while (i < input.length() && Character.isDigit(input.charAt(i))) {
						numVal += input.charAt(i++);
					}
					i--;
					tokens.add(createToken(Codes.NUMERIC, numVal));
				}
			}
		}

		return tokens;
	}

}
