package wsg.main;

import java.awt.image.BufferedImage;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;

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
	public static BufferedImage dirt_2;
	public static BufferedImage stone_1;
	public static BufferedImage stone1_top;
	public static BufferedImage wood_1;
	
	
	
	public void init(){
		blocks.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "spritesheet.png"));
		player.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "playersheet-big.png"));
		
		HUD_bot = loadImageFrom.LoadImageFrom(Main.class, "HUD_bot.jpg");

		mouse = player.getTile(352, 0, 16, 16);
		mousePressed = player.getTile(368, 0, 16, 16);
		
		button_unpressed = player.getTile(352, 16, 32, 16);
		button_pressed = player.getTile(352, 32, 32, 16);
		
		
		grass_1 = blocks.getTile(0, 0, tileWidth, tileHeight);
		dirt_1 = blocks.getTile(0, 32, tileWidth, tileHeight);
		dirt_1 = blocks.getTile(0, 32, tileWidth, tileHeight);
		stone_1 = blocks.getTile(0, 64, tileWidth, tileHeight);
		stone1_top = blocks.getTile(0,96, tileWidth, tileHeight);
		wood_1 = blocks.getTile(0, 128, tileWidth, tileHeight);
		
	}
	
	public static BufferedImage getGrass_1() { return grass_1; }
	public static BufferedImage getDirt_1()  { return dirt_1;  }
	public static BufferedImage getDirt_2()  { return dirt_2;  }
	public static BufferedImage getStone_1() { return stone_1; }
	public static BufferedImage getStone1_top() { return stone1_top; }
	public static BufferedImage getWood_1() { return wood_1; }
	public static BufferedImage getMouse() { return mouse; }
	public static BufferedImage getMousePressed() { return mousePressed; }
	public static BufferedImage getButtonUnpressed() { return button_unpressed; }
	public static BufferedImage getButtonPressed() { return button_pressed; }
	
}
