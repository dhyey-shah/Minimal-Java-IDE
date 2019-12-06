package my.java.editor.ui.listeners;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

import my.java.editor.compiler.lexer.Lexer;
import my.java.editor.compiler.lexer.TokenParser;
import my.java.editor.compiler.parser.Parser;
import my.java.editor.ui.CustomTextPane;
import my.java.editor.ui.TextEditorPanel;

public class DocumentListenerEvent implements DocumentListener {

	private CustomTextPane editorPane;
	private TextEditorPanel.LineNumber lineNumber;
	private Lexer lexer;
	private Parser parser;
	private TokenParser token;
	private List<Lexer.Token<Lexer.Codes>> tokenStream;
	private StyledDocument styledDoc;
	private Document doc;
//	private UpdateUI updateUI;
	
	private TextEditorPanel editorPanel;

	public DocumentListenerEvent(TextEditorPanel editorPanel) {
		this.editorPanel = editorPanel;
		
		editorPane = editorPanel.getEditorPane();


		token = new TokenParser();
		lexer = new Lexer();
		parser = new Parser();

		List<ArrayList<String>> list = token.parseTokens();

		lexer.setKeywords(list.get(0));
		lexer.setPrimitives(list.get(1));
		lexer.setOperators(list.get(2));

		styledDoc = editorPane.getStyledDocument();
		doc = editorPane.getDocument();
		
//		updateUI = new UpdateUI();
	}

	public List<Lexer.Token<Lexer.Codes>> getTokenStram() {
		return tokenStream;
	}

	public void documentEventHandler() {

		editorPanel.new LineNumber().execute(); // To be included in UpdateUI

		UpdateUI updateUI = new UpdateUI();
		updateUI.execute();

	}

// Creating thread for bg process and synchronizing it with EDT for UI updates
	private static class UpdateDocument {
		private final int s, e;
		private final Style style;
		private boolean val;

		UpdateDocument(int s, int e, Style style, boolean val) {
			this.s = s;
			this.e = e;
			this.style = style;
			this.val = val;
		}
	}

	private class UpdateUI extends SwingWorker<UpdateDocument, List<UpdateDocument>> {
		UpdateDocument updateDocument;
		List<UpdateDocument> updateDocumentList = new ArrayList<>();
		
		@Override
		protected UpdateDocument doInBackground() throws Exception {
			try {
				tokenStream = lexer.generateTokens(doc.getText(0, doc.getLength()));
				if (tokenStream == null)
					return null;
			} catch (BadLocationException ee) {
			}

			for (Lexer.Token<Lexer.Codes> token : tokenStream) {
				int s = token.getStartIndex();
				int e = token.getEndIndex();

				try {

					if (token.t == Lexer.Codes.PRIMITIVE || token.t == Lexer.Codes.KEYWORDS) {
						updateDocumentList.add(new UpdateDocument(s, e - s, styledDoc.getStyle("keyword"), true));
						//System.out.println(updateDocument.s+" "+updateDocument.e);

					}

					else if (token.t == Lexer.Codes.IDENTIFIER) {
						updateDocumentList.add(new UpdateDocument(s, e - s, styledDoc.getStyle("regular"), true));
					}

				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
			
			
			publish(updateDocumentList);
			
			parser.setTokenStream(tokenStream);
			parser.parse();
			
			return updateDocument;
		}

		@Override
		protected void process(List<List<UpdateDocument>> updateDocumentList) {
			//System.out.println(tokenStream);
			List<UpdateDocument> lastUpdate = updateDocumentList.get(updateDocumentList.size()-1);
			try {
				for(UpdateDocument docAttribute: lastUpdate) {
					//docAttribute = updateDocument.get(updateDocument.size()-1);
				//System.out.println(docAttribute.s+" "+docAttribute.e);
				if (!(docAttribute == null))
					styledDoc.setCharacterAttributes(docAttribute.s, docAttribute.e, docAttribute.style,
							docAttribute.val);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void initParser() {
		Runnable runnable = ()->{
			
		};
		
		Thread t = new Thread(runnable);
		t.start();
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
