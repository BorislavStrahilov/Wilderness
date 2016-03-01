package wsg.managers;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import inventory.Item;
import wsg.main.Assets;
import wsg.main.Main;

public class Mousemanager implements MouseListener, MouseWheelListener, MouseMotionListener {

	private static int mouseMovedX, mouseMovedY;
	public static Point mouse;
	public static boolean pressed;

	public static BufferedImage unpressedImage = Assets.getMouse();
	public static BufferedImage pressedImage = Assets.getMousePressed();
	public static BufferedImage defaultUnpressedImage = Assets.getMouse();
	public static BufferedImage defaultPressedImage = Assets.getMousePressed();
	
	public static Item itemHeld;
	
	
	public void tick(){
		mouse = new Point(mouseMovedX, mouseMovedY);
		
		
		//because this loads before assets are loaded
		if(unpressedImage == null){
			unpressedImage = Assets.getMouse();
		}
		if(pressedImage == null){
			pressedImage = Assets.getMousePressed();
		}
		
		//because this loads before assets are loaded
		if(defaultUnpressedImage == null){
			defaultUnpressedImage = Assets.getMouse();
		}
		if(defaultPressedImage == null){
			defaultPressedImage = Assets.getMousePressed();
		}
		
	}
	
	public void render(Graphics2D g){
		
		
		if(pressed){
			g.drawImage(defaultPressedImage, mouseMovedX-22, mouseMovedY-22, 48, 48, null);
		}else{
			g.drawImage(defaultUnpressedImage, mouseMovedX-22, mouseMovedY-22, 48, 48, null);
		}
		
	}
	
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			pressed = true;
		}
		
	}

	
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			pressed = false;
		
		
	}

	

	public void mouseDragged(MouseEvent e) {
		mouseMovedX = e.getX();
		mouseMovedY = e.getY();
		
	}

	public void mouseMoved(MouseEvent e) {
		mouseMovedX = e.getX();
		mouseMovedY = e.getY();
		
	}

	public void mouseWheelMoved(MouseWheelEvent e) {}
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	
	
}
