package wsg.managers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import my.project.gop.main.IDGameLoop;
import my.project.gop.main.loadImageFrom;
import wsg.MoveableObjects.Player;
import wsg.generator.World;
import wsg.main.Assets;
import wsg.main.Main;

public class HUDmanager {

	private Player player;
	private World world;

	private Color energyColor;
	private Color tempColor;
	private Color hungerColor;

	private double temp, hunger, energy;

	public boolean debugInfo = false;
	private Font debugFont = new Font("Consolas", Font.BOLD, 20);
	private Font defaultFont;

	// LIGHTs
	private BufferedImage light;

	public HUDmanager(World world) {
		this.world = world;
		this.player = world.getPlayer();
	}

	public void render(Graphics2D g) {

		// display various debug info
		if (debugInfo) {
			// save old font and change into new one
			defaultFont = g.getFont();
			g.setFont(debugFont);

			g.drawString("[DEBUG]", 10, 25);
			debugFont = debugFont.deriveFont(18f);
			g.setFont(debugFont);

			g.drawString("-FPS: " + IDGameLoop.fps, 20, 50);
			g.drawString("-PlayerXpos: " + (int) player.getPos().getWorldLocation().xpos, 20, 70);
			g.drawString("-PlayerYpos: " + (int) player.getPos().getWorldLocation().ypos, 20, 90);
			g.drawString("-PlayerAniState: " + player.getAnimationState(), 20, 110);
			g.drawString("-Loaded Blocks: " + world.getTiles().getBlocks().size(), 20, 130);
			g.drawString("-Block Entities: " + world.getBlock_ents().size(), 20, 150);
			

			debugFont = debugFont.deriveFont(20f);
			g.setFont(defaultFont);
		} // end debug info rendering

		g.setFont(g.getFont().deriveFont(17f));

		temp = player.temperature / 60;
		hunger = player.hunger / 60;
		energy = player.energy / 60;

		if (temp < 0)
			temp = 0;
		if (temp > 100)
			temp = 100;

		if (energy < 0)
			energy = 0;
		if (energy > 100)
			energy = 100;

		if (hunger < 0)
			hunger = 0;
		if (hunger > 100)
			hunger = 100;

		// HUD
		g.setColor(Color.black);
		g.drawImage(Assets.HUD_bot, 0, Main.height - Main.height / 5, Main.width, Main.height / 5, null);

		energyColor = new Color((int) (255 - (energy * 2.55)), (int) (energy * 2.55), 0);
		tempColor = new Color(75, 0, (int) (255 - (temp * 2.55)));
		hungerColor = new Color((int) (hunger * 2.55), (int) (255 - (hunger * 2.55)), 0);

		// ENERGY BAR
		g.drawString("Energy", Main.width - (Main.width / 7) - 80, Main.height - (Main.height / 6) + 14);
		g.setColor(energyColor);
		g.fillRect(Main.width - (Main.width / 7), Main.height - (Main.height / 6) - 2,
				(int) ((Main.width / 8 * (energy / 100))), 20);
		g.setColor(Color.black);
		g.drawRect(Main.width - (Main.width / 7), Main.height - (Main.height / 6) - 2, Main.width / 8, 20);

		// HUNGER BAR
		g.drawString("Hunger", Main.width - (Main.width / 7) - 80, Main.height - (Main.height / 6) + 54);
		g.setColor(hungerColor);
		g.fillRect(Main.width - (Main.width / 7), Main.height - (Main.height / 6) + 38,
				(int) ((Main.width / 8 * (hunger / 100))), 20);
		g.setColor(Color.black);
		g.drawRect(Main.width - (Main.width / 7), Main.height - (Main.height / 6) + 38, Main.width / 8, 20);

		// TEMP BAR
		g.drawString("Temp", Main.width - (Main.width / 7) - 68, Main.height - (Main.height / 6) + 94);
		g.setColor(tempColor);
		g.fillRect(Main.width - (Main.width / 7), Main.height - (Main.height / 6) + 78,
				(int) ((Main.width / 8 * (temp / 100))), 20);
		g.setColor(Color.black);
		g.drawRect(Main.width - (Main.width / 7), Main.height - (Main.height / 6) + 78, Main.width / 8, 20);

	}

}
