package wsg.generator;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Vector;

import my.project.gop.main.Vector2F;
import wsg.main.Assets;

public class Block extends Rectangle {

	Vector2F pos = new Vector2F();
	private int blockSize = 48;
	private BlockType blocktype;
	private BufferedImage blockImage;
	private boolean isSolid;
	private boolean isAlive;
	private boolean dropped = false;
	private boolean shouldDrop = false;

	public Block(Vector2F pos, BlockType blocktype) {
		this.pos = pos;
		this.blocktype = blocktype;

		init();
	}

	public Block isSolid(boolean isSolid) {
		this.isSolid = isSolid;
		return this;
	}

	public void init() {

		setBounds((int) pos.xpos, (int) pos.ypos, blockSize, blockSize);
		isAlive = true;

		switch (blocktype) {

		case GRASS_1:
			blockImage = Assets.getGrass_1();

			break;
			
		case DIRT_1:
			blockImage = Assets.getDirt_1();
			
			break;

		case STONE_1:
			blockImage = Assets.getStone_1();

			break;
			
		case STONE1_TOP:
			blockImage = Assets.getStone1_top();

			break;
			
		case WOOD_1:
			blockImage = Assets.getWood_1();

			break;

		}// end switch

	}

	public void tick(double deltaTime) {
		if (isAlive) {
			setBounds((int) pos.xpos, (int) pos.ypos, blockSize, blockSize);
		}

	}

	public void render(Graphics2D g) {
		if (isAlive) {
			g.drawImage(blockImage, (int) pos.getWorldLocation().xpos, (int) pos.getWorldLocation().ypos, blockSize,
					blockSize, null);
		}
		else{
			if(!dropped && shouldDrop){
				float xpos = pos.xpos + 24 - 12;
				float ypos = pos.ypos + 24 - 12;
				
				Vector2F newPos = new Vector2F(xpos, ypos);
				
				World.dropBlockEntity(newPos, blockImage);
				dropped = true;
			}
		}

	}

	public boolean isSolid() {
		return isSolid;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public enum BlockType {
		GRASS_1, GRASS_2, 
		DIRT_1, DIRT_2,
		STONE_1, STONE_2,
		STONE1_TOP, 
		WOOD_1
	}

}
