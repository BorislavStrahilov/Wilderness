package wsg.generator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import my.project.gop.main.Vector2F;
import wsg.MoveableObjects.Player;
import wsg.generator.Block.BlockType;

public class World {
	
	private String worldName;
	private BufferedImage worldImage;
	private int worldWidth, worldHeight;
	
	private TileManager tiles = new TileManager();
	private Player player = new Player();
	
	public static CopyOnWriteArrayList<BlockEntity> block_ents = new CopyOnWriteArrayList<BlockEntity>();

	public World(String worldName, BufferedImage worldImage, int width, int height) {
		this.worldName = worldName;
		this.worldImage = worldImage;
		this.worldWidth = width;
		this.worldHeight = height;
	
	}


	public void generateWorld() {
		
		int col;
		
		/*	00FF00 - grass1
		 *  00F000 - grass2
		 *  955e40 - dirt1
		 *  000000 - stone1
		 *  636059 - stone2
		 *  86827a - stone2_top
		 *  aeb019 - wood1
		 */

		
		for(int x=0; x<worldWidth; x++){
			for(int y=0; y<worldHeight; y++){

				col = worldImage.getRGB(x, y);
				
				//GRASS
				if((col & 0xFFFFFF) == 0x00FF00){
					tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.GRASS_1));
				}
				else if((col & 0xFFFFFF) == 0x00F000){
					tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.GRASS_2));
				}
				
				//DIRT
				else if((col & 0xFFFFFF) == 0x955e40){
					tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.DIRT_1));
				}
				
				
				//WOOD
				else if((col & 0xFFFFFF) == 0xaeb019){
					tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WOOD_1));
				}
				
				
				//STONE
				else if((col & 0xFFFFFF) == 0x636059){
					tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.STONE_1).isSolid(true));
				}
				else if((col & 0xFFFFFF) == 0x000000){
					tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.STONE_2).isSolid(true));
				}
				else if((col & 0xFFFFFF) == 0x86827a){
					tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.STONE1_TOP).isSolid(true));
				}
				
				
				
			}//end inner for
		}//end outer for
		
		
		Player.init(this);		

		
	}
	
	public void tick(double deltaTime){
		tiles.tick(deltaTime);
		player.tick(deltaTime);
		
		for(BlockEntity ent : block_ents){
			if(player.render.intersects(ent)){
				ent.tick(deltaTime);
				ent.setAlive(true);
			}else{
				ent.setAlive(false);
			}
		}
	}
	
	
	public void render(Graphics2D g){
		tiles.render(g);
		
		for(BlockEntity ent : block_ents){
			if(player.render.intersects(ent)){
				ent.render(g);
			}
		}
		
		player.render(g);
		
	}
	
	public static void dropBlockEntity(Vector2F pos, BufferedImage blockImage){
		
		BlockEntity ent = new BlockEntity(pos, blockImage);
		
		if(block_ents.contains(ent) != true){
			block_ents.add(ent);
		}
		
	}
	
	public static void removeDroppedEntity(BlockEntity blockEntity) {
				
		if(block_ents.contains(blockEntity) == true){
			block_ents.remove(blockEntity);
		}
		
	}
	
	
	public BufferedImage getWorldImage() { return worldImage; }
	public TileManager getTiles() { return tiles; }
	public Player getPlayer() { return player; }
	public int getWorldHeight() { return worldHeight; }
	public int getWorldWidth() { return worldWidth; }
	public String getWorldName() { return worldName; }




}
