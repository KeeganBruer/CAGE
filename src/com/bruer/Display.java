package com.bruer;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Display {
	private boolean running = false;
	private JFrame frame;
	private Panel textArea;
	private File currentFile;
	private void constructor(String name) {
		this.running = true;
		frame = new JFrame(name);
		frame.setSize(640, 480);
        frame.setJMenuBar(this.menu());
		frame.addWindowListener(this.onCloseOperation());
		frame.addComponentListener(this.onResize());
		textArea = new Panel();
		textArea.setPreferredSize(this.frame.getSize());
		textArea.setLocation(0, 0);
		frame.add(textArea);
	}
	public Display() {
		this.constructor("");
	}
	
	public Display(String name) {
		this.constructor(name);
	}
	
	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}
	
/*******************************************************************************************/	
	private JMenuBar menu() {
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
			JMenuItem openFile = new JMenuItem("Open File");
			openFile.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						JFileChooser fileChooser = new JFileChooser();
						int returnVal = fileChooser.showOpenDialog(frame);
				        if (returnVal == JFileChooser.APPROVE_OPTION) {
				        	currentFile = fileChooser.getSelectedFile();
				        	Scanner scan = new Scanner(currentFile);
				        	textArea.textArea.setText(scan.nextLine());
					       	while (scan.hasNextLine()) {
					       		String line = scan.nextLine();
					       		for (String word : line.split(" ")) {
					       			if (word.equals("import")) {
					       				textArea.appendToPane(word + " ", Color.CYAN);
					       			} else {
					       				textArea.appendToPane(word + " ", Color.BLACK);
					       			}
					       		}
					       		textArea.appendToPane("\n", Color.BLACK);
					       	}
					       	scan.close();
				        }
					} catch (Exception e) {
						
					}
				}
			});
			file.add(openFile);
			JMenuItem saveFile = new JMenuItem("Save File");
			saveFile.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						PrintWriter writer = new PrintWriter(currentFile, "UTF-8");
						String[] array = textArea.textArea.getText().split("\n");
						for (String line : array) {
							writer.println(line);
						}
						writer.flush();
						writer.close();
					} catch (Exception e) {
						
					}
				}
			});
			file.add(saveFile);
			menu.add(file);
			
			JMenu edit = new JMenu("Edit");
			JMenuItem fontSizeIncrease = new JMenuItem("Increase Font Size");
			fontSizeIncrease.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					textArea.changeFontSize(5);
				}
			});
			edit.add(fontSizeIncrease);
			JMenuItem fontSizeDecrease = new JMenuItem("Decrease Font Size");
			fontSizeDecrease.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					textArea.changeFontSize(-5);
				}
			});
			edit.add(fontSizeDecrease);
			menu.add(edit);
			
			
			
			JMenu help = new JMenu("Help");
			JMenuItem propertiesMenu = new JMenuItem("Properties");
			propertiesMenu.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					new PropertiesDisplay();
				}
			});
			help.add(propertiesMenu);
			menu.add(help);
		return menu;
	}
	
	private WindowAdapter onCloseOperation() {
		return new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
            	if (CONFIG.ExitDefault) {
            		running = false;
                    e.getWindow().dispose();
                    System.exit(0);
            	} else {
            		int dialogResult = JOptionPane.showConfirmDialog (frame, "Are You Sure You Want To Exit?","Warning",JOptionPane.YES_NO_OPTION);
	            	if(dialogResult == JOptionPane.YES_OPTION){
	            		running = false;
	                    e.getWindow().dispose();
	                    System.exit(0);
	            	}
            	}
            }
        };
	}
	
	
	private ComponentAdapter onResize() {
		return new ComponentAdapter() 
		{  
		        public void componentResized(ComponentEvent evt) {
		            Component c = (Component)evt.getSource();
		        	textArea.setPreferredSize(c.getSize());
		        }
		};
	}
	
}
