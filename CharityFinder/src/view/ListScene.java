/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */

package view;

import java.util.ArrayList;
import java.util.HashMap;

import controller.CharityController;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

// list scene that displays the list of charities generated from Charity Navigator REST API

public class ListScene extends Scene {
	
	// private member variables
	private Label titleLabel;
	private Button backButton;
	private HBox titleSection;
	private VBox sceneRoot;
	private ListView<String> charityListView;
	private Label pickLabel;
	private ComboBox<String> stateComboBox;
	private HBox chooseHBox;
	private ArrayList<Charity> charities;
	private ArrayList<String> charityNames;
	private HashMap<String, Charity> charityMap;
	private User user = UserSingleton.getUser();
	
	// constructor
	public ListScene() {
		super(new VBox(5), Style.WIDTH, Style.HEIGHT);
		initControls();
		addControls();
		styleControls();
		setControlActions();
	}
	
	// initialize all ui controls 
	private void initControls() {
		sceneRoot = (VBox)getRoot();
		titleSection = new HBox(2);
		backButton = new Button(Messages.getString("Constant.BACK"));
		titleLabel = new Label(Messages.getString("Constant.LIST"));
		pickLabel = new Label(Messages.getString("Constant.PICK"));
		stateComboBox = new ComboBox<String>();
		chooseHBox = new HBox(5);
		charityListView = new ListView<String>();
		charities = CharityController.generateDefaultCharityList();
		charityNames = CharityController.getCharityStrings(charities);
		charityMap = CharityController.getCharityMap(charities);
		charityListView.getItems().addAll(charityNames);
	}
	
	// add controls to parent
	private void addControls() {
		titleSection.getChildren().addAll(backButton, titleLabel);
		chooseHBox.getChildren().addAll(pickLabel, stateComboBox);
		sceneRoot.getChildren().addAll(titleSection, chooseHBox, charityListView);
	}
	
	// style the controls
	private void styleControls() {
		Style.styleBackground(sceneRoot);
		sceneRoot.setAlignment(Pos.TOP_CENTER);
		Style.styleLabel(Style.TITLE, titleLabel);
		Style.styleButton(backButton, Constant.LOGOUT);
		Style.styleLabel(Style.TINY, pickLabel);
		stateComboBox.setItems(FXCollections.observableList(CharityController.stateAbbrevs));
		stateComboBox.getSelectionModel().select(4);
		chooseHBox.setAlignment(Pos.CENTER);
		HBox.setMargin(backButton, Style.titleLabelInsets);
		
	}
	
	// set the control actions
	private void setControlActions() {
		backButton.setOnAction(e -> {
			AppStage.setScene(new DashboardScene(), Messages.getString("Constant.DASH_TITLE"));
		});
		stateComboBox.setOnAction(e->{
			charities = CharityController.generateListByState(stateComboBox.getValue());
			charityNames = CharityController.getCharityStrings(charities);
			charityMap = CharityController.getCharityMap(charities);
			charityListView.setItems(FXCollections.observableArrayList(charityNames));
		});
		charityListView.setOnMouseClicked(e->{
			if(e.getClickCount() == 2) {
				Charity pickedCharity = charityMap.get(charityListView.getSelectionModel().getSelectedItem());
				AppStage.popUpCharityWindow(pickedCharity, user, Constant.FAVORITE_ADD);
			}
		});
	}
	
}
