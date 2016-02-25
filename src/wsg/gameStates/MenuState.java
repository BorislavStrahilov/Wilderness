package wsg.gameStates;

import java.awt.Color;
import java.awt.Graphics2D;

import wsg.gameState.GameState;
import wsg.gameState.GameStateButton;
import wsg.gameState.GameStateManager;
import wsg.main.Assets;
import wsg.main.Main;
import wsg.managers.Mousemanager;

public class MenuState extends GameState {
	
	private GameStateButton startGame;
	private GameStateButton quitGame;
	private GameStateButton options;
	private Mousemanager mm;
	
	private int buttonWidth  = 96*2;
	private int buttonHeight = 96;
	
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		mm = new Mousemanager();
		startGame = new GameStateButton(Main.width/2 - (buttonWidth/2), Main.height/4 - (buttonHeight/2), 
				                         new DungeonLevelLoader(gsm), gsm, "Start Game",
				                         Assets.getButtonPressed(), Assets.getButtonUnpressed());
		
		options = new GameStateButton(Main.width/2 - (buttonWidth/2), Main.height/2 - (buttonHeight/2), 
                new OptionMenuState(gsm), gsm, "Options",
                Assets.getButtonPressed(), Assets.getButtonUnpressed());
		
		quitGame = new GameStateButton(Main.width/2 - (buttonWidth/2), (float) (Main.height*0.75 - (buttonHeight/2)), "Quit",
                						Assets.getButtonPressed(), Assets.getButtonUnpressed());
		
		

	}

	public void tick(double deltaTime) {
		mm.tick();
		startGame.tick();
		options.tick();
		quitGame.tick();
		
		if(quitGame.isPressed()){
			if(quitGame.isHeldOver())
				System.exit(1);
		}
		
	}

	public void render(Graphics2D g) {
		g.setColor(new Color(26, 24, 9));
		g.fillRect(0, 0, Main.width, Main.height);
		
		startGame.render(g);
		options.render(g);
		quitGame.render(g);
		
		mm.render(g);
	}

}
