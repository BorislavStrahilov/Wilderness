package wsg.gameState;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Stack;

import wsg.gameStates.MenuState;

public class GameStateManager {

	public static ArrayList<GameState> states;
	public int numStates;
	
	public GameStateManager() {
		states = new ArrayList<GameState>();
		states.add(new MenuState(this));
		numStates = states.size() - 1;
	}

	public void tick(double deltaTime){
		states.get(numStates).tick(deltaTime);
	}
	
	public void render(Graphics2D g){
		states.get(numStates).render(g);
	}

	public void init() {
		states.get(numStates).init();
	}
	
	
	
}
