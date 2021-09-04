package gui;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.ItemManager;
import objects.Item;

public class ItemSearch {
	static Item foundItem;
	static ArrayList<Item> itemsList = ItemManager.sendItems();

	public static Item display() {
		foundItem = null;

		Stage addWindow = new Stage();
		addWindow.initModality(Modality.APPLICATION_MODAL);
		addWindow.setTitle("Add item");
		addWindow.setWidth(500);
		addWindow.setHeight(440);

		/**
		 * Searching For Items
		 */
		Label searchByNameLabel = new Label("Search by name");
		TextField searchByName = new TextField();
		Label searchByManufacturerLabel = new Label("Search by manufacturer");
		TextField searchByManufacturer = new TextField();
		Label searchBySkuLabel = new Label("Search by SKU");
		TextField searchBySku = new TextField();
		Button searchButton = new Button("Search");

		VBox inputFields = new VBox(10, searchByNameLabel, searchByName, searchByManufacturerLabel, searchByManufacturer,
				searchBySkuLabel, searchBySku, searchButton);
		inputFields.setAlignment(Pos.BASELINE_CENTER);

		GuiCustomization.addCSS("h2", false, null, 0, searchByNameLabel, searchByManufacturerLabel,
				searchBySkuLabel);
		GuiCustomization.addCSS("search-button", false, null, 0, searchButton);
		GuiCustomization.addCSS("bgpadding", false, null, 0, inputFields);

		VBox.setMargin(searchByManufacturerLabel, new Insets(30, 0, 0, 0));
		VBox.setMargin(searchBySkuLabel, new Insets(30, 0, 0, 0));
		VBox.setMargin(searchButton, new Insets(30, 0, 0, 0));
		Scene addScene = new Scene(inputFields, 700, 350);
		addScene.getStylesheets().add("gui.css");






		/**
		 * Lists the items to add to an order.
		 */
		ScrollPane sp = new ScrollPane();
		sp.fitToWidthProperty().set(true);
		Scene itemsScene = new Scene(sp, 500, 500);
		itemsScene.getStylesheets().add("gui.css");

		// Holds the list of BorderPanes.
		VBox overall = new VBox(15);
		overall.setPrefSize(470, 500);
		GuiCustomization.addCSS("bgpadding", false, null, 0, overall);

		BorderPane[] addItemsBP = new BorderPane[10];


		// Search button
		searchButton.setOnAction(e -> {
			if (!searchByName.getText().isBlank() || !searchBySku.getText().isBlank() || !searchByManufacturer.getText().isBlank()) {
				ArrayList<Item> foundItems = new ArrayList<>();
				Label[] itemFound = new Label[10];
				Label[] itemSku = new Label[10];
				Label[] itemDesc = new Label[10];
				Label[] inventory = new Label[10];
				Label[] price = new Label[10];
				TextField[] amounts = new TextField[10];
				Button[] itemFoundButton = new Button[10];
				Label noItemsFoundLabel = new Label("No items found.");
				Button noItemsFoundButton = new Button("Go back.");

				VBox titleVbox = new VBox();

				for (int x = 0; x < itemsList.size(); x++) {
					boolean name = itemsList.get(x).getItemName().toLowerCase().contains(searchByName.getText().toLowerCase()) &&
							searchByName.getText().length() > 0;
					boolean manu = itemsList.get(x).getManufacturer().toLowerCase().contains(searchByManufacturer.getText().toLowerCase()) &&
							searchByManufacturer.getText().length() > 0;
					boolean sku = itemsList.get(x).getSku().toLowerCase().contains(searchBySku.getText().toLowerCase()) &&
							searchBySku.getText().length() > 0;
					if (name || manu || sku) {
						foundItems.add(itemsList.get(x));
					}
				}

				if (foundItems.size() > 0) {
					for (int x = 0; x < foundItems.size(); x++) {
						final int snapshot = x;
						Item found = foundItems.get(x);
						addItemsBP[x] = new BorderPane();

						// Item name
						itemFound[x] = new Label(foundItems.get(x).getItemName());

						// Item SKU and Description
						itemSku[x] = new Label(foundItems.get(x).getSku());
						itemDesc[x] = new Label(foundItems.get(x).getDescription());
						titleVbox = new VBox(10, itemFound[x], itemSku[x], itemDesc[x]);

						// Inventory count
						inventory[x] = new Label("Available: " + foundItems.get(x).getInventoryCount());
						VBox invbox = new VBox(inventory[x]);

						// Item price
						price[x] = new Label("Price: $" + foundItems.get(x).getPrice());
						VBox pricebox = new VBox(price[x]);

						// Quantity input
						Label quantityLabel = new Label("Quantity: ");
						amounts[x] = new TextField("1");
						itemFoundButton[x] = new Button("Add Item");
						VBox qtyVBox = new VBox(quantityLabel, amounts[x]);
						VBox.setMargin(amounts[x], new Insets(10, 0, 0, 0));
						BorderPane qtyContainer = new BorderPane();
						qtyContainer.setLeft(qtyVBox);
						qtyContainer.setRight(itemFoundButton[x]);
						BorderPane.setAlignment(itemFoundButton[x], Pos.BOTTOM_RIGHT);

						// Add item button
						itemFoundButton[x].setOnAction(event -> {
							found.setQuantity(Integer.parseInt(amounts[snapshot].getText()));
							for (Item foundItem2 : foundItems) {
								if (foundItem2.equals(found)) {
									foundItem2.setInventoryCount(foundItem2.getInventoryCount() - Integer.parseInt(amounts[snapshot].getText()));

								}
							}

							foundItem = found;
							addWindow.close();
						});

						// CSS
						GuiCustomization.addCSS("h2", false, null, 0, itemFound[x]);
						GuiCustomization.addCSS("itemDesc", false, null, 0, itemSku[x], inventory[x],
								price[x]);
						GuiCustomization.addCSS("search-button", false, Cursor.HAND, 0, itemFoundButton[x]);


						// Add elements together
						addItemsBP[x].setTop(titleVbox);
						addItemsBP[x].setLeft(invbox);
						addItemsBP[x].setRight(pricebox);
						if (found.getInventoryCount() > 0) {
							addItemsBP[x].setBottom(qtyContainer);
							BorderPane.setMargin(qtyContainer, new Insets(15,5,5,5));
						}

						BorderPane.setMargin(titleVbox, new Insets(5,5,5,5));
						BorderPane.setMargin(invbox, new Insets(5,5,5,5));
						BorderPane.setMargin(pricebox, new Insets(5,5,5,5));
						addItemsBP[x].getStyleClass().clear();
						addItemsBP[x].getStyleClass().add("add-items-bg");

						Region spacer = new Region();
						spacer.setPrefHeight(50);
						HBox.setHgrow(spacer, Priority.ALWAYS);
						overall.getChildren().add(addItemsBP[x]);
						overall.getChildren().add(spacer);
					}
				}
				else {
					overall.getChildren().add(noItemsFoundLabel);
					noItemsFoundButton.setOnAction(event -> {
						addWindow.setScene(addScene);
					});
					overall.getChildren().add(noItemsFoundButton);
				}

				sp.setContent(overall);
				sp.setPrefSize(500, 500);
				addWindow.setScene(itemsScene);

			}

		});

		addWindow.setScene(addScene);
		addWindow.showAndWait();

		return foundItem;
	}


}
