package gui;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import manager.InvoiceManager;
import manager.ItemManager;
import objects.Customer;
import objects.Invoice;
import objects.Item;

public class NewSale {

	Button button;
	MenuButton dropButton;

	static Button mainNewSale, mainExistingSale, mainInventory, mainNewCustomer, mainExistingCustomer;
	static Button cashSaleButton, customerSaleButton;
	static Button newSaleButton, existingSaleButton, inventoryButton, newCustomerButton;
	static Button existingCustomerButton, addItemButton, deleteItemButton, checkoutButton;
	static Button cash, debit, visa, mc, amex;
	static Button refundcash, refunddebit, refundvisa, refundmc, refundamex;
	static VBox paymentVBox;
	static Label cashLabel, debitLabel, visaLabel, mcLabel, amexLabel;
	static VBox tableContainer;
	static HBox addDeleteButtonBox;
	static VBox paymentContainer;
	static BorderPane newSaleTitleContainer;
	static Label newSaleLabel;
	static VBox totalsContainer;
	static TableView<Item> saleTable;
	static Label subtotal, gst, total, balance;
	static Button completeInvoice;
	ArrayList<Invoice> invoices = new ArrayList<>();
	ArrayList<Customer> customers = new ArrayList<>();




	static Invoice dummyInv = new Invoice();

	static Item a;

	public static BorderPane newSaleDisplay(int invoiceNumber, Customer customerName) {
		BorderPane bp = new BorderPane();
		// New Sale
		dummyInv.setInvoiceNumber(invoiceNumber);
		if (customerName != null) {
			dummyInv.setCustomer(customerName);
		}


		// Contains all of the items on the new sale page.
		BorderPane newSalePageContainer = new BorderPane();
		// End

		// Header, containing title, invoice number and customer
		newSaleLabel = new Label("New Sale");
		Label invoiceNumberLabel = new Label("#" + Integer.toString(dummyInv.getInvoiceNumber()));
		Label customerNameLabel = new Label();

		if (customerName == null) {
			customerNameLabel.setText("Cash sale");
		}
		else {
			customerNameLabel.setText(customerName.getCustomerName());
		}

		VBox rightHeader = new VBox(invoiceNumberLabel, customerNameLabel);
		rightHeader.setAlignment(Pos.BASELINE_RIGHT);
		
		GuiCustomization.addCSS("h1", false, null, 0, newSaleLabel);
		GuiCustomization.addCSS("h2", false, null, 0, invoiceNumberLabel, customerNameLabel);

		newSaleTitleContainer = new BorderPane();
		newSaleTitleContainer.setLeft(newSaleLabel);
		newSaleTitleContainer.setRight(rightHeader);
		BorderPane.setMargin(rightHeader, new Insets(0, 20, 10, 0));

		// Table of items
		TableColumn<Item, String> itemSku, itemName;
		TableColumn<Item, Double> itemPrice;
		TableColumn<Item, Integer> itemQty;

		saleTable = new TableView<>();
		itemSku = new TableColumn<>("SKU");
		itemName = new TableColumn<>("Name");
		itemPrice = new TableColumn<>("Price");
		itemQty = new TableColumn<>("Quantity");

		saleTable.getColumns().add(itemSku);
		saleTable.getColumns().add(itemName);
		saleTable.getColumns().add(itemPrice);
		saleTable.getColumns().add(itemQty);

		itemSku.setCellValueFactory(new PropertyValueFactory<>("sku"));
		itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
		itemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		itemQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		itemSku.prefWidthProperty().bind(saleTable.widthProperty().multiply(0.125));
		itemName.prefWidthProperty().bind(saleTable.widthProperty().multiply(0.5));
		itemPrice.prefWidthProperty().bind(saleTable.widthProperty().multiply(0.125));
		itemQty.prefWidthProperty().bind(saleTable.widthProperty().multiply(0.125));


		// Add and delete buttons
		addItemButton = new Button("Add new item +");
		deleteItemButton = new Button("- Delete selected item");
		addItemButton.setOnAction(e -> { addItemButton(e); });
		deleteItemButton.setOnAction(e -> { deleteItemButton(e); });

		GuiCustomization.addCSS("add-remove-button", false, Cursor.HAND, 0, addItemButton, deleteItemButton);

		addDeleteButtonBox = new HBox(50, addItemButton, deleteItemButton);
		addDeleteButtonBox.setAlignment(Pos.BASELINE_CENTER);


		// Table of items and add/delete buttons
		tableContainer = new VBox(saleTable, addDeleteButtonBox);
		tableContainer.setPrefWidth(720);


		// Totals section
		VBox subtotalBox, gstBox, totalBox, balanceBox;
		Label subtotalLabel, gstLabel, totalLabel, balanceLabel;
		Separator s1, s2, s3, s4;

		subtotalLabel = new Label("Subtotal");
		s1 = new Separator();
		subtotal = new Label("$0.00");
		subtotalBox = new VBox(subtotalLabel, s1, subtotal);

		gstLabel = new Label("GST");
		s2 = new Separator();
		gst = new Label("$0.00");
		gstBox = new VBox(gstLabel, s2, gst);

		totalLabel = new Label("Total");
		s3 = new Separator();
		total = new Label("$0.00");
		totalBox = new VBox(totalLabel, s3, total);

		balanceLabel = new Label("Balance");
		s4 = new Separator();
		balance = new Label("$0.00");
		balanceBox = new VBox(balanceLabel, s4, balance);

		checkoutButton = new Button("> Checkout >");
		checkoutButton.setOnAction( e -> checkout(e) );

		GuiCustomization.addCSS("totals", false, null, 200, subtotalBox, gstBox, totalBox, balanceBox);
		GuiCustomization.addCSS("add-remove-button", false, Cursor.HAND, 200, checkoutButton);

		totalsContainer = new VBox(20, subtotalBox, gstBox, totalBox, balanceBox, checkoutButton);
		HBox outerContainer = new HBox(10, tableContainer, totalsContainer);


		newSalePageContainer.setTop(newSaleTitleContainer);
		newSalePageContainer.setBottom(outerContainer);
		BorderPane.setMargin(newSaleTitleContainer, new Insets(20,0,0,20));
		BorderPane.setMargin(outerContainer, new Insets(0,0,70,20));
		
		newSalePageContainer.setMinHeight(700);
		newSalePageContainer.setMinWidth(966);
		return newSalePageContainer;
	}

