package gui;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;


public class BasePanel extends JPanel {

	private CardLayout cardLayout;
	private Color green;
	
	public BasePanel() {
		green = new Color(34,139,34);
		cardLayout = new CardLayout();
		setBackground(green);
		setLayout(cardLayout);
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}

	public void setCardLayout(CardLayout cardLayout) {
		this.cardLayout = cardLayout;
	}
}
