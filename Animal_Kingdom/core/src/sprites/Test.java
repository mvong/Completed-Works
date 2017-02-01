package sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import character.AdventureGirl;
import character.Robot;
import screens.PlayScreen;

public class Test extends Sprite{
	public Body b2b;
	private World world;
	
	//textures
	private TextureRegion testStand;
	private TextureRegion testDie;
	private TextureRegion testRun;
	
	public Test(World world, TextureAtlas ta){
		super(ta.findRegion("adventure girl/Idle (1)_girl"));
		defineTest(world);
		this.world = world;
		testStand = new TextureRegion(getTexture(), 1, 1, 400, 400);
		
		setBounds(0, 0, 641/PlayScreen.PPM/8, 542/PlayScreen.PPM/8);
		translateY(100);
		setRegion(testStand);
	}
	
	public void update(float delta){
		setPosition(b2b.getPosition().x - getWidth() / 2, b2b.getPosition().y - getHeight() / 2);
	}
	
	private void defineTest(World world){
		BodyDef bdef = new BodyDef();
		bdef.position.set(300/PlayScreen.PPM,300/PlayScreen.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2b = world.createBody(bdef);
	
		
		
		FixtureDef fdef = new FixtureDef();
		//CircleShape shape = new CircleShape();
		PolygonShape shape = new PolygonShape();
		//shape.setRadius(30/PlayScreen.PPM);
		//fdef.density = 1f;
		//fdef.friction = 100;
		shape.setAsBox(20/PlayScreen.PPM, 28/PlayScreen.PPM);
		fdef.shape = shape;
		b2b.createFixture(fdef);
		
	}
}
