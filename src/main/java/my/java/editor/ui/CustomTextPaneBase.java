package my.java.editor.ui;

import java.awt.Color;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

import javax.swing.JTextPane;
import javax.swing.event.CaretListener;
import javax.swing.text.Element;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public abstract class CustomTextPaneBase extends JTextPane implements HighlightPainter, CaretListener, MouseListener{

	private boolean highLightCurrentLine;
	private boolean addDefaultStyles;
	
	private final Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
	private Style regular;
	
	private final static Color keywordColor = new Color(186, 45, 94);
	
	public CustomTextPaneBase() {
		// No-args constructor
		init();
	}

	public CustomTextPaneBase(StyledDocument doc) {
		super(doc);
		init();
	}

	protected abstract void addCustomCaretListener();
	protected abstract void addCustomMouseListener();
	
	public void addStyleToDoc(StyledDocument styleDoc) {
		Consumer<StyledDocument> styles = doc -> {
			
			regular = doc.addStyle("regular", defaultStyle);
			StyleConstants.setFontFamily(defaultStyle, "SansSerif");
			
	        Style s = doc.addStyle("final", regular);
	        StyleConstants.setItalic(s, true);
			StyleConstants.setForeground(s, Color.BLUE);
	        
	        s = doc.addStyle("keyword", regular);
	        StyleConstants.setForeground(s, keywordColor);
	        StyleConstants.setBold(s, true);
	 
	        s = doc.addStyle("large", regular);
	        StyleConstants.setFontSize(s, 16);
		};
		
		styles.accept(styleDoc);
	}
	
	public int getCaretLineNumber() {
		int pos = getCaretPosition();
		Element map = getDocument().getDefaultRootElement();
		return map.getElementIndex(pos);
	}

	public int getCaretOffsetFromLineStart() {
		int pos = getCaretPosition();
		return pos - getLineStartOffset(getCaretLineNumber());
	}

	public int getLineCount() {
		Element map = getDocument().getDefaultRootElement();
		return map.getElementCount();
	}
	
	public int getLineEndOffset(int line) {
		Element map = getDocument().getDefaultRootElement();
        Element lineElem = map.getElement(line);
        int endOffset = lineElem.getEndOffset();
        return ((line == map.getElementCount() - 1) ? (endOffset - 1) : endOffset);
	}
	
	public int getLineStartOffset(int line) {
		Element map = getDocument().getDefaultRootElement();
		return map.getElement(line).getStartOffset();
	}
	
	public boolean getAddDefaultStyles() {
		return addDefaultStyles;
	}
	
	public boolean getHighLightCurrentLine() {
		return highLightCurrentLine;
	}
	
	public StyledDocument getCurrentStyledDocument() {
		return getStyledDocument();
	}
	
	public void setAddDefaultStyles(boolean state) {
		addDefaultStyles = state;
	}
	
	public void setHighLightCurrentLine(boolean state) {
		highLightCurrentLine = state;
	}
	
	public void init() {
		//Defaults for various properties
		setHighLightCurrentLine(true);
		setAddDefaultStyles(true);
		
		//Setting Default Style
		addStyleToDoc(getCurrentStyledDocument());
		
	}
}
