package screens;

import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Scenes.Hud;
import character.AdventureGirl;
import character.Boss;
import character.Character.Direction;
import character.Enemy;
import character.Gorilla;
import character.Octopus;
import character.Player;
import character.Robot;
import character.UserLabel;
import client.AKClient;
import packet.EnemyInfo;
import packet.InputInfo;
import packet.PlayerInfo;
import packet.PositionInfo;
import packet.UpdateDatabase;

public class PlayScreen implements Screen {
	
	//client viewport and camera
	private AKClient c;
	private OrthographicCamera gamecam;
	private Viewport gameport;
	Texture texture;
	
	//world/renderer
	private World world;
	private Box2DDebugRenderer b2dr;
	
	//map
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	//box2d
	
	private Hud hud;
	private Box2DCreator worldCreator;
	private Boss boss;
	private boolean bossCreated;
	
	private int maxSpeed;
	private float playerAccel;

	//sprite sheets
	private TextureAtlas atlas;
	
	// To test
	public static final float PPM = 100;
	
	// Players
	private Vector<Player> players;
	private Player me;
	private HashSet<UserLabel> userLabels;
	private UserLabel myLabel;
	
	private int frameCount;
	private int bossFrameCount;
		
	private Boolean isGuest;
	
	private Stack<String> targets;
	
	
	//skin
	Skin skin;
	
