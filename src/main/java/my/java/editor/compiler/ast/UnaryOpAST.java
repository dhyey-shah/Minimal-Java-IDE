package my.java.editor.compiler.ast;

public class UnaryOpAST implements OpAST {
	private String op;
	
	public UnaryOpAST(String op) {
		this.op = op;
	}
	
	@Override
	public String getOP() {
		return op;
	}
}
