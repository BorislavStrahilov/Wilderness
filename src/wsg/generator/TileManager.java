package wsg.generator;

import java.awt.Graphics2D;
import java.util.ArrayList;

import wsg.MoveableObjects.Player;

public class TileManager {

	public static ArrayList<Block> blocks;
	
	
	public TileManager() {
		blocks = new ArrayList<Block>();
	}
	
	
	
	public void tick(double deltaTime){
		
		for(Block block: blocks){
			block.tick(deltaTime);
			

			
			if(Player.render.intersects(block)){
				block.setAlive(true);
			}
			else{
				block.setAlive(false);
			}
		}
		
		
	}

	public void render(Graphics2D g){
		for(Block block: blocks){
			block.render(g);
		}
	}
	
	public static ArrayList<Block> getBlocks() { return blocks; } 
	
}
