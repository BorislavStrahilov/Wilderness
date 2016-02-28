package wsg.main;

import java.awt.Point;

import wsg.generator.Block;
import wsg.managers.TileManager;

public class Check {
	
	public static boolean CollisionPlayerBlock(Point p1, Point p2){
		for(Block block: TileManager.getBlocks()){
			if(block.isSolid())
				if(block.contains(p1) || block.contains(p2))
					return true;			
		}//end for
		
		return false;
	}
	

}
