package character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import client.AKClient;
import screens.PlayScreen;

public class UserLabel extends Label {

	private Player player;
	public Viewport viewport;
	public Stage stage;
	public Table table;
	private Camera camera;
	
	// Main constructor
	public UserLabel(Player player, String username) {
		this(player, username, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
	}
	
	public Player getPlayer(){
		return player;
	}
	
	// Automatically called in main constructor
	public UserLabel(Player player, CharSequence text, LabelStyle style) {
		super(text, style);
		this.player = player;
		init();
	}
	
	public BitmapFont createFont() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("./../core/assets/ARIAL.TTF"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); 
		return font12;
	}
	
	public void init() {
		BitmapFont bmf = createFont();
		bmf.getData().setScale(1f, 1f);
		//Label ul = new Label("TempUserName", new Label.LabelStyle(bmf, Color.WHITE));
		this.setSize(0.01f, 0.01f);	
		this.setPosition(player.getBody().getPosition().x, player.getBody().getPosition().y);
		SpriteBatch b = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new FitViewport(AKClient.V_WIDTH,AKClient.V_HEIGHT, camera);
		stage = new Stage(viewport, b);
		table = new Table();
		table.bottom();
		//table.padBottom(player.getBody().getPosition().y);
		//table.debugTable();
		//table.debugCell();
		table.setFillParent(true);
		
		table.add(this);
		
		stage.addActor(table);
		
	}
	
	public void act(float delta){
		//float x = player.getChar().getBoundingRectangle().x / (800/AKClient.PPM) * AKClient.V_WIDTH;
		float x = player.getChar().getBoundingRectangle().x / 320 * AKClient.V_WIDTH;
		//float x = 0;
		float y = player.getChar().getBoundingRectangle().y / (480/AKClient.PPM) * AKClient.V_HEIGHT - 10;
		//camera.position.x = player.getChar().getBoundingRectangle().x;
		//camera.position.y = player.getChar().getBoundingRectangle().y;
		this.setPosition(x,y);
	}
}
