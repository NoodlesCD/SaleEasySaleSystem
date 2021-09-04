package gui;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import manager.CustomerManager;
import objects.Customer;

public class EditCustomer {
	static Label customerLabel;
	static TextField nameField, addressField, cityField, provinceField, postalField, phoneField, phoneField2;
	static Button search;
	
	public static BorderPane editCustomer(Customer customer) {
		// Contains all of the items on the new sale page.
		BorderPane customerPageContainer = new BorderPane();

		// search by invoice number or customer
		// Header, containing title, invoice number and customer
		customerLabel = new Label("New Customer");
		GuiCustomization.addCSS("h1", false, null, 0, customerLabel);
		
		BorderPane customerTitleContainer = new BorderPane();
		customerTitleContainer.setLeft(customerLabel);
		
		Label name = new Label("Full name");
		nameField = new TextField();
		VBox nameBox = new VBox(10, name, nameField);
		
		Label address = new Label("Street Address");
		addressField = new TextField();
		VBox addressBox = new VBox(10, address, addressField);
		
		Label city = new Label("City");
		cityField = new TextField();
		VBox cityBox = new VBox(10, city, cityField);
		
		Label province = new Label("Province");
		provinceField = new TextField();
		VBox provinceBox = new VBox(10, province, provinceField);
		
		Label postal = new Label("Postal code");
		postalField = new TextField();
		VBox postalBox = new VBox(10, postal, postalField);
		
		VBox leftBox = new VBox(20, nameBox, addressBox, cityBox, provinceBox, postalBox);
		leftBox.setPrefWidth(350);
		
		Label phone = new Label("Primary phone number");
		phoneField = new TextField();
		VBox phoneBox = new VBox(10, phone, phoneField);
		
		Label phone2 = new Label("Secondary phone number");
		phoneField2 = new TextField();
		VBox phoneBox2 = new VBox(10, phone2, phoneField2);
		
		VBox rightBox = new VBox(20, phoneBox, phoneBox2);
		rightBox.setPrefWidth(350);
		HBox inputs = new HBox(20, leftBox, rightBox);
		
		search = new Button("Create new customer");
		search.setOnAction(e -> createNewCustomer(e, customer));
		Region spacer = new Region();
		spacer.setPrefHeight(20);
		HBox.setHgrow(spacer, Priority.ALWAYS);
		VBox buttonBox = new VBox(20, spacer, search);
		
		VBox inputContainer = new VBox(10, inputs, buttonBox);
		inputContainer.setPrefWidth(720);
		
		GuiCustomization.addCSS("h2", false, null, 0, name, address, city, province, postal, phone, phone2);
		GuiCustomization.addCSS("add-remove-button", false, Cursor.HAND, 0, search);
		
		customerPageContainer.setTop(customerTitleContainer);
		customerPageContainer.setCenter(inputContainer);
		BorderPane.setMargin(customerTitleContainer, new Insets(20,0,0,30));
		BorderPane.setMargin(inputContainer, new Insets(0,0,0,30));
		
		
		
		if (customer != null) {
			customerLabel.setText("Edit Customer");
			search.setText("Save changes");
			nameField.setText(customer.getCustomerName());
			addressField.setText(customer.getStreetAddress());
			cityField.setText(customer.getCity());
			provinceField.setText(customer.getProvince());
			postalField.setText(customer.getPostalCode());
			phoneField.setText(customer.getPrimaryPhoneNumber());
			phoneField2.setText(customer.getSecondaryPhoneNumber());
		}
		
		customerPageContainer.setMinHeight(700);
		customerPageContainer.setMinWidth(966);
		
		return customerPageContainer;
	}
	
	public static void createNewCustomer(ActionEvent e, Customer customer) {
		if (!nameField.getText().isEmpty() && !addressField.getText().isEmpty() && 
			!cityField.getText().isEmpty() && !provinceField.getText().isEmpty() && 
			!postalField.getText().isEmpty() && !phoneField.getText().isEmpty()) {
			if (customer == null) {
				CustomerManager.addCustomer(new Customer(CustomerManager.generateCustomerId(), nameField.getText(), addressField.getText(), cityField.getText(),
						provinceField.getText(), postalField.getText(), phoneField.getText(),
						0, 0));
				AlertBox.showAlert(400, 120, "Success", "Customer added successfully");
				SideMenu.returnToSplashScreen();
			}
			
			else {
				CustomerManager.editCustomer(new Customer(customer.getCustomerId(), nameField.getText(), addressField.getText(), cityField.getText(),
						provinceField.getText(), postalField.getText(), phoneField.getText(),
						0, 0));
				AlertBox.showAlert(400, 120, "Success", "Customer updated.");
				SideMenu.returnToSplashScreen();
			}
		}
		else {
			AlertBox.showAlert(400, 120, "Required fields missing", "There are required fields missing.");
		}
	}
}
