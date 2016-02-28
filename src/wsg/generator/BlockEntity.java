package wsg.generator;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import wsg.generator.World;
import my.project.gop.main.Vector2F;

public class BlockEntity extends Rectangle {
	
	private static final long serialVersionUID = 1L;

	private Vector2F pos;
	private BufferedImage blockImage;
	private double rotation;
	private double rotationSpeed = 0.8;
	private double blockSize = 24;
	private boolean isAlive;
	private int lifeTime = 300;
	private boolean isDying;
	private float lifeFade = 1;

	public BlockEntity(Vector2F pos, BufferedImage blockImage) {
		this.pos = pos;
		this.blockImage = blockImage;
		rotation = new Random().nextInt(180);
		isAlive = true;
		setBounds((int) pos.xpos, (int) pos.ypos, (int) blockSize, (int) blockSize);
	}

	public void tick(double deltaTime) {

		if (isAlive) {
			setBounds((int) pos.xpos, (int) pos.ypos, (int) blockSize, (int) blockSize);
			rotation -= rotationSpeed;
			
			if(!isDying){
				if(lifeTime != 0){
					lifeTime--;
				}
				if(lifeTime == 0){
					isDying = true;
				}
			}
		}//end if isAlive
		
		if(isDying){
			if(lifeFade != 0.00001){
				lifeFade-= 0.01;
			}
			
			blockSize -= 0.15;
			pos.xpos += 0.075;
			pos.ypos += 0.075;
			
			if(lifeFade <= 0.00001){
				World.removeDroppedEntity(this);
				isAlive = false;
			}
		}
	}

	public void render(Graphics2D g) {
		
		
		if(isDying){
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, lifeFade));
		}
		
		if(isAlive){
			
			g.rotate(Math.toRadians(rotation), pos.getWorldLocation().xpos + blockSize / 2,
					pos.getWorldLocation().ypos + blockSize / 2);
			// ######################################################
	
			
			
			
			// image
			g.drawImage(blockImage, (int) pos.getWorldLocation().xpos, (int) pos.getWorldLocation().ypos, (int) blockSize,
					(int) blockSize, null);
	
			// outline
			g.drawRect((int) pos.getWorldLocation().xpos, (int) pos.getWorldLocation().ypos, (int) blockSize,
					(int) blockSize);
	
		
			
			
			// ######################################################
			g.rotate(-Math.toRadians(rotation), pos.getWorldLocation().xpos + blockSize / 2,
					pos.getWorldLocation().ypos + blockSize / 2);
			
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));


		}//end if its alive
	}//end render

	
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
}
