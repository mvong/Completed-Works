package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import game_logic.JDBCAuthentication;
//import game_logic.UserAuthentication;
import packet.UserInfo;

public class LoginPage extends JPanel {
	
	private Color gameColor;
	private BaseFrame baseFrame;
	
	private JLabel loginHeader;
	private JLabel gameHeader;
	
	private JLabel errorMessage;
	
	private CustomTextField usernameTextField;
	private CustomTextField passwordTextField;
	
	private JButton loginButton;
	private JButton createAccountButton;
	private JButton playAsGuestButton;
	
	private JDBCAuthentication jdbc;
	private AvatarScreen avatarScreen;
	
	private Image backgroundImage;
	
	private boolean isGuest;

	public LoginPage(BaseFrame baseFrame) {
		baseFrame.getAKClient().setLoginPage(this);
		this.baseFrame = baseFrame;
		initializeComponents();
		setBackground();
		setFormat();
		createGUI();
		addEvents();
		setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, null);
	}
	

	private void setBackground() {
		try {
			backgroundImage = ImageIO.read(new File("./../core/assets/Pictures/jungle.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Dimension size = new Dimension(backgroundImage.getWidth(null), backgroundImage.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	}

	public static void setUIFont (javax.swing.plaf.FontUIResource f) {
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get (key);
			if  (value != null && value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put (key, f);
			}
		}
	} 
	
	private void initializeComponents() {
		setUIFont (new javax.swing.plaf.FontUIResource("Times New Roman", Font.PLAIN, 40));
		
		gameColor = new Color(34, 139, 34);
		
		loginHeader = new JLabel("login or create an account to play");
		loginHeader.setForeground(Color.green);
		gameHeader = new JLabel("Animal Kingdom!");
		gameHeader.setForeground(Color.green);
		gameHeader.setFont(new Font("Times New Roman", Font.PLAIN, 90));
		
		errorMessage = new JLabel("");
		errorMessage.setForeground(new Color(128, 0, 0));
		errorMessage.setAlignmentX(CENTER_ALIGNMENT);
		
		usernameTextField = new CustomTextField(20);
		passwordTextField = new CustomTextField(20);
		
		loginButton = new JButton("Login");
		createAccountButton = new JButton("Create Account");
		playAsGuestButton = new JButton("Play as Guest");
		
		//fileInterpreter = new UsernameAndPasswordInterpreter();
		
		
		isGuest = false;
	}

	private void setFormat() {
		setLayout(new BorderLayout());
		setBackground(gameColor);
		
		loginHeader.setAlignmentX(CENTER_ALIGNMENT);
		gameHeader.setAlignmentX(CENTER_ALIGNMENT);
		
		usernameTextField.setForeground(Color.BLACK);
		passwordTextField.setForeground(Color.BLACK);
		
		loginButton.setEnabled(false);
		createAccountButton.setEnabled(false);
	}

	private void createGUI() {
		setLayout(new BorderLayout());
		
		JPanel north = new JPanel();
		//north.setBackground(gameColor);
		north.setOpaque(false);
		north.setPreferredSize(new Dimension(1800, 200));
		north.setMaximumSize(north.getPreferredSize());
		add(north, BorderLayout.NORTH);

		JPanel center = new JPanel();
		//center.setBackground(gameColor);
		center.setOpaque(false);
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		
		center.add(Box.createGlue());
		center.add(loginHeader);
		center.add(Box.createGlue());
		center.add(gameHeader);
		center.add(Box.createGlue());		
		center.add(errorMessage);
		center.add(Box.createGlue());
		
		usernameTextField.setPreferredSize(new Dimension(500, 60));
		usernameTextField.setMaximumSize(usernameTextField.getPreferredSize());
		usernameTextField.setPlaceholder("username");
		center.add(usernameTextField);
		center.add(Box.createGlue());
		passwordTextField.setPreferredSize(new Dimension(500, 60));
		passwordTextField.setMaximumSize(usernameTextField.getPreferredSize());
		passwordTextField.setPlaceholder("password");
		center.add(passwordTextField);
		center.add(Box.createGlue());
		
		JPanel buttonOptions = new JPanel();
		buttonOptions.setPreferredSize(new Dimension(800, 50));
		buttonOptions.setMaximumSize(buttonOptions.getPreferredSize());
		//buttonOptions.setBackground(gameColor);
		buttonOptions.setOpaque(false);
		buttonOptions.setAlignmentX(CENTER_ALIGNMENT);
		buttonOptions.setLayout(new BoxLayout(buttonOptions, BoxLayout.X_AXIS));
		buttonOptions.add(Box.createGlue());
		buttonOptions.add(loginButton);
		buttonOptions.add(Box.createGlue());
		buttonOptions.add(createAccountButton);	
		buttonOptions.add(Box.createGlue());
		buttonOptions.add(playAsGuestButton);	
		buttonOptions.add(Box.createGlue());
		center.add(buttonOptions);
		center.add(Box.createGlue());
		
		add(center, BorderLayout.CENTER);
		
		JPanel south = new JPanel();
		//south.setBackground(gameColor);
		south.setOpaque(false);
		south.setPreferredSize(new Dimension(1800, 200));
		south.setMaximumSize(south.getPreferredSize());
		add(south, BorderLayout.SOUTH);
	}

	public boolean isLoginEnabled() {
		if (!usernameTextField.isTextWrittenIn() || !passwordTextField.isTextWrittenIn() || usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public void enableLogin() {
		if (isLoginEnabled()) {
			loginButton.setEnabled(true);
			createAccountButton.setEnabled(true);
		}
		else {
			loginButton.setEnabled(false);
			createAccountButton.setEnabled(false);
		}
	}

	public void switchToAvatarScreen(UserInfo userInfo) {
		System.out.println("The following information is about to be sent to the avatar screen from the login screen: ");
    	System.out.println("username: " + userInfo.getUsername());
    	System.out.println("password: " + userInfo.getPassword());
    	System.out.println("games played: " + userInfo.getGamesPlayed());
    	System.out.println("games won: " + userInfo.getGamesWon());
    	System.out.println("is guest? : " + isGuest);
		avatarScreen = new AvatarScreen(baseFrame, userInfo, isGuest);
		baseFrame.getBasePanel().add(avatarScreen, "2");
		baseFrame.getBasePanel().getCardLayout().show(baseFrame.getBasePanel(), "2");
	}

	private void addEvents() {
		
		usernameTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {	}

			@Override
			public void insertUpdate(DocumentEvent e) { 
				enableLogin();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				enableLogin();
			}
			
		});
		
		passwordTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {	}

			@Override
			public void insertUpdate(DocumentEvent e) { 
				enableLogin();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				enableLogin();
			}
			
		});
		
		playAsGuestButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Random rd = new Random();
				UserInfo user = new UserInfo("guest"+rd.nextInt(10000), "", 0, 0, false, false, true);
				baseFrame.getAKClient().getClient().sendTCP(user);	
				isGuest = true;
			}
			
		});
		
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO: update game stats instead of passing in 0's
				UserInfo user = new UserInfo(usernameTextField.getText(), passwordTextField.getText(), 0, 0, true, false, false);
				baseFrame.getAKClient().getClient().sendTCP(user);
				isGuest = false;
			}
		
		});
		
		createAccountButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("The current username is: " + usernameTextField.getText());
				System.out.println("The current password is: " + passwordTextField.getText());
				if (usernameTextField.isTextWrittenIn() && passwordTextField.isTextWrittenIn()) {
					UserInfo user = new UserInfo(usernameTextField.getText(), passwordTextField.getText(), 0, 0, false, true, false);
					baseFrame.getAKClient().getClient().sendTCP(user);
					isGuest = false;

				}
			}
		});	
	}

	public JLabel getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage.setText(errorMessage);
	}

}
