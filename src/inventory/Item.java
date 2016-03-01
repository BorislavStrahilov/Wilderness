package inventory;

import java.awt.image.BufferedImage;

import wsg.main.Assets;

public enum Item {
	
	HIDE("Hide", 500, Assets.getItem_Hide()),
	ROCK("Rock", 501, Assets.getItem_Rock());
	
	
	private int itemID;
	private BufferedImage itemImage;
	private String itemName;

	
	private Item(String name, int id, BufferedImage image){
		this.itemName = name;
		this.itemID = id;
		this.itemImage = image;
		
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public int getItemID() {
		return itemID;
	}
	
	public BufferedImage getItemImage() {
		return itemImage;
	}
}
