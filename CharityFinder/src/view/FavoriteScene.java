/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */
package view;

import java.util.ArrayList;

import controller.CharityController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Charity;
import model.User;
import model.UserSingleton;
import resources.Constant;
import resources.Messages;
import resources.Style;

// displays user's favorite charities
// includes listview to show list of charities

public class FavoriteScene extends Scene {
	
	// private member variables
	private Label titleLabel;
	private ListView<String> favoriteListView;
	private Label noFavesLabel;
	private Button backButton;
	private HBox titleSection;
	private VBox sceneRoot;
	private User user = UserSingleton.getUser();
	
	// constructor
	public FavoriteScene() {
		super(new VBox(), Style.WIDTH, Style.HEIGHT);
		initControls();
		addControls();
		styleControls();
		setControlActions();
	}
	
	// initialize all ui controls and the parent
	private void initControls() {
		sceneRoot = (VBox)getRoot();
		titleSection = new HBox(2);
		backButton = new Button(Messages.getString("Constant.BACK"));
		titleLabel = new Label(Messages.getString("Constant.FAVORITES"));
		noFavesLabel = new Label(Messages.getString("Constant.NO_FAVES"));
		noFavesLabel.setVisible(false);
		favoriteListView = new ListView<String>();
		ArrayList<Charity> favCharities = user.getFavoriteCharities();
		ArrayList<String> charityNames = CharityController.getCharityStrings(favCharities);
		favoriteListView.getItems().addAll(charityNames);
		if(user.getFavoriteCharities().size() == 0) {
			favoriteListView.setVisible(false);
			noFavesLabel.setVisible(true);
		}
	}
	
	// add the controls to the parent
	private void addControls() {
		titleSection.getChildren().addAll(backButton, titleLabel);
		sceneRoot.getChildren().addAll(titleSection, noFavesLabel, favoriteListView);
	}
	
	// style the controls
	private void styleControls() {
		Style.styleBackground(sceneRoot);
		sceneRoot.setAlignment(Pos.CENTER);
		Style.styleLabel(Style.TITLE, titleLabel);
		Style.styleLabel(Style.ERROR, noFavesLabel);
		Style.styleButton(backButton, Constant.LOGOUT);
		HBox.setMargin(backButton, Style.titleLabelInsets);
	}
	
	// set the control actions
	private void setControlActions() {
		backButton.setOnAction(e->{
			AppStage.setScene(new DashboardScene(), Messages.getString("Constant.DASH_TITLE"));
		});
		favoriteListView.setOnMouseClicked(e->{
			if(e.getClickCount() == 2) {
				Charity pickedCharity = user.getFavoriteCharities().get(favoriteListView.getSelectionModel().getSelectedIndex());
				AppStage.popUpCharityWindow(pickedCharity, user, Constant.VIEW);
			}
		});
	}
}
