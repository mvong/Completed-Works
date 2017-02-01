package screens;

import java.util.Vector;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import character.Boss;
import character.Enemy;
import character.Gorilla;
import character.Octopus;
import client.AKClient;

public class Box2DCreator {
	
	private World gameWorld;
	private TiledMap gameMap;
	private Vector<Enemy> enemies;
	private TextureAtlas textures;
	private Boss boss;
	private PlayScreen owner;
	
	public Box2DCreator(World world, TiledMap map, TextureAtlas at, PlayScreen owner){
		gameWorld = world;
		gameMap = map;
		enemies = new Vector<Enemy>();
		textures = at;
		this.owner = owner;
	}
	
	public void createEnemies(){
		for(MapObject object: gameMap.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject)object).getRectangle();
			enemies.add(new Gorilla(gameWorld, textures, rect.getX()/PlayScreen.PPM, rect.getY()/PlayScreen.PPM, owner));
		}
		for(MapObject object: gameMap.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject)object).getRectangle();
			enemies.add(new Octopus(gameWorld, textures, rect.getX()/PlayScreen.PPM, rect.getY()/PlayScreen.PPM, owner));
		}
	}
	
	public void createBoss(){
		for(MapObject object: gameMap.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject)object).getRectangle();
			boss = new Boss(gameWorld, textures, rect.getX()/PlayScreen.PPM, rect.getY()/PlayScreen.PPM, owner);
			enemies.add(boss);
		}
	}
	
	public Boss getBoss(){
		return boss;
	}
	
	public boolean canGenerateBoss(){
		if(enemies.size()==0){
			return true;
		}
		else return false;
	}

	
	public void setGroundObjects(){
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		for(MapObject object: gameMap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject)object).getRectangle();
			
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX()+ rect.getWidth()/2)/PlayScreen.PPM, (rect.getY()+ rect.getHeight()/2)/PlayScreen.PPM);
			body = gameWorld.createBody(bdef);
			
			shape.setAsBox((rect.getWidth()/2)/PlayScreen.PPM, (rect.getHeight()/2)/PlayScreen.PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = AKClient.MAP_BIT;
			fdef.filter.maskBits = AKClient.PLAYER_BIT | AKClient.ENEMY_BIT;
			body.createFixture(fdef);
			
		}
	}
	
	public Vector<Enemy> getEnemies(){
		return enemies;
	}
	
	public Enemy getEnemy(Integer ID){
		for(Enemy enemy:enemies){
			if(enemy.getUID().intValue() == ID.intValue())
				return enemy;
		}
		return null;
	}

}
