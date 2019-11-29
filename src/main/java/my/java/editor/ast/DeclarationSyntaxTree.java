package my.java.editor.ast;

import my.java.editor.lexer.Lexer;

public class DeclarationSyntaxTree<T extends Lexer.Codes> extends SyntaxTree {
	
	private final String GOAL;
	private Node<T> typeNode;
	private Node<T> varNameNode;
	
	public DeclarationSyntaxTree(String goal,Node<T> typeNode,Node<T> varNameNode) {
		this.GOAL = goal;
		this.typeNode = typeNode;
		this.varNameNode = varNameNode;
	}
}
