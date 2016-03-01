package inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import my.project.gop.main.Vector2F;
import wsg.gameLoop.GameLoop;
import wsg.managers.Mousemanager;

public class Slot extends Rectangle {

	private Font defaultFont = GameLoop.font;

	private Item item;
	private int slotID = 0;

	private Vector2F pos = new Vector2F();
	private int slotSize = 48;

	private boolean isHeldOver;

	public Slot(float xpos, float ypos) {
		this.pos.xpos = xpos;
		this.pos.ypos = ypos;
		setBounds((int) pos.xpos, (int) pos.ypos, slotSize, slotSize);

	}

	public void tick() {
		setBounds((int) pos.xpos, (int) pos.ypos, slotSize, slotSize);

		//clear slot if item is null
		if(item == null)
			clear();
		
		// if mouse is hovering over
		if (this.contains(Mousemanager.mouse)) {
			isHeldOver = true;
		} else {
			isHeldOver = false;
		}

	}// end tick

	public void render(Graphics2D g) {

		// slot contains item, draw it's image
		if (item != null) {
			g.drawImage(item.getItemImage(), (int) pos.xpos, (int) pos.ypos, 48, 48, null);
		}

		// outline slot if mouse hovers over it
		if (isHeldOver) {
			g.setColor(Color.green);

			g.drawRect((int) pos.xpos, (int) pos.ypos, slotSize - 1, slotSize - 1);
		}else{
			g.setColor(Color.black);

			g.drawRect((int) pos.xpos, (int) pos.ypos, slotSize - 1, slotSize - 1);
		}
		

	}// end render

	public void setItem(Item item) {

		this.item = item;
		slotID = item.getItemID();
	}

	public void clear() {
		slotID = 0;
		item = null;
	}

	public Item getItem() {
		return item;
	}

	public boolean isAir() {
		return (item == null && slotID == 0);
	}

	public boolean hasSameID(Item item) {
		return (item.getItemID() == slotID);
	}
	
	public int getSlotID() {
		return slotID;
	}
	
	
	public boolean isLeftClicked(){
		if(isHeldOver){
			if(InventoryMouseManager.leftClicked){
				InventoryMouseManager.leftClicked = false;
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}//end left click check
	
	public boolean isRightClicked(){
		if(isHeldOver){
			if(InventoryMouseManager.rightClicked){
				InventoryMouseManager.rightClicked = false;
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}//end right click check
	
	
	public boolean hasItem() {
		if(item != null){
			if(slotID != 0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}//end hasItem check

}
