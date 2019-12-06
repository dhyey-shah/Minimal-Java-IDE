package my.java.editor.compiler.ast;

public class CallSyntaxTree<T> extends SyntaxTree  {
	private Node<T> callee;
	private ArgsSyntaxTree<T> argsListNode;
	
	public CallSyntaxTree(Node<T> callee,ArgsSyntaxTree<T> argsListNode){
		this.callee = callee;
		this.argsListNode = argsListNode;
	}
}
