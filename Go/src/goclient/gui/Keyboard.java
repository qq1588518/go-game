package goclient.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener 
{
	DrawingManager manager;
	
	public Keyboard(DrawingManager manager) 
	{
		this.manager = manager;
	}
	
	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) 
	{ 
		if(e.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			manager.changeMode(DrawingMode.GROUPMODE);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			manager.changeMode(DrawingMode.SINGLEMODE);
		}
	}

}
