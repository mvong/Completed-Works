package gui;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// STILL TRYING TO FIX GIT

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.AKClient;


public class BaseFrame extends JFrame {
	
	private BasePanel basePanel;
	private LoginPage loginPage;
	private AvatarScreen avatarScreen;
	private Color green;
	private AKClient client;
	
	public BaseFrame(AKClient client) { 
		//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.client = client;
		initializeComponents();
		createGUI();
		addEvents();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	public void initializeComponents() {
		green = new Color(34,139,34);
		basePanel = new BasePanel();
		loginPage = new LoginPage(this);
	}
	
	public void createGUI() {
		setBackground(green);
		setSize(1800, 1200);
		add(basePanel);
		//basePanel.setLayout(cardLayout);
        basePanel.add(loginPage, "1");
	}

	public void addEvents() {
		addWindowListener(new WindowAdapter() {
			@Override
	         public void windowClosing(WindowEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, 
				        "Are you sure you want to exit the program?", "Exit Program Message Box",
				        JOptionPane.YES_NO_OPTION);

			    if (confirmed == JOptionPane.YES_OPTION) {
			    	System.exit(0);
			    }
	         }
		});
	}
	
	public BasePanel getBasePanel() {
		return basePanel;
	}

	public void setBasePanel(BasePanel basePanel) {
		this.basePanel = basePanel;
	}
	
	public AKClient getAKClient(){
		return client;
	}

}
