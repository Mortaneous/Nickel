/*
 * About.java
 */
package com.mortaneous.Nickel;

import javax.swing.*;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.LayoutStyle.ComponentPlacement;

public class About extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2154261421600826751L;

	private JLabel info;
	
	private JButton closeButton;
	
	public About(JFrame owner, String name)
	{
		super(owner, "About " + name, true);
		setSize(300,200);
		setLocationRelativeTo(owner);
		setResizable(false);
		
		initializeControls(name);
		createLayout();
	}
	
	public void initializeControls(String appName)
	{
		info = new JLabel(getAboutText(appName));
		
		closeButton = new JButton("Close");
		//closeButton.addActionListener((ActionEvent) -> { setVisible(false); });
		closeButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setVisible(false);
			}
		});
	}
	
	public void createLayout()
	{
		Container pane = getContentPane();
		GroupLayout layout = new GroupLayout(pane);
		pane.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		GroupLayout.SequentialGroup horzGroup = layout.createSequentialGroup();
		horzGroup.addComponent(info)
				 .addPreferredGap(ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				 .addComponent(closeButton);
		layout.setHorizontalGroup(horzGroup);

		GroupLayout.SequentialGroup vertGroup = layout.createSequentialGroup();
		vertGroup.addComponent(info)
				 .addPreferredGap(ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				 .addComponent(closeButton);
		layout.setVerticalGroup(vertGroup);

		// GroupLayout.SequentialGroup horzGroup = layout.createSequentialGroup();
		// horzGroup.addGroup(layout.createParallelGroup()
										// .addComponent(info)
										// .addComponent(description)
										// .addComponent(closeButton)
						   // );
		// layout.setHorizontalGroup(horzGroup);

		// GroupLayout.SequentialGroup vertGroup = layout.createSequentialGroup();
		// vertGroup.addComponent(info)
				 // .addComponent(description)
				 // .addGroup(layout.createParallelGroup()
					// .addPreferredGap(ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					// .addComponent(closeButton));
		layout.setVerticalGroup(vertGroup);
	}
	
	public String getAboutText(String appName)
	{
		String text = "<html>" + 
			appName + "<br>" +
			"Version 1.0<br>" +
			"<br>" + 
			"This is my first text editor<br>" +
			"Written in Java<br>";
			
		return text;
	}
}