	// Click to add an item
	public static void addItemButton(ActionEvent event) {
		if (event.getSource() == addItemButton) {
			a = ItemSearch.display();
			if (a != null) {
				// Add items to table and invoice
				saleTable.getItems().add(a);
				dummyInv.addItem(a);
				
				// Update totals
				subtotal.setText("$" + Double.toString(Math.round(dummyInv.getSubtotal()* 100.0) / 100.0));
				gst.setText("$" + Double.toString(Math.round(dummyInv.getGST()* 100.0) / 100.0));
				total.setText("$" + Double.toString(Math.round(dummyInv.getTotal()* 100.0) / 100.0));
				dummyInv.setBalance(dummyInv.getTotal());
				balance.setText("$" + Double.toString(Math.round(dummyInv.getBalance()* 100.0) / 100.0));
			}
		}
	}

	// Click to delete an item
	public static void deleteItemButton(ActionEvent event) {
		if (event.getSource() == deleteItemButton) {
			// Updates the inventory if deleted
			ItemManager.updateInv(saleTable.getSelectionModel().getSelectedItem().getSku(),
					saleTable.getSelectionModel().getSelectedItem().getQuantity());
			
			// Removes item from table and invoice
			dummyInv.removeItem(saleTable.getSelectionModel().getSelectedItem());
			saleTable.getItems().remove(saleTable.getSelectionModel().getSelectedIndex());

			// Update totals
			subtotal.setText("$" + Double.toString(dummyInv.getSubtotal()));
			gst.setText("$" + Double.toString(dummyInv.getGST()));
			total.setText("$" + Double.toString(dummyInv.getTotal()));
			dummyInv.setBalance(dummyInv.getTotal());
			balance.setText("$" + Double.toString(Math.round(dummyInv.getBalance()* 100.0) / 100.0));

		}
	}

