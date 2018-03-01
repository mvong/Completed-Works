/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */

package view;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Charity;
import model.User;
import resources.Constant;
import resources.Style;

// main stage to update scenes

public class AppStage extends Application {
	
	// private member variables
	private static Stage primaryStage;
	private static String currentBackgroundStyle = Style.backgroundStyle;
	private static String currentButtonStyle = Style.buttonStyle;
	private static Paint currentTextColor = Style.textColor;
	private static String currentListViewStyle = Style.listViewStyle;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setResizable(false);
		AppStage.primaryStage = primaryStage;
		setScene(new LoginScene(), Constant.LOGIN_TITLE);
	}
	
	// Set the stage to the given scene
	public static void setScene(Scene scene, String title) {
		changeUIColors(scene);
		AppStage.primaryStage.setScene(scene);
		AppStage.primaryStage.setTitle(title);
		AppStage.primaryStage.show();
	}
	
	// pop up the charity information window
	public static void popUpCharityWindow(Charity charity, User user, String scene) {
		Stage stage = new Stage(StageStyle.UNDECORATED);
		stage.initOwner(primaryStage);
		stage.setX(10);
		stage.setY(30);
		Scene charityScene = scene.equals(Constant.FAVORITE_ADD) ? new FavoriteCharityScene(charity, user) : new ViewCharityScene(charity, user);
		changeUIColors(charityScene);
		charityScene.setOnMouseClicked(e->{
			stage.close();
		});
		stage.setScene(charityScene);
		stage.show();
	}
	
	// Update the member variables for the current background, button, and text colors/styles
	public static void updateUIColors(String backgroundStyle, String buttonStyle, Paint textColor, String listViewStyle) {
		currentBackgroundStyle = backgroundStyle;
		currentButtonStyle = buttonStyle;
		currentTextColor = textColor;
		currentListViewStyle = listViewStyle;
		changeUIColors(primaryStage.getScene());
	}
	
	// Change the background UI color according to the current styles
	private static void changeUIColors(Scene scene) {
		updateBackgroundStyle(scene);
		updateButtonAndTextStyle(scene);
		updateLabelStyle(scene);
		updateTextColor(scene);
		updateHBoxControls(scene);
		updateCheckBoxStyle(scene);
		updateListViewStyle(scene);
	}
	
	// update scene background style
	private static void updateBackgroundStyle(Scene scene) {
		scene.getRoot().setStyle(currentBackgroundStyle);
	}
	
	// update button style and text color
	private static void updateButtonAndTextStyle(Scene scene) {
		scene.getRoot().getChildrenUnmodifiable().stream().filter(control -> {
			return control instanceof Button;
		}).forEach(control -> {
			Button button = (Button)control;
			button.setStyle(currentButtonStyle);
			button.setTextFill(currentTextColor);
			button.setOnMouseEntered(e->{
				button.setStyle(Style.hoverStyle);
			});
			button.setOnMouseExited(e->{
				button.setStyle(currentButtonStyle);
			});
		});
	}
	
	// update label text color
	private static void updateLabelStyle(Scene scene) {
		scene.getRoot().getChildrenUnmodifiable().stream().filter(control -> {
			return control instanceof Label;
		}).forEach(control -> {
			Label label = (Label)control;
			label.setTextFill(currentTextColor);
		});
	}
	
	// update text color
	private static void updateTextColor(Scene scene) {
		scene.getRoot().getChildrenUnmodifiable().stream().filter(control -> {
			return control instanceof Text;
		}).forEach(control -> {
			Text text = (Text)control;
			text.setFill(currentTextColor);
		});
	}
	
	// update buttons and labels in hboxes
	private static void updateHBoxControls(Scene scene) {
		scene.getRoot().getChildrenUnmodifiable().stream().filter(control -> {
			return control instanceof HBox;
		}).forEach(control -> {
			HBox hbox = (HBox)control;
			for(Node n : hbox.getChildren()) {
				if(n instanceof Label) {
					Label label = (Label)n;
					label.setTextFill(currentTextColor);
				}
				if(n instanceof Button) {
					Button button = (Button)n;
					button.setStyle(currentButtonStyle);
					button.setTextFill(currentTextColor);
					button.setOnMouseEntered(e->{
						button.setStyle(Style.hoverStyle);
					});
					button.setOnMouseExited(e->{
						button.setStyle(currentButtonStyle);
					});
				}
			}
		});
	}
	
	//update check box style
	private static void updateCheckBoxStyle(Scene scene) {
		scene.getRoot().getChildrenUnmodifiable().stream().filter(control -> {
			return control instanceof CheckBox;
		}).forEach(control -> {
			CheckBox cb = (CheckBox)control;
			cb.setTextFill(currentTextColor);
		});
	}
	
	// update list view style
	private static void updateListViewStyle(Scene scene) {
		scene.getRoot().getChildrenUnmodifiable().stream().filter(control -> {
			return control instanceof ListView;
		}).forEach(control -> {
			@SuppressWarnings("unchecked")
			ListView<String> lv = (ListView<String>)control;
			lv.setStyle(currentListViewStyle);
		});
	}
	
	// main
	public static void main(String[] args) {
		launch(args);
	}

}
