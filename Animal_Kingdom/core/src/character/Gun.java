package character;

import packet.DamageInfo;
import screens.PlayScreen;

public class Gun extends Weapon {
	
	public Gun(BasePlayer bp) {
		super(bp);
		//arbitrary 400 unit range for now.
		range = 400;
		//arbitrary 10 damage for now.
		damage = 10;
	}
	
	public void attack(Character enemy){
		enemy.getOwner().getAKClient().getClient().sendTCP(new DamageInfo("", enemy.getUID(), damage));
	}
}
