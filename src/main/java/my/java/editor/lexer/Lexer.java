package my.java.editor.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer{

//	private String word;

	private final List<String> keywords;
	private final List<String> primitives;
	
	public Lexer() {
		keywords = new ArrayList<>();
		initKeywords();
		
		primitives = new ArrayList<>();
		initPrimitives();
	}
	
	public static enum Codes {
		KEYWORDS,PRIMITIVE, SEMI, EQU, IDENTIFIER, OPERATORS, NUMERIC;
	}
	
	public static class Token<T> {
		public final T t;
		public final String value;

		private int s,e;
		
		public Token(T t, String value) {
			this.t = t;
			this.value = value;
		}
		
		public Token(T t,String value,int s,int e) {
			this.t = t;
			this.value = value;
			this.s = s;
			this.e = e;
		}

		protected void setIndex(int s,int e) {
			this.s = s;
			this.e = e;
		}
		
		public int getStartIndex() {
			return s;
		}
		
		public int getEndIndex() {
			return e;
		}
		
		@Override
		public String toString() {
			return t.toString()+" "+String.valueOf(s);
		}
	}

	public void initKeywords() {
		keywords.add("abstract");
		keywords.add("public");
		keywords.add("private");
		keywords.add("protected");
		keywords.add("final");
	}
	
	public void initPrimitives() {
		primitives.add("int");
		primitives.add("float");
		primitives.add("double");
	}
	
/*	public String getIdentifier(String word,int index) {
		int j=index;
		for( ; j < word.length(); ) {
            if(Character.isLetter(word.charAt(j))) {
                j++;
            } else {
                return word.substring(index, j);
            }
        }
        return word.substring(index, j);
	}*/
	
	public Token<Codes> createToken(Codes c,String val){
		return new Token<Codes>(c,val);
	}
	
	public Token<Codes> createToken(Codes c,String val,int s,int e){
		return new Token<Codes>(c,val,s,e);
	}
	
	public List<Token<Codes>> generateTokens(String input) {
		List<Token<Codes>> tokens = new ArrayList<>();
		String identifier="";
		String numVal="";
		
		for(int i=0;i<input.length();i++) {
			Character currChar = input.charAt(i);
			
			switch(currChar) {
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
				if(Character.isLetter(currChar)) {
					int startIndex=i,endIndex;
					//System.out.println(i);
					while(i<input.length() && Character.isLetter(input.charAt(i))) {
						identifier+=input.charAt(i++);
					}
					
					if(keywords.contains(identifier)) {
						endIndex = i;
						tokens.add(createToken(Codes.KEYWORDS,identifier,startIndex,endIndex));
					}
					else if(primitives.contains(identifier)) {
						endIndex = i;
						tokens.add(createToken(Codes.PRIMITIVE,identifier,startIndex,endIndex));
						
						
					}
					else {
						endIndex = i;
						tokens.add(createToken(Codes.IDENTIFIER,identifier,startIndex,endIndex));
					}
					
					identifier = "";
					i--;
				}
				else if(Character.isDigit(currChar)) {
					while( i<input.length() && Character.isDigit(input.charAt(i))) {
						numVal+=input.charAt(i++);
					}
					i--;
					tokens.add(createToken(Codes.NUMERIC,numVal));
				}
			}
		}
		
		return tokens;
	}

}
