package wsg.managers;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import my.project.gop.main.Vector2F;
import wsg.generator.Block;
import wsg.generator.World;

public class LightManager {
	
	
	
	private CopyOnWriteArrayList <LightSource> lights;
	private CopyOnWriteArrayList <Block> blocks;
	
	public LightSource playerLight;
	private World world;
	

	public LightManager(CopyOnWriteArrayList<Block> blocks, World world) {
		this.world = world;
		this.blocks = blocks;

		
	}
	
	public void init(){
		lights = new CopyOnWriteArrayList<LightSource>();
		
		
		playerLight = new LightSource(200, 200, 48, 11);
		lights.add(0, playerLight);

				
	}
	
	public void tick(){
		
		for(LightSource light: lights){
			
			if(light != null)
				light.tick();	
		}
		
		
		if( world.currentlyNightTime ){
			//have light follow player's X location
			playerLight.getLocation().xpos = 
					world.getPlayer().getPos().xpos 
					- world.getPlayer().getPos().getWorldLocation().xpos 
					+ world.getPlayer().getPos().xpos - 32;
			//have light follow player's Y location
			playerLight.getLocation().ypos = 
					world.getPlayer().getPos().ypos 
					- world.getPlayer().getPos().getWorldLocation().ypos 
					+ world.getPlayer().getPos().ypos;
		}
		
		
	}
	
	public void render(Graphics2D g){
		
		for(LightSource light: lights){
			if(light != null)
				light.render(g);
		}
		
	}
	
	public CopyOnWriteArrayList<LightSource> getLights() {
		return lights;
	}
	
	public void addLight(float xpos, float ypos, int lightSize, int lightDistanceMultiplier){
		lights.add(new LightSource(xpos,ypos,lightSize,lightDistanceMultiplier));
	}
	

}
