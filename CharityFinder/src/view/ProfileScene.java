/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */

package view;

import controller.ProfileController;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import model.User;
import model.UserSingleton;
import resources.Constant;
import resources.Messages;
import resources.Style;

// user profile scene to display user's name, age and about info

public class ProfileScene extends Scene {
	
	// private member variables
	private Label titleLabel;
	private Label nameLabel;
	private Label ageLabel;
	private Label aboutLabel;
	private Text nameText;
	private Text ageText;
	private Text aboutText;
	private Button backButton;
	private GridPane sceneRoot;
	
	private TextField fNameTextField;
	private TextField lNameTextField;
	private TextField ageTextField;
	private TextArea aboutTextArea;
	private CheckBox editCheckBox;
	private Button saveButton;
	private User user = UserSingleton.getUser();
	
	// constructor
	public ProfileScene() {
		super(new GridPane(), Style.WIDTH, Style.HEIGHT);
		initControls();
		addControls();
		styleControls();
		setControlActions();
	}
	
	// initialize all controls and parent
	private void initControls() {
		sceneRoot = (GridPane)getRoot();
		backButton = new Button(Messages.getString("Constant.BACK"));
		backButton.setId(Constant.BACK);
		titleLabel = new Label(Messages.getString("Constant.PROFILE"));
		nameLabel = new Label(Messages.getString("Constant.NAME"));
		ageLabel = new Label(Messages.getString("Constant.AGE"));
		aboutLabel = new Label(Messages.getString("Constant.ABOUT"));
		nameText = new Text(user.getFirstName() + " " + user.getLastName());
		ageText = new Text(String.valueOf(user.getAge()));
		aboutText = new Text(user.getAboutMe());
		fNameTextField = new TextField(user.getFirstName());
		lNameTextField = new TextField(user.getLastName());
		ageTextField = new TextField(String.valueOf(user.getAge())) {
			@Override public void replaceText(int start, int end, String text) {
		        if (!text.matches("[a-z;',./{}]")) {
		            super.replaceText(start, end, text);
		        }
		    }
		};
		aboutTextArea = new TextArea(user.getAboutMe());
		fNameTextField.setVisible(false);
		lNameTextField.setVisible(false);
		ageTextField.setVisible(false);
		aboutTextArea.setVisible(false);
		saveButton = new Button(Messages.getString("Constant.SAVE"));
		editCheckBox = new CheckBox(Messages.getString("Constant.EDIT"));
	}
	
	// add controls to parent
	private void addControls() {
		sceneRoot.add(backButton, 0, 0); sceneRoot.add(titleLabel, 1, 0, 6, 1); sceneRoot.add(editCheckBox, 7, 0);
		sceneRoot.add(nameLabel, 2, 1); sceneRoot.add(nameText, 3, 1); sceneRoot.add(fNameTextField, 3, 1, 2, 1); sceneRoot.add(lNameTextField, 5, 1, 2, 1);
		sceneRoot.add(ageLabel, 2, 2); sceneRoot.add(ageText, 3, 2); sceneRoot.add(ageTextField, 3, 2, 4, 1);
		sceneRoot.add(aboutLabel, 2, 3); sceneRoot.add(aboutText, 3, 3, 4, 5); sceneRoot.add(aboutTextArea, 3, 3, 4, 2);
		sceneRoot.add(saveButton, 3, 6, 2, 1);
	}
	
	// style the controls;
	private void styleControls() {
		for(int i = 0 ; i < 8 ; i++) {
			sceneRoot.getColumnConstraints().add(new ColumnConstraints(100));
			sceneRoot.getRowConstraints().add(new RowConstraints(50));
		}
		Style.styleBackground(sceneRoot);
		Style.styleButton(backButton, Constant.LOGOUT);
		Style.styleButton(saveButton, "Save Info");
		Style.styleLabel(Style.TITLE, titleLabel);
		Style.styleLabel(Style.TINY, nameLabel);
		Style.styleLabel(Style.TINY, ageLabel);
		Style.styleLabel(Style.TINY, aboutLabel);
		GridPane.setHalignment(titleLabel, HPos.CENTER);
		GridPane.setHalignment(backButton, HPos.CENTER);
		GridPane.setHalignment(saveButton, HPos.CENTER);
		saveButton.setVisible(false);
		sceneRoot.getChildren().stream().filter(control -> {
			return control instanceof Text;
		}).forEach(control -> {
			Text text = (Text)control;
			Style.styleText(text);
			text.setWrappingWidth(400);
			GridPane.setHalignment(text, HPos.LEFT);
			GridPane.setValignment(text, VPos.TOP);
		});
		sceneRoot.getChildren().stream().filter(control -> {
			return control instanceof Label;
		}).forEach(control -> {
			Label label = (Label)control;
			GridPane.setValignment(label, VPos.TOP);
		});
		sceneRoot.getChildren().stream().filter(control -> {
			return control instanceof TextInputControl;
		}).forEach(control -> {
			TextInputControl textInput = (TextInputControl)control;
			textInput.setMaxWidth(400);
			if(textInput instanceof TextArea) {
				TextArea ta = (TextArea)textInput;
				ta.setWrapText(true);
			}
			GridPane.setValignment(textInput, VPos.TOP);
		});
	}
	
	// set control actions
	private void setControlActions() {
		backButton.setOnAction(e->{
			AppStage.setScene(new DashboardScene(), Messages.getString("Constant.DASH_TITLE"));
		});
		editCheckBox.setOnAction(e->{
			boolean selected = editCheckBox.isSelected();
			updateVisibility(selected);
		});
		saveButton.setOnAction(e->{
			nameText.setText(fNameTextField.getText().trim() + " " + lNameTextField.getText().trim());
			ageText.setText(ageTextField.getText().trim());
			aboutText.setText(aboutTextArea.getText().trim());
			editCheckBox.setSelected(false);
			String firstName = fNameTextField.getText().trim();
			String lastName = lNameTextField.getText().trim();
			String age = ageText.getText().trim();
			String about = aboutText.getText().trim();
			user.setAge(Integer.valueOf(age));
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setAboutMe(about);
			ProfileController.updateUserInfo(user);
			updateVisibility(editCheckBox.isSelected());
		});
	}
	
	// hide fields
	private void updateVisibility(boolean visible) {
		nameText.setVisible(!visible);
		ageText.setVisible(!visible);
		aboutText.setVisible(!visible);
		fNameTextField.setVisible(visible);
		lNameTextField.setVisible(visible);
		ageTextField.setVisible(visible);
		aboutTextArea.setVisible(visible);
		saveButton.setVisible(visible);
		fNameTextField.setText(user.getFirstName());
		lNameTextField.setText(user.getLastName());
		ageTextField.setText(String.valueOf(user.getAge()));
		aboutTextArea.setText(user.getAboutMe());
	}
}
