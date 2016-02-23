package wsg.gameLoop;

import my.project.gop.main.IDGameLoop;
import my.project.gop.main.Vector2F;
import wsg.gameState.GameStateManager;
import wsg.main.Assets;

public class GameLoop extends IDGameLoop {

	GameStateManager gsm;
	public static Assets assets = new Assets();
	public static Vector2F map = new Vector2F();
	
	public GameLoop(int width, int height) {
		super(width, height);
	}
	
	
	public void init() {
		
		Vector2F.setWorldVariables(map.xpos, map.ypos);
		
		assets.init();
		gsm = new GameStateManager();
		gsm.init();
		
		super.init();
	}
	
	
	public void tick(double deltaTime) {
		Vector2F.setWorldVariables(map.xpos, map.ypos);

		gsm.tick(deltaTime);
	
	}
	
	public void render() {
		super.render();
		gsm.render(graphics2D);
		
		clear();
	}
	
	public void clear() {
		super.clear();
	}
	

	

}
