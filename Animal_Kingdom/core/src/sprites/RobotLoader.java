package sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class RobotLoader extends Sprite{
	
	public RobotLoader(TextureAtlas ta){
		super(ta.findRegion("Robot"));
		
	}
	
	public TextureRegion getStanding(){
		TextureRegion standing = new TextureRegion(getTexture(), 1, 3950, 500, 600);
		return standing;
	}
	
	public Animation getRunning(){
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(new TextureRegion(getTexture(), 3450, 3950, 500, 600));
		frames.add(new TextureRegion(getTexture(),2850 ,3950,  500, 600));
		frames.add(new TextureRegion(getTexture(), 4000, 3950, 500, 600));
		
		Animation running = new Animation(0.1f, frames);
		return running;
		
	}
	
	public Animation getAttacking(boolean isGun){
		Array<TextureRegion> frames = new Array<TextureRegion>();
		if(!isGun){
			frames.add(new TextureRegion(getTexture(), 2350, 3950, 500, 600));
			frames.add(new TextureRegion(getTexture(), 1750, 3380, 500, 600));
			frames.add(new TextureRegion(getTexture(), 1150, 2850, 500, 600));
			
			Animation attack = new Animation(0.1f, frames);
			return attack;
		}
		else{
			frames.add(new TextureRegion(getTexture(), 5700, 3950, 500, 600));
			frames.add(new TextureRegion(getTexture(), 6300, 3950, 500, 600));
			
			Animation attack = new Animation(0.1f, frames);
			return attack;
		}
	}
	
	public Animation getJumping(){
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(new TextureRegion(getTexture(), 600, 3950, 500, 600));
		frames.add(new TextureRegion(getTexture(), 1150, 3950, 500, 600));
		frames.add(new TextureRegion(getTexture(), 1750, 3950, 500, 600));
		
		Animation jump = new Animation(0.1f, frames);
		return jump;
	}
	
	public Animation getDying(){
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(new TextureRegion(getTexture(), 6780, 3960, 500, 600));
		frames.add(new TextureRegion(getTexture(), 6780, 3450, 500, 600));
		frames.add(new TextureRegion(getTexture(), 6800, 2850, 550, 600));
	
		Animation dying = new Animation(0.1f, frames);
		return dying;
	}


}
