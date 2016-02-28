package wsg.main;

import java.awt.image.BufferedImage;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;
import resources.test;

public class Assets {
	
	SpriteSheet blocks = new SpriteSheet();
	public static SpriteSheet player = new SpriteSheet();
	private int tileWidth = 32;
	private int tileHeight = 32;
	
	
	//HUD BOTTOM
	public static BufferedImage HUD_bot;
	
	//MOUSE
	public static BufferedImage mouse;
	public static BufferedImage mousePressed;
	
	//BUTTONS
	public static BufferedImage button_unpressed;
	public static BufferedImage button_pressed;

	
	//TILES
	public static BufferedImage grass_1;
	public static BufferedImage dirt_1;
	public static BufferedImage dirt_TL;
	public static BufferedImage dirt_TR;
	public static BufferedImage dirt_BL;
	public static BufferedImage dirt_BR;

	
	
	
	public void init(){
		blocks.setSpriteSheet(loadImageFrom.LoadImageFrom(test.class, "spritesheet.png"));
		player.setSpriteSheet(loadImageFrom.LoadImageFrom(test.class, "playersheet.png"));
		
		HUD_bot = loadImageFrom.LoadImageFrom(test.class, "HUD_bot.jpg");

		mouse = player.getTile(352, 0, 16, 16);
		mousePressed = player.getTile(368, 0, 16, 16);
		
		button_unpressed = player.getTile(352, 16, 32, 16);
		button_pressed = player.getTile(352, 32, 32, 16);
		
		
		grass_1 = blocks.getTile(0, 0, tileWidth, tileHeight);
		dirt_1 = blocks.getTile(0, 32, tileWidth, tileHeight);
		dirt_TL = blocks.getTile(32, 32, tileWidth, tileHeight);
		dirt_TR = blocks.getTile(64, 32, tileWidth, tileHeight);
		dirt_BL = blocks.getTile(96, 32, tileWidth, tileHeight);
		dirt_BR = blocks.getTile(128, 32, tileWidth, tileHeight);



		
	}
	
	public static BufferedImage getGrass_1()  { return grass_1; }
	public static BufferedImage getDirt_1()   { return dirt_1;  }
	public static BufferedImage getDirt_TL()  { return dirt_TL; }
	public static BufferedImage getDirt_TR()  { return dirt_TR; }
	public static BufferedImage getDirt_BL()  { return dirt_BL; }
	public static BufferedImage getDirt_BR()  { return dirt_BR; }

	
	public static BufferedImage getMouse() { return mouse; }
	public static BufferedImage getMousePressed() { return mousePressed; }
	
	public static BufferedImage getButtonUnpressed() { return button_unpressed; }
	public static BufferedImage getButtonPressed() { return button_pressed; }


	
}
