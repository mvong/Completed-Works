package character;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import screens.PlayScreen;

public abstract class Enemy extends Character implements Attack{
	protected Integer damage;
	protected Vector2 velocity;
	
	public Enemy(){
		super();
		UID = enemyCount++ * 2 + 1;
	}	
	public Enemy(World world, PlayScreen owner) {
		super(world, owner);

		UID = enemyCount++ * 2 + 1;
		this.type = Type.ENEMY;
		velocity = new Vector2(1,0);
		this.damage = 10;


	}
	
	public abstract void update(float delta);
	public Integer getDamage(){
		return damage;
	}
	
	public void attack(Character enemy){
		enemy.setHealth(enemy.getHealth() - damage);
	}
	
	public void reverseVelocity(boolean x, boolean y){
		if(x) velocity.x = -velocity.x;
		if(y) velocity.y = -velocity.y;
	}
}
