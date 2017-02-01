package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.badlogic.gdx.Gdx;

import packet.UserInfo;


public class AvatarScreen extends JPanel{
	
	private static final long serialVersionUID = -8401520642116525942L;

	private BaseFrame baseFrame;
	private UserInfo userInfo;
	
	private BufferedImage girl1;
	private BufferedImage girl2;
	private BufferedImage guy1;
	private BufferedImage guy2;
	private BufferedImage choose1;
	private BufferedImage choose2;
	
	// String type, String weapon, String name, Boolean isGuest
	private String chosenAvatar;
	private String chosenWeapon;
	private boolean isGuest;
	
	private JLabel pickYourWeaponLabel, pickYourAvatarLabel, previewLabel, previewImage;
	private JButton weapon1, weapon2;
	private JButton avatar1, avatar2;
	private JButton startButton;
	private Color green;
	private Boolean avatarChosen, weaponChosen;
	private int weaponNum, avatarNum;
	
	private JLabel waitingMessage;
	private Image backgroundImage;
	
	public AvatarScreen(BaseFrame baseFrame, UserInfo userInfo, boolean isGuest) {
		this.baseFrame = baseFrame;
		this.userInfo = userInfo;
		this.isGuest = isGuest;
		this.baseFrame.getAKClient().setAvatarScreen(this);
		setLayout(new BorderLayout());
		//setBackground(green);
		setBackground();
		setOpaque(true);
		
		getSprite();
		initializeComponents();
		appearanceStettings();
		imageSettings();
		createGUI();
		addEvents();
	}
	
	public void setWaitingLabel(String str){
		waitingMessage.setText(str);
	}
	
