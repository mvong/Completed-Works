/* Mark Vong
 * ITP 368
 * Final GUI project
 * mvong@usc.edu
 */
package view;

import java.io.IOException;

import controller.LoginController;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import model.User;
import model.UserSingleton;
import resources.Constant;
import resources.Messages;
import resources.Style;

// login scene to authenticate user or sign up the user
// can also change the look of the UI

public class LoginScene extends Scene {
	
	// private member variables
	private Label titleLabel;
	private TextField usernameTextField;
	private PasswordField passwordField;
	private Button loginButton;
	private Button signupButton;
	private GridPane sceneRoot;
	private TextField fNameTextField;
	private TextField lNameTextField;
	private TextField ageTextField;
	private TextArea aboutTextArea;
	private CheckBox signupCheckBox;
	private CheckBox changeBackgroundCheckBox;
	private CheckBox spanishCheckBox;
	private Label errorLabel;
	
	// constructor
	public LoginScene() {
		super(new GridPane(), Style.LOGIN_WIDTH, Style.LOGIN_HEIGHT);
		initControls();
		addControls();
		styleControls();
		setControlActions();
	}
	
	// initialize the controls and parent
	private void initControls() {
		sceneRoot = (GridPane)getRoot();
		titleLabel = new Label(Messages.getString("Constant.LOGIN_WELCOME"));
		errorLabel = new Label();
		errorLabel.setVisible(false);
		usernameTextField = new TextField();
		usernameTextField.setPromptText(Messages.getString("Constant.USERNAME"));
		passwordField = new PasswordField();
		passwordField.setPromptText(Messages.getString("Constant.PASSWORD"));
		fNameTextField = new TextField();
		fNameTextField.setPromptText(Messages.getString("Constant.FIRST_NAME"));
		fNameTextField.setVisible(false);
		lNameTextField = new TextField();
		lNameTextField.setPromptText(Messages.getString("Constant.LAST_NAME"));
		lNameTextField.setVisible(false);
		ageTextField = new TextField() {
			@Override public void replaceText(int start, int end, String text) {
		        if (!text.matches("[a-z;',./{}]")) {
		            super.replaceText(start, end, text);
		        }
		    }
		};
		ageTextField.setPromptText(Messages.getString("Constant.AGE"));
		ageTextField.setVisible(false);
		aboutTextArea = new TextArea("");
		aboutTextArea.setPromptText(Messages.getString("Constant.ABOUT"));
		aboutTextArea.setVisible(false);
		aboutTextArea.setWrapText(true);
		loginButton = new Button(Messages.getString("Constant.LOGIN"));
		signupButton = new Button(Messages.getString("Constant.SIGNUP"));
		signupButton.setVisible(false);
		signupCheckBox = new CheckBox(Messages.getString("Constant.SIGNUP"));
		changeBackgroundCheckBox = new CheckBox(Messages.getString("Constant.CHANGE_BACKGROUND"));
		spanishCheckBox = new CheckBox(Messages.getString("Constant.SPANISH"));
	}
	
	// add controls to parent
	private void addControls() {
		sceneRoot.add(titleLabel, 1, 0);
		sceneRoot.add(errorLabel, 1, 1);
		sceneRoot.add(usernameTextField, 1, 2);
		sceneRoot.add(passwordField, 1, 3);
		sceneRoot.add(fNameTextField, 1, 4);
		sceneRoot.add(lNameTextField, 1, 4);
		sceneRoot.add(ageTextField, 1, 5);
		sceneRoot.add(aboutTextArea, 1, 6);
		sceneRoot.add(loginButton, 1, 7);
		sceneRoot.add(signupButton, 1, 7);
		sceneRoot.add(signupCheckBox, 1, 8); 
		sceneRoot.add(changeBackgroundCheckBox, 1, 8);
		sceneRoot.add(spanishCheckBox, 1, 8);
	}
	
