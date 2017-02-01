package character;

public abstract class Weapon implements Attack {

	protected Integer damage;
	protected float range;
	
	private BasePlayer character;
	
	public Weapon(BasePlayer bp) {
		//TODO: fill in
		
		character = bp;
	}
	
	public Integer getDamage() {
		return damage;
	}
	
	public float getRange() {
		return range;
	}
	
	public void setDamage(int d){
		damage = d;
	}
}
