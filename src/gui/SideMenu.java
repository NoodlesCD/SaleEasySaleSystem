package gui;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import manager.InvoiceManager;

public class SideMenu {
	static BorderPane windowContainer = new BorderPane();
	static VBox splashScreenBox;
	static Button newSaleButton, existingSaleButton, inventoryButton, newCustomerButton, existingCustomerButton;
	static Boolean menuOpen = false;
	static VBox menuListing;
	static HBox outerWindowContainer;
	static Scene mainWindowScene;
	static Pane menuPane = new Pane();
	static Pane newSale, existingSale;
	public static Scene displayMenu() {
		
		// Side menu
		Image logoImage = new Image("logo.png");
		ImageView logoIV = new ImageView(logoImage);
		logoIV.setFitWidth(200);
		logoIV.setPreserveRatio(true);

		newSaleButton = new Button("  New sale");
		existingSaleButton = new Button("  Previous sale");
		inventoryButton = new Button("  Inventory");
		newCustomerButton = new Button("  New customer");
		existingCustomerButton = new Button("  Existing customer");

		menuListing = new VBox(10, logoIV, newSaleButton, existingSaleButton,
				inventoryButton, newCustomerButton, existingCustomerButton);
		GuiCustomization.addCSS("side-menu-buttons-bg", false, null, 235, menuListing);
		GuiCustomization.addCSS("side-menu-buttons", false, Cursor.HAND, 235, newSaleButton, existingSaleButton,
				inventoryButton, newCustomerButton, existingCustomerButton);
		GuiCustomization.addCSS("new-sale", true, null, 0, newSaleButton);
		GuiCustomization.addCSS("prev-sale", true, null, 0, existingSaleButton);
		GuiCustomization.addCSS("inventory", true, null, 0, inventoryButton);
		GuiCustomization.addCSS("new-cust", true, null, 0, newCustomerButton);
		GuiCustomization.addCSS("prev-cust", true, null, 0, existingCustomerButton);

		newSaleButton.setOnAction(e -> newSaleAction(e));
		existingSaleButton.setOnAction(e -> existingSaleAction(e));
		inventoryButton.setOnAction(e -> inventoryAction(e));
		newCustomerButton.setOnAction(e -> newCustomerAction(e));
		existingCustomerButton.setOnAction(e -> existingCustomerAction(e));

		// Splash page
		splashScreenBox = new VBox();
		BackgroundSize bs = new BackgroundSize(100, 100, true, true, true, true);
		BackgroundImage bi = new BackgroundImage(new Image("logolarge.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		          bs);
		splashScreenBox.setBackground(new Background(bi));
		splashScreenBox.setMinHeight(700);
		splashScreenBox.setMinWidth(966);
		
		outerWindowContainer = new HBox(windowContainer);
		outerWindowContainer.getChildren().addAll(menuPane);

		windowContainer.setLeft(menuListing);
		windowContainer.setCenter(splashScreenBox);
		
		mainWindowScene = new Scene(outerWindowContainer, 1200, 700);
		mainWindowScene.getStylesheets().add("gui.css");
		return mainWindowScene;
	}

	public static void newSaleAction(ActionEvent e) {
		if (!menuOpen) {
			menuPane.getChildren().clear();
			menuOpen = true;
			Region spacer = new Region();
			spacer.setPrefHeight(100);
			HBox.setHgrow(spacer, Priority.ALWAYS);
	
			Button cashSaleButton = new Button("Cash/Retail");
			Button customerSaleButton = new Button("Existing Customer");
			VBox saleTypeBox = new VBox(20, cashSaleButton, customerSaleButton);
			newSale = new Pane(saleTypeBox);
			saleTypeBox.setMaxSize(275, 200);
			saleTypeBox.setAlignment(Pos.TOP_CENTER);
			menuPane.getChildren().add(newSale);
			menuPane.setTranslateX(-966);
			menuPane.setTranslateY(90);
			
			GuiCustomization.addCSS("side-menu-extended", false, null, 235, saleTypeBox);
			saleTypeBox.setViewOrder(0);
			cashSaleButton.setOnAction(a -> {
				menuOpen = false;
				menuPane.getChildren().clear();
				windowContainer.setCenter(NewSale.newSaleDisplay(InvoiceManager.getInvoiceNumber(), null));
			});
			customerSaleButton.setOnAction(b -> {
				windowContainer.setCenter(ExistingCustomer.searchExistingCustomer(true, false, false));
			});
			
			//windowContainer.setCenter(saleTypeBox);
			GuiCustomization.addCSS("side-menu-buttons", false, Cursor.HAND, 235, cashSaleButton, customerSaleButton);
			
			mainWindowScene.setOnMousePressed(event -> {
		        if (!newSale.equals(event.getSource())) {
		        	menuOpen = false;
		        	menuPane.getChildren().clear();
		        }
			});
			
		}
	}

	public static void existingSaleAction(ActionEvent e) {
		if (!menuOpen) {
			menuPane.getChildren().clear();
			menuOpen = true;
			Region spacer = new Region();
			spacer.setPrefHeight(100);
			HBox.setHgrow(spacer, Priority.ALWAYS);
			
			Label searchByInvNum = new Label("Invoice Number");
			TextField invoiceNumber = new TextField();
			Button customerSaleButton = new Button("Search");
			HBox textAndButton = new HBox(25, invoiceNumber, customerSaleButton);
			
			Region innerSpacer = new Region();
			spacer.setPrefHeight(50);
			HBox.setHgrow(spacer, Priority.ALWAYS);
			
			Button searchByCustomer = new Button("Search by Customer");
			
			VBox existingSaleBox = new VBox(20, searchByInvNum, textAndButton, innerSpacer, searchByCustomer);
			existingSaleBox.setMaxSize(275, 200);
			existingSaleBox.setAlignment(Pos.TOP_CENTER);
			
			existingSale = new Pane(existingSaleBox);
			menuPane.getChildren().addAll(existingSale);
			menuPane.setTranslateX(-966);
			menuPane.setTranslateY(90);
			
			GuiCustomization.addCSS("side-menu-extended", false, null, 235, existingSaleBox);
			GuiCustomization.addCSS("side-menu-buttons", false, Cursor.HAND, 100, customerSaleButton);
			GuiCustomization.addCSS("side-menu-buttons", false, Cursor.HAND, 235, searchByCustomer);
			
			customerSaleButton.setOnAction(a -> {
				menuOpen = false;
				menuPane.getChildren().clear();
				for (int x = 0; x < InvoiceManager.getInvoiceList().size(); x++) {
					if (InvoiceManager.getInvoiceList().get(x).getInvoiceNumber() == Double.parseDouble(invoiceNumber.getText())) {
						windowContainer.setCenter(PreviousSale.displayPreviousSale(InvoiceManager.getInvoiceList().get(x)));
					}
				}
				
			});
			
			searchByCustomer.setOnAction(a -> {
				menuOpen = false;
				menuPane.getChildren().clear();
				windowContainer.setCenter(ExistingCustomer.searchExistingCustomer(false, true, false));
			});
			
			outerWindowContainer.setOnMousePressed(event -> {
		        if (!existingSale.equals(event.getSource())) {
		        	menuOpen = false;
		        	menuPane.getChildren().clear();
		        }
			});
			
		}
	}

	public static void inventoryAction(ActionEvent e) {

	}

	public static void newCustomerAction(ActionEvent e) {
		menuPane.getChildren().clear();
		windowContainer.setCenter(EditCustomer.editCustomer(null));
	}

	public static void existingCustomerAction(ActionEvent e) {
		menuPane.getChildren().clear();
		windowContainer.setCenter(ExistingCustomer.searchExistingCustomer(false, false, true));
	}
	public static void setCenterScreen(Region region) {
		menuPane.getChildren().clear();
		windowContainer.setCenter(region);
	}
	public static void returnToSplashScreen() {
		menuPane.getChildren().clear();
		windowContainer.setCenter(splashScreenBox);
	}
}
