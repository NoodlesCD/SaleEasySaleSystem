package objects;

public class Item {
	private String itemName;
	private String manufacturer;
	private int rating;
	private double price;
	private double cost;
	private String sku;
	private String description;
	private int inventoryCount;
	private int quantity;

	public Item() {}

	public Item(String itemName, String manufacturer, int rating, double price, double cost, String sku,
			String description, int inventoryCount) {
		super();
		this.itemName = itemName;
		this.manufacturer = manufacturer;
		this.rating = rating;
		this.price = price;
		this.cost = cost;
		this.sku = sku;
		this.description = description;
		this.inventoryCount = inventoryCount;
	}



	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getInventoryCount() {
		return inventoryCount;
	}

	public void setInventoryCount(int inventoryCount) {
		this.inventoryCount = inventoryCount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
