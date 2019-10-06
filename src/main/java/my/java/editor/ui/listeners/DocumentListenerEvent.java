package my.java.editor.ui.listeners;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

import my.java.editor.lexer.Lexer;
import my.java.editor.lexer.TokenParser;
import my.java.editor.ui.CustomTextPane;
import my.java.editor.ui.TextEditorPanel;

public class DocumentListenerEvent implements DocumentListener {

	private CustomTextPane editorPane;
	private TextEditorPanel.LineNumber lineNumber;
	private Lexer lexer;
	private TokenParser token;
	private List<Lexer.Token<Lexer.Codes>> tokenStream;
	private StyledDocument styledDoc;
	private Document doc;

	public DocumentListenerEvent(TextEditorPanel editorPanel) {
		editorPane = editorPanel.getEditorPane();

		lineNumber = editorPanel.new LineNumber();

		token = new TokenParser();
		lexer = new Lexer(false);
		
		List<ArrayList<String>> list = token.parseTokens();
		
		lexer.setKeywords(list.get(0));
		lexer.setPrimitives(list.get(1));
		
		styledDoc = editorPane.getStyledDocument();
		doc = editorPane.getDocument();
	}

	public void documentEventHandler() {

		lineNumber.changeDetected();
		try {
			tokenStream = lexer.generateTokens(doc.getText(0, doc.getLength()));
		} catch (BadLocationException ee) {}
		
		for (Lexer.Token<Lexer.Codes> token : tokenStream) {
			int s = token.getStartIndex();
			int e = token.getEndIndex();
			SwingUtilities.invokeLater(()->{
				try {
	
					if (token.t == Lexer.Codes.PRIMITIVE || token.t == Lexer.Codes.KEYWORDS) {
						styledDoc.setCharacterAttributes(s, e - s, styledDoc.getStyle("keyword"), true);
	
					}
	
					else if (token.t == Lexer.Codes.IDENTIFIER) {
						styledDoc.setCharacterAttributes(s, e - s, styledDoc.getStyle("regular"), true);
					}
	
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			});
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		documentEventHandler();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		documentEventHandler();
	}
}
