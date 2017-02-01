package character;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;

import ai.B2dSteeringEntity;
import client.AKClient;
import screens.PlayScreen;

public abstract class Character extends Sprite{
	protected static Integer enemyCount = 0;
	protected static Integer playerCount = 0;
	protected Integer UID;
	protected String username;
	
	// set health to be the width of the health bar
	protected double health = 1;
	
	protected Body b2body;
	protected Direction direction;
	protected Type type;
	protected AttackState attackState;
	protected World world;
	
	
	//closest collision calculation variables. 
	private float minDistance = 2;
	private int minIndex = -1;
	
	protected boolean disappeared;
	protected boolean willDisappear;
	protected boolean isDead = false;
	
	private PlayScreen owner;
	
	public Character(){
		
	}
	
	Character(World world, PlayScreen owner){
		//UID = count++;
		this.world = world;
		this.attackState = AttackState.VULNERABLE;
		this.health = 160;
		this.owner = owner;
	}
	
	public static void resetCount(){
		enemyCount = 0;
		playerCount = 0;
	}
	
	public enum Direction{
		LEFT, RIGHT, BIDIRECTIONAL
	}
	
	public enum AttackState{
		INVULNERABLE, VULNERABLE
	}
	
	public enum Type{
		PLAYER, ENEMY
	}
	
	public boolean getDisappeared() {
		return disappeared;
	}
	
	public boolean IsDead(){
		return isDead;
	}
	
	public void setDead(boolean dead) {
		isDead = dead;
	}
		
	public void dealDamage(int val){
		if(this.attackState == AttackState.VULNERABLE){
			this.health -= val;
			if (this instanceof BasePlayer) {
				BasePlayer bp = (BasePlayer)this;
				bp.getHealthbar().updateHealth(val);
			}
		}
		if(this.health <= 0){
			//TODO: this character died!
			willDisappear = true;
			isDead = true;
			System.out.println(getUID() + " has died.");
		}
	}
	
	//check for valid targets and attack
	public void attemptAttack(){
		//System.out.println("Type: " + getType());
		if(getType() == Character.Type.PLAYER){
			//TODO: check for valid target.
			BasePlayer p = (BasePlayer)this;
			Character enemy = findFixture(p.getWeapon().getRange());
			if(enemy == this) return;
			
			p.getWeapon().attack(enemy);
			System.out.println(enemy.getUID() + " has " + enemy.getHealth() + " health.");
		}
		else if(getType() == Character.Type.ENEMY){
			//TODO: use body to get hitbox and check for players
			Character enemy = this;
			
			Enemy e = (Enemy)this;
			e.attack(enemy);
		}
	}

	/**
	 * Returns the character that was closest in the direction that the attack was fired in a certain range
	 * Returns itself if no character was found. 
	 * 
	 */
	 private Character findFixture(float range){
		//System.out.println("entered");
		int directionMultiplier = direction == Direction.LEFT ? -1 : 1;
		final Vector2 oldPosition = b2body.getWorldCenter();
		Vector2 newPosition = new Vector2(oldPosition.x + directionMultiplier * range/PlayScreen.PPM, oldPosition.y);
		final ArrayList<Character> targets = new ArrayList<Character>();
		//hit collision logic. 
		RayCastCallback r = new RayCastCallback(){
			/**
			 * This function runs on all fixtures that the ray collides with.
			 * return 0 to stop searching
			 * return 1 to continue searching
			 */
			@Override
			public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
				if(fixture.getFilterData().categoryBits == AKClient.MAP_BIT) return 0;
				Body b = fixture.getBody();
				
				
				Character unique = b.getUserData() instanceof B2dSteeringEntity ? (Character)fixture.getUserData() : (Character)b.getUserData();
				
				//body is an enemy! see if it's the closest.
				if(unique.getType() == Type.ENEMY){
					if(fraction < minDistance){
						targets.add(unique);
						minDistance = fraction;
						minIndex = targets.size() - 1;
					}
				}
				//continue the ray to find all collisions. 
				return 1;
			}
		};
		
		//search
		world.rayCast(r, oldPosition, newPosition);
		int tempIndex = minIndex;
		//reset variables. 
		minDistance = 2;
		minIndex = -1;
		//hit closest enemy. 
		if(!targets.isEmpty()){
			//TODO: do damage!
			return targets.get(tempIndex);
		}
		return this;
	}
	 
	public AttackState getAttackState(){
		return attackState;
	}
	
	public Integer getUID() {
		return UID;
	}
	
	public String getUsername() {
		return username;
	}
	
	public Type getType() {
		return type;
	}
	
	public double getHealth() {
		return health;
	}
	
	public Body getBody() {
		return b2body;
	}
	
	public Direction getDirection() {
		return direction;
	}
	public Character getChar() {
		return this;
	}
	
	public PlayScreen getOwner(){
		return owner;
	}
	
	public void setDirection(Direction dir){
		direction = dir;
	}
	
	public void setHealth(double health){
		this.health = health;
	}
	
	public World getWorld(){
		return world;
	}
}