	// Checkout
	public static void checkout(ActionEvent event) {
		// Title
		Button goBack = new Button("< Back");
		newSaleLabel.setText("Checkout");
		newSaleTitleContainer.setCenter(goBack);
		BorderPane.setMargin(goBack, new Insets(0,0,0,340));

		completeInvoice = new Button("Complete");
		
		GuiCustomization.addCSS("add-remove-button", false, Cursor.HAND, 200, completeInvoice);
		goBack.setOnAction( e -> {
			newSaleTitleContainer.setCenter(null);
			tableContainer.getChildren().remove(paymentContainer);
			tableContainer.getChildren().addAll(saleTable, addDeleteButtonBox);
			totalsContainer.getChildren().remove(completeInvoice);
			totalsContainer.getChildren().add(checkoutButton);
			newSaleLabel.setText("New Sale");
		});

		// Buttons to specify payment type
		Label charge = new Label("Charge");
		cash = new Button("Cash");
		debit = new Button("Debit");
		visa = new Button("Visa");
		mc = new Button("Mastercard");
		amex = new Button("American Express");

		cash.setOnAction( e -> paymentProcess(e) );
		debit.setOnAction( e -> paymentProcess(e) );
		visa.setOnAction( e -> paymentProcess(e) );
		mc.setOnAction( e -> paymentProcess(e) );
		amex.setOnAction( e -> paymentProcess(e) );

		// Buttons to specify return payment type
		Label refund = new Label("Refund");
		refundcash = new Button("Refund Cash");
		refunddebit = new Button("Refund Debit");
		refundvisa = new Button("Refund Visa");
		refundmc = new Button("Refund Mastercard");
		refundamex = new Button("Refund American Express");

		VBox chargeButtonsContainer = new VBox(10, charge, cash, debit, visa, mc , amex);
		VBox refundButtonsContainer = new VBox(10, refund, refundcash, refunddebit, refundvisa, refundmc, refundamex);
		HBox chargeRefundButtonsOuter = new HBox(50, chargeButtonsContainer, refundButtonsContainer);

		GuiCustomization.addCSS("main-menu-buttons", false, Cursor.HAND, 0, goBack);
		GuiCustomization.addCSS("main-menu-buttons", false, Cursor.HAND, 300, cash, debit, visa, mc, amex,
				refundcash, refunddebit, refundvisa, refundmc, refundamex);
		GuiCustomization.addCSS("h2", false, null, 0, charge, refund);

		// Complete invoice button
		completeInvoice.setOnAction( e -> {
			
			// If balance is 0
			if (dummyInv.getBalance() == 0) {
				InvoiceManager.addInvoice(dummyInv);
				SideMenu.returnToSplashScreen();
			}
			
			// If there is still a balance remaining
			else {
				AlertBox.showAlert(400, 120, "Balance remaining", "There is still a balance of " + dummyInv.getBalance() + " remaining.");
			}
		});
		totalsContainer.getChildren().remove(checkoutButton);
		totalsContainer.getChildren().add(completeInvoice);


		// Shows the payment taken
		Label paymentLabel = new Label("Payment Taken");
		Separator s0 = new Separator();
		cashLabel = new Label("");
		debitLabel = new Label("");
		visaLabel = new Label("");
		mcLabel = new Label("");
		amexLabel = new Label("");
		subtotal = new Label("$" + Double.toString(dummyInv.getSubtotal()));
		paymentVBox = new VBox(paymentLabel, s0);
		GuiCustomization.addCSS("totals", false, null, 450, paymentVBox);


		paymentContainer = new VBox(50, chargeRefundButtonsOuter);
		paymentContainer.setPrefWidth(720);

		tableContainer.getChildren().removeAll(saleTable, addDeleteButtonBox);
		tableContainer.getChildren().add(paymentContainer);
	}

