package gui;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import manager.InvoiceManager;
import objects.Invoice;
import objects.Item;

public class PreviousSale {
	public static BorderPane displayPreviousSale(Invoice invoice) {
		// Contains all of the items on the new sale page.
		BorderPane newSalePageContainer = new BorderPane();

		// search by invoice number or customer
		// Header, containing title, invoice number and customer
		Label previousSaleLabel = new Label("Previous Sale");
		Label invoiceNumberLabel = new Label("#" + Integer.toString(invoice.getInvoiceNumber()));
		Label customerNameLabel = new Label();
		
		// Update customer label with customer name, or cash sale.
		if (invoice.getCustomer() == null) {
			customerNameLabel.setText("Cash sale");
		}
		else {
			customerNameLabel.setText(invoice.getCustomer().getCustomerName());
		}

		VBox rightHeader = new VBox(invoiceNumberLabel, customerNameLabel);

		rightHeader.setAlignment(Pos.BASELINE_RIGHT);
		GuiCustomization.addCSS("h1", false, null, 0, previousSaleLabel);
		GuiCustomization.addCSS("h2", false, null, 0, invoiceNumberLabel, customerNameLabel);
		
		BorderPane newSaleTitleContainer = new BorderPane();
		newSaleTitleContainer.setLeft(previousSaleLabel);
		newSaleTitleContainer.setRight(rightHeader);
		BorderPane.setMargin(rightHeader, new Insets(0, 20, 10, 0));


		// Table of items
		TableColumn<Item, String> itemSku, itemName;
		TableColumn<Item, Double> itemPrice;
		TableColumn<Item, Integer> itemQty;

		TableView<Item> saleTable = new TableView<>();
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

		// Table of items and add/delete buttons
		VBox tableContainer = new VBox(saleTable);
		tableContainer.setPrefWidth(720);

		// Totals section
		VBox subtotalBox, gstBox, totalBox, balanceBox;
		Label subtotalLabel, gstLabel, totalLabel, balanceLabel;
		Separator s1, s2, s3, s4;

		subtotalLabel = new Label("Subtotal");
		s1 = new Separator();
		Label subtotal = new Label(Double.toString(invoice.getSubtotal()));
		subtotalBox = new VBox(subtotalLabel, s1, subtotal);

		gstLabel = new Label("GST");
		s2 = new Separator();
		Label gst = new Label(Double.toString(invoice.getGST()));
		gstBox = new VBox(gstLabel, s2, gst);

		totalLabel = new Label("Total");
		s3 = new Separator();
		Label total = new Label(Double.toString(invoice.getTotal()));
		totalBox = new VBox(totalLabel, s3, total);

		balanceLabel = new Label("Balance");
		s4 = new Separator();
		Label balance = new Label(Double.toString(invoice.getBalance()));
		balanceBox = new VBox(balanceLabel, s4, balance);

		GuiCustomization.addCSS("totals", false, null, 200, subtotalBox, gstBox, totalBox, balanceBox);

		VBox totalsContainer = new VBox(20, subtotalBox, gstBox, totalBox, balanceBox);
		HBox outerContainer = new HBox(10, tableContainer, totalsContainer);


		newSalePageContainer.setTop(newSaleTitleContainer);
		newSalePageContainer.setBottom(outerContainer);
		BorderPane.setMargin(newSaleTitleContainer, new Insets(20,0,0,20));
		BorderPane.setMargin(outerContainer, new Insets(0,0,70,20));
		
		// Adds items from previous invoice to table
		ArrayList<Item> invoiceItems = invoice.getItems();
		for (int x = 0; x < invoiceItems.size(); x++) {
			saleTable.getItems().add(invoiceItems.get(x));
		}
		
		newSalePageContainer.setMinHeight(700);
		newSalePageContainer.setMinWidth(966);
		return newSalePageContainer;
	}
}
