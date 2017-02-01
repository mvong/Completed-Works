package character;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import ai.B2dSteeringEntity;
import character.Character.Direction;
import character.Character.Type;
import character.BasePlayer.State;
import client.AKClient;
import screens.PlayScreen;
import sprites.BossLoader;
import sprites.GorillaLoader;

public class Boss extends Enemy {
	private Animation bossRun;
	private boolean facingRight;
	private float stateTimer;
	private State curState;
	private State prevState;
	private BossLoader animationLoader;
	private World world;
	private Type type;
	private float x;
	private float y;
	private Player target;
	private B2dSteeringEntity aiFunction;
	private Boolean dead;
	private float deathTime;

	public Boss(){
		super();
		this.setHealth(1000);
	}
	
	public Boss(World world, TextureAtlas at, float x, float y, PlayScreen owner) {
		super(world, owner);
		dead = false;
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
		setBounds(getX(), getY(), 150/PlayScreen.PPM, 120/PlayScreen.PPM);
		defineBoss(world);
		aiFunction = new B2dSteeringEntity(this.b2body, .3f);
		
		
		animationLoader = new BossLoader(at);
		
		bossRun = animationLoader.getRunning();	
	}
	
	public void setTarget(Player target){
		this.target = target;
		Arrive<Vector2> arriveSB = new Arrive<Vector2>(aiFunction, target)
				.setTimeToTarget(.01f)
				.setArrivalTolerance(2f)
				.setDecelerationRadius(5);
		aiFunction.setBehavior(arriveSB);
	}
	
	public Type getType(){
		return type;
	}
	
	private void defineBoss(World world){
		BodyDef bdef = new BodyDef();
		bdef.position.set(x, y);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		b2body.setUserData(getChar());
		
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		fdef.filter.categoryBits = AKClient.ENEMY_BIT;
		fdef.filter.maskBits = AKClient.PLAYER_BIT | AKClient.MAP_BIT;
		shape.setRadius(45f/PlayScreen.PPM);
		fdef.shape = shape;
		b2body.createFixture(fdef);
		
		Fixture fix = b2body.getFixtureList().get(0);
		fix.setUserData(getChar());
	}
	
	public BossLoader getLoader() {
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
	
	public TextureRegion getFrame(float dt) {
		curState = getState();
		TextureRegion tr;
		switch(curState){
			case RUNNING:
				tr = bossRun.getKeyFrame(stateTimer, true);
				break;
			case STANDING:
			default:
				tr = animationLoader.getStanding();
				break;
			case DEAD:
				tr = animationLoader.getDying();
				break;
		}
		if(this.b2body.getLinearVelocity().x < 0 && tr.isFlipX()) {
			tr.flip(true, false);
			facingRight = false;
		}
		else if(this.b2body.getLinearVelocity().x>0 && !tr.isFlipX()) {
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
	
	public void update(float delta){
		aiFunction.update(delta);
		stateTimer += delta;
		setPosition(b2body.getPosition().x- getWidth()/2, b2body.getPosition().y- getHeight()/2);
		setRegion(getFrame(delta));
		if(willDisappear) {
			setRegion(animationLoader.getDying());
			b2body.setActive(false);
			b2body.setAwake(false);
			deathTime += delta;
		}
		if(willDisappear && deathTime> .5){
			dead = true;
			willDisappear = false;
			world.destroyBody(b2body);
			disappeared = true;
			stateTimer = 0;
		}
		
	}
	
	public Boolean died() {
		return dead;
	}
	
	public void draw(Batch batch){
		if(!disappeared | stateTimer < 1) {
			super.draw(batch);
		}
	}

}
