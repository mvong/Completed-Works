package character;

import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ai.SteeringUtils;
import screens.PlayScreen;

abstract public class Player extends BasePlayer implements Location {
	
	
	
	protected Integer gamesPlayed;
	protected Integer gamesWon;
	
	protected boolean initAttack;
	
	public Player(){
		super();
	}
	
	abstract public void update(float delta);
	
	public Player(World world, PlayScreen owner) {
		super(world, owner);
		this.type = Type.PLAYER;
	}
	
	public Integer getGamesPlayed() {
		return gamesPlayed;
	}
	
	public Integer getGamesWon() {
		return gamesWon;
	}
	
	public void setAttack() {
		initAttack = true;
	}

	@Override
	public Vector2 getPosition() {
		return this.b2body.getPosition();
	}

	@Override
	public float getOrientation() {
		return this.b2body.getAngle();
	}

	@Override
	public void setOrientation(float orientation) {
		
	}

	@Override
	public float vectorToAngle(Vector vector) {
		return SteeringUtils.vectorToAngle((Vector2) vector);
	}

	@Override
	public Vector angleToVector(Vector outVector, float angle) {
		return SteeringUtils.angleToVector((Vector2) outVector, angle);
	}

	@Override
	public Location newLocation() {
		return null;
	}

	public void setGamesPlayed(Integer gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public void setGamesWon(Integer gamesWon) {
		this.gamesWon = gamesWon;
	}
	
	
}
