package my.java.editor.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.event.CaretEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

public class CustomTextPane extends CustomTextPaneBase {

	private Rectangle2D lastView;

	private final Color defaultHighLightColor = new Color(185, 205, 237);  //Blue shade

	public CustomTextPane() {
		try {
			getHighlighter().addHighlight(0, 0, this);
		} catch (BadLocationException ble) {
		}
		
		addCustomCaretListener();
		addCustomMouseListener();
	}
	
	protected void addCustomCaretListener() {
		addCaretListener(this);
	}

	protected void addCustomMouseListener() {
		addMouseListener(this);
	}
	
	@Override
	public void caretUpdate(CaretEvent e) {
		resetHighLight();
	}
	
	public void mousePressed(MouseEvent e) {
		resetHighLight();
	}
	
	public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c) {
		try {
			Rectangle2D r = modelToView2D(getCaretPosition());
			g.setColor(setLighter(defaultHighLightColor));
			g.fillRect(0, (int) r.getY(), getWidth(), (int) r.getHeight());

			if (lastView == null)
				lastView = r;
		} catch (BadLocationException ble) {
			System.out.println(ble);
		}
	}
	
	public void resetHighLight() {
		try {
			int offset = getCaretPosition();
			Rectangle2D currentView = modelToView2D(offset);

			if (lastView.getY() != currentView.getY()) {
				repaint();
				lastView = currentView;
			}
		} catch (BadLocationException ble) {
		}
	}

	public Color setLighter(Color color) {
		int red = Math.min(255, (int) (color.getRed() * 1.2));
		int green = Math.min(255, (int) (color.getGreen() * 1.2));
		int blue = Math.min(255, (int) (color.getBlue() * 1.2));
		return new Color(red, green, blue);
	}

	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
}
