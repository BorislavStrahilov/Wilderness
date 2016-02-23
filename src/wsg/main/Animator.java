package wsg.main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animator {

	private ArrayList<BufferedImage> frames;
	public volatile boolean running = false;
	public BufferedImage sprite;
	
	private long prevTime, speed;
	private int frameAtPause, currFrame;
	
	public Animator(ArrayList<BufferedImage> frames){
		this.frames = frames;
		
	}
	
	public void setSpeed(long speed) { this.speed = speed; }
	
	public void update(long time){
		
		if(running){
			if(time - prevTime >= speed){
				currFrame++;
				
				try{
					sprite = frames.get(currFrame);	
				}catch(IndexOutOfBoundsException e){
					reset();
					sprite = frames.get(currFrame);
				}
				prevTime = time;	
			}
		}//end if running
		
	}//end update
	
	
	public void play(){
		running = true;
		
		prevTime = 0;
		frameAtPause = 0;
		currFrame = 0;
		
	}
	
	public void stop(){
		running = false;
		
		prevTime = 0;
		frameAtPause = 0;
		currFrame = 0;
	}
	
	public void pause(){
		frameAtPause = currFrame;
		running = false;
	}
	
	public void resume(){
		currFrame = frameAtPause;
		running = false;
	}
	
	public void reset(){
		currFrame = 0;
	}
	
	public boolean isDoneAnimating(){
		if(currFrame == frames.size())
			return true;
		else
			return false;
		
	}
	
	
	
}