	// Processes the payment selected
	public static void paymentProcess(ActionEvent event) {
		// New window
		Stage paymentWindow = new Stage();
		paymentWindow.initModality(Modality.APPLICATION_MODAL);
		paymentWindow.setTitle("Payment");
		paymentWindow.setWidth(350);
		paymentWindow.setHeight(250);

		Label enterTotal = new Label("Enter total payment");
		TextField totalField = new TextField(Double.toString(dummyInv.getBalance()));
		Button acceptPayment = new Button("Add payment");
		VBox paymentBox = new VBox(5, enterTotal, totalField, acceptPayment);
		Scene paymentScene = new Scene(paymentBox, 120, 300);
		paymentScene.getStylesheets().add("gui.css");

		VBox.setMargin(enterTotal, new Insets(30, 0, 0, 0));
		VBox.setMargin(totalField, new Insets(0, 30, 0, 30));
		VBox.setMargin(acceptPayment, new Insets(30, 0, 0, 0));
		
		paymentBox.setAlignment(Pos.BASELINE_CENTER);
		acceptPayment.setAlignment(Pos.BASELINE_CENTER);
		totalField.setPrefWidth(50);
		
		GuiCustomization.addCSS("bg", false, null, 0, paymentBox);
		GuiCustomization.addCSS("h3", false, null, 0, enterTotal);
		GuiCustomization.addCSS("main-menu-buttons", false, Cursor.HAND, 200, acceptPayment);
		
		// Add payment button functionality
		acceptPayment.setOnAction(e -> {
			// Updates totals
			dummyInv.setBalance(dummyInv.getBalance() - Double.parseDouble(totalField.getText()));
			balance.setText("$" + dummyInv.getBalance());
			
			// Cash
			if (event.getSource() == cash) {
				if (!paymentVBox.getChildren().contains(cashLabel)) {
					paymentVBox.getChildren().add(cashLabel);
				}
				dummyInv.setCash(dummyInv.getCash() + Double.parseDouble(totalField.getText()));
				cashLabel.setText("Cash: $" + dummyInv.getCash());
			}
			
			// Debit
			else if (event.getSource() == debit) {
				if (!paymentVBox.getChildren().contains(debitLabel)) {
					paymentVBox.getChildren().add(debitLabel);
				}
				dummyInv.setDebit(dummyInv.getDebit() + Double.parseDouble(totalField.getText()));
				debitLabel.setText("Debit: $" + dummyInv.getDebit());
			}
			
			// Visa
			else if (event.getSource() == visa) {
				if (!paymentVBox.getChildren().contains(visaLabel)) {
					paymentVBox.getChildren().add(visaLabel);
				}
				dummyInv.setVisa(dummyInv.getVisa() + Double.parseDouble(totalField.getText()));
				visaLabel.setText("Visa: $" + dummyInv.getVisa());
			}
			
			// Mastercard
			else if (event.getSource() == mc) {
				if (!paymentVBox.getChildren().contains(mcLabel)) {
					paymentVBox.getChildren().add(mcLabel);
				}
				dummyInv.setMc(dummyInv.getMc() + Double.parseDouble(totalField.getText()));
				mcLabel.setText("Mastercard: $" + dummyInv.getMc());
			}
			
			// American Express
			else if (event.getSource() == amex) {
				if (!paymentVBox.getChildren().contains(amexLabel)) {
					paymentVBox.getChildren().add(amexLabel);
				}
				dummyInv.setAmex(dummyInv.getAmex() + Double.parseDouble(totalField.getText()));
				amexLabel.setText("AmEx: $" + dummyInv.getAmex());
			}
			
			if (!paymentContainer.getChildren().contains(paymentVBox)) {
				paymentContainer.getChildren().add(paymentVBox);
			}
			paymentWindow.close();
		});

		paymentWindow.setScene(paymentScene);
		paymentWindow.showAndWait();
	}

}
