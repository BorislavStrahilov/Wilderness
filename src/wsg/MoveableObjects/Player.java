package wsg.MoveableObjects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import wsg.generator.World;
import my.project.gop.main.Vector2F;
import wsg.gameLoop.GameLoop;
import wsg.main.Animator;
import wsg.main.Assets;
import wsg.main.Check;
import wsg.main.Main;
import wsg.managers.GUImanager;
import wsg.managers.HUDmanager;
import wsg.managers.Mousemanager;

public class Player implements KeyListener {

	static Vector2F pos;
	private static World world;


	private static int width = 32;
	private static int height = 32;
	private int scale = 2;

	public static int energy;
	public static int hunger;
	public static int temperature;

	public static int energyTimer;
	public static int hungerTimer;
	public static int temperatureTimer;

	public static int energyTimeStart;
	public static int hungerTimeStart;
	public static int temperatureTimeStart;

	private static boolean up, down, left, right, running;
	private static boolean moving;
	private static boolean hasSpawned = false;
	
	private float maxSpeed = 150;
	private float fixedDt = 1F / 60F;

	private float speedUp = 0;
	private float speedDown = 0;
	private float speedLeft = 0;
	private float speedRight = 0;

	private float slowdown = 24F;
	private float speedupSpeed = 24F;
	private static long ani_speed = 180;

	private Mousemanager playerMM = new Mousemanager();

	// RENDERING
	private static int renderDistanceW = 41; // 60
	private static int renderDistanceH = 19; // 34
	public static Rectangle render;

	private float moveAmountU;
	private float moveAmountD;
	private float moveAmountL;
	private float moveAmountR;

	private int animationState = 0;
	private static ArrayList<BufferedImage> listUp;
	private static Animator ani_up;
	private static ArrayList<BufferedImage> listDown;
	private static Animator ani_down;
	private static ArrayList<BufferedImage> listLeft;
	private static Animator ani_left;
	private static ArrayList<BufferedImage> listRight;
	private static Animator ani_right;
	private static ArrayList<BufferedImage> listIdleUp;
	private static Animator ani_idleUp;
	private static ArrayList<BufferedImage> listIdleDown;
	private static Animator ani_idleDown;
	private static ArrayList<BufferedImage> listIdleLeft;
	private static Animator ani_idleLeft;
	private static ArrayList<BufferedImage> listIdleRight;
	private static Animator ani_idleRight;

	private static int spriteWidth = 16;
	private static int spriteHeight = 16;
	private double aniStart, aniTime;

	/*
	 * 0 - up 1 - down 2 - right 3 - left 4 - idle up 5 - idle down 6 - idle
	 * left 7 - idle right
	 */

	private static HUDmanager hud;
	private static GUImanager gui;

	public Player() {
		

		
		pos = new Vector2F((Main.width / 2) - (width / 2), (Main.height / 2) - ((Main.height / 5) / 2) - (height / 2));

	}

