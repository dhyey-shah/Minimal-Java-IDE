package my.java.editor.compiler.ast;

public class BinaryOpAST implements OpAST {
	private String op;
	
	public BinaryOpAST(String op) {
		this.op = op;
	}
	
	@Override
	public String getOP() {
		return op;
	}
}
