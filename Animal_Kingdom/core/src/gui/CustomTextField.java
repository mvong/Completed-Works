package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// taken from https://lazicbrano.wordpress.com/2013/08/01/jtextfield-placeholder/

public class CustomTextField extends JTextField {

	private Font originalFont;
	private Color originalForeground;
	private Color placeholderForeground = new Color(160, 160, 160);
	private boolean textWrittenIn = false;
	
	public CustomTextField(int columns) {
		super(columns);
	}
	
	public void setFont(Font f) { 
		super.setFont(f);
		if (!isTextWrittenIn()) {
			originalFont = f;
		}
	}
	
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (isTextWrittenIn()) {
			originalForeground = fg;
		}
	}
	
	public Color getPlaceholderForeground() {
		return placeholderForeground;
	}
	
	public void setPlaceholderForeground(Color placeholderForeground) {
		this.placeholderForeground = placeholderForeground;
	}
	
	public boolean isTextWrittenIn() {
		return textWrittenIn;
	}
	
	public void setTextWrittenIn(boolean textWrittenIn) {
		this.textWrittenIn = textWrittenIn;
	}
	
	public void setPlaceholder(final String text) {
		this.customizeText(text);
		
		this.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {
				warn();
				
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				warn();
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				warn();
				
			}
			
			public void warn() {
				if (getText().trim().length() != 0) {
					setFont(originalFont);
					setForeground(originalForeground);
					setTextWrittenIn(true);
				}
			}
		
		});
		
		this.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (!isTextWrittenIn()) {
					setText("");
				}
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (getText().trim().length() == 0) {
					customizeText(text);
				}
			}
		});
	}
	
	private void customizeText(String text) {
		setText(text);
		setFont(new Font(getFont().getFamily(), Font.PLAIN, getFont().getSize()));
		setForeground(getPlaceholderForeground());
		setTextWrittenIn(false);
	}
	
	
}
