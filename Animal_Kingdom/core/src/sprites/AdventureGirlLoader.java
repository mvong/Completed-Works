package sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AdventureGirlLoader extends Sprite{
	
	public AdventureGirlLoader(TextureAtlas ta){
		super(ta.findRegion("AdventureGirl"));
		
	}
	
	public TextureRegion getStanding(){
		TextureRegion standing = new TextureRegion(getTexture(), 1900 ,5050, 500, 600);
		return standing;
	}
	
	public Animation getRunning(){
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add( new TextureRegion(getTexture(), 4500, 5050, 500, 600));
		frames.add( new TextureRegion(getTexture(), 5150, 5050, 500, 600));
		frames.add(new TextureRegion(getTexture(), 5800, 5050, 500, 600));
		
		Animation running = new Animation(0.1f, frames);
		return running;
		
	}
	
	public Animation getAttacking(boolean isGun){
		Array<TextureRegion> frames = new Array<TextureRegion>();
		if(!isGun){
			frames.add(new TextureRegion(getTexture(), 2550, 5050, 600, 600));
			frames.add(new TextureRegion(getTexture(), 3200, 5050, 500, 600));
			frames.add(new TextureRegion(getTexture(), 3850, 5600, 500, 600));
			
			Animation attack = new Animation(0.1f, frames);
			return attack;
		}
		else{
			frames.add(new TextureRegion(getTexture(), 6450, 5600, 500, 600));
			frames.add(new TextureRegion(getTexture(), 6450, 5050, 500, 600));
			
			Animation attack = new Animation(0.1f, frames);
			return attack;
		}
	}
	
	public Animation getJumping(){
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(new TextureRegion(getTexture(), 3850, 6150, 500, 600));
		frames.add(new TextureRegion(getTexture(), 5080, 6150, 500, 600));
		frames.add(new TextureRegion(getTexture(), 5730, 6150, 500, 600));
		frames.add(new TextureRegion(getTexture(), 3150, 6150, 500, 600));
		
		Animation jump = new Animation(0.1f, frames);
		return jump;
	}
	
	public Animation getDying(){
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(new TextureRegion(getTexture(), 3900, 5050, 500, 600));
		frames.add(new TextureRegion(getTexture(),  1, 4893, 500, 600));
		frames.add(new TextureRegion(getTexture(),  650, 4893, 500, 600));
	
		Animation dying = new Animation(0.1f, frames);
		return dying;
	}

}
