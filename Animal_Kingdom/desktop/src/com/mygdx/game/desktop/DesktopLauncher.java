package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import client.AKClient;
import gui.BaseFrame;

public class DesktopLauncher {
	
	public DesktopLauncher() {}
	
	public void launchGame() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.fullscreen = true;
		new LwjglApplication(new AKClient(), config);
	}
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.fullscreen = true;
		new LwjglApplication(new AKClient(), config);
	}
}
