package wsg.gameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import my.project.gop.main.Vector2F;
import wsg.managers.Mousemanager;

public class GameStateButton extends Rectangle{

	private Vector2F pos = new Vector2F();
	private GameState gamestate;
	private GameStateManager gsm;
	private boolean isClicked;
	private boolean isHeldOver;
	private int width = 64*2;
	private int height = 64;
	private BufferedImage pressedImg;
	private BufferedImage unpressedImg;
	
	
	public GameStateButton(float xpos, float ypos, GameState gamestate, GameStateManager gsm, BufferedImage pressedImg, BufferedImage unpressedImg) {
		this.gamestate = gamestate;
		this.gsm = gsm;
		this.pos.xpos = xpos;
		this.pos.ypos = ypos;
		this.pressedImg = pressedImg;
		this.unpressedImg = unpressedImg;
		setBounds((int)pos.xpos,(int) pos.ypos, width, height);
	
	}
	
	public GameStateButton(float xpos, float ypos, BufferedImage pressedImg, BufferedImage unpressedImg) {
		this.pos.xpos = xpos;
		this.pos.ypos = ypos;
		this.pressedImg = pressedImg;
		this.unpressedImg = unpressedImg;
		setBounds((int)pos.xpos,(int) pos.ypos, width, height);
	
	}
	
	
	public void tick(){
		setBounds((int)pos.xpos,(int) pos.ypos, width, height);
		
		if(getBounds().contains(Mousemanager.mouse)){
			isHeldOver = true;
		}
		else{
			isHeldOver = false;
		}
	}
	
	public void render(Graphics2D g){
				
		if(!isHeldOver){
			g.drawImage(unpressedImg, (int)pos.xpos, (int)pos.ypos, width, height, null);
		}
		else{
			g.drawImage(pressedImg, (int)pos.xpos, (int)pos.ypos, width, height, null);
		}
		
	}
	
	public boolean isClicked(){ return isClicked; }
	public boolean isHeldOver(){ return isHeldOver; }
	

}
