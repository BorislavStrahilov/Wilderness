package inventory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

import my.project.gop.main.Vector2F;
import wsg.MoveableObjects.Player;
import wsg.generator.Block;
import wsg.generator.World;
import wsg.main.Main;
import wsg.managers.Mousemanager;

public class Inventory {

	private Vector2F pos = new Vector2F();
	private int slotSize = 48;

	private int width;
	private int height;

	public Rectangle worldScreen;
	public Rectangle worldScreenSmall;
	private Point itemWorldPos;

	private CopyOnWriteArrayList<Slot> slots;
	private boolean toggled;

	private Color bgColor = new Color(0, 0, 0, 80);

	private World world;

	public Inventory(float xpos, float ypos, int width, int height, World world) {
		this.pos.xpos = xpos;
		this.pos.ypos = ypos;
		this.width = width;
		this.height = height;
		this.world = world;

	}

	public void init() {

		slots = new CopyOnWriteArrayList<Slot>();

		worldScreen = new Rectangle(0, 0, Main.width - 1, Main.height - Main.height / 5);
		
		worldScreenSmall = new Rectangle(
								(int) (Main.width * 0.415),
								(int)  (Main.height * 0.275),
								Main.width/6,
								Main.height/4);
				
		// loop through inventory
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
				slots.add(new Slot(pos.xpos + (y * slotSize), pos.ypos + (x * slotSize)));
			}
		}

	}

	public void tick() {

		// only update if inventory is toggled
		if (toggled) {
			for (Slot slot : slots) {
				slot.tick();

				if (slot.isLeftClicked()) {

					// if slot has item in it
					if (!slot.isAir()) {

						//if mouse is holding an item
						if (Main.mm.itemHeld != null) {

	
							//slot has different item than mouse
							if(slot.getItem() != Main.mm.itemHeld){
																
								//store slot's item
								Item tempItem = slot.getItem();
								
								//clear slot and place mouse's item in
								slot.clear();
								slot.setItem(Main.mm.itemHeld);
								
								//mouse holds old slot item
								Main.mm.itemHeld = tempItem;
								
								//mouse has image of old item
								Main.mm.defaultUnpressedImage = tempItem.getItemImage();
								Main.mm.defaultPressedImage = tempItem.getItemImage();
								
							}						
						}
						//mouse isn't holding item
						else {

							// make cursor look like item
							Main.mm.defaultUnpressedImage = slot.getItem().getItemImage();
							Main.mm.defaultPressedImage = slot.getItem().getItemImage();

							// store item onto cursor
							Main.mm.itemHeld = slot.getItem();
							
							slot.clear();
						}
					}
					// if slot is empty
					else {

						// reset cursor, place item, and remove item from mouse
						Main.mm.defaultUnpressedImage = Main.mm.unpressedImage;
						Main.mm.defaultPressedImage = Main.mm.pressedImage;

						if (Main.mm.itemHeld != null)
							slot.setItem(Main.mm.itemHeld);

						Main.mm.itemHeld = null;

					}
				}

				if (slot.isRightClicked()) {

				}
			}
		}//end if toggled
		
	}

	public void render(Graphics2D g) {

		// only update if inventory is toggled
		if (toggled) {

			g.setColor(bgColor);
			g.fillRect((int) pos.xpos, (int) pos.ypos, width * slotSize, height * slotSize);
			g.setColor(Color.white);

			for (Slot slot : slots) {
				slot.render(g);
			}
		}
		
		
	}
	

	// drop an item from mouse to world
	public void dropItem(Mousemanager mm, Point playerPos) {

		itemWorldPos = playerPos;
		
		// see which block mouse intersects with
		for (Block block : world.getTiles().blocks) {
			if (block.worldPosBounds.contains(itemWorldPos)) {

				//if block doesnt contain an item
				if(block.getItem() == null){
					//reset item pos
					itemWorldPos = null;
					
					block.setItem(mm.itemHeld);
					
					//reset mouse
					mm.itemHeld = null;
					mm.defaultPressedImage = mm.pressedImage;
					mm.defaultUnpressedImage = mm.unpressedImage;
	
					break;
				}
			}
		}

	}//end drop item
	
	
	// drop an item from mouse to world
	public void pickupItem(Mousemanager mm) {
		
		// see which block mouse intersects with
		for (Block block : world.getTiles().blocks) {
			if (block.worldPosBounds.contains(mm.mouse)) {

				
				//if block has an item
				if(block.getItem() != null){
					//mouse now holds item from block
					mm.itemHeld = block.getItem();
					mm.defaultPressedImage = block.getItem().getItemImage();
					mm.defaultUnpressedImage = block.getItem().getItemImage();
					
					//block holds nothing
					block.removeItem();
	
					break;
				}
			}
		}
		


	}

	// find first empty slot, add item to it
	public void addItem(Item item) {

		for (Slot slot : slots) {

			// if slot is empty
			if (slot.isAir()) {
				slot.setItem(item);
				break;
			} 

		} // end for loop

	}

	// add to specific slot object
	public void addItem(Item item, Slot slot) {

		// if its empty
		if (slot.isAir()) {
			slot.setItem(item);
		}
	}

	// add item to specified slot
	public void addItem(Item item, int slotNum) {

		if (slotNum < slots.size()) {
			Slot slot = slots.get(slotNum);

			// if its empty
			if (slot.isAir()) {
				slot.setItem(item);
			} 

		} else {
			System.err.println("SLOT CHOSEN IS OUT OF BOUNDS!");
		}

	}

	// toggle inventory
	public void toggle() {
		toggled = !toggled;
	}

}
