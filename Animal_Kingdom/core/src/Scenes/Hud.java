package Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import character.Player;
import client.AKClient;
import gui.BaseFrame;

public class Hud {

	public Stage stage;
	private Viewport viewport;
	
	public HealthBar healthBar;
	
	private Integer worldTimer;
	private float timeCount;
	private static Integer score;
	
	Label healthBarLabel;
	Label usernameLabel;
	Label tempLabel;
	Label menuLabel;
	
	TextButton button;
    TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
    
    private Dialog stats;
    private TextButton back;
    private Boolean registered;
	
	public Hud(SpriteBatch sb, final AKClient c, Player player, final Boolean registered) {
		this.registered = registered;
		viewport = new FitViewport(AKClient.V_WIDTH, AKClient.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		healthBarLabel = new Label("HEALTH", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		healthBar = new HealthBar();
		usernameLabel = new Label(player.getUsername(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		tempLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		menuLabel = new Label("MENU", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		  
	    //menu
	  	final SelectBox<String> menu = new SelectBox<String>(skin);
	  	String[] a = {"", "Stats", "Exit"};
	  	String[] b = {"", "Exit"};
	  	if(!registered) {
	  		menu.setItems(a);
	  	}
	  	else {
	  		menu.setItems(b);
	  	}
	  	
	    //stats dialog set up
	  	if(!registered) {
	  		back = new TextButton("Back", skin);
	  		stats = new Dialog("Stats", skin);
	  		System.out.println("these are the stats in the hud: ");
	  		System.out.println("games played for " + player.getUsername() + ": " + player.getGamesPlayed());
	  		System.out.println("games won for " + player.getUsername() + ": " + player.getGamesWon());
	  		stats.text("Times Played: " + player.getGamesPlayed());
	  		stats.text("     Wins: " + player.getGamesWon());
	  		stats.button(back);
	  		    
	  		back.addListener(new ChangeListener() {
	  		    @Override
	  		    public void changed(ChangeEvent event, Actor actor) {
	  		        stats.hide();
	  		        menu.setSelectedIndex(0);
	  		    }
	  		});
	  	} 
	  	
	    menu.addListener(new ChangeListener() {
	    	public void changed (ChangeEvent event, Actor actor) {
	    		int tmp = menu.getSelectedIndex();
	    		if(tmp == 0) {
					menu.hideList();
				}
				else if(tmp == 1) {
					if(!registered) {
						menu.setSelectedIndex(0);
						stats.show(stage);
					}
					else {
						System.exit(0);
					}
				}
				else {
					System.exit(0);
				}
			}
	    });
	    
		table.add(healthBarLabel).expandX().padTop(10);
		table.add(usernameLabel).expandX().padTop(10);
		table.add(menuLabel).expandX().padTop(10);
		table.row();
		table.add(healthBar);
		table.add(tempLabel).expandX();
		table.add(menu).expandX();
		Gdx.input.setInputProcessor(stage);

		stage.addActor(table);	
	}
	
	public void resize(int width, int height) {
	    stage.getViewport().update(width, height);  
	}
	
}
