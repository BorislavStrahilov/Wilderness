package wsg.generator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;
import wsg.MoveableObjects.Player;
import wsg.generator.Block.BlockType;
import wsg.main.Main;

public class World {

	private String worldName;
	private BufferedImage map;
	
	public static Vector2F map_pos = new Vector2F();


	private int worldWidth, worldHeight;
	private int blockSize = 48;

	private boolean hasSize = false;
	private TileManager tiles;
	private CopyOnWriteArrayList<BlockEntity> block_ents;
	private Player player;

	
	//spawn point
	Block spawn_block;
	
	
	public World(String worldName) {
		this.worldName = worldName;
	}

	public void generate(String worldImageName) {
		
		map = null;

		if (hasSize) {

			try {
				map = loadImageFrom.LoadImageFrom(Main.class, worldImageName);
			} catch (Exception e) {
				e.printStackTrace();
			}

			int color;
			
			/*	00FF00 - grass1
			 *  00F000 - grass2
			 *  955e40 - dirt1
			 *  000000 - stone1
			 *  636059 - stone2
			 *  86827a - stone2_top
			 *  aeb019 - wood1
			 */
			
			for (int x = 0; x < worldWidth; x++) {
				for (int y = 0; y < worldHeight; y++) {

					color = map.getRGB(x, y);
					
					//GRASS
					if((color & 0xFFFFFF) == 0x00FF00){
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.GRASS_1));
					}
					else if((color & 0xFFFFFF) == 0x00F000){
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.GRASS_2));
					}
					
					//DIRT
					else if((color & 0xFFFFFF) == 0x955e40){
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.DIRT_1));
					}
					
					
					//WOOD
					else if((color & 0xFFFFFF) == 0xaeb019){
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.WOOD_1));
					}
					
					
					//STONE
					else if((color & 0xFFFFFF) == 0x636059){
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.STONE_1).isSolid(true));
					}
					else if((color & 0xFFFFFF) == 0x000000){
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.STONE_2).isSolid(true));
					}
					else if((color & 0xFFFFFF) == 0x86827a){
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.STONE1_TOP).isSolid(true));
					}
					
					
					
					
				}
			} // end outer for
			
		} // end if has Size
	}

	public void init() {
		block_ents = new CopyOnWriteArrayList<BlockEntity>();
		tiles = new TileManager();
		
		//player init
		if(player != null)
			player.init(this);
		
		map_pos.xpos = spawn_block.getBlockPos().xpos - player.getPos().xpos;
		map_pos.ypos = spawn_block.getBlockPos().ypos - player.getPos().ypos;
		
		//initialize world vector
		Vector2F.setWorldVariables(map_pos.xpos, map_pos.ypos);
		
	}

	public void tick(double deltaTime){	
		Vector2F.setWorldVariables(map_pos.xpos, map_pos.ypos);
		
		spawn_block.tick(deltaTime);
		
		tiles.tick(deltaTime);
		
		if(player != null)
			player.tick(deltaTime);
		
		if(!block_ents.isEmpty()){
			for(BlockEntity ent : block_ents){
				if(Player.render.intersects(ent)){
					ent.tick(deltaTime);
					ent.setAlive(true);
				}else{
					ent.setAlive(false);
				}
			}
		}//end if blocks is empty
		
	}
	
	
	public void render(Graphics2D g){
		tiles.render(g);		
		
		if(!block_ents.isEmpty()){
			for(BlockEntity ent : block_ents){
				if(Player.render.intersects(ent)){
					ent.render(g);
				}
			}
		}
		
		
		if(player != null)
			player.render(g);
		
	}
	

	public void setWorldSpawn(float xpos, float ypos){
		if(xpos < worldWidth){
			if(ypos < worldHeight){
				
				Block spawn = new Block (new Vector2F(xpos*blockSize, ypos*blockSize));
				this.spawn_block = spawn;
			}
		}

	}
	
	public Vector2F getWorldSpawn() { return spawn_block.pos; }


	public void setSize(int width, int height) {
		worldWidth = width;
		worldHeight = height;

		hasSize = true;
	}

	public void addPlayer(Player player) {
		this.player = player;
		
	}
	
	public void dropBlockEntity(Vector2F pos, BufferedImage blockImage){
		
		BlockEntity ent = new BlockEntity(pos, blockImage);
		
		if(block_ents.contains(ent) != true){
			block_ents.add(ent);
		}
		
	}
	
	public void removeDroppedEntity(BlockEntity blockEntity) {
				
		if(block_ents.contains(blockEntity) == true){
			block_ents.remove(blockEntity);
		}
		
	}
	
	
	public Vector2F getWorldPos() { return map_pos; }
	public float getWorldXpos() { return map_pos.xpos; }
	public float getWorldYpos() { return map_pos.ypos; }


}
