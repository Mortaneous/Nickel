/*
 * NickGui.java
 */
 
package com.mortaneous.misc.Nickel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import java.io.*;

public class NickGui extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5159469222782210075L;

	private static final String defaultName = "Nickel Text Editor";
	
	private String appName;
	private String fileName;
	
	private JMenuBar menuBar;
	
	private JMenu fileMenu;
	private JMenuItem fileNewMI;
	private JMenuItem fileOpenMI;
	private JMenuItem fileSaveMI;
	private JMenuItem fileSaveAsMI;
	private JMenuItem fileCloseMI;
	private JMenuItem fileExitMI;

	private JMenu aboutMenu;
	private JMenuItem aboutMI;
	
	private JScrollPane view;
	private JEditorPane editor;
	
	public NickGui()
	{
		this(defaultName);
	}
	
	public NickGui(String name)
	{
		appName = name;
		fileName = "";
		updateTitleBar(fileName);
		
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		InitializeControls();
	}
	
	public void InitializeControls()
	{
		//
		// Menu
		//
		
		// menu bar
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// build the File menu
		fileNewMI = new JMenuItem("New", KeyEvent.VK_N);
		fileNewMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		fileNewMI.addActionListener(this);

		fileOpenMI = new JMenuItem("Open...", KeyEvent.VK_O);
		fileOpenMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		fileOpenMI.addActionListener(this);
		
		fileSaveMI = new JMenuItem("Save", KeyEvent.VK_S);
		fileSaveMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		fileSaveMI.addActionListener(this);
		
		fileSaveAsMI = new JMenuItem("Save As...\t", KeyEvent.VK_A);
		fileSaveAsMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK));
		fileSaveAsMI.addActionListener(this);
		
		fileCloseMI = new JMenuItem("Close", KeyEvent.VK_C);
		fileCloseMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		fileCloseMI.addActionListener(this);

		fileExitMI = new JMenuItem("Exit", KeyEvent.VK_X);
		fileExitMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		fileExitMI.addActionListener(this);
		
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.add(fileNewMI);
		fileMenu.add(fileOpenMI);
		fileMenu.add(fileSaveMI);
		fileMenu.add(fileSaveAsMI);
		fileMenu.add(fileCloseMI);
		fileMenu.addSeparator();
		fileMenu.add(fileExitMI);
		
		menuBar.add(fileMenu);
		
		// build the Help menu
		aboutMI = new JMenuItem("About " + appName + "...", KeyEvent.VK_A);
		aboutMI.addActionListener(this);

		aboutMenu = new JMenu("Help");
		aboutMenu.setMnemonic(KeyEvent.VK_H);
		aboutMenu.add(aboutMI);

		menuBar.add(aboutMenu);
		
		//
		// Main area
		//		
		editor = new JEditorPane();
		editor.setEditable(false);
		
		view = new JScrollPane(editor);
		setContentPane(view);
		
		//createLayout(view);
	}
	
	private void createLayout(JComponent... component)
	{
		Container pane = getContentPane();
		pane.add(component[0], BorderLayout.CENTER);
	}

	private void updateTitleBar(String filename)
	{
		if(filename == null || filename.isEmpty()) {
			setTitle(appName);
		}
		else {
			setTitle(filename + " - " + appName);
		}
	}
	
	//
	// ActionListener interface
	//
	public void actionPerformed(ActionEvent event)
	{
		Object source = event.getSource();
		
		// File -> New
		if(source == fileNewMI) {
			updateTitleBar(fileName = "");
			editor.setText("");
			editor.setEditable(true);
		}
		// File -> Open...
		else if(source == fileOpenMI) {
			JFileChooser fileChooser = new JFileChooser(".");
			if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				
				File file = fileChooser.getSelectedFile();
				try {
					FileInputStream fi = new FileInputStream(file);
					editor.read(fi, file);
					fi.close();
					editor.setEditable(true);
					updateTitleBar(fileName = file.getPath());
				}
				catch(IOException ex) {
					System.out.println("fileOpen() ::: " + ex.getMessage());
				}
			}
		}
		// File - Save
		else if(source == fileSaveMI) {
			if(fileName.isEmpty()) {
				saveFileAs();
			}
			else {
				saveFile(new File(fileName));
			}
		}
		// File - Save As...
		else if(source == fileSaveAsMI) {
			saveFileAs();
		}
		// File - Close
		else if(source == fileCloseMI) {
			editor.setText("");
			editor.setEditable(false);
			updateTitleBar(fileName = "");
		}
		// File - Exit
		else if(source == fileExitMI) {
			System.exit(0);
		}
		// Help -> About...
		else if(source == aboutMI) {
			JDialog dlg = new About(this, appName);
			dlg.setVisible(true);
		}
	}

	private void saveFile(File file)
	{
		if(file != null) {
			try {
				FileOutputStream fo = new FileOutputStream(file);
				fo.write(editor.getText().getBytes());
				fo.close();
				updateTitleBar(fileName + " [saved]");
			}
			catch(Exception ex) {
				updateTitleBar(fileName + " [save failed]");
				System.out.println("saveFile() ::: " + ex.getMessage());
			}
		}
	}
	
	private void saveFileAs()
	{
		String directory;
		
		if(fileName.isEmpty()) {
			directory = ".";
		}
		else {
			directory = fileName.substring(0, fileName.lastIndexOf('\\'));
		}
		//System.out.println("directory = " + directory);
		
		JFileChooser fileChooser = new JFileChooser(directory);
		if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			fileName = file.getPath();

			saveFile(file);
		}
	}
}
