package com.bruer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.text.AttributeSet;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class Panel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JScrollPane scrollFrame;
	public JTextPane textArea;
	private int fontSize = 15;
	public Panel() {
		super();
		this.setLayout(null);
		textArea = new JTextPane(); 
		textArea.setLocation(0, 0);
		textArea.setFont(new Font(textArea.getFont().getFontName(), Font.PLAIN, fontSize));
		scrollFrame = new JScrollPane();
		scrollFrame.setLocation(0, 0);
		scrollFrame.getViewport().add(textArea);
		this.add(scrollFrame);
	}
	public void appendToPane(String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        javax.swing.text.AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len);
        textArea.setCharacterAttributes(aset, false);
        textArea.replaceSelection(msg);
    }
	public void changeFontSize(int change) {
		this.fontSize += change;
		System.out.println(this.fontSize);
		textArea.setFont(new Font(textArea.getFont().getFontName(), Font.PLAIN, fontSize));
	}
	
	public void setPreferredSize(Dimension s) {
		super.setPreferredSize(s);
		this.setSize(s);
		textArea.setSize(s);
		scrollFrame.setSize((int)(s.getWidth()-20), (int)(s.getHeight()-70));
	}
}
