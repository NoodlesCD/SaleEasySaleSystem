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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import manager.CustomerManager;
import manager.InvoiceManager;
import objects.Customer;
import objects.Invoice;
import objects.Item;

public class ExistingCustomer {
	static TextField nameField, phoneField, addressField, postalField;
	static VBox buttonBox, inputContainer;
	
	/**
	 * 
	 * @param newSale Set as true, if a New Sale is being generated after searching.
	 * @param prevSale Set as true, if a Previous Sale is being viewed after searching.
	 * @param prevCust Set as true, if viewing/editing information on an existing customer.
	 */
	public static BorderPane searchExistingCustomer(boolean newSale, boolean prevSale, boolean prevCust) {
		// Contains all of the items on the new sale page.
		BorderPane customerPageContainer = new BorderPane();

		// search by invoice number or customer
		// Header, containing title, invoice number and customer
		Label customerLabel = new Label("Search for Customer");
		GuiCustomization.addCSS("h1", false, null, 0, customerLabel);
		
		BorderPane customerTitleContainer = new BorderPane();
		customerTitleContainer.setLeft(customerLabel);
		
		// Text input fields
		Label name = new Label("Search by name");
		nameField = new TextField();
		VBox nameBox = new VBox(10, name, nameField);
		
		Label phone = new Label("Search by phone number");
		phoneField = new TextField();
		VBox phoneBox = new VBox(10, phone, phoneField);
		
		Label address = new Label("Search by address");
		addressField = new TextField();
		VBox addressBox = new VBox(10, address, addressField);
		
		Label postal = new Label("Search by postal code");
		postalField = new TextField();
		VBox postalBox = new VBox(10, postal, postalField);
		
		Button search = new Button("Search");
		search.setOnAction(e-> {
			searchForCustomer(e, newSale, prevSale, prevCust);
		});
		
		Region spacer = new Region();
		spacer.setPrefHeight(20);
		HBox.setHgrow(spacer, Priority.ALWAYS);
		buttonBox = new VBox(10, spacer, search);
		
		inputContainer = new VBox(30, nameBox, phoneBox, addressBox, postalBox, buttonBox);
		inputContainer.setPrefWidth(720);
		inputContainer.setAlignment(Pos.TOP_CENTER);
		
		GuiCustomization.addCSS("h2", false, null, 0, name, phone, address, postal);
		GuiCustomization.addCSS("add-remove-button", false, Cursor.HAND, 0, search);
		
		customerPageContainer.setTop(customerTitleContainer);
		customerPageContainer.setCenter(inputContainer);
		BorderPane.setMargin(customerTitleContainer, new Insets(20,0,0,30));
		BorderPane.setMargin(inputContainer, new Insets(0,0,0,30));
		
		customerPageContainer.setMinHeight(700);
		customerPageContainer.setMinWidth(966);
		return customerPageContainer;
	}
	
	public static void searchForCustomer(ActionEvent e, boolean newSale, boolean prevSale, boolean prevCust) {
		// A table of customers meeting search criteria
		ArrayList<Customer> customerList = CustomerManager.getCustomerList();
		ArrayList<Customer> foundCustomerList = new ArrayList<>();
		
		TableColumn<Customer, String> nameColumn, phoneColumn, postalColumn, addressColumn;
		TableView<Customer> customerTable = new TableView<>();
		nameColumn = new TableColumn<>("Name");
		phoneColumn = new TableColumn<>("Phone #");
		postalColumn = new TableColumn<>("Postal Code");
		addressColumn = new TableColumn<>("Address");
		
		customerTable.getColumns().add(nameColumn);
		customerTable.getColumns().add(phoneColumn);
		customerTable.getColumns().add(postalColumn);
		customerTable.getColumns().add(addressColumn);
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
		phoneColumn.setCellValueFactory(new PropertyValueFactory<>("primaryPhoneNumber"));
		postalColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("streetAddress"));
		
