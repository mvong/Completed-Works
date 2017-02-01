package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BossLoader extends Sprite {
protected TextureAtlas atlas;
	
	public BossLoader(TextureAtlas ta){
		super(ta.findRegion("boss_sprite_preview"));
		
	}
	
	public TextureRegion getStanding(){
		TextureRegion standing =new TextureRegion(getTexture(), 8195, 6000, 1250, 800);
		return standing;
	}
	
	public Animation getRunning(){
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(new TextureRegion(getTexture(), 8195, 6000, 1250, 800));
		frames.add(new TextureRegion(getTexture(), 9550, 6000, 1250, 800));
		frames.add(new TextureRegion(getTexture(), 10900, 6000, 1250, 800));
		
		Animation running = new Animation(0.1f, frames);
		return running;
		
	}
	
	public TextureRegion getDying(){
		return new TextureRegion(getTexture(), 700, 1, 600, 600);
	}

}