	// style the controls and the parent
	private void styleControls() {
		Style.styleBackground(sceneRoot);
		sceneRoot.getColumnConstraints().add(new ColumnConstraints(50));
		sceneRoot.getColumnConstraints().add(new ColumnConstraints(400));
		sceneRoot.getColumnConstraints().add(new ColumnConstraints(50));
		titleLabel.requestFocus();
		titleLabel.setWrapText(true);
		GridPane.setHalignment(titleLabel, HPos.CENTER);
		GridPane.setMargin(titleLabel, Style.titleInsets);
		GridPane.setHalignment(errorLabel, HPos.CENTER);
		sceneRoot.getChildren().stream().filter(control -> {
			return control instanceof TextField;
		}).forEach(control -> {
			TextField textField = (TextField)control;
			GridPane.setMargin(textField, Style.textfieldInsets);
			Style.styleTextField(textField);
			textField.focusedProperty().addListener(e->{
				errorLabel.setVisible(false);
			});
		});
		Style.styleLabel(Style.ERROR, errorLabel);
		fNameTextField.setMaxWidth(200);
		lNameTextField.setMaxWidth(200);
		GridPane.setHalignment(fNameTextField, HPos.LEFT);
		GridPane.setHalignment(lNameTextField, HPos.RIGHT);
		GridPane.setMargin(aboutTextArea, Style.textfieldInsets);
		aboutTextArea.setFont(Style.textFont);
		GridPane.setMargin(signupButton, Style.buttonInsets);
		GridPane.setMargin(loginButton, Style.buttonInsets);
		GridPane.setMargin(signupCheckBox, Style.buttonInsets);
		Style.styleLabel(Style.TITLE, titleLabel);
		GridPane.setHalignment(loginButton, HPos.CENTER);
		GridPane.setHalignment(signupButton, HPos.CENTER);
		Style.styleButton(loginButton, null);
		Style.styleButton(signupButton, null);
		Style.styleCheckBox(signupCheckBox);
		GridPane.setHalignment(signupCheckBox, HPos.LEFT);
		Style.styleCheckBox(spanishCheckBox);
		GridPane.setHalignment(spanishCheckBox, HPos.CENTER);
		GridPane.setMargin(spanishCheckBox, Style.buttonInsets);
		GridPane.setMargin(changeBackgroundCheckBox, Style.buttonInsets);
		GridPane.setHalignment(changeBackgroundCheckBox, HPos.RIGHT);
		Style.styleCheckBox(changeBackgroundCheckBox);
	}
	
	// set the control actions
	private void setControlActions() {
		loginButton.setOnAction(e->{
			User user = new User(usernameTextField.getText(), passwordField.getText());
			try {
				if(LoginController.signInUser(user, errorLabel)) {
					UserSingleton.getInstance(user);
					AppStage.setScene(new DashboardScene(), Constant.DASH_TITLE);
				} 	
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		signupButton.setOnAction(e->{
			if(usernameTextField.getText().trim().length() > 0 
					&& passwordField.getText().trim().length() > 0 
					&& fNameTextField.getText().trim().length() > 0 
					&& lNameTextField.getText().trim().length() > 0
					&& ageTextField.getText().trim().length() > 0 
					&& aboutTextArea.getText().trim().length() > 0) {
					
				User user = new User(usernameTextField.getText(), 
						passwordField.getText(), fNameTextField.getText(), lNameTextField.getText(),
						Integer.valueOf(ageTextField.getText()), aboutTextArea.getText());
				try {
					if(LoginController.createUser(user, errorLabel)) {
						UserSingleton.getInstance(user);
						AppStage.setScene(new DashboardScene(), Constant.DASH_TITLE);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else {
				errorLabel.setText(Constant.FILL_FORM);
				errorLabel.setVisible(true);
			}
		});
		
		signupCheckBox.setOnAction(e->{
			boolean selected = signupCheckBox.isSelected();
			fNameTextField.setVisible(selected);
			lNameTextField.setVisible(selected);
			ageTextField.setVisible(selected);
			aboutTextArea.setVisible(selected);
			signupButton.setVisible(selected);
			loginButton.setVisible(!selected);
			
		});
		
		changeBackgroundCheckBox.setOnAction(e->{
			boolean selected = changeBackgroundCheckBox.isSelected();
			if(selected) {
				AppStage.updateUIColors(Style.customBackgroundStyle, Style.customButtonStyle, 
						Style.customTextColor, Style.customListViewStyle);
			}
			else {
				AppStage.updateUIColors(Style.backgroundStyle, Style.buttonStyle, 
						Style.textColor, Style.listViewStyle);
			}
		});
		
		spanishCheckBox.setOnAction(e-> {
			boolean selected = spanishCheckBox.isSelected();
			if(selected) {
				Messages.setResourceBundle(Messages.BUNDLE_NAME_ES);
			}
			else {
				Messages.setResourceBundle(Messages.BUNDLE_NAME);
			}
			updateLocalization();
		});
	}
	
	// update the fields based on user specified language
	private void updateLocalization() {
		titleLabel.setText(Messages.getString("Constant.LOGIN_WELCOME"));
		usernameTextField.setPromptText(Messages.getString("Constant.USERNAME"));
		passwordField.setPromptText(Messages.getString("Constant.PASSWORD"));
		loginButton.setText(Messages.getString("Constant.LOGIN"));
		signupButton.setText(Messages.getString("Constant.SIGNUP"));
		fNameTextField.setPromptText(Messages.getString("Constant.FIRST_NAME"));
		lNameTextField.setPromptText(Messages.getString("Constant.LAST_NAME"));
		ageTextField.setPromptText(Messages.getString("Constant.AGE"));
		aboutTextArea.setPromptText(Messages.getString("Constant.ABOUT"));
		signupCheckBox.setText(Messages.getString("Constant.SIGNUP"));
		changeBackgroundCheckBox.setText(Messages.getString("Constant.CHANGE_BACKGROUND"));
		spanishCheckBox.setText(Messages.getString("Constant.SPANISH"));
	}
	
	
	
	

}
