package my.java.editor.compiler.ast;

public class NumericExprSyntaxTree<T> extends SyntaxTree {
	private ExprSyntaxTree<T> expr1;
	private Node<T> operator;
	private NumericExprSyntaxTree<T> expr2;
	
	public NumericExprSyntaxTree(Node<T> operator,ExprSyntaxTree<T> expr) {
		this.operator = operator;
		this.expr1 = expr;
	}
	
	public NumericExprSyntaxTree(ExprSyntaxTree<T> expr1,NumericExprSyntaxTree<T> expr2) {
		this.expr1= expr1;
		this.expr2 = expr2;
	}
	
	public void getNumericExpression() {
	}
}