		Button selectButton = new Button("Select");
		GuiCustomization.addCSS("add-remove-button", false, Cursor.HAND, 0, selectButton);
		
		// Looks for customers with matching search criteria
		for (int x = 0; x < customerList.size(); x++) {
			boolean name = customerList.get(x).getCustomerName().toLowerCase().contains(nameField.getText().toLowerCase()) &&
					nameField.getText().length() > 0;
			boolean phone = (customerList.get(x).getPrimaryPhoneNumber().contains(phoneField.getText()) ||
					customerList.get(x).getSecondaryPhoneNumber().contains(phoneField.getText())) &&
					phoneField.getText().length() > 0;
			boolean postal = customerList.get(x).getPostalCode().toLowerCase().contains(postalField.getText().toLowerCase()) &&
					postalField.getText().length() > 0;
			boolean address = customerList.get(x).getStreetAddress().toLowerCase().contains(addressField.getText().toLowerCase()) &&
					addressField.getText().length() > 0;
			if (name || phone || postal || address) {
				foundCustomerList.add(customerList.get(x));
				customerTable.getItems().add(customerList.get(x));
			}
		}
		
		// If no customers are found
		if (foundCustomerList.isEmpty()) {
			AlertBox.showAlert(400, 120, "No customers found", "No customers found");
		}
		
		// If customers are found
		else {
			inputContainer.getChildren().clear();
			inputContainer.getChildren().addAll(customerTable, selectButton);
			
			// Generates a new sale with the selected customer
			if (newSale) {
				selectButton.setOnAction(a -> {
					if (customerTable.getSelectionModel().getSelectedItem() != null) {
						SideMenu.setCenterScreen(NewSale.newSaleDisplay(InvoiceManager.getInvoiceNumber(), customerTable.getSelectionModel().getSelectedItem()));
					}
				});
			}
			
			// Generates a list of previous sales from the selected customer 
			else if (prevSale) {
				selectButton.setOnAction(a -> {
					ArrayList<Invoice> invoiceList = InvoiceManager.getInvoiceList();
					ArrayList<Invoice> foundInvoiceList = new ArrayList<>();
					
					for (int x = 0; x < invoiceList.size(); x++) {
						if (invoiceList.get(x).getCustomer().equals(customerTable.getSelectionModel().getSelectedItem())) {
							foundInvoiceList.add(invoiceList.get(x));
						}
					}
					
					TableView<Invoice> invoiceTable = new TableView<>();
					TableColumn<Invoice, Integer> invNumberColumn = new TableColumn<>("Invoice #");
					TableColumn<Invoice, Customer> customerColumn = new TableColumn<>("Customer");
					TableColumn<Invoice, Double> totalColumn = new TableColumn<>("Total");
					
					invoiceTable.getColumns().add(invNumberColumn);
					invoiceTable.getColumns().add(customerColumn);
					invoiceTable.getColumns().add(totalColumn);
					
					invNumberColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
					customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
					totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
					
					if (foundInvoiceList.size() > 0) {
						for (int x = 0; x < foundInvoiceList.size(); x++) {
							invoiceTable.getItems().add(foundInvoiceList.get(x));
							inputContainer.getChildren().clear();
							inputContainer.getChildren().addAll(invoiceTable, selectButton);
							selectButton.setOnAction(ab -> {
								SideMenu.setCenterScreen(PreviousSale.displayPreviousSale(invoiceTable.getSelectionModel().getSelectedItem()));
							});
						}
					}
					else {
						AlertBox.showAlert(400, 120, "No invoices found", "No invoices found");
					}
				});
				
			}
			
			// Proceeds to the edit customer page with selected customer's information
			else if (prevCust) {
				selectButton.setOnAction(a -> {
					SideMenu.setCenterScreen(EditCustomer.editCustomer(customerTable.getSelectionModel().getSelectedItem()));
				});
			}
		}
	}
}
