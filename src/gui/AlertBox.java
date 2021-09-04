package gui;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import manager.CustomerManager;
import objects.Customer;

public class AlertBox {
	public static void showAlert(int width, int height, String title, String message) {
		Stage alertWindow = new Stage();
		alertWindow.setTitle(title);
		alertWindow.setWidth(width);
		alertWindow.setHeight(height);
		alertWindow.initStyle(StageStyle.UNDECORATED);
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		
		Label alertLabel = new Label(message);
		Button closeButton = new Button("Ok");
		closeButton.setOnAction( e -> {
			alertWindow.close();
		});
		
		VBox container = new VBox(alertLabel, closeButton);
		container.setAlignment(Pos.BASELINE_CENTER);
		
		GuiCustomization.addCSS("h2", false, null, 0, alertLabel);
		GuiCustomization.addCSS("main-menu-buttons", false, Cursor.HAND, 0, closeButton);
		GuiCustomization.addCSS("bg-warning", false, null, 0, container);
		
		Scene alertScene = new Scene(container);
		alertScene.getStylesheets().add("gui.css");
		alertWindow.setScene(alertScene);
		alertWindow.show();
	}
}
