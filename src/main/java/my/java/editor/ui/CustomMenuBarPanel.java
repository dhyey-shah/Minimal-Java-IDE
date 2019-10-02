package my.java.editor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class CustomMenuBarPanel extends JPanel {

	private final JMenuBar menuBar;
	private final JMenu menu;
	private final JMenuItem saveItem, openItem;
	private final SaveAction saveAction;
	private final OpenAction openAction;
	
	static File savedLocationFile;

	public CustomMenuBarPanel(TextEditorPanel editorPanel) {
		super(new BorderLayout());
		menuBar = new JMenuBar();

		menu = new JMenu("File");

		saveItem = new JMenuItem("Save");
		saveAction = new SaveAction(editorPanel);

		openItem = new JMenuItem("Open");
		openAction = new OpenAction(editorPanel);
		
		init();
	}

	private void init() {
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.setBackground(Color.WHITE);
		
		menu.add(openItem);
		openItem.addActionListener(openAction);
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		
		menu.add(saveItem);
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveItem.addActionListener(saveAction);

		menuBar.add(menu);

		add(menuBar, BorderLayout.NORTH);
	}
}

class SaveAction implements ActionListener {
	private JFileChooser saveLocation;
	private FileWriter fileWriter;
	private TextEditorPanel editorPanel;

	public SaveAction(TextEditorPanel editorPanel) {
		// TODO Auto-generated constructor stub
		saveLocation = new JFileChooser();

		this.editorPanel = editorPanel;
	}

	public void saveDialogShowEvent() {
		int action = saveLocation.showSaveDialog(null);

		if (action == saveLocation.APPROVE_OPTION) {
			CustomMenuBarPanel.savedLocationFile = saveLocation.getSelectedFile();
			writeFile(CustomMenuBarPanel.savedLocationFile, editorPanel.getEditorPane().getText());
		}
	}

	public void writeFile(File file, String text) {

		try {
			fileWriter = new FileWriter(file);
			fileWriter.write(text);
			fileWriter.close();
		} catch (IOException ee) {
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (CustomMenuBarPanel.savedLocationFile == null)
			saveDialogShowEvent();
		else
			writeFile(CustomMenuBarPanel.savedLocationFile, editorPanel.getEditorPane().getText());
	}
}

class OpenAction implements ActionListener {
	private JFileChooser openLocation;
	private TextEditorPanel editorPanel;

	public OpenAction(TextEditorPanel editorPanel) {
		openLocation = new JFileChooser();
		this.editorPanel = editorPanel;
	}

	public void openDialogShowEvent() {
		int action = openLocation.showOpenDialog(null);

		if (action == openLocation.APPROVE_OPTION) {
			CustomMenuBarPanel.savedLocationFile = openLocation.getSelectedFile();
			editorPanel.getEditorPane().setText(readFile(CustomMenuBarPanel.savedLocationFile));
		}
	}

	public String readFile(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			return sb.toString();
		} catch (IOException ee) {
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		openDialogShowEvent();
	}
}