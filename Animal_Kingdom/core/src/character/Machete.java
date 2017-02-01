package character;

import packet.DamageInfo;
import screens.PlayScreen;

public class Machete extends Weapon {
	
	public Machete(BasePlayer bp) {
		super(bp);
		//arbitrary 50 unit range for now.
		range = 50;
		//arbitrary 10 damage for now.
		damage = 10;
	}
	
	public void attack(Character enemy){
		enemy.getOwner().getAKClient().getClient().sendTCP(new DamageInfo("", enemy.getUID(), damage));
		System.out.println(enemy.getUID()+" takes "+ damage+ " dmg");
	}
}
