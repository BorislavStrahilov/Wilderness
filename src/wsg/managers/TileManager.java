package wsg.managers;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import wsg.MoveableObjects.Player;
import wsg.generator.Block;
import wsg.generator.World;

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
			if(block.isAlive()){
				//render block
				block.render(g);
				
				//if block has item, render it
				if(block.getItem() != null)
					block.renderItemImage(g);
				
			}
		}
		

		
	}
	
	public static CopyOnWriteArrayList<Block> getBlocks() { return blocks; } 
	
}