	public void init(World givenWorld) {
		
		world = givenWorld;
		
		hud = new HUDmanager(world);
		gui = new GUImanager();

		render = new Rectangle(
				(int) (pos.xpos - pos.getWorldLocation().xpos + pos.xpos - renderDistanceW * 32 / 2 + width / 2),
				(int) (pos.ypos - pos.getWorldLocation().ypos + pos.ypos - renderDistanceH * 32 / 2 + height / 2),
				renderDistanceW * 32, renderDistanceH * 32);

		listUp = new ArrayList<BufferedImage>();
		listDown = new ArrayList<BufferedImage>();
		listLeft = new ArrayList<BufferedImage>();
		listRight = new ArrayList<BufferedImage>();
		listIdleUp = new ArrayList<BufferedImage>();
		listIdleDown = new ArrayList<BufferedImage>();
		listIdleLeft = new ArrayList<BufferedImage>();
		listIdleRight = new ArrayList<BufferedImage>();

		listUp.add(Assets.player.getTile(0, 0, spriteWidth, spriteHeight));
		listUp.add(Assets.player.getTile(16, 0, spriteWidth, spriteHeight));
		listUp.add(Assets.player.getTile(32, 0, spriteWidth, spriteHeight));
		listUp.add(Assets.player.getTile(48, 0, spriteWidth, spriteHeight));

		listDown.add(Assets.player.getTile(64, 0, spriteWidth, spriteHeight));
		listDown.add(Assets.player.getTile(80, 0, spriteWidth, spriteHeight));
		listDown.add(Assets.player.getTile(96, 0, spriteWidth, spriteHeight));
		listDown.add(Assets.player.getTile(112, 0, spriteWidth, spriteHeight));

		listLeft.add(Assets.player.getTile(0, 16, spriteWidth, spriteHeight));
		listLeft.add(Assets.player.getTile(16, 16, spriteWidth, spriteHeight));
		listLeft.add(Assets.player.getTile(32, 16, spriteWidth, spriteHeight));
		listLeft.add(Assets.player.getTile(48, 16, spriteWidth, spriteHeight));

		listRight.add(Assets.player.getTile(64, 16, spriteWidth, spriteHeight));
		listRight.add(Assets.player.getTile(80, 16, spriteWidth, spriteHeight));
		listRight.add(Assets.player.getTile(96, 16, spriteWidth, spriteHeight));
		listRight.add(Assets.player.getTile(112, 16, spriteWidth, spriteHeight));

		// IDLE
		listIdleUp.add(Assets.player.getTile(0, 0, spriteWidth, spriteHeight));
		listIdleDown.add(Assets.player.getTile(64, 0, spriteWidth, spriteHeight));
		listIdleLeft.add(Assets.player.getTile(24, 96, spriteWidth, spriteHeight));
		listIdleRight.add(Assets.player.getTile(64, 16, spriteWidth, spriteHeight));

		// UP
		ani_up = new Animator(listUp);
		ani_up.setSpeed(ani_speed);
		ani_up.play();

		// DOWN
		ani_down = new Animator(listDown);
		ani_down.setSpeed(ani_speed);
		ani_down.play();

		// LEFT
		ani_left = new Animator(listLeft);
		ani_left.setSpeed(ani_speed);
		ani_left.play();

		// RIGHT
		ani_right = new Animator(listRight);
		ani_right.setSpeed(ani_speed);
		ani_right.play();

		// IDLE UP
		ani_idleUp = new Animator(listIdleUp);
		ani_idleUp.setSpeed(ani_speed);
		ani_idleUp.play();

		// IDLE DOWN
		ani_idleDown = new Animator(listIdleDown);
		ani_idleDown.setSpeed(ani_speed);
		ani_idleDown.play();

		// IDLE LEFT
		ani_idleLeft = new Animator(listIdleLeft);
		ani_idleLeft.setSpeed(ani_speed);
		ani_idleLeft.play();

		// IDLE RIGHT
		ani_idleRight = new Animator(listIdleRight);
		ani_idleRight.setSpeed(ani_speed);
		ani_idleRight.play();

		hunger = 0;
		energy = 6000;
		temperature = 4200;

		hungerTimeStart = (int) System.nanoTime() / 1000000000;
		energyTimeStart = (int) System.nanoTime() / 1000000000;
		temperatureTimeStart = (int) System.nanoTime() / 1000000000;
		
		hasSpawned = true;

	}

	public void tick(double deltaTime) {

		aniTime = (System.nanoTime() / 100000000);

		hungerTimer = (int) (System.nanoTime() / 1000000000) - hungerTimeStart;
		energyTimer = (int) (System.nanoTime() / 1000000000) - energyTimeStart;
		temperatureTimer = (int) (System.nanoTime() / 1000000000) - temperatureTimeStart;

		if (hungerTimer % 1 == 0)
			hunger++;
		if (energyTimer % 1 == 0)
			energy--;
		if (temperatureTimer % 1 == 0)
			temperature--;

		playerMM.tick();

		render = new Rectangle(
				(int) (pos.xpos - pos.getWorldLocation().xpos + pos.xpos - renderDistanceW * 32 / 2 + width / 2),
				(int) (pos.ypos - pos.getWorldLocation().ypos + pos.ypos - renderDistanceH * 39 / 2 + height * 2 + 5),
				renderDistanceW * 32, renderDistanceH * 32);

		moveAmountU = (float) (speedUp * fixedDt);
		moveAmountD = (float) (speedDown * fixedDt);
		moveAmountL = (float) (speedLeft * fixedDt);
		moveAmountR = (float) (speedRight * fixedDt);

		if (up) {
			moveMapUp(moveAmountU);
			animationState = 0;
		} else {
			moveMapUpGlide(moveAmountU);
		}

		if (down) {
			moveMapDown(moveAmountD);
			animationState = 1;
		} else {
			moveMapDownGlide(moveAmountD);
		}

		if (left) {
			moveMapLeft(moveAmountL);
			animationState = 2;
		} else {
			moveMapLeftGlide(moveAmountL);
		}

		if (right) {
			moveMapRight(moveAmountR);
			animationState = 3;
		} else {
			moveMapRightGlide(moveAmountR);
		}

		// standing still
		if (!up && !down && !right && !left && (aniTime % 3 == 0)) {
			animationState = 4;
			
			if(moving)
				moving = false;
			
		}

		if (running && animationState != 4) {
			if (ani_speed != 100) {
				ani_speed = 100;
				ani_up.setSpeed(ani_speed);
				ani_down.setSpeed(ani_speed);
				ani_left.setSpeed(ani_speed);
				ani_right.setSpeed(ani_speed);
				maxSpeed = 230;
			}
		} else {
			if (ani_speed != 180) {
				ani_speed = 180;
				ani_up.setSpeed(ani_speed);
				ani_down.setSpeed(ani_speed);
				ani_left.setSpeed(ani_speed);
				ani_right.setSpeed(ani_speed);
				maxSpeed = 150;
			}
		}
		
	}// end tick

