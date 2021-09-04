package gui;

import javafx.scene.Cursor;
import javafx.scene.layout.Region;

public class GuiCustomization {
	// Used for adding CSS classes to GUI objects
	public static void addCSS(String cssStyleName, boolean saveExistingCSS, Cursor cursor,
			int prefWidth, Region... region) {
		for (Region element : region) {
			// Saves/clears existing styles
			if (!saveExistingCSS) {
				element.getStyleClass().clear();
			}

			// Sets cursor
			if (cursor != null) {
				element.setCursor(cursor);
			}

			// Sets preferred width
			if (prefWidth != 0) {
				element.setPrefWidth(prefWidth);
			}

			// Adds style
			element.getStyleClass().add(cssStyleName);
		}
	}
	
	
}
