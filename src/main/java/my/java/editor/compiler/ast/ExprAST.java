package my.java.editor.compiler.ast;

public class ExprAST implements AST {
	
	private VALUE value;
	
	public static enum VALUE{
		NULL,THIS,SUPER,INSTANCEOF,TRUE,FALSE;
	}
	
	/**
	 * Blank Constructor for sub-classes
	 */
	public ExprAST() {
	}

	/**
	 * Accept special keywords :
	 * <li>{@code null}</li>
	 * <li>{@code super}</li>
	 * <li>{@code this}</li>
	 * 
	 * @param value
	 */
	public ExprAST(VALUE value) {
		this.value = value;
	}
	
	public VALUE getValue() {
		return value;
	}
}