	public void render(Graphics2D g) {

		// going up
		if (animationState == 0) {
			g.drawImage(ani_up.sprite, (int) pos.xpos - width / scale, (int) pos.ypos - height, width * scale,
					height * scale, null);

			if (up)
				ani_up.update(System.currentTimeMillis());

		}

		// going down
		if (animationState == 1) {
			g.drawImage(ani_down.sprite, (int) pos.xpos - width / scale, (int) pos.ypos - height, width * scale,
					height * scale, null);

			if (down)
				ani_down.update(System.currentTimeMillis());

		}
		// going left
		if (animationState == 2) {
			g.drawImage(ani_left.sprite, (int) pos.xpos - width / scale, (int) pos.ypos - height, width * scale,
					height * scale, null);

			if (left)
				ani_left.update(System.currentTimeMillis());

		}
		// going right
		if (animationState == 3) {
			g.drawImage(ani_right.sprite, (int) pos.xpos - width / scale, (int) pos.ypos - height, width * scale,
					height * scale, null);

			if (right)
				ani_right.update(System.currentTimeMillis());

		}
		// idle
		if (animationState == 4) {
			g.drawImage(ani_idleDown.sprite, (int) pos.xpos - width / scale, (int) pos.ypos - height, width * scale,
					height * scale, null);
			ani_idleDown.update(System.currentTimeMillis());

		}
		
		
//		g.setColor(Color.black);
//		
//		if(World.currentlyNightTime){
//			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
//			g.fillRect((int) pos.xpos - (width / scale), (int) (pos.ypos - height), width * scale, height * scale);
//		}


		g.setColor(Color.white);
	
	}

	public void moveMapUp(float speed) {
		if (!Check.CollisionPlayerBlock(

				new Point((int) (pos.xpos + world.map_pos.xpos), (int) (pos.ypos + world.map_pos.ypos - speed)),

				new Point((int) (pos.xpos + world.map_pos.xpos + width),
						(int) (pos.ypos + world.map_pos.ypos - speed)))) {

			if (speedUp < maxSpeed) {
				speedUp += slowdown;
			} else {
				speedUp = maxSpeed;
			}

			world.map_pos.ypos -= speed;

		} else {
			speedUp = 0;
		}
	}

	public void moveMapUpGlide(float speed) {
		if (!Check.CollisionPlayerBlock(

				new Point((int) (pos.xpos + world.map_pos.xpos), (int) (pos.ypos + world.map_pos.ypos - speed)),

				new Point((int) (pos.xpos + world.map_pos.xpos + width),
						(int) (pos.ypos + world.map_pos.ypos - speed)))) {

			if (speedUp != 0) {
				speedUp -= slowdown;

				if (speedUp < 0) {
					speedUp = 0;
				}

			}

			world.map_pos.ypos -= speed;

		} else {
			speedUp = 0;
		}
	}

	public void moveMapDown(float speed) {
		if (!Check.CollisionPlayerBlock(

				new Point((int) (pos.xpos + world.map_pos.xpos), (int) (pos.ypos + world.map_pos.ypos + height + speed)),

				new Point((int) (pos.xpos + world.map_pos.xpos + width),
						(int) (pos.ypos + world.map_pos.ypos + height + speed)))) {

			if (speedDown < maxSpeed) {
				speedDown += slowdown;
			} else {
				speedDown = maxSpeed;
			}

			world.map_pos.ypos += speed;

		} else {
			speedDown = 0;
		}
	}

