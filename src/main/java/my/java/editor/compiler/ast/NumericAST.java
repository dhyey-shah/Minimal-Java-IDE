package my.java.editor.compiler.ast;

public class NumericAST<T extends OpAST> extends ExprAST {
	private T op;
	private AST leftChild;
	private AST rightChild;

	public NumericAST(AST leftChild, T op, AST rightChild) {
		this.leftChild = leftChild;
		this.op = op;
		this.rightChild = rightChild;
	}

	public NumericAST(AST node, T op, boolean isLeftChild) {
		this.op = op;

		if (isLeftChild) {
			this.leftChild = node;
			this.rightChild = null;
		} else {
			this.rightChild = node;
			this.leftChild = null;
		}
	}

	public AST getLeftChild() {
		return leftChild;
	}

	public AST getRightChild() {
		return rightChild;
	}

	public T getOp() {
		return op;
	}
}
