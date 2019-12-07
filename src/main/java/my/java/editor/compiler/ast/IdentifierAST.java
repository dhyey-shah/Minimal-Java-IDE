package my.java.editor.compiler.ast;

public class IdentifierAST implements AST{
	private String identifier;
	
	public IdentifierAST(String identifier) {
		this.identifier = identifier;
	}
	
	public String getIdentifier() {
		return identifier;
	}
}
