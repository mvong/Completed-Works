package sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class GorillaLoader extends Sprite{

	
	public GorillaLoader(TextureAtlas ta){
		super(ta.findRegion("cyborggorillasprite"));
		
	}
	
	public TextureRegion getStanding(){
		TextureRegion standing = new TextureRegion(getTexture(), 1050, 2051, 1000,  800);
		return standing;
	}
	
	public Animation getRunning(){
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(new TextureRegion(getTexture(), 1, 2051, 1000,  800));
		frames.add(new TextureRegion(getTexture(), 1050, 2051, 1000,  800));
		frames.add(new TextureRegion(getTexture(), 3150, 2051, 1000,  800));
		frames.add(new TextureRegion(getTexture(), 2100, 2051, 1000,  800));
		
		Animation running = new Animation(0.1f, frames);
		return running;
		
	}
	
	public TextureRegion getDying(){
		return new TextureRegion(getTexture(), 700, 1, 600, 600);
	}
	

}
