package inventory;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InventoryMouseManager implements MouseListener{
	
	public static boolean leftClicked;
	public static boolean rightClicked;

	public InventoryMouseManager() {

	}
	
	
	
	
	public void mousePressed(MouseEvent e) {
		
		//left click
		if(e.getButton() == MouseEvent.BUTTON1){
			leftClicked = true;
			rightClicked = false;
		}
		
		//right click
		if(e.getButton() == MouseEvent.BUTTON3){
			leftClicked = false;
			rightClicked = true;
		}
		
	}

	
	public void mouseReleased(MouseEvent e) {
		//left click
		if(e.getButton() == MouseEvent.BUTTON1){
			leftClicked = false;
		}
		
		//right click
		if(e.getButton() == MouseEvent.BUTTON3){
			rightClicked = false;
		}
		
	}
	

	
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}



}
