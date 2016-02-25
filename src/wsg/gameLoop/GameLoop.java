package wsg.gameLoop;

import java.awt.Font;

import my.project.gop.main.IDGameLoop;
import my.project.gop.main.Vector2F;
import wsg.gameState.GameStateManager;
import wsg.main.Assets;
import wsg.main.FontLoader;

public class GameLoop extends IDGameLoop {
	
	private static final long serialVersionUID = 1L;

	GameStateManager gsm;
	public static Assets assets = new Assets();
	public Font font;
	public FontLoader fontLoader;
	
	public GameLoop(int width, int height) {
		super(width, height);
	}
	
	
	public void init() {
			
		assets.init();
		gsm = new GameStateManager();
		gsm.init();
		
		fontLoader = new FontLoader("imagine_font.ttf");
		font = fontLoader.font;
		font = font.deriveFont(20f);
		
		super.init();
	}
	
	
	public void tick(double deltaTime) {
		gsm.tick(deltaTime);
	
	}
	
	public void render() {
		super.render();
		
		graphics2D.setFont(font);
		
		gsm.render(graphics2D);
		
		clear();
	}
	
	public void clear() {
		super.clear();
	}
	

	

}
