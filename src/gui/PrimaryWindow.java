package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class PrimaryWindow extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {
		System.setProperty("prism.lcdtext", "false");
		primaryStage.setTitle("Sales System");
		primaryStage.setScene(SideMenu.displayMenu());
		primaryStage.show();
	}
}