	public PlayScreen(AKClient c, Vector<PlayerInfo> playerInfos, int gamesPlayed, int gamesWon){
		frameCount = 0;
		maxSpeed = 3;
		playerAccel = 0.1f;
		this.c = c;
		this.players = new Vector<Player>();
		this.userLabels = new HashSet<UserLabel>();
		
		//viewport = new FitViewport(800/PPM, 480/PPM, camera);
		world = new World(new Vector2(0,-10), true);
		b2dr = new Box2DDebugRenderer();
		
		gamecam = new OrthographicCamera();
		
		// use this if you want the old one:
		gameport = new FitViewport(800/AKClient.PPM, 480/AKClient.PPM, gamecam);
		//gameport = new FitViewport(AKClient.V_WIDTH / AKClient.PPM, AKClient.V_HEIGHT / AKClient.PPM, gamecam);
		
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("./../core/assets/floorImages.tmx");
		// note that ppm is a public member of akclient now
		renderer = new OrthogonalTiledMapRenderer(map, 1 / AKClient.PPM);
	 	
		// use this if you want the old one:
		gamecam.position.set(400/AKClient.PPM, 240/AKClient.PPM,0);
		//gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight(), 0);
		setUpContactListener();
		//use asset manager instead?
		atlas = new TextureAtlas("./../core/assets/SpriteSheets/AnimalKingdomSprites.pack");
		worldCreator = new Box2DCreator(world, map, atlas, this);
		worldCreator.setGroundObjects();
		worldCreator.createEnemies();
		//send enemies to server
		try{
			c.getClient().sendTCP(new EnemyInfo(worldCreator.getEnemies()));
		}catch(IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
		boss = null;
		//player = new Test(world, atlas);
		
		//initialize players
		for(PlayerInfo pi: playerInfos){
			createPlayer(pi);
		}
		
		//this.players.add(robot);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		bossCreated = false;
		

		setGameStats(gamesPlayed, gamesWon);

		hud = new Hud(c.batch, c, (Player) me, isGuest);
		
		//destroy the avatar screen
		c.getBaseFrame().dispose();
		
	}
	
	//enable faster walking
	private void toggleCheats(){
		maxSpeed = maxSpeed == 3 ? 5 : 3;
		playerAccel = playerAccel == 0.1f ? 0.4f : 0.1f;
		if(me.getWeapon().getDamage() == 10){
			me.getWeapon().setDamage(100);
		}
		else{
			me.getWeapon().setDamage(10);
		}
	}
	
	public void setTargets(Stack<String> targets){
		this.targets = targets;
	}
	
	public void setGameStats(int gamesPlayed, int gamesWon){
		me.setGamesPlayed(gamesPlayed);
		me.setGamesWon(gamesWon);
		c.getClient().sendTCP(new UpdateDatabase(c.getUserInfo(), gamesPlayed + 1, gamesWon));
	}
	
	private void setUpContactListener() {
		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				// TODO Auto-generated method stub
				short fACategoryBit = contact.getFixtureA().getFilterData().categoryBits;
				short fBCategoryBit = contact.getFixtureB().getFilterData().categoryBits;
				if(fACategoryBit == AKClient.PLAYER_BIT && fBCategoryBit == AKClient.ENEMY_BIT) {
					Gdx.app.log("Collision detected!", "Player hit enemy...player loses hp.");
					Enemy enemy;
					Player player;
					if(contact.getFixtureB().getBody().getUserData() instanceof Gorilla) {
						enemy = (Gorilla)contact.getFixtureB().getBody().getUserData();
					}
					else if(contact.getFixtureB().getBody().getUserData() instanceof Octopus){
						enemy = (Octopus)contact.getFixtureB().getBody().getUserData();
					}
					else{
						enemy = (Boss)contact.getFixtureB().getUserData();
					}
					if(contact.getFixtureA().getBody().getUserData() instanceof AdventureGirl) {
						player = (AdventureGirl)contact.getFixtureA().getBody().getUserData();
					}
					else {
						player = (Robot)contact.getFixtureA().getBody().getUserData();
					}
					enemy.attack(player);
					System.out.println(player.getChar().getUsername() + "'s hp is now "+ player.getChar().getHealth());
					
					if(player.getChar().getHealth() <= 0 && player.equals(me)) {
						player.setDead(true);
						System.out.println(player.getChar().getUsername() + " has died.");
						TextButton exit = new TextButton("Exit", skin);
						final Dialog gameOver = new Dialog("Game Over", skin);
						gameOver.text("Game Over!");
						gameOver.button(exit);
						gameOver.show(hud.stage);
				  		    
				  		exit.addListener(new ChangeListener() {
				  		    @Override
				  		    public void changed(ChangeEvent event, Actor actor) {
				  		    	//gameOver.hide();
				  		        Gdx.app.exit();
				  		    }
				  		});
					}
				}
				else if(fBCategoryBit == AKClient.PLAYER_BIT && fACategoryBit == AKClient.ENEMY_BIT) {
					Gdx.app.log("Collision detected!", "Enemy hit player...player loses hp.");
				}
			}
			@Override
			public void endContact(Contact contact) {}
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {}
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {}
		});
	}
	
	public TextureAtlas getTextureAtlas(){
		return atlas;
	}
	
	public World getWorld(){
		return world;
	}
	
	public AKClient getAKClient(){
		return c;
	}
	
	public void createBoss(){
		if(bossCreated)
			return;
		worldCreator.createBoss();
		boss = worldCreator.getBoss();
		bossCreated = true;
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	public void jump(Player bp){
		//ch.getBody().applyLinearImpulse(new Vector2(0, 6f), girl.getBody().getWorldCenter(), true);
		bp.jump();
	}
	
	public void right(character.Character ch){
		ch.getBody().applyLinearImpulse(new Vector2(playerAccel, 0), ch.getBody().getWorldCenter(), true);
		ch.setDirection(Direction.RIGHT);
	}
	
	public void left(character.Character ch){
		ch.getBody().applyLinearImpulse(new Vector2(-playerAccel, 0), ch.getBody().getWorldCenter(), true);
		ch.setDirection(Direction.LEFT);
	}
	
	public void attack(character.Character ch){
		ch.attemptAttack();
		((Player) ch).setAttack();
	}
	
	public void handleInput(float delta){
		//use isKeyJustPressed for jumps.	
		Boolean up = false,  right = false,  left = false, attack = false;
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			jump(me);
			up = true;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && me.getBody().getLinearVelocity().x <= maxSpeed){
			right(me);
			right = true;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && me.getBody().getLinearVelocity().x >= -maxSpeed){
			left(me);
			left = true;
		} 
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			attack(me);
			attack = true;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.F11)){
			toggleCheats();
		}
		c.handleInput(up, right, left, attack);	
	}
	
	public void update(float delta){
		if(me == null) return;
		
		//update boss behavior
		if(boss != null && targets != null){
			bossFrameCount++;
			if(bossFrameCount == 300){
				String target = targets.pop();
				for(Player pl : players){
					if(pl.getName().equals(target)){
						boss.setTarget(pl);
						break;
					}
				}
				bossFrameCount = 0;
			}
		}
		
		//Update the position and the healthbar
		frameCount++;
		if(frameCount == 10){
			frameCount = 0;
			Vector2 linVel = me.getBody().getLinearVelocity();
			Vector2 pos = me.getBody().getPosition();
			c.getClient().sendTCP(new PositionInfo(linVel, pos, me.getName(), me.getHealth()));
			hud.healthBar.setHealth((int)me.getHealth());
		}
		
		
		handleInput(delta);
		if(me.getBody().getPosition().x > 400/PlayScreen.PPM && 
				me.getBody().getPosition().x < 31600/PlayScreen.PPM) gamecam.position.x = me.getBody().getPosition().x;
		gamecam.update();
		renderer.setView(gamecam);
		world.step(1/60f, 6, 1);
		for(Player bp : players){
			bp.update(delta);
		}
		//update enemies
		for(Enemy enemy : worldCreator.getEnemies()){
			enemy.update(delta);	
		}
		//update userlabels
		for(UserLabel ul : userLabels){
			ul.act(delta);
		}
		if(boss != null) boss.update(delta);
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		//clear the screen		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(delta);
		
		renderer.render();
		
		//b2dr.render(world, gamecam.combined);
		
		SpriteBatch b = c.batch;
		
		b.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		hud.stage.draw();
		
		for(UserLabel ul: userLabels) {
			if(ul.getPlayer().equals(me)) {
				myLabel = ul;
				continue;
			}
			if(ul.getPlayer().getDisappeared())
				continue;
			b.setProjectionMatrix(ul.stage.getCamera().combined);
			ul.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
			ul.stage.draw();
		}
		b.setProjectionMatrix(gamecam.combined);
		// SETTING THE HUD
		b.begin();
		
		// Most recent player (current) should be rendered last so they will always be on top
		for(Player pl : players){
			if(!pl.equals(me))
				pl.getChar().draw(b);
		}
		if(boss != null) boss.getChar().draw(b);
		if(bossCreated) {
			if(boss.died()) {
				System.out.println("You won!");
				TextButton exit = new TextButton("Exit", skin);
				final Dialog gameOver = new Dialog("Won", skin);
				gameOver.text("You won!");
				gameOver.button(exit);
				gameOver.show(hud.stage);
		  		    
		  		exit.addListener(new ChangeListener() {
		  		    @Override
		  		    public void changed(ChangeEvent event, Actor actor) {
		  		    	//gameOver.hide();
		  		        Gdx.app.exit();
		  		    }
		  		});
			}
		}
		
		for(int i = 0 ; i < worldCreator.getEnemies().size(); i++) { 
			if(worldCreator.getEnemies().get(i).getDisappeared()) {
				worldCreator.getEnemies().remove(worldCreator.getEnemies().get(i));
			}
			else {
				worldCreator.getEnemies().get(i).draw(b);
			}
		}
		me.draw(b);
		b.end();
		b.setProjectionMatrix(myLabel.stage.getCamera().combined);
		myLabel.stage.act();
		myLabel.stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		gameport.update(width, height);
		hud.stage.getViewport().update(width, height);
		for(UserLabel ul : userLabels) {
			ul.stage.getViewport().update(width, height);
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	
	//functions to be invoked by client
	public void processDeath(int UID){
		worldCreator.getEnemy(UID).dealDamage(10000);
		System.out.println("Character "+UID+" is dead!");
	}
	
	public void handleDisconnection(String playerName){
		for(Player bp : players){
			if(bp.getName().equals(playerName))
				bp.dealDamage(1000000);
		}
	}
	
	public void updatePlayer(InputInfo info){
		//System.out.println("Updating player "+info.getName());
		for(Player bp : players){
			if(bp.equals(me))
				continue;
			if(bp.getName().equals(info.getName())){
				if(info.getUp())
					jump(bp);
				if(info.getRight())
					right(bp);
				if(info.getLeft())
					left(bp);
				if(info.getAttack())
					attack(bp);
				return;
			}
		}
	}
	
	
	synchronized public void updatePlayerPos(PositionInfo info){
		if(me == null) return;
		//System.out.println("To be updated:"+info.getName());
		if(info.getName().equals(me.getName()))	return;
		for(Player bp : players){
			if(bp.getName().equals(info.getName())){
				//System.out.println("Position updated for "+bp.getName());
				synchronized(world) {
					while(world.isLocked()){
						
					}
					bp.getBody().setLinearVelocity(info.getLinVel());
					if(!world.isLocked())
						bp.getBody().setTransform(info.getPos(), 0);
				}
				bp.setHealth(info.getHealth());
				return;
			}
		}
	}
	
	private void createPlayer(PlayerInfo info){
		isGuest = info.isGuest();
		Boolean isGun = null;
		Player bp = null;
		if(info.getWeapon().equals("melee"))
			isGun = false;
		else
			isGun = true;
		if(info.getType().equals("girl")){
			bp = new AdventureGirl(atlas, world, info.getUserInfo().getUsername(), 0, 0, info.isGuest(), isGun, this);
			
		}
		else{
			bp = new Robot(atlas, world, info.getUserInfo().getUsername(), 0, 0, info.isGuest(), isGun, this);
			
		}
		if(info.getUserInfo().getUsername().equals(c.getName())){
			me = bp;
			System.out.println("My name is "+me.getName());
		}
		if(bp != null){
			this.players.add(bp);
			System.out.println("ID "+bp.getUID()+" Name "+bp.getName());
			userLabels.add(new UserLabel(bp, bp.getName()));
		}
	}
}
