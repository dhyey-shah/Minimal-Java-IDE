package my.java.editor.compiler.parser;

import java.util.ArrayList;
import java.util.List;

import my.java.editor.compiler.ast.AST;
import my.java.editor.compiler.ast.BinaryOpAST;
import my.java.editor.compiler.ast.ExprAST;
import my.java.editor.compiler.ast.IdentifierAST;
import my.java.editor.compiler.ast.NumericAST;
import my.java.editor.compiler.ast.OpAST;
import my.java.editor.compiler.ast.TestingExprAST;
import my.java.editor.compiler.ast.UnaryOpAST;
import my.java.editor.compiler.lexer.Lexer;

/**
 * @author Dhyey
 *
 */
public class Parser {

	private List<Lexer.Token<Lexer.CODES>> tokenStream;
	private List<AST> rootNodes;
	private Lexer.Token<Lexer.CODES> token;
	private int currTokenNum = -1;

	public Parser() {
		this(null);
	}

	public Parser(List<Lexer.Token<Lexer.CODES>> tokenStream) {
		if (tokenStream != null) {
			setTokenStream(tokenStream);
		}
		rootNodes = new ArrayList<>();
	}

	public void setTokenStream(List<Lexer.Token<Lexer.CODES>> tokenStream) {
		this.tokenStream = tokenStream;
	}

	private Lexer.Token<Lexer.CODES> getNextToken() {
		if (currTokenNum != tokenStream.size() - 1)
			return tokenStream.get(++currTokenNum);
		else
			return null;
	}

	public String syntaxError(String error) {
		System.out.println(error);
		return null;
	}

	public boolean checkSemi() {
		try {
			token = getNextToken();
			if (token.getCodes() == Lexer.CODES.SEMI)
				return true;
			else {
				syntaxError("Semicolon expected");
				return false;
			}
		} catch (NullPointerException e) {
			syntaxError("Semicolon expected");
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	<T extends AST> T parseExpr() {
		Lexer.CODES codes = token.getCodes();
		if (codes == Lexer.CODES.BOOLEANS) { // Logical expr

		} else if (codes == Lexer.CODES.BIN_OP) { // Numeric Expr

		} else if (codes == Lexer.CODES.TILDE) { // BIT EXPR

		} else if (codes == Lexer.CODES.SP_KEYWORDDS) { // EXPR
			if (token.getValue() == "null") {
				return (T) new ExprAST(ExprAST.VALUE.NULL);
			} else if (token.getValue() == "super") {
				return (T) new ExprAST(ExprAST.VALUE.SUPER);
			} else if (token.getValue() == "this") {
				return (T) new ExprAST(ExprAST.VALUE.THIS);
			} else {
				ExprAST a = new ExprAST(ExprAST.VALUE.INSTANCEOF);

				// TO BE ADDED
			}
		} else if (codes == Lexer.CODES.BOOLEANS) {
			if (token.getValue() == "true") {
				return (T) new ExprAST(ExprAST.VALUE.TRUE);
			} else {
				return (T) new ExprAST(ExprAST.VALUE.FALSE);
			}
		} else if (codes == Lexer.CODES.LPAREN) { // EXPR
			token = getNextToken();
			ExprAST expr = parseExpr();
			if (expr == null) {
				return null;
			} else {
				token = getNextToken();
				if (token.getCodes() != Lexer.CODES.RPAREN) {
					syntaxError("')' Expected " + token.getValue() + "given");
					expr = null;
					return null;
				} else {
					return (T) expr;
				}
			}
		} else if (codes == Lexer.CODES.IDENTIFIER) { // EXPR
			return (T) new IdentifierAST(token.getValue());
		}

		return null;
	}

	/*
	 * Grammer: numeric_expression ::= ( ( "-" | "++" | "--" ) expression ) | (
	 * expression ( "++" | "--" ) ) | ( expression ( "+" | "+=" | "-" | "-=" | "*" |
	 * "*=" | "/" | "/=" | "%" | "%=" ) expression ) 
	 * 
	 * return NumericAST; }
	 */

	NumericAST<? extends OpAST> parseNumericExpr() {
		OpAST op;
		if (token.getCodes() == Lexer.CODES.UNARY_OP) {
			op = new UnaryOpAST(token.getValue());

			token = getNextToken();
			AST expr = parseExpr();
			if (expr == null) {
				op = null;
				return null;
			} else {
				if (checkSemi()) {
					System.out.println("UNARY AST CREATED");
					return new NumericAST<OpAST>(expr, op, false);
				}
				else {
					return null;
				}
			}
		} else {
			AST expr = parseExpr();
			if (expr == null) {
				return null;
			} else {
				token = getNextToken();
				if (token.getCodes() == Lexer.CODES.UNARY_OP) {
					op = new UnaryOpAST(token.getValue());
					return new NumericAST<OpAST>(expr, op, true);
				} else if (token.getCodes() == Lexer.CODES.BIN_OP) {
					op = new BinaryOpAST(token.getValue());
					token = getNextToken();

					AST expr2 = parseExpr();
					if (expr2 == null) {
						op = null;
						expr = null;
						return null;
					} else {
						if (checkSemi()) {
							System.out.println("BINARY AST CREATED");
							return new NumericAST<OpAST>(expr, op, expr2);
						}
						else {
							return null;
						}

					}
				} else {
					expr = null;
					return null;
				}
			}
		}
	}

	public void parse() {
		token = getNextToken();
		AST ast;
		while (token != null) {
			if (token.getCodes() == Lexer.CODES.UNARY_OP) {
				ast = parseNumericExpr();
			} else if (token.getCodes() == Lexer.CODES.SEMI) {
				token = getNextToken();

			} else {
				ast = parseNumericExpr();
				
			}
			token = getNextToken();
		}

		System.out.println(rootNodes);
	}

}