	private void setBackground() {
		try {
			backgroundImage = ImageIO.read(new File("./../core/assets/Pictures/jungle.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dimension size = new Dimension(backgroundImage.getWidth(null), backgroundImage.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, null);
	}
	
	private void getSprite() {
		BufferedImage girl;
		BufferedImage guy;
		try {
			girl = ImageIO.read(new File("./../core/assets/SpriteSheets/AnimalKingdomSprites.png"));
			System.out.println("read girl from file");
			guy = ImageIO.read(new File("./../core/assets/SpriteSheets/AnimalKingdomSprites4.png"));
			System.out.println("read guy from file");
			girl1 = girl.getSubimage(3200, 200, 500, 500);
			girl2 = girl.getSubimage(6400, 200, 500, 500);
			guy1 = guy.getSubimage(5720, 30, 500, 500);
			guy2 = guy.getSubimage(1720, 30, 500, 500);
			System.out.println("girl: " + girl);
			choose1 = girl.getSubimage(1300, 150, 500, 500);
			choose2 = guy.getSubimage(50, 620, 500, 500);
			
		} catch (IOException e) {
			JLabel lbl = new JLabel(e.getMessage());
		    JOptionPane.showMessageDialog(null, lbl, "ImageDialog", JOptionPane.PLAIN_MESSAGE, null);
		}
	}
	
	private void initializeComponents() {
		green = new Color(34,139,34);
		avatarChosen = false;
		weaponChosen = false;
		weaponNum = -1;
		avatarNum = -1;
		
		pickYourWeaponLabel = new JLabel("Pick our weapon: ");
		pickYourAvatarLabel = new JLabel("Pick your avatar: ");
		previewLabel = new JLabel("Preview: ");
		//aesthetics that need change
		weapon1 = new JButton("Melee");
		weapon2 = new JButton("Range");
		avatar1 = new JButton();
		avatar2 = new JButton();
		
		previewImage = new JLabel();
		
		waitingMessage = new JLabel("This is the waiting message");
		startButton = new JButton("Start Game");
		startButton.setEnabled(false);
		
		if (isGuest) {
			weapon1.setEnabled(false);
			weapon2.setEnabled(false);
			avatar1.setEnabled(false);
			avatar2.setEnabled(false);
			weaponChosen = true;
			avatarChosen = true;
			enableStart();
		}
	}
	
	private void appearanceStettings() {
		setSize(500, 50, pickYourWeaponLabel, pickYourAvatarLabel);
		setSize(400, 200, weapon1, weapon2, avatar1, avatar2);
		setSize(300, 500, previewImage);
		previewImage.setBackground(Color.white);
		previewImage.setOpaque(false);
		previewImage.setFont(new Font("Times New Roman", Font.BOLD, 25));
		setSize(300, 50, startButton);
		
		//color
		pickYourWeaponLabel.setForeground(Color.GREEN);
		pickYourAvatarLabel.setForeground(Color.GREEN);
		waitingMessage.setForeground(Color.GREEN);
		previewLabel.setForeground(Color.green);
		
		//font
		Font font = new Font("Times New Roman", Font.BOLD, 30);
		pickYourWeaponLabel.setFont(font);
		pickYourAvatarLabel.setFont(font);
		weapon1.setFont(font);
		weapon2.setFont(font);
		previewLabel.setFont(font);
		previewLabel.setHorizontalAlignment(JLabel.CENTER);
		
		//start
		startButton.setBackground(Color.white);
		startButton.setOpaque(true);
		startButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		startButton.setAlignmentY(JButton.CENTER_ALIGNMENT);
		//startButton.setEnabled(false);
		startButton.setFont(font);
		
	}
	
	private void imageSettings() {
		System.out.println("choose1: " + choose1);
		Image tmp = choose1.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
		avatar1.setIcon(new ImageIcon(tmp));
		tmp = choose2.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
		avatar2.setIcon(new ImageIcon(tmp));
	}
	
	private void createGUI() {
		JPanel north = new JPanel(new BorderLayout());
		JPanel titlePanel = new JPanel();
		//titlePanel.setBackground(green);
		titlePanel.setOpaque(false);
		JLabel title = new JLabel("Avatar Screen");
		title.setForeground(Color.green);
		titlePanel.add(title);
		title.setFont(new Font("Times New Roman", Font.PLAIN, 90));
		title.setAlignmentX(CENTER_ALIGNMENT);
		//north.setBackground(green);
		north.setOpaque(false);
		north.setPreferredSize(new Dimension(1600, 600));
		north.add(titlePanel, BorderLayout.NORTH);
		north.add(createLeft(), BorderLayout.WEST);
		north.add(createRight(), BorderLayout.EAST);
		add(north, BorderLayout.NORTH);
		
		JPanel south = new JPanel();
		south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
		//south.setBackground(green);
		south.setOpaque(false);
		waitingMessage.setAlignmentX(CENTER_ALIGNMENT);
		waitingMessage.setVisible(false);
		JLabel bufferLabel = new JLabel("");
		bufferLabel.setForeground(green);
		startButton.setAlignmentX(CENTER_ALIGNMENT);
		south.add(Box.createGlue());
		south.add(waitingMessage);
		south.add(Box.createGlue());
		south.add(bufferLabel);
		south.add(Box.createGlue());
		south.add(startButton);
		south.add(Box.createGlue());
		add(south, BorderLayout.SOUTH);
	}
	
	private JPanel createLeft() {
		JPanel left = new JPanel(new GridBagLayout());
		left.setPreferredSize(new Dimension(1000, 600));
		//left.setBackground(green);
		left.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		
		//weapons
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(20, 20, 0, 20);
		left.add(pickYourWeaponLabel, gbc);
		gbc.gridy = 1;
		left.add(weapon1, gbc);
		gbc.gridx = 1;
		left.add(weapon2, gbc);
		
		//avatar
		gbc.gridx = 0;
		gbc.gridy = 2;
		left.add(pickYourAvatarLabel, gbc);
		gbc.gridy = 3;
		left.add(avatar1, gbc);
		gbc.gridx = 1;
		left.add(avatar2, gbc);
		
		return left;
	}
	
	private JPanel createRight() {
		JPanel right = new JPanel(new GridBagLayout());
		right.setPreferredSize(new Dimension(500, 600));
		//right.setBackground(green);
		right.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(25, 20, 20, 20);
		right.add(previewLabel, gbc);
		gbc.insets = new Insets(0, 20, 20, 20);
		gbc.gridy = 1;
		right.add(previewImage, gbc);
		
		return right;
	}
	
	private void addEvents() {
		weapon1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				weapon1.setEnabled(false);
				weapon2.setEnabled(true);
				weaponChosen = true;
				weaponNum = 1;
				enableStart();
				if(avatarChosen) {
					if(avatarNum == 1) {
						Image tmp = girl1.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
						previewImage.setIcon(new ImageIcon(tmp));
					}
					else {
						Image tmp = guy2.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
						previewImage.setIcon(new ImageIcon(tmp));
					}
				}
				else {
					previewImage.setText("   Please choose an avatar.");
				}
			}
		
		});
		
		weapon2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				weapon2.setEnabled(false);
				weapon1.setEnabled(true);
				weaponChosen = true;
				weaponNum = 2;
				enableStart();
				if(avatarChosen) {
					if(avatarNum == 1) {
						Image tmp = girl2.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
						previewImage.setIcon(new ImageIcon(tmp));
					}
					else {
						Image tmp = guy1.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
						previewImage.setIcon(new ImageIcon(tmp));
					}
				}
				else {
					previewImage.setText("   Please choose an avatar.");
				}
			}
		
		});
		
		avatar1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				avatar1.setEnabled(false);
				avatar2.setEnabled(true);
				avatarChosen = true;
				avatarNum = 1;
				enableStart();
				if(weaponChosen) {
					if(weaponNum == 1) {
						Image tmp = girl1.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
						previewImage.setText("");
						previewImage.setIcon(new ImageIcon(tmp));
					}
					else {
						Image tmp = girl2.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
						previewImage.setText("");
						previewImage.setIcon(new ImageIcon(tmp));
					}
				}
				else {
					Image tmp = choose1.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
					previewImage.setIcon(new ImageIcon(tmp));
				}
			}
		
		});
		
		avatar2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				avatar2.setEnabled(false);
				avatar1.setEnabled(true);
				avatarChosen = true;
				avatarNum = 2;
				enableStart();
				if(weaponChosen) {
					if(weaponNum == 1) {
						Image tmp = guy2.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
						previewImage.setText("");
						previewImage.setIcon(new ImageIcon(tmp));
					}
					else {
						Image tmp = guy1.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
						previewImage.setText("");
						previewImage.setIcon(new ImageIcon(tmp));
					}
				}
				else {
					Image tmp = choose2.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
					previewImage.setIcon(new ImageIcon(tmp));
				}
			}
		
		});
		
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				// get the selected avatar and weapon
				// weapon is selected if they are disabled
				waitingMessage.setText("Waiting for other users to join the game...");
				waitingMessage.setVisible(true);
				if (!weapon1.isEnabled()) {
					chosenWeapon = "melee";
				}
				else if (!weapon2.isEnabled()) {
					chosenWeapon = "range";
				}
				
				if (!avatar1.isEnabled()) {
					chosenAvatar = "girl";
				}
				else if (!avatar2.isEnabled()) {
					chosenAvatar = "robot";
				}
				
				if (isGuest) {
					chosenWeapon = "melee";
					chosenAvatar = "girl";
				}
	        	startButton.setEnabled(false);
				baseFrame.getAKClient().sendUserInfo(chosenAvatar, chosenWeapon, userInfo, isGuest);
			}	
		});
	}
	
	private void enableStart() {
		if(weaponChosen && avatarChosen) {
			startButton.setEnabled(true);
		}
	}
	
	@SafeVarargs
	public static <T extends JComponent> void setSize(int x, int y, T ...components){
		
		Dimension dim = new Dimension(x, y);
		
		for (T component: components){
			component.setPreferredSize(dim);
		}
	}
	
}
