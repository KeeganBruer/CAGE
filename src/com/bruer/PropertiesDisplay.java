package com.bruer;

import javax.swing.JFrame;

public class PropertiesDisplay {
	private JFrame frame;
	public PropertiesDisplay() {
		this.constructor("Properties");
	}
	
	private void constructor(String name) {
		frame = new JFrame(name);
		frame.setSize(640, 480);
		frame.setVisible(true);
	}
}
