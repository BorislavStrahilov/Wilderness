package wsg.managers;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import wsg.MoveableObjects.Player;
import wsg.generator.Block;

public class TileManager {

	public static CopyOnWriteArrayList<Block> blocks;
	
	
	public TileManager() {
		blocks = new CopyOnWriteArrayList<Block>();
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
	
	public static CopyOnWriteArrayList<Block> getBlocks() { return blocks; } 
	
}
