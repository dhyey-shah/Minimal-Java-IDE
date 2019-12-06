package my.java.editor.compiler.parser;

import java.util.ArrayList;
import java.util.List;

import my.java.editor.compiler.ast.ExprSyntaxTree;
import my.java.editor.compiler.ast.Node;
import my.java.editor.compiler.ast.NumericExprSyntaxTree;
import my.java.editor.compiler.ast.SyntaxTree;
import my.java.editor.compiler.lexer.Lexer;

/**
 * @author Dhyey
 *
 * @param <T>
 */
public class Parser<T extends Lexer.Token<Lexer.Codes>> {

	private List<T> tokenStream;
	private List<SyntaxTree> rootNodes;
	private Lexer.Token<Lexer.Codes> token;
	private int currTokenNum = -1;

	public Parser() {
		this(null);
	}

	public Parser(List<T> tokenStream) {
		if (tokenStream != null) {
			setTokenStream(tokenStream);
		}
		rootNodes = new ArrayList<>();
	}

	public void setTokenStream(List<T> tokenStream) {
		this.tokenStream = tokenStream;
	}

	private T getNextToken() {
		if (currTokenNum != tokenStream.size() - 1)
			return tokenStream.get(++currTokenNum);
		else
			return null;
	}

	public Node<Lexer.Codes> createNode(Lexer.Codes c, String v) {
		return new Node<Lexer.Codes>(c.PRIMITIVE, v);
	}

	public String syntaxError(String error) {
		System.out.println(error);
		return null;
	}

	/*
	 * SyntaxTree parseNumericExpr() { SyntaxTree tree; Lexer.Token<Lexer.Codes>
	 * tokenTemp = token; if (token.getCodes() == Lexer.Codes.OPERATORS) { tree =
	 * parseExpr(); if (tree == null) { return null; } return new
	 * NumericExprSyntaxTree<>(new
	 * Node<Lexer.Codes>(tokenTemp.getCodes(),tokenTemp.getValue()), tree); } else
	 * if((tree = parseExpr()) == null) { return null; } else { token =
	 * getNextToken(); if(token.getCodes() == Lexer.Codes.OPERATORS) {
	 * 
	 * } }
	 * 
	 * 
	 * return null; }
	 * 
	 * SyntaxTree parseExpr() { token = getNextToken();
	 * 
	 * return null; }
	 * 
	 *//**
		 * ARGLIST := EXPRESSION { "," EXPRESSION }
		 * 
		 * @return Node
		 */

	/*
	 * SyntaxTree parseArgExpr() {
	 * 
	 * token = getNextToken();
	 * 
	 * if (token.getCodes() != Lexer.Codes.RPAREN) { ArgsSyntaxTree<Lexer.Codes>
	 * argsRoot = new ArgsSyntaxTree<>(); while (true) { token = getNextToken(); if
	 * (token.getCodes() == Lexer.Codes.RPAREN) {
	 * 
	 * return argsRoot; } else if (token.getCodes() == Lexer.Codes.IDENTIFIER) {
	 * argsRoot.addChild(new Node<Lexer.Codes>(token.getCodes(), token.getValue()));
	 * } else if (token.getCodes() == Lexer.Codes.COMMA) { token = getNextToken(); }
	 * else { syntaxError("Unexpected token: " + token.getCodes()); argsRoot = null;
	 * // Garbage Collection break; } } }
	 * 
	 * return null; }
	 * 
	 *//**
		 * IDENTIFIER := "a..z,$,_" { "a..z,$,_,0..9" }
		 * 
		 * called by
		 * 
		 * @return SyntaxTree
		 * 
		 *//*
			 * SyntaxTree parseIdentifierExpr() { Lexer.Token<Lexer.Codes> tokenTemp =
			 * token;
			 * 
			 * token = getNextToken(); Lexer.Codes code = token.getCodes();
			 * 
			 * SyntaxTree tree;
			 * 
			 * if (code == Lexer.Codes.LPAREN) { tree = parseArgExpr(); if (tree == null)
			 * return null; if ((token = getNextToken()).getCodes() != Lexer.Codes.SEMI)
			 * return null;
			 * 
			 * tree = new CallSyntaxTree<>(new Node<Lexer.Codes>(tokenTemp.getCodes(),
			 * tokenTemp.getValue()), (ArgsSyntaxTree<Lexer.Codes>) tree); return tree; }
			 * else if (code == Lexer.Codes.OPERATORS) {
			 * 
			 * } return null; }
			 */

	ExprSyntaxTree<Lexer.Codes> parseExpr() {
		token = getNextToken();

		Lexer.Codes codes = token.getCodes();

		if (codes == Lexer.Codes.BOOLEANS) { // Logical expr

		} else if (codes == Lexer.Codes.OPERATORS) { // Numeric Expr

		} else if (codes == Lexer.Codes.LOGICAL_NOT) { // LOGICAL Expr

		} else if (codes == Lexer.Codes.TILDE) { // BIT EXPR

		} else if (codes == Lexer.Codes.SP_KEYWORDDS) { // EXPR
			
		}

		return null;
	}

	NumericExprSyntaxTree<Lexer.Codes> parseNumericExpr() {
		token = getNextToken();

		if (token.getCodes() == Lexer.Codes.OPERATORS) {
			Node<Lexer.Codes> a = createNode(token.getCodes(), token.getValue());
			ExprSyntaxTree<Lexer.Codes> b = parseExpr();
			if (b == null) {
				a = null;
				return null;
			} else
				return new NumericExprSyntaxTree<>(a, b);
		} else {
			ExprSyntaxTree<Lexer.Codes> a = parseExpr();
			if (a == null)
				return null;
			else {
				NumericExprSyntaxTree<Lexer.Codes> b = parseNumericExpr();
				if (b == null) {
					a = null;
					return null;
				} else
					return new NumericExprSyntaxTree<>(a, b);
			}
		}
	}

	public void parse() {
		token = getNextToken();

		while (token != null) {
			if (token.getCodes() == Lexer.Codes.OPERATORS) {

			} else if (token.getCodes() == Lexer.Codes.SEMI) {
				token = getNextToken();
			}

		}

		System.out.println(rootNodes);
	}

}
