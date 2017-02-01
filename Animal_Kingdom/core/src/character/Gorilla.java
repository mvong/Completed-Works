package character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import character.Character.Direction;
import character.Character.Type;
import character.BasePlayer.State;
import client.AKClient;
import screens.PlayScreen;
import sprites.GorillaLoader;

public class Gorilla extends Enemy{

	private Animation gorillaRun;
	private boolean facingRight;
	private float stateTimer;
	private State curState;
	private State prevState;
	private GorillaLoader animationLoader;
	private World world;
	private Type type;
	private float x;
	private float y;
	private float deathTime;
	
	public Gorilla(){
		super();
		this.setHealth(100);
	}
	
	public Gorilla(World world, TextureAtlas at, float x, float y, PlayScreen owner) {
		super(world, owner);
		this.curState = State.RUNNING;
		this.prevState = State.RUNNING;
		this.direction = Direction.RIGHT;
		stateTimer = 0;
		facingRight = true;
		this.setHealth(100);
		this.type = Type.ENEMY;
		this.world = world;
		this.x = x;
		this.y = y;
		deathTime = 0;
		setBounds(getX(), getY(), 100/PlayScreen.PPM, 80/PlayScreen.PPM);
		defineGorilla(world);
		
		
		animationLoader = new GorillaLoader(at);
		
		gorillaRun = animationLoader.getRunning();	
	}
	
	public Type getType(){
		return type;
	}
	
	private void defineGorilla(World world){
		BodyDef bdef = new BodyDef();
		bdef.position.set(x, y);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		b2body.setUserData(getChar());
		
		FixtureDef fdef = new FixtureDef();
		fdef.restitution = 1;
		CircleShape shape = new CircleShape();
		fdef.filter.categoryBits = AKClient.ENEMY_BIT;
		fdef.filter.maskBits = AKClient.PLAYER_BIT | AKClient.MAP_BIT;
		shape.setRadius(30/PlayScreen.PPM);
		fdef.shape = shape;
		b2body.createFixture(fdef);
		
	}
	
	public GorillaLoader getLoader() {
		return animationLoader;
	}
	
	
	public State getState() {
		if(willDisappear){
			return State.DEAD;
		}
		if(this.b2body.getLinearVelocity().x != 0) {
			return State.RUNNING;
		}
		else {
			return State.STANDING;
		}
	}
	public void move(){
		
	}
	
	public TextureRegion getFrame(float dt) {
		curState = getState();
		TextureRegion tr;
		switch(curState){
			case RUNNING:
				tr = gorillaRun.getKeyFrame(stateTimer, true);
				break;
			case STANDING:
			default:
				tr = animationLoader.getStanding();
				break;
			case DEAD:
				tr = animationLoader.getDying();
				break;
		}
		if(velocity.x < 0 && tr.isFlipX()) {
			tr.flip(true, false);
			facingRight = false;
		}
		else if(velocity.x>0 && !tr.isFlipX()) {
			tr.flip(true, false);
			
			facingRight = false;
		}
		if(curState == prevState){ 
			stateTimer += dt;
		}
		else stateTimer = 0;
		
		prevState = curState;
		return tr;
	}
	@Override
	public void update(float delta) {
		stateTimer += delta;
		b2body.setLinearVelocity(velocity);
		if(stateTimer > 3){
			reverseVelocity(true, false);
			stateTimer = 0;
		}
		if(willDisappear) {
			setRegion(animationLoader.getDying());
			b2body.setActive(false);
			b2body.setAwake(false);
			deathTime += delta;
		}
		if(willDisappear && deathTime> .5){
			willDisappear = false;
			world.destroyBody(b2body);
			disappeared = true;
			stateTimer = 0;
		}
		setPosition(b2body.getPosition().x- getWidth()/2, b2body.getPosition().y- getHeight()/2);
		setRegion(getFrame(delta));
		
		
	}
	
	public void draw(Batch batch){
		if(!disappeared | stateTimer < 1) {
			super.draw(batch);
		}
	}

	
}
