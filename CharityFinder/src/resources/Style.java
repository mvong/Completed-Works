/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */

package resources;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

// Style class for gui styling

public class Style {
	
	// type of labels
	public final static String TITLE = "title";
	public final static String TEXT = "text";
	public final static String TINY = "tiny";
	public final static String ERROR = "error";
			
	// fonts
	public static Font titleFont = Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 30);
	public static Font xFont = Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 25);
	public static Font textFont = Font.font("Helvetica", FontWeight.BLACK, FontPosture.REGULAR, 20);
	public static Font tinyFont = Font.font("Helvetica", FontWeight.BLACK, FontPosture.REGULAR, 15);
	public static Font errorFont = Font.font("Helvetica", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 20);
	
	// default styles
	public static String backgroundStyle = "-fx-background-color: maroon";
	public static String buttonStyle = "-fx-background-color: gray";
	public static String exitButtonStyle = "-fx-background-color: transparent";
	public static String listViewStyle = "-fx-control-inner-background: maroon;\n"
			+ "-fx-background-color: transparent;\n"
			+ "-fx-text-fill: white";
	public static String hoverStyle = "-fx-background-color: -fx-shadow-highlight-color";
	
	// custom styles
	public static String customBackgroundStyle = "-fx-background-color: royalblue";
	public static String customButtonStyle = "-fx-background-color: transparent;\n"
			+ "-fx-border-color: white;\n";
	public static String customListViewStyle = "-fx-control-inner-background: royalblue;\n"
			+ "-fx-background-color: transparent;\n";
	public static String profileTextInputStyle = "-fx-text-fill: white;\n "
			+ "-fx-background-color: transparent;";
	
	// colors
	public static Paint textColor = Color.WHITE;
	public static Paint errorTextColor = Color.RED;
	public static Paint customTextColor = Color.GOLD;
	
	// Login scene sizes
	public static double LOGIN_WIDTH = 500.0;
	public static double LOGIN_HEIGHT = 600.0;
	
	// Other scene sizes
	public static double WIDTH = 800.0;
	public static double HEIGHT = 500.0;
	public static double CHARITY_HEIGHT = 700.0;
	
	// top, right, bottom, left
	public static Insets titleInsets = new Insets(20, 0, 30, 0);
	public static Insets textInsets = new Insets(0, 0, 5, 0);
	public static Insets textfieldInsets = new Insets(0, 0, 20, 0);
	public static Insets buttonInsets = new Insets(10, 0, 20, 0);
	
	// rest title label insets
	public static Insets titleLabelInsets = new Insets(5, 200, 5, 10);
	
	// style textfields
	public static void styleTextField(TextField textField) {
		textField.setFont(textFont);
	}
	// style button
	public static void styleButton(Button button, String typeButton) {
		Font buttonFont = (typeButton == null || typeButton.equals(Constant.DASH_ITEM)) ? textFont : tinyFont;
		if(typeButton != null && typeButton.equals(Constant.DASH_ITEM)) {
			button.setPrefSize(120, 100);
		}
		String bStyle = buttonStyle;
		if(typeButton != null && typeButton.equals("X")) {
			bStyle = exitButtonStyle;
		}
		if(typeButton != null && typeButton.equals(Constant.FAVORITE_ADD)) {
			buttonFont = xFont;
		}
		button.setStyle(bStyle);
		button.setFont(buttonFont);
		button.setTextFill(textColor);
		button.setTextAlignment(TextAlignment.CENTER);
		button.setWrapText(true);
	}
	
	// style checkbox
	public static void styleCheckBox(CheckBox checkBox) {
		checkBox.setFont(tinyFont);
		checkBox.setTextFill(textColor);
	}
	// style text
	public static void styleText(Text text) {
		text.setFill(textColor);
		text.setFont(textFont);
	}

	// style label
	public static void styleLabel(String type, Label label) {
		Font labelFont = null;
		Paint labelTextColor = textColor;
		switch(type) {
			case TITLE:
				labelFont = titleFont;
				break;
			case TEXT:
				labelFont = textFont;
				break;
			case TINY:
				labelFont = tinyFont;
				break;
			case ERROR:
				labelFont = errorFont;
				labelTextColor = errorTextColor;
				break;
		}
		label.setWrapText(true);
		label.setFont(labelFont);
		label.setTextFill(labelTextColor);
	}
	
	// style background
	public static void styleBackground(Parent root) {
		root.setStyle(backgroundStyle);
	}
	
	// style textinputcontrol
	public static void styleTextInput(TextInputControl textInput) {
		textInput.setFont(Style.textFont);
		textInput.setStyle(profileTextInputStyle);
		textInput.setEditable(false);
	}
	
}
