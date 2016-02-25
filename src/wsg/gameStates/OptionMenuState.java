package wsg.gameStates;

import java.awt.Color;
import java.awt.Graphics2D;

import wsg.gameState.GameState;
import wsg.gameState.GameStateButton;
import wsg.gameState.GameStateManager;
import wsg.main.Assets;
import wsg.main.Main;
import wsg.managers.Mousemanager;

public class OptionMenuState extends GameState {
	
	private GameStateButton test1;
	private GameStateButton test2;
	private GameStateButton back;
	private Mousemanager mm;
	
	private int buttonWidth  = 96*2;
	private int buttonHeight = 96;
	
	
	public OptionMenuState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		mm = new Mousemanager();
		
		test1 = new GameStateButton(Main.width/2 - (buttonWidth/2), (float) (Main.height*0.25 - (buttonHeight/2)), "test1",
				Assets.getButtonPressed(), Assets.getButtonUnpressed());
		
		test2 = new GameStateButton(Main.width/2 - (buttonWidth/2), (float) (Main.height*0.5 - (buttonHeight/2)), "test2",
				Assets.getButtonPressed(), Assets.getButtonUnpressed());
		
		back = new GameStateButton(Main.width/2 - (buttonWidth/2), (float) (Main.height*0.75 - (buttonHeight/2)), 
                new MenuState(gsm), gsm, "Back",
                Assets.getButtonPressed(), Assets.getButtonUnpressed());
		
		

	}

	public void tick(double deltaTime) {
		mm.tick();
		test1.tick();
		test2.tick();
		back.tick();
		
	}

	public void render(Graphics2D g) {
		g.setColor(new Color(26, 24, 9));
		g.fillRect(0, 0, Main.width, Main.height);
		
		test1.render(g);
		test2.render(g);
		back.render(g);
		
		mm.render(g);
	}

}
