package wsg.generator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;
import wsg.MoveableObjects.Player;
import wsg.generator.Block.BlockType;
import wsg.main.Main;

public class Map {

	public static BufferedImage map = null;
	
	public World world1;

	public Map() {
	
	}

	public void init(){
		
		try{
			map = loadImageFrom.LoadImageFrom(Main.class, "map.png");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		world1 = new World("world1", map, 100, 100);
		world1.generateWorld();
		
	}
	
	public void tick(double deltaTime){
		world1.tick(deltaTime);
		
	}
	
	
	public void render(Graphics2D g){
		world1.render(g);
		
	}
	
}
