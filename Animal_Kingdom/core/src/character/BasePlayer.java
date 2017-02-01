package character;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import Scenes.HealthBar;
import character.BasePlayer.State;
import screens.PlayScreen;


public class BasePlayer extends Character { 
	
	public enum State{ DEAD, FALLING, JUMPING, SECONDJUMP, RUNNING, STANDING, ATTACKING }
	
	protected State curState;
	protected State prevState;
	
	
	
	protected Weapon weapon;
	protected HealthBar healthbar;
	protected boolean initAttack;
	protected String name;
	
	public BasePlayer(){
		super();
		UID = playerCount++ * 2;
		
	}
	
	public BasePlayer(World world, PlayScreen owner) {
		super(world, owner);
		//definePlayer();
		healthbar = new HealthBar();
		UID = playerCount++ * 2;
		
	}
	
	
	public void jump() {
		if(curState != State.JUMPING && curState != State.SECONDJUMP) {
			getBody().applyLinearImpulse(new Vector2(0, 5f), getBody().getWorldCenter(), true);
			curState = State.JUMPING;
		}
		else if(curState == State.JUMPING && curState != State.SECONDJUMP) {
			getBody().applyLinearImpulse(new Vector2(0, 5f), getBody().getWorldCenter(), true);
			curState = State.SECONDJUMP;
		}
	}
	
	private void definePlayer(){
		BodyDef bdef = new BodyDef();
		
		//TODO: change spawning position
		bdef.position.set(300,300);
		
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);

		//TODO: add correct player size and attributes. 
		FixtureDef fdef = new FixtureDef();
		//CircleShape shape = new CircleShape();
		//shape.setRadius(10);
		//fdef.density = 1f;
		//fdef.friction = 100;
		//fdef.shape = shape;
		b2body.createFixture(fdef);
		
		//TODO: add gun fixture?
	}
	
	public String getName(){
		return username;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}

	public HealthBar getHealthbar() {
		return healthbar;
	}

	public void setHealthbar(HealthBar healthbar) {
		this.healthbar = healthbar;
	}
	
	public void setAttack() {
		initAttack = true;
	}	
}
