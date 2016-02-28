package wsg.generator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import my.project.gop.main.Vector2F;
import wsg.main.Assets;
import wsg.managers.LightSource;

public class Block extends Rectangle {

	private static final long serialVersionUID = 1L;

	Vector2F pos = new Vector2F();
	private int blockSize = 48;
	private BlockType blocktype;
	private BufferedImage blockImage;
	private boolean isSolid;
	private boolean isAlive;
	private boolean dropped = false;
	private boolean shouldDrop = false;

	private float lightLevel;

	// regular
	public Block(Vector2F pos, BlockType blocktype) {
		this.pos = pos;
		this.blocktype = blocktype;

		init();
	}

	// spawn block constructor
	public Block(Vector2F pos) {
		setBounds((int) pos.xpos, (int) pos.ypos, blockSize, blockSize);
		this.pos = pos;
		isAlive = true;
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

		case DIRT_TL:
			blockImage = Assets.getDirt_TL();
			break;

		case DIRT_TR:
			blockImage = Assets.getDirt_TR();
			break;

		case DIRT_BL:
			blockImage = Assets.getDirt_BL();
			break;

		case DIRT_BR:
			blockImage = Assets.getDirt_BR();
			break;

		}// end switch

	}

	public void tick(double deltaTime) {
		if (isAlive) {
			setBounds((int) pos.xpos, (int) pos.ypos, blockSize, blockSize);
		}

	}

	public void renderBlockLightLevel(Graphics2D g) {
		if (isAlive) {

			g.setColor(Color.black);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, lightLevel));
			g.fillRect((int) pos.getWorldLocation().xpos, (int) pos.getWorldLocation().ypos, blockSize, blockSize);

			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			g.setColor(Color.white);
		}

	}

	public void render(Graphics2D g) {
		if (isAlive) {
			if (blockImage != null) {

				g.drawImage(blockImage, (int) pos.getWorldLocation().xpos, (int) pos.getWorldLocation().ypos, blockSize,
						blockSize, null);
				
				
				
				g.setColor(Color.black);

				if(World.currentlyNightTime){
					g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
					g.fillRect((int) pos.getWorldLocation().xpos, (int) pos.getWorldLocation().ypos, blockSize, blockSize);
				}

				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, lightLevel));
				g.fillRect((int) pos.getWorldLocation().xpos, (int) pos.getWorldLocation().ypos, blockSize, blockSize);

				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
				g.setColor(Color.white);

			}
		} else {
			if (!dropped && shouldDrop) {
				float xpos = pos.xpos + 24 - 12;
				float ypos = pos.ypos + 24 - 12;

				Vector2F newPos = new Vector2F(xpos, ypos);

				World.dropBlockEntity(newPos, blockImage);
				dropped = true;
			}
		}

	}

	public void addShadow(float amount) {

		if (lightLevel < 1)
			lightLevel += amount;
		
		if (lightLevel > 1)
			lightLevel = 1;

	}

	public void removeShadow(float amount) {
		
		if (lightLevel > 0.0001)
			lightLevel -= amount;
		
		if (lightLevel < 0)
			lightLevel = 0;
		
	}

	public Vector2F getBlockPos() {
		return pos;
	}

	public float getLightLevel() {
		return lightLevel;
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

	public void setLightLevel(float lightLevel) {
		this.lightLevel = lightLevel;
	}
	
	
	public enum BlockType {
		GRASS_1, DIRT_1, DIRT_TL, DIRT_TR, DIRT_BL, DIRT_BR
	}

}
