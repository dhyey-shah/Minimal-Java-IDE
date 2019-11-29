package my.java.editor.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import my.java.editor.ui.listeners.DocumentListenerEvent;

public class TextEditorPanel extends JPanel implements LayoutFactory {

	private JTextPane editorPane1, linePane;
	private CustomTextPane editorPane;
	private JScrollPane editorScrollPane, lineScrollPane;
	
	private Element root;
	private StyledDocument lineDoc;
	private int lineCount = 1;
	
	public TextEditorPanel() {
		super(new BorderLayout());

		editorPane = new CustomTextPane();
		// editorPane = configEditorPane();
		linePane = configLinePane();

		editorScrollPane = new JScrollPane(editorPane);
		lineScrollPane = new JScrollPane(linePane);

		configEditorScrollPane();
		configLineScrollPane();

		add(lineScrollPane, BorderLayout.WEST);
		add(editorScrollPane, BorderLayout.CENTER);

		editorPane.getDocument().addDocumentListener(new DocumentListenerEvent(this));
		
		
		//Line Number
		root = editorPane.getDocument().getDefaultRootElement();
		lineDoc = linePane.getStyledDocument();

		try {
			lineDoc.insertString(0, "  1\n", null);
		} catch (BadLocationException ee) {
		}
		
	}

	public CustomTextPane getEditorPane() {
		return editorPane;
	}

	private JTextPane configEditorPane() {
		JTextPane editor = new JTextPane();

		Consumer<StyledDocument> styles = doc -> {
			Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

			Style regular = doc.addStyle("regular", def);
			StyleConstants.setFontFamily(def, "SansSerif");

			Style s = doc.addStyle("italic", regular);
			StyleConstants.setItalic(s, true);

			s = doc.addStyle("bold", regular);
			StyleConstants.setBold(s, true);

			s = doc.addStyle("small", regular);
			StyleConstants.setFontSize(s, 10);

			s = doc.addStyle("large", regular);
			StyleConstants.setFontSize(s, 16);
		};

		StyledDocument doc = editor.getStyledDocument();
		styles.accept(doc);

		return editor;
	}

	private JTextPane configLinePane() {
		JTextPane editor = new JTextPane();
		editor.setEditable(false);
		
		
		Consumer<StyledDocument> styles = doc -> {
			Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

			Style regular = doc.addStyle("regular", def);
			StyleConstants.setFontFamily(def, "SansSerif");

			Style s = doc.addStyle("italic", regular);
			StyleConstants.setItalic(s, true);

			s = doc.addStyle("bold", regular);
			StyleConstants.setBold(s, true);

			s = doc.addStyle("small", regular);
			StyleConstants.setFontSize(s, 10);

			s = doc.addStyle("large", regular);
			StyleConstants.setFontSize(s, 16);
		};

		StyledDocument doc = editor.getStyledDocument();
		styles.accept(doc);

		return editor;
	}

	private void configEditorScrollPane() {
		editorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	}

	private void configLineScrollPane() {
		Dimension d = lineScrollPane.getSize();
		d.height = editorScrollPane.getHeight();
		d.width = 40;
		lineScrollPane.setPreferredSize(d);

		lineScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		lineScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		lineScrollPane.getVerticalScrollBar().setModel(editorScrollPane.getVerticalScrollBar().getModel());

	}

	private class LineNumberUpdate {
		boolean cmp;
		int start;
		String data;

		public LineNumberUpdate(boolean cmp, int start, String data) {
			// TODO Auto-generated constructor stub
			this.cmp = cmp;
			this.start = start;
			this.data = data;
		}
	}

	public class LineNumber extends SwingWorker<LineNumberUpdate, Void> {

	/*	private Element root;
		private StyledDocument lineDoc;*/

		

	/*	public LineNumber() {
			root = TextEditorPanel.this.editorPane.getDocument().getDefaultRootElement();
			lineDoc = TextEditorPanel.this.linePane.getStyledDocument();

			try {
				lineDoc.insertString(0, "  1\n", null);
			} catch (BadLocationException ee) {
			}
		}*/

		public LineNumberUpdate doInBackground() {

			int currCount = root.getElementCount();

			if (lineCount != currCount) {
				/*if (lineCount < currCount) {
					lineCount = currCount;
					return new LineNumberUpdate(true, lineDoc.getLength(), "  " + String.valueOf(lineCount) + "\n");

				} else {*/
					lineCount = currCount;

					String num = "";

					for (int i = 1; i <= lineCount; i++) {
						num += "  ";
						num += String.valueOf(i) + "\n";
					}

					return new LineNumberUpdate(false, 0, num);

					/*
					 * try { lineDoc.remove(0, lineDoc.getLength()); lineDoc.insertString(0, num,
					 * null); } catch(BadLocationException ee) {}
					 */
				
			}

			return null;
		}

		@Override
		public void done() {
			
			try {
				LineNumberUpdate up = get();
				if (up != null) {
					if (!up.cmp) {
						lineDoc.remove(0, lineDoc.getLength());
					}
					lineDoc.insertString(up.start, up.data, null);
					linePane.setCaretPosition(editorPane.getCaretPosition());
				}
			} 
			catch(IllegalArgumentException e) {
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
