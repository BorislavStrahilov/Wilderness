package wsg.managers;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import wsg.main.Assets;

public class Mousemanager implements MouseListener, MouseWheelListener, MouseMotionListener {

	private static int mouseMovedX, mouseMovedY;
	public static Point mouse;
	public static boolean pressed;
	
	
	public void tick(){
		mouse = new Point(mouseMovedX, mouseMovedY);
		
	}
	
	public void render(Graphics2D g){
		
		
		if(pressed){
			g.drawImage(Assets.getMousePressed(), mouseMovedX-22, mouseMovedY-22, 48, 48, null);
		}else{
			g.drawImage(Assets.getMouse(), mouseMovedX-22, mouseMovedY-22, 48, 48, null);
		}
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			pressed = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			pressed = false;
		
		
	}

	

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMovedX = e.getX();
		mouseMovedY = e.getY();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseMovedX = e.getX();
		mouseMovedY = e.getY();
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }
	
	
}
