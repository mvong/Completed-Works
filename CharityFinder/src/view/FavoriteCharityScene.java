/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */
package view;


import controller.CharityController;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Charity;
import model.User;
import resources.Constant;
import resources.Messages;
import resources.Style;

// Favorite charity information scene
// Displays the information for each charity
// Inherits from ViewCharityScene
// Includes a favorite button to allow user to favorite the charity

public class FavoriteCharityScene extends ViewCharityScene {
	
	// private favorite button
	private Button favoriteButton;
	
	// constructor
	public FavoriteCharityScene(Charity charity, User user) {
		super(charity, user);
		initControls();
		addControls();
		styleControls();
		setControlActions();
	}
	
	// initialize parent controls and fav button
	protected void initControls() {
		super.initControls();
		favoriteButton = new Button(Messages.getString("Constant.FAVORITE_ADD"));
	}
	
	// add all controls to parent
	protected void addControls() {
		super.addControls();
		sceneRoot.add(favoriteButton, 1, 5);
	}
	
	// style all controls and parent
	protected void styleControls() {
		super.styleControls();
		Style.styleButton(favoriteButton, Constant.FAVORITE_ADD);
		GridPane.setHalignment(favoriteButton, HPos.CENTER);
	}
	
	// set the fav button action
	protected void setControlActions() {
		super.setControlActions();
		favoriteButton.setOnAction(e->{
			// add favorite to user's list
			CharityController.addCharityToList(user, charity);
			Stage stage = (Stage)getWindow();
			stage.close();
		});
	}

}
