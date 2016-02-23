package wsg.gameStates;

import java.awt.Graphics2D;

import my.project.gop.main.IDGameLoop;
import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;
import wsg.gameState.GameState;
import wsg.gameState.GameStateManager;
import wsg.generator.Map;
import wsg.main.Main;

public class DungeonLevelLoader extends GameState {
	
	Map map;

	public DungeonLevelLoader(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		map = new Map();
		map.init();
	}

	public void tick(double deltaTime) {
		map.tick(deltaTime);
		
	}

	public void render(Graphics2D g) {
		map.render(g);
	}

}
