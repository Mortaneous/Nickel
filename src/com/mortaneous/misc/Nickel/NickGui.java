/*
 * NickGui.java
 */
 
package com.mortaneous.misc.Nickel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NickGui extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5159469222782210075L;

	private static final String defaultName = "Nickel Text Editor";
	
	private String appName;
	
	private JMenuBar menuBar;
	
	private JMenu fileMenu;
	private JMenuItem fileNewMI;
	private JMenuItem fileOpenMI;
	private JMenuItem fileExitMI;

	private JMenu aboutMenu;
	private JMenuItem aboutMI;
	
	public NickGui()
	{
		this(defaultName);
	}
	
	public NickGui(String name)
	{
		setTitle(appName = name);
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

		fileExitMI = new JMenuItem("Exit", KeyEvent.VK_X);
		fileExitMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		fileExitMI.addActionListener(this);
		
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.add(fileNewMI);
		fileMenu.add(fileOpenMI);
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
	}

	//
	// ActionListener interface
	//
	public void actionPerformed(ActionEvent event)
	{
		Object source = event.getSource();
		if(source == fileExitMI) {
			System.exit(0);
		}
		else if(source == aboutMI) {
			JDialog dlg = new About(this, appName);
			dlg.setVisible(true);
		}
	}
}