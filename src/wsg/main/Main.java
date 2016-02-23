package wsg.main;

import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;

import my.project.gop.main.GameWindow;
import my.project.gop.main.IDGameLoop;
import my.project.gop.main.SpriteSheet;
import wsg.MoveableObjects.Player;
import wsg.gameLoop.GameLoop;
import wsg.managers.Mousemanager;

public class Main {
	
	public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	
	/*
	public static int width = gd.getDisplayMode().getWidth();
	public static int height = gd.getDisplayMode().getHeight();
	*/
	public static int width = 1280;
	public static int height = 720;
	
	
	static SpriteSheet blocks = new SpriteSheet();

	public static void main(String[] args) {
		GameWindow frame = new GameWindow("Wilderness", width, height);
		frame.setFullscreen(0);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor cursor = toolkit.createCustomCursor(toolkit.getImage(""), new Point(0,0), "Cursor");
		frame.setCursor(cursor);
		
		frame.addMouseListener(new Mousemanager());
		frame.addMouseMotionListener(new Mousemanager());
		frame.addMouseWheelListener(new Mousemanager());
		
		frame.addKeyListener(new Player());
		frame.add(new GameLoop(width, height));
		frame.setVisible(true);
		
	}

}
