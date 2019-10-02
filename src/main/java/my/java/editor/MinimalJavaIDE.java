package my.java.editor;

import javax.swing.SwingUtilities;

import my.java.editor.ui.Layout;

public class MinimalJavaIDE {

	private Layout layout;
	
	public MinimalJavaIDE() {
		
		
		SwingUtilities.invokeLater(()->
		createUI());
		
	}
	
	public void createUI() {
		layout = new Layout();
		layout.addComponents();
		
	}
	
	public static void main(String[] args) {
		new MinimalJavaIDE();
	}

}
