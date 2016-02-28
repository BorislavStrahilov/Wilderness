package wsg.generator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;
import resources.test;
import wsg.MoveableObjects.Player;
import wsg.generator.Block.BlockType;
import wsg.main.Main;
import wsg.managers.LightManager;
import wsg.managers.LightSource;
import wsg.managers.TileManager;

public class World {

	private String worldName;
	private BufferedImage map;
	
	public static Vector2F map_pos = new Vector2F();


	private int worldWidth, worldHeight;
	private int blockSize = 48;

	private boolean hasSize = false;
	private TileManager tiles;
	private static CopyOnWriteArrayList<BlockEntity> block_ents;
	private LightManager lm;
	private Player player;
	
	private boolean nightTimeStart = false;
	private boolean dayTimeStart = false;
	
	public static boolean currentlyDayTime = true;
	public static boolean currentlyNightTime = false;
	
	//spawn point
	Block spawn_block;
	
	
	public World(String worldName) {
		this.worldName = worldName;
	}

	public void generate(String worldImageName) {
		
		map = null;

		if (hasSize) {

			try {
				map = loadImageFrom.LoadImageFrom(test.class, worldImageName);
			} catch (Exception e) {
				e.printStackTrace();
			}

			int color;
			
			/*	00FF00 - grass1
			 * 
			 *  955e40 - dirt1
			 *  5f3d34 - dirt_tl
			 *  573127 - dirt_tr
			 *  42251d - dirt_bl
			 *  955e44 - dirt_br
			 *  
			 */
			
			for (int x = 0; x < worldWidth; x++) {
				for (int y = 0; y < worldHeight; y++) {

					color = map.getRGB(x, y);	
					
					//GRASS
					if((color & 0xFFFFFF) == 0x00FF00){
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.GRASS_1));
					}
					
					//DIRT
					else if((color & 0xFFFFFF) == 0x955e40){
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.DIRT_1));
					}
					//DIRT_TL
					else if((color & 0xFFFFFF) == 0x5f3d34){
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.DIRT_TL));
					}
					//DIRT_TR
					else if((color & 0xFFFFFF) == 0x573127){
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.DIRT_TR));
					}
					//DIRT_BL
					else if((color & 0xFFFFFF) == 0x42251d){
						tiles.blocks.add(new Block(new Vector2F(x*blockSize, y*blockSize), BlockType.DIRT_BL));
					}
					
					
				}
			} // end outer for
			
		} // end if has Size
	}

	public void init() {
		block_ents = new CopyOnWriteArrayList<BlockEntity>();
		tiles = new TileManager();
		lm = new LightManager(tiles.blocks, this);
		lm.init();
		
		//player init
		if(player != null)
			player.init(this);
		
		map_pos.xpos = spawn_block.getBlockPos().xpos - player.getPos().xpos;
		map_pos.ypos = spawn_block.getBlockPos().ypos - player.getPos().ypos;
		
		//initialize world vector
		Vector2F.setWorldVariables(map_pos.xpos, map_pos.ypos);
				
	}

	public void tick(double deltaTime){
		
		//change block light to night 
		//mode if its night time
		if(nightTimeStart){
			for(Block block: tiles.blocks){
				block.setLightLevel(1.0f);
			}
						
			nightTimeStart = false;
		}
		
		//same but for dayTime
		if(dayTimeStart){
			for(Block block: tiles.blocks){
				block.setLightLevel(0.0f);
			}
			
			dayTimeStart = false;
		}
		
		
		Vector2F.setWorldVariables(map_pos.xpos, map_pos.ypos);
	
			
		if(!player.hasSpawned()){
			spawn_block.tick(deltaTime);
		}
		
		tiles.tick(deltaTime);
		lm.tick();
		
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
		
		for(Block block: TileManager.blocks){
			if(player.render.intersects(block))
				block.renderBlockLightLevel(g);
		}
		
		if(player != null){
			player.getHUD().render(g);
			player.getMouseManager().render(g);

		}
		
		lm.render(g);
		
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
	
	public LightManager getLm() { return lm; }
	public Vector2F getWorldPos() { return map_pos; }
	public float getWorldXpos() { return map_pos.xpos; }
	public float getWorldYpos() { return map_pos.ypos; }
	public CopyOnWriteArrayList<BlockEntity> getBlock_ents() { return block_ents; }
	public TileManager getTiles() { return tiles; }
	public boolean getNightTimeStart() { return nightTimeStart; }
	public void setNightTimeStart(boolean nightTime){
		this.nightTimeStart = nightTime;
	}
	
	public boolean getDayTimeStart() { return dayTimeStart; }
	public void setDayTimeStart(boolean dayTimeStart){
		this.dayTimeStart = dayTimeStart;
	}

	public Player getPlayer() {
		return player;
	}


}
