/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */
package view;

import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.User;
import model.UserSingleton;
import resources.Constant;
import resources.Messages;
import resources.Style;

// dashboard scene to display the user's options: view profile, favorite charities, list of charities

public class DashboardScene extends Scene {
	
	// private member variables
	private Label titleLabel;
	private Button favoriteButton;
	private Button mapButton;
	private Button profileButton;
	private Button logoutButton;
	private GridPane sceneRoot;
	private User user = UserSingleton.getUser();
	
	// constructor
	public DashboardScene() {
		super(new GridPane(), Style.WIDTH, Style.HEIGHT);
		initControls();
		addControls();
		styleControls();
		setControlActions();
	}
	
	// initialize all ui controls and parent
	private void initControls() {
		sceneRoot = (GridPane)getRoot();
		logoutButton = new Button(Messages.getString("Constant.LOGOUT"));
		logoutButton.setId(Constant.LOGOUT);
		titleLabel = new Label(Messages.getString("Constant.DASH_WELCOME") + user.getFirstName() + "!");
		favoriteButton = new Button(Messages.getString("Constant.FAVORITES"));
		favoriteButton.setId(Constant.DASH_ITEM);
		mapButton = new Button(Messages.getString("Constant.LIST"));
		mapButton.setId(Constant.DASH_ITEM);
		profileButton = new Button(Messages.getString("Constant.PROFILE"));
		profileButton.setId(Constant.DASH_ITEM);
	}
	
	// add controls to parent
	private void addControls() {
		sceneRoot.add(logoutButton, 0, 0, 1, 1); sceneRoot.add(titleLabel, 2, 0, 4, 1);
		sceneRoot.add(favoriteButton, 2, 2, 2, 2); sceneRoot.add(mapButton, 4, 2, 2, 2);
		sceneRoot.add(profileButton, 2, 5, 2, 2); 
	}
	
	// style the controls and parent
	private void styleControls() {
		for(int i = 0; i < 8 ; i++) {
			sceneRoot.getColumnConstraints().add(new ColumnConstraints(100));
			sceneRoot.getRowConstraints().add(new RowConstraints(50));
		}
		Style.styleBackground(sceneRoot);
		Style.styleLabel(Style.TITLE, titleLabel);
		sceneRoot.getChildren().stream().forEach(control -> GridPane.setHalignment(control, HPos.CENTER));
		sceneRoot.getChildren().stream().filter(control-> {
			return control instanceof Button;
		}).forEach(control -> {
				Button currButton = (Button)control;
				Style.styleButton(currButton, currButton.getId());
		});
	}
	
	// set the control actions
	private void setControlActions() {
		logoutButton.setOnAction(e->{
			AppStage.updateUIColors(Style.backgroundStyle, Style.buttonStyle, Style.textColor, Style.listViewStyle);
			Messages.setResourceBundle(Messages.BUNDLE_NAME);
			UserSingleton.logOut();
			AppStage.setScene(new LoginScene(), Messages.getString("Constant.LOGIN_TITLE"));
		});
		favoriteButton.setOnAction(e->{
			AppStage.setScene(new FavoriteScene(), Messages.getString("Constant.FAVORITES"));
		});
		mapButton.setOnAction(e->{
			AppStage.setScene(new ListScene(), Messages.getString("Constant.LIST"));
		});
		profileButton.setOnAction(e->{
			AppStage.setScene(new ProfileScene(), Messages.getString("Constant.PROFILE"));
		});
	}
}
