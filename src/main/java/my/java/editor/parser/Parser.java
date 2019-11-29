package my.java.editor.parser;

import java.util.ArrayList;
import java.util.List;

import my.java.editor.ast.DeclarationSyntaxTree;
import my.java.editor.ast.Node;
import my.java.editor.ast.SyntaxTree;
import my.java.editor.lexer.Lexer;

public class Parser<T extends Lexer.Token<Lexer.Codes>> {

	private List<T> tokenStream;
	private List<SyntaxTree> rootNodes;
	private Lexer.Token<Lexer.Codes> token;
	private int currTokenNum;

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

	public SyntaxTree parseDeclaration() {
		currTokenNum = -1;
		token = getNextToken();
		if (token == null)
			return null;
		Node<Lexer.Codes> type = parseType();
		if (type == null || token == null)
			return null;

		Node<Lexer.Codes> identifier = parseIdentifier();
		if (identifier == null || token == null) {
			syntaxError("Expected Identifier");
			return null;
		}

		Node<Lexer.Codes> semi = parseSemi();
		if (semi == null) {
			syntaxError("Expected ';'");
			return null;
		}

		// System.out.println("Hello");
		return new DeclarationSyntaxTree<>("Declaration", type, identifier);
	}

	public Node<Lexer.Codes> parseType() {
		Node<Lexer.Codes> node;
		if (token.getCodes() == Lexer.Codes.PRIMITIVE) {
			node = new Node<Lexer.Codes>(token.getCodes(), token.getValue());
			token = getNextToken();
			return node;
		} else if (token.getCodes() == Lexer.Codes.IDENTIFIER) {
			node = new Node<Lexer.Codes>(token.getCodes(), token.getValue());
			token = getNextToken();
			return node;
		}
		return null;

	}

	public Node<Lexer.Codes> parseIdentifier() {
		Node<Lexer.Codes> node;
		if (token == null)
			System.out.println("Null TOken");
		else if (token.getCodes() == Lexer.Codes.IDENTIFIER) {
			node = new Node<Lexer.Codes>(token.getCodes(), token.getValue());
			token = getNextToken();
			return node;
		}
		return null;
	}

	public Node<Lexer.Codes> parseSemi() {
		Node<Lexer.Codes> node;
		if (token.getCodes() == Lexer.Codes.SEMI) {
			node = new Node<Lexer.Codes>(token.getCodes(), token.getValue());
			token = getNextToken();
			return node;
		}
		return null;
	}

	public void parse() {

		List<Node<Lexer.Codes>> newNodeList = new ArrayList<>();

		if (tokenStream == null) {
			System.out.println("Null Token Stream");
		} else {

			SyntaxTree s = parseDeclaration();
			System.out.println(s);

		}
	}
}
