package character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import character.BasePlayer.State;
import client.AKClient;
import screens.PlayScreen;
import sprites.RobotLoader;

public class Robot extends Player {

	private World world;
	private RobotLoader animationLoader;
	private float stateTimer;
	private boolean isGun;
	
	public Robot(){
		super();
	}
	
	public Robot(TextureAtlas ta, World world, String username, Integer gamesPlayed, Integer gamesWon, boolean isGuest, boolean isGun, PlayScreen owner) {
		super(world, owner);
		this.world = world;
		defineRobot();
		this.username = username;
		this.b2body.setUserData(getChar());
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		this.isGun = isGun;
		this.weapon = isGun ? new Gun(this) : new Machete(this); 
		stateTimer = 0;
		animationLoader = new RobotLoader(ta);
		initCharPosition();
	}
	
	private void defineRobot() {
		// Define body
		BodyDef bdef = new BodyDef();
		bdef.position.set(300/PlayScreen.PPM, 300/PlayScreen.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		this.b2body = this.world.createBody(bdef);
		// Create fixture for body
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(20/PlayScreen.PPM, 28/PlayScreen.PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = AKClient.PLAYER_BIT;
		fdef.filter.maskBits = AKClient.ENEMY_BIT | AKClient.MAP_BIT;
		b2body.createFixture(fdef);
	}
	
	private void initCharPosition() {
		getChar().setBounds(0, 0, 641/PlayScreen.PPM/8, 542/PlayScreen.PPM/8);
		getChar().translateY(100);
		getChar().setRegion(animationLoader.getStanding());
	}
	
	public void update(float delta) {
		if(willDisappear)
			curState = State.DEAD;
		getChar().setPosition(b2body.getPosition().x - getChar().getWidth()/2, b2body.getPosition().y - getChar().getHeight()/2);
		getChar().setRegion(getFrame(delta));
	}
	
	public RobotLoader getLoader() {
		return animationLoader;
	}
	
	public State getState() {
		if(isDead) {
			return State.DEAD;
		}
		else {
			if(initAttack) {
				initAttack = false;
				return State.ATTACKING;
			}
			else if(this.b2body.getLinearVelocity().y != 0 && curState != State.SECONDJUMP) {
				return State.JUMPING;
			}
			else if(this.b2body.getLinearVelocity().y != 0 && curState == State.JUMPING && curState != State.SECONDJUMP) {
				return State.SECONDJUMP;
			}
			else if(this.b2body.getLinearVelocity().x != 0 && this.b2body.getLinearVelocity().y == 0) {
				return State.RUNNING;
			}
			else if(this.b2body.getLinearVelocity().y == 0){
				return State.STANDING;
			}
			else {
				return curState;
			}
		}
	}

	public TextureRegion getFrame(float dt) {
		if(prevState == State.ATTACKING && stateTimer < 0.3) curState = prevState;
		else curState = getState();
		TextureRegion tr;
		switch(curState){
			case DEAD:
				tr = animationLoader.getDying().getKeyFrame(stateTimer);
				b2body.setActive(false);
				break;
			case ATTACKING:
				tr = animationLoader.getAttacking(isGun).getKeyFrame(stateTimer);
				break;
			case JUMPING: 
				tr = animationLoader.getJumping().getKeyFrame(stateTimer);
				break;
			case RUNNING:
				tr = animationLoader.getRunning().getKeyFrame(stateTimer, true);
				break;
			case FALLING:
			case STANDING:
			default:
				tr = animationLoader.getStanding();
				break;
		}
		if(!isDead) {
			if(this.direction == Direction.LEFT && !tr.isFlipX()) {
				tr.flip(true, false);
			}
			else if(this.direction == Direction.RIGHT && tr.isFlipX()) {
				tr.flip(true, false);
			}
		}
		if(curState == prevState){ 
			stateTimer += dt;
		}
		else stateTimer = 0;
		
		prevState = curState;
		return tr;
	}
	

}
