package my.java.editor.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class ArgsSyntaxTree<T> extends SyntaxTree{
	
	private List<Node<T>> children;
	
	public ArgsSyntaxTree() {
		children = new ArrayList<>();
	}
	
	public void addChild(Node<T> arg) {
		children.add(arg);
	}
}
