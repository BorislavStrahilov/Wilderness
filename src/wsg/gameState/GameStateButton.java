package wsg.gameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import my.project.gop.main.Vector2F;
import wsg.managers.Mousemanager;

public class GameStateButton extends Rectangle{

	private static final long serialVersionUID = 1L;
	
	private Vector2F pos = new Vector2F();
	private GameState gamestate;
	private GameStateManager gsm;
	private boolean isHeldOver;
	public int width = 96*2;
	public int height = 96;
	private BufferedImage pressedImg;
	private BufferedImage unpressedImg;
	private String buttonMessage;
	
	
	public GameStateButton(float xpos, float ypos, GameState gamestate,
							  GameStateManager gsm, String buttonMessage, 
							  BufferedImage pressedImg, BufferedImage unpressedImg) 
	{
		this.gamestate = gamestate;
		this.gsm = gsm;
		this.pos.xpos = xpos;
		this.pos.ypos = ypos;
		this.pressedImg = pressedImg;
		this.unpressedImg = unpressedImg;
		this.buttonMessage = buttonMessage;
		setBounds((int)pos.xpos,(int) pos.ypos, width, height);
		

	
	}
	
	public GameStateButton(float xpos, float ypos, String buttonMessage, BufferedImage pressedImg, BufferedImage unpressedImg) {
		this.pos.xpos = xpos;
		this.pos.ypos = ypos;
		this.pressedImg = pressedImg;
		this.unpressedImg = unpressedImg;
		this.buttonMessage = buttonMessage;
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
		
		if(gamestate != null){
			if(isHeldOver){
				if(isPressed()){
										
					gsm.states.set(gsm.numStates, gamestate);
					gsm.states.get(gsm.numStates).init();
					
					isHeldOver = false;
					Mousemanager.pressed = false;
				}
			}
			
		}
	}
	
	public void render(Graphics2D g){
				
		if(!isHeldOver){
			g.drawImage(unpressedImg, (int)pos.xpos, (int)pos.ypos, width, height, null);
		}
		else{
			g.drawImage(pressedImg, (int)pos.xpos, (int)pos.ypos, width, height, null);
		}
		
		g.setColor(Color.black);
				
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at, true, true);
		int textWidth = (int) g.getFont().getStringBounds(buttonMessage, frc).getWidth();
		
		g.drawString(buttonMessage, (float) (pos.xpos + width/2 - textWidth/2), (float) (pos.ypos + height/1.8));
		
		g.setColor(Color.white);
		
	}
	
	public boolean isHeldOver(){ return isHeldOver; }
	
	public boolean isPressed() { return Mousemanager.pressed; }
	

}
