package my.java.editor.ast;

import my.java.editor.lexer.Lexer;

public class Node<T extends Lexer.Codes>{
	public final T type;
	public final String name;
	
	public Node<T> parent = null;
	public Node<T> child = null;
	
	public Node(T type,String name) {
		this.type= type;
		this.name = name;
	}
	
	public void setParent(Node<T> parent) {
		this.parent = parent;
	}
	
	public void setChild(Node<T> child) {
		this.child = child;
	}
	
	public T getType() {
		return type;
	}
	
	public Node<T> getParent() {
		return parent;
	}
	
	public Node<T> getChild() {
		return parent;
	}
}