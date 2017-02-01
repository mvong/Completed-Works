package sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class OctopusLoader extends Sprite {
	
protected TextureAtlas atlas;
	
	public OctopusLoader(TextureAtlas ta){
		super(ta.findRegion("octopusEnemy"));
		
	}
	
	public TextureRegion getStanding(){
		TextureRegion standing =new TextureRegion(getTexture(), 30, 380, 500,  400);
		return standing;
	}
	
	public Animation getRunning(){
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(new TextureRegion(getTexture(), 80, 1, 500,  400));
		frames.add(new TextureRegion(getTexture(), 80, 800, 500,  400));
		frames.add(new TextureRegion(getTexture(), 80, 1650, 500,  400));
		
		Animation running = new Animation(0.1f, frames);
		return running;
		
	}
	
	public TextureRegion getDying(){
		return new TextureRegion(getTexture(), 700, 1, 600, 600);
	}

}