	public void moveMapDownGlide(float speed) {
		if (!Check.CollisionPlayerBlock(

				new Point((int) (pos.xpos + world.map_pos.xpos), (int) (pos.ypos + world.map_pos.ypos + height + speed)),

				new Point((int) (pos.xpos + world.map_pos.xpos + width),
						(int) (pos.ypos + world.map_pos.ypos + height + speed)))) {

			if (speedDown != 0) {
				speedDown -= slowdown;

				if (speedDown < 0) {
					speedDown = 0;
				}

			}

			world.map_pos.ypos += speed;

		} else {
			speedDown = 0;
		}
	}

	public void moveMapRight(float speed) {
		if (!Check.CollisionPlayerBlock(

				new Point((int) (pos.xpos + world.map_pos.xpos + (width * 1.25) + speed),
						(int) (pos.ypos + world.map_pos.ypos)),

				new Point((int) (pos.xpos + world.map_pos.xpos + (width * 1.25) + speed),
						(int) (pos.ypos + world.map_pos.ypos + height)))) {

			if (speedRight < maxSpeed) {
				speedRight += slowdown;
			} else {
				speedRight = maxSpeed;
			}

			world.map_pos.xpos += speed;
		} else {
			speedRight = 0;
		}
	}

	public void moveMapRightGlide(float speed) {
		if (!Check.CollisionPlayerBlock(

				new Point((int) (pos.xpos + world.map_pos.xpos + (width * 1.25) + speed),
						(int) (pos.ypos + world.map_pos.ypos)),

				new Point((int) (pos.xpos + world.map_pos.xpos + (width * 1.25) + speed),
						(int) (pos.ypos + world.map_pos.ypos + height)))) {

			if (speedRight != 0) {
				speedRight -= slowdown;

				if (speedRight < 0) {
					speedRight = 0;
				}
			}

			world.map_pos.xpos += speed;

		} else {
			speedRight = 0;
		}
	}

	public void moveMapLeft(float speed) {
		if (!Check.CollisionPlayerBlock(

				new Point((int) (pos.xpos + world.map_pos.xpos - (width * 0.25) - speed),
						(int) (pos.ypos + world.map_pos.ypos + height)),

				new Point((int) (pos.xpos + world.map_pos.xpos - (width * 0.25) - speed),
						(int) (pos.ypos + world.map_pos.ypos)))) {

			if (speedLeft < maxSpeed) {
				speedLeft += slowdown;
			} else {
				speedLeft = maxSpeed;
			}

			world.map_pos.xpos -= speed;

		} else {
			speedLeft = 0;
		}
	}

	public void moveMapLeftGlide(float speed) {
		if (!Check.CollisionPlayerBlock(

				new Point((int) (pos.xpos + world.map_pos.xpos - (width * 0.25) - speed),
						(int) (pos.ypos + world.map_pos.ypos + height)),

				new Point((int) (pos.xpos + world.map_pos.xpos - (width * 0.25) - speed),
						(int) (pos.ypos + world.map_pos.ypos)))) {

			if (speedLeft != 0) {
				speedLeft -= slowdown;

				if (speedLeft < 0) {
					speedLeft = 0;
				}
			}

			world.map_pos.xpos -= speed;

		} else {
			speedLeft = 0;
		}
	}

