package wsg.managers;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import my.project.gop.main.Vector2F;
import wsg.generator.Block;

public class LightSource {

	private Vector2F location = new Vector2F();
	private int lightSize;
	private int lightDistanceMultiplier;
	private double lightDistance;
	private Rectangle lightDetection;
	private float distance;

	public LightSource(float xpos, float ypos, int lightSize, int lightDistanceMultiplier) {
		this.location.xpos = xpos;
		this.location.ypos = ypos;
		this.lightSize = lightSize;
		this.lightDistanceMultiplier = lightDistanceMultiplier;
		this.lightDistance = lightSize * lightDistanceMultiplier;
	}

	public void tick() {

		lightDetection = new Rectangle(
				(int) (location.xpos - lightDistance / 2 + lightSize / 2),
				(int) (location.ypos - lightDistance / 2 + lightSize / 2),
				(int) lightDistance, (int) lightDistance);

		for (Block block : TileManager.blocks) {
			if (block != null) {
				if (lightDetection != null) {
					if (lightDetection.intersects(block.getBounds())) {

						distance = (float) Vector2F.getDistanceOnScreen(location, block.getPos());

						
						if(distance > lightDistance){
							block.removeShadow(0.00045f);
						}
						
						for (int i = lightSize; i < lightDistance; i++) {
							if (distance < i) {
								
								
//								if (i == lightSize) {
//									if (block.getLightLevel() > .10) {
//										block.removeShadow(0.15f);
//									}
//								}
								
								
								if (i == lightSize * 2) {
									if (block.getLightLevel() > .10) {
										block.removeShadow(0.1f);
									}
								}
								
								if (i == lightSize * 3) {
									if (block.getLightLevel() > .20) {
										block.removeShadow(0.1f);
									}
								}
								if (i == lightSize * 4) {
									if (block.getLightLevel() > .30) {
										block.removeShadow(0.1f);
									}
								}
								if (i == lightSize * 5) {
									if (block.getLightLevel() > .50) {
										block.removeShadow(0.1f);
									}
								}
								if (i == lightSize * 6) {
									if (block.getLightLevel() > .60) {
										block.removeShadow(0.1f);
									}
								}
								if (i == lightSize * 7) {
									if (block.getLightLevel() > .70) {
										block.removeShadow(0.1f);
									}
								}
								if (i == lightSize * 8) {
									if (block.getLightLevel() > .80) {
										block.removeShadow(0.1f);
									}
								}
								if (i == lightSize * 9) {
									if (block.getLightLevel() > .90) {
										block.removeShadow(0.1f);
									}
								}
								
								
							} // end if

						} // end for
						
						

						

					}
				}
			}

		} // blocks for

	}

	public void render(Graphics2D g) {

//		g.fillRect((int) location.getWorldLocation().xpos, 
//				   (int) location.getWorldLocation().ypos, 
//				   (int) lightSize, (int) lightSize);

	
	}// end render

	public double getLightDistance() {
		return lightDistance;
	}

	public double getLightSize() {
		return lightSize;
	}

	public Vector2F getLocation() {
		return location;
	}

	public Rectangle getLightDetection() {
		return lightDetection;
	}

}
