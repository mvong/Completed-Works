/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */
package view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Charity;
import model.User;
import resources.Messages;
import resources.Style;

// View charity scene to display the information for the selected charity
// Displays the charity's name, type, status, classification, and location

public class ViewCharityScene extends Scene {
	
	// private member variables
	protected Label titleLabel;
	protected Label addressLabel;
	protected Label typeLabel;
	protected Label statusLabel;
	protected Label classificationLabel;
	protected Text addressText;
	protected Text typeText;
	protected Text statusText;
	protected Text classificationText;
	protected Button exitButton;
	protected GridPane sceneRoot;
	protected Charity charity;
	protected User user;
	
	// constructor
	public ViewCharityScene(Charity charity, User user) {
		super(new GridPane(), Style.WIDTH, Style.CHARITY_HEIGHT);
		this.charity = charity;
		this.user = user;
		initControls();
		addControls();
		styleControls();
		setControlActions();
	}
	
	// initialize all ui controls and parent
	protected void initControls() {
		sceneRoot = (GridPane)getRoot();
		exitButton = new Button("X");
		titleLabel = new Label(charity.getName());
		addressLabel = new Label(Messages.getString("Constant.ADDRESS")); addressText = new Text(charity.getFullAddress());
		typeLabel = new Label(Messages.getString("Constant.TYPE")); typeText = new Text(charity.getType());
		statusLabel = new Label(Messages.getString("Constant.STATUS")); statusText = new Text(charity.getStatus());
		classificationLabel = new Label(Messages.getString("Constant.CLASSIFICATION")); classificationText = new Text(charity.getClassification());
	}
	
	// add controls to the parent
	protected void addControls() {
		sceneRoot.add(titleLabel, 1, 0); sceneRoot.add(exitButton, 0, 0);
		sceneRoot.add(addressLabel, 1, 1); sceneRoot.add(addressText, 1, 1);
		sceneRoot.add(typeLabel, 1, 2); sceneRoot.add(typeText, 1, 2);
		sceneRoot.add(statusLabel, 1, 3); sceneRoot.add(statusText, 1, 3);
		sceneRoot.add(classificationLabel, 1, 4); sceneRoot.add(classificationText, 1, 4);
	}
	
	// style the controls and the parent
	protected void styleControls() {
		sceneRoot.getColumnConstraints().add(new ColumnConstraints(50));
		sceneRoot.getColumnConstraints().add(new ColumnConstraints(700));
		sceneRoot.getColumnConstraints().add(new ColumnConstraints(50));
		for(int i = 0 ; i < 7 ; i++) {
			sceneRoot.getRowConstraints().add(new RowConstraints(100));
		}
		Style.styleBackground(sceneRoot);
		Style.styleLabel(Style.TITLE, titleLabel);
		Style.styleLabel(Style.TINY, addressLabel);
		Style.styleLabel(Style.TINY, typeLabel);
		Style.styleLabel(Style.TINY, statusLabel);
		Style.styleLabel(Style.TINY, classificationLabel);
		Style.styleButton(exitButton, "X");
		GridPane.setHalignment(exitButton, HPos.LEFT);
		GridPane.setValignment(exitButton, VPos.TOP);
		GridPane.setHalignment(titleLabel, HPos.CENTER);
		GridPane.setHalignment(addressLabel, HPos.LEFT); GridPane.setHalignment(addressText, HPos.RIGHT);
		GridPane.setHalignment(typeLabel, HPos.LEFT); GridPane.setHalignment(typeText, HPos.RIGHT);
		GridPane.setHalignment(statusLabel, HPos.LEFT); GridPane.setHalignment(statusText, HPos.RIGHT);
		GridPane.setHalignment(classificationLabel, HPos.LEFT	); GridPane.setHalignment(classificationText, HPos.RIGHT);
		sceneRoot.getChildren().stream().filter(control -> {
			return control instanceof Text;
		}).forEach(control -> {
			Text text = (Text)control;
			Style.styleText(text);
			text.setWrappingWidth(450);
		});
	}
	
	// set the control actions
	protected void setControlActions() {	
		exitButton.setOnAction(e->{
			Stage stage = (Stage)getWindow();
			stage.close();
		});
	}
}
