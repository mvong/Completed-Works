package Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class HealthBar extends Actor{

	ShapeRenderer sr;
	static private boolean projectionMatrixSet;
	
	public final double healthBarX = 30;
    public final double healthBarY = 405;
    public double healthBarWidth = 160;
    public final double healthBarHeight = 20;
	
	public HealthBar(){
	    sr = new ShapeRenderer();
	    projectionMatrixSet = false;
	
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		if(healthBarWidth > 0) {
			batch.end();
			
			// NOTE: RIGHT NOW THERE ARE HARD-CODED NUMBERS
			// TODO: adjust healthbarwidth based on health
		    sr.begin(ShapeType.Filled);
		    if (healthBarWidth > 112) {
		    	sr.setColor(Color.GREEN);
		    }
		    else if (healthBarWidth <= 112 && healthBarWidth > 64) {
		    	sr.setColor(Color.ORANGE);
		    }
		    else {
		    	sr.setColor(Color.RED);
		    }
		    sr.rect((int)healthBarX, (int)healthBarY, (int)healthBarWidth, (int)healthBarHeight);
		    sr.end();
		
		    batch.begin();
		}
	}	
	
	// TEST: health bar decreases with time
	// TODO: decrease with damage
	public void updateHealth(Integer health) {
		healthBarWidth -= health;
	}
	
	public void setHealth(Integer health){
		healthBarWidth = health;
	}
}