	/*
	 * 
	 * public void movePlayerUp(){ topLeft.y -= moveAmountU; topRight.y -=
	 * moveAmountU;
	 * 
	 * // collision check if (!Check.CollisionPlayerBlock(topLeft, topRight)) {
	 * if (speedUp < maxSpeed) { speedUp += speedupSpeed; } else speedUp =
	 * maxSpeed;
	 * 
	 * pos.ypos -= moveAmountU; }else{ speedUp = 0; }
	 * 
	 * }//end movePlayerUp public void movePlayerDown(){ botLeft.y +=
	 * moveAmountD; botRight.y += moveAmountD;
	 * 
	 * // collision check if (!Check.CollisionPlayerBlock(botLeft, botRight)) {
	 * if (speedDown < maxSpeed) { speedDown += speedupSpeed; } else speedDown =
	 * maxSpeed;
	 * 
	 * pos.ypos += moveAmountD; }else{ speedDown = 0; }
	 * 
	 * }//end movePlayerDown public void movePlayerLeft(){ botLeft.x -=
	 * moveAmountL; topLeft.x -= moveAmountL;
	 * 
	 * // collision check if (!Check.CollisionPlayerBlock(botLeft, topLeft)) {
	 * if (speedLeft < maxSpeed) { speedLeft += speedupSpeed; } else speedLeft =
	 * maxSpeed;
	 * 
	 * pos.xpos -= moveAmountL; }else{ speedLeft = 0; }
	 * 
	 * }//end movePlayerLeft public void movePlayerRight(){ botRight.x +=
	 * moveAmountR; topRight.x += moveAmountR;
	 * 
	 * // collision check if (!Check.CollisionPlayerBlock(topRight, botRight)) {
	 * if (speedRight < maxSpeed) { speedRight += speedupSpeed; } else
	 * speedRight = maxSpeed;
	 * 
	 * pos.xpos += moveAmountR; }else{ speedRight = 0; }
	 * 
	 * }//end movePlayerRight public void movePlayerUpGlide(){ topLeft.y -=
	 * moveAmountU; topRight.y -= moveAmountU;
	 * 
	 * // collision check if (!Check.CollisionPlayerBlock(topLeft, topRight)) {
	 * if (speedUp != 0) { speedUp -= slowDown;
	 * 
	 * if (speedUp < 0) speedUp = 0; } pos.ypos -= moveAmountU; }else{ speedUp =
	 * 0; }
	 * 
	 * }//end movePlayerUpGlide public void movePlayerDownGlide(){ botLeft.y +=
	 * moveAmountD; botRight.y += moveAmountD;
	 * 
	 * // collision check if (!Check.CollisionPlayerBlock(botLeft, botRight)) {
	 * if (speedDown != 0) { speedDown -= slowDown;
	 * 
	 * if (speedDown < 0) speedDown = 0; } pos.ypos += moveAmountD; }else{
	 * speedDown = 0; }
	 * 
	 * }//end movePlayerDownGLide public void movePlayerLeftGlide(){ botLeft.x
	 * -= moveAmountL; topLeft.x -= moveAmountL;
	 * 
	 * // collision check if (!Check.CollisionPlayerBlock(botLeft, topLeft)) {
	 * if (speedLeft != 0) { speedLeft -= slowDown;
	 * 
	 * if (speedLeft < 0) speedLeft = 0; } pos.xpos -= moveAmountL; }else{
	 * speedLeft = 0; }
	 * 
	 * }//end movePlayerLeftGlide public void movePlayerRightGlide(){ botRight.x
	 * += moveAmountR; topRight.x += moveAmountR;
	 * 
	 * // collision check if (!Check.CollisionPlayerBlock(topRight, botRight)) {
	 * if (speedRight != 0) { speedRight -= slowDown;
	 * 
	 * if (speedRight < 0) speedRight = 0; } pos.xpos += moveAmountR; }else{
	 * speedRight = 0; }
	 * 
	 * }//end movePlayerRightGlide
	 * 
	 * 
	 */

	public static HUDmanager getHUD() {
		return hud;
	}
	
	public Mousemanager getMouseManager() {
		return playerMM;
	}
	
	public Vector2F getPos() {
		return pos;
	}
	
	public static World getWorld() {
		return world;
	}

	public int getAnimationState() {
		return animationState;
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	public boolean hasSpawned() { return hasSpawned; }

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			
			if(!moving){
				moving = true;
			}
			
			up = true;
		}
		if (key == KeyEvent.VK_S) {
			
			if(!moving){
				moving = true;
			}
			
			down = true;
		}
		if (key == KeyEvent.VK_A) {
			
			if(!moving){
				moving = true;
			}
			
			left = true;
		}
		if (key == KeyEvent.VK_D) {
			
			if(!moving){
				moving = true;
			}
			
			right = true;
		}
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(1);
		}
		if (key == KeyEvent.VK_SHIFT) {
			running = true;
		}
		
		if (key == KeyEvent.VK_F) {
			
			
			if( world.currentlyDayTime){
				world.currentlyDayTime = false;
				world.currentlyNightTime = true;
				world.setNightTimeStart(true);
				
			}
			
			else if( world.currentlyNightTime){
				
				world.currentlyNightTime = false;
				world.currentlyDayTime = true;
				world.setDayTimeStart(true);
			}

		}
		
		
		//DEBUG ENABLE
		if (key == KeyEvent.VK_F3 && hud.debugInfo == false) {
			hud.debugInfo = true;
		}
		
		//DEBUG DISABLE
		else if (key == KeyEvent.VK_F3 && hud.debugInfo == true) {
			hud.debugInfo = false;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			up = false;
		}
		if (key == KeyEvent.VK_S) {
			down = false;
		}
		if (key == KeyEvent.VK_A) {
			left = false;
		}
		if (key == KeyEvent.VK_D) {
			right = false;
		}
		if (key == KeyEvent.VK_SHIFT) {
			running = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
