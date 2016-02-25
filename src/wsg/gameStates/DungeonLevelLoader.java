package wsg.gameStates;

import java.awt.Graphics2D;

import wsg.MoveableObjects.Player;
import wsg.gameState.GameState;
import wsg.gameState.GameStateManager;
import wsg.generator.World;

public class DungeonLevelLoader extends GameState {
	
	World world;

	public DungeonLevelLoader(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		world = new World("world1");
		world.setSize(100,100);
		world.setWorldSpawn(50, 50);
		world.addPlayer(new Player());
		world.init();
		world.generate("map.png");
		
	}

	public void tick(double deltaTime) {
		world.tick(deltaTime);
	}

	public void render(Graphics2D g) {
		world.render(g);
	}

}
