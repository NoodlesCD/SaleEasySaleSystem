package manager;

import java.util.ArrayList;

import objects.Item;

public class ItemManager {
	ArrayList<Item> itemList = new ArrayList<>();

	public ArrayList<Item> getItemList() {
		return itemList;
	}

	public void addItem(Item item) {
		itemList.add(item);
	}



	static Item a = new Item("Walnut Oak Modern Bookshelf", "Toponeware", 4, 78.43, 48.98, "WOMBTW", "A fine looking bookshelf, made out of walnut and oak", 5);
	static Item b = new Item("Contemporary Cabinet Display", "Toponeware", 5, 220.81, 128.33, "CCDTW", "A display shelf for various items in your home", 7);
	static Item c = new Item("Tall Storage Cabinet with Door", "Vasagle", 3, 135.99, 99.84, "TSCDVG", "Display or organize with this shelf", 3);
	static ArrayList<Item> itemsList = new ArrayList<>();


	public static ArrayList<Item> sendItems() {
		itemsList.add(a);
		itemsList.add(b);
		itemsList.add(c);

		return itemsList;
	}

	public static void updateInv(String sku, int qty) {
		if (sku == a.getSku()) {
			a.setInventoryCount(a.getInventoryCount() + qty);
		}
		if (sku == b.getSku()) {
			b.setInventoryCount(b.getInventoryCount() + qty);
		}
		if (sku == c.getSku()) {
			c.setInventoryCount(c.getInventoryCount() + qty);
		}
	}
}
