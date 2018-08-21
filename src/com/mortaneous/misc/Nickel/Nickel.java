/*
 * Nickel.java
 */
 
package com.mortaneous.misc.Nickel;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Nickel
{
	public void run()
	{
		EventQueue.invokeLater( () -> {
			JFrame gui = new NickGui();
			gui.setVisible(true);
		});
	}
	
	public static void main(String[] args)
	{
		Nickel nickel = new Nickel();
		nickel.run();
	}
}