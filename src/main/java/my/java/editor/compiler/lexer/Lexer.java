package my.java.editor.compiler.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

	private final List<String> keywords;
	private final List<String> primitives;
	private final List<String> booleans;
	private final List<String> specialKeywords;

	public Lexer() {
		keywords = new ArrayList<>();
		primitives = new ArrayList<>();
		booleans = new ArrayList<>();
		specialKeywords = new ArrayList<>();
	}

	public static enum CODES {
		KEYWORDS, PRIMITIVE, SEMI, EQU, LPAREN, RPAREN, COMMA, IDENTIFIER, BIN_OP, NUMERIC, DOT, BOOLEANS,
		 TILDE, SP_KEYWORDDS, UNARY_OP;
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

	public void setBoolean(List<String> list) {
		booleans.addAll(list);
	}

	public void setSpecialKeywords(List<String> list) {
		specialKeywords.addAll(list);
	}

	public Token<CODES> createToken(CODES c, String val) {
		return new Token<CODES>(c, val);
	}

	public Token<CODES> createToken(CODES c, String val, int s, int e) {
		return new Token<CODES>(c, val, s, e);
	}

	public List<Token<CODES>> generateTokens(String input) {
		List<Token<CODES>> tokens = new ArrayList<>();
		String identifier = "";
		String numVal = "";

		for (int i = 0; i < input.length(); i++) {
			Character currChar = input.charAt(i);

			switch (currChar) {
			case ' ':
				break;
			case '+':
				if (input.charAt(i + 1) == '+') {
					tokens.add(createToken(CODES.UNARY_OP, "++"));
					i++;
				} else if (input.charAt(i + 1) == '=') {
					tokens.add(createToken(CODES.BIN_OP, "+="));
					i++;
				} else
					tokens.add(createToken(CODES.BIN_OP, "+"));
				break;
			case '-':
				tokens.add(createToken(CODES.BIN_OP, "-"));
				break;
			case '*':
				tokens.add(createToken(CODES.BIN_OP, "*"));
				break;
			case '/':
				tokens.add(createToken(CODES.BIN_OP, "/"));
				break;
			case '=':
				tokens.add(createToken(CODES.EQU, "="));
				break;
			case '(':
				tokens.add(createToken(CODES.LPAREN, "("));
				break;
			case ')':
				tokens.add(createToken(CODES.RPAREN, ")"));
				break;
			case ',':
				tokens.add(createToken(CODES.COMMA, ","));
				break;
			case '.':
				tokens.add(createToken(CODES.DOT, "."));
				break;
			case '!':
				tokens.add(createToken(CODES.UNARY_OP, "!"));
				break;
			case '~':
				tokens.add(createToken(CODES.TILDE, "~"));
				break;
			case ';':
				tokens.add(createToken(CODES.SEMI, ";"));
				break;
			default:
				if (Character.isLetter(currChar)) {
					int startIndex = i, endIndex;
					// System.out.println(i);
					while (i < input.length()
							&& (Character.isLetter(input.charAt(i)) || Character.isDigit(input.charAt(i)))) {
						identifier += input.charAt(i++);
					}

					if (keywords.contains(identifier)) {
						endIndex = i;
						tokens.add(createToken(CODES.KEYWORDS, identifier, startIndex, endIndex));
					} else if (primitives.contains(identifier)) {
						endIndex = i;
						tokens.add(createToken(CODES.PRIMITIVE, identifier, startIndex, endIndex));

					} else if (booleans.contains(identifier)) {
						endIndex = i;
						tokens.add(createToken(CODES.BOOLEANS, identifier, startIndex, endIndex));
					} else if (specialKeywords.contains(identifier)) {
						endIndex = i;
						tokens.add(createToken(CODES.SP_KEYWORDDS, identifier, startIndex, endIndex));

					} else {
						endIndex = i;
						tokens.add(createToken(CODES.IDENTIFIER, identifier, startIndex, endIndex));
					}

					identifier = "";
					i--;
				} else if (Character.isDigit(currChar)) {
					while (i < input.length() && Character.isDigit(input.charAt(i))) {
						numVal += input.charAt(i++);
						if (Character.isLetter(input.charAt(i))) {
							System.out.println("Lexer: error token");
						}
					}
					i--;
					tokens.add(createToken(CODES.NUMERIC, numVal));
				}
			}
		}

		return tokens;
	}

}
