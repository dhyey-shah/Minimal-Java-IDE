package my.java.editor.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Layout extends JFrame
					implements LayoutFactory{
	
	private TextEditorPanel editorPanel;
	private CustomMenuBarPanel menuBarPanel;
	
	public Layout() {
		super("Minimal Java IDE");
		
		editorPanel = new TextEditorPanel();

		menuBarPanel = new CustomMenuBarPanel(editorPanel);
		
		addCustomWindowListener();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		setVisible(true);
	}
	
	public void addComponents() {
		add(menuBarPanel, BorderLayout.NORTH);
		add(editorPanel,BorderLayout.CENTER);
	}
	
	public void addCustomWindowListener() {
		addWindowListener(new WindowAdapter() {
			public void windowOpened( WindowEvent e) {
				editorPanel.getEditorPane().requestFocus();
			}
		});
	}
	
}
