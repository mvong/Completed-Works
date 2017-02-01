package ai;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class B2dSteeringEntity implements Steerable<Vector2>{
	
	Body body;
	boolean tagged;
	float maxLinearSpeed;
	float maxLinearAcceleration;
	float maxAngularSpeed;
	float maxAngularAcceleration;
	float boundingRadius;
	
	SteeringBehavior<Vector2> behavior;
	SteeringAcceleration<Vector2> steeringOutput;
	
	
	public B2dSteeringEntity(Body body, float boundingRadius ){
		this.body = body;
		this.boundingRadius = boundingRadius;
		maxLinearSpeed = 100;
		maxLinearAcceleration = 200;
		maxAngularSpeed = 30;
		maxAngularAcceleration = 5;
		
		this.tagged = false;
		this.steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
		
		this.body.setUserData(this);
	}
	
	public void update(float delta){
		if(behavior != null){
			behavior.calculateSteering(steeringOutput);
			applySteering(delta);
		}
		
	}
	
	private void applySteering(float delta){
		boolean anyAccelerations = false;
		if(!steeringOutput.linear.isZero()){
			Vector2 force = steeringOutput.linear.scl(delta);
			body.applyForceToCenter(force, true);
			anyAccelerations = true;
		}
		
		if(steeringOutput.angular != 0){
			body.applyTorque(steeringOutput.angular*delta, true);
			anyAccelerations = true;
		}else{
			Vector2 linVel = getLinearVelocity();
			if(!linVel.isZero()){
				float newOrientation = vectorToAngle(linVel);
				body.setAngularVelocity(newOrientation- getAngularVelocity()*delta);
				body.setTransform(body.getPosition(), newOrientation);
			}
		}
		
		if(anyAccelerations){
			Vector2 velocity = body.getLinearVelocity();
			float currentSpeedSquare = velocity.len2();
			//linear capping
			if(currentSpeedSquare > (maxLinearSpeed * maxLinearSpeed)){
				body.setLinearVelocity(velocity.scl(maxLinearSpeed/(float)Math.sqrt(currentSpeedSquare)));
			}
			
			//angular capping
			if(body.getAngularVelocity() > maxAngularSpeed){
				body.setAngularVelocity(maxAngularSpeed);
			}
		}
	}

	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}

	@Override
	public float getOrientation() {
		return body.getAngle();
	}

	@Override
	public void setOrientation(float orientation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float vectorToAngle(Vector2 vector) {
		return SteeringUtils.vectorToAngle(vector);
	}

	@Override
	public Vector2 angleToVector(Vector2 outVector, float angle) {
		return SteeringUtils.angleToVector(outVector, angle);
	}

	@Override
	public Location<Vector2> newLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getZeroLinearSpeedThreshold() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setZeroLinearSpeedThreshold(float value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getMaxLinearSpeed() {
		return maxLinearSpeed;
	}

	@Override
	public void setMaxLinearSpeed(float maxLinearSpeed) {
		this.maxLinearSpeed = maxLinearSpeed;
	}

	@Override
	public float getMaxLinearAcceleration() {
		return maxLinearAcceleration;
	}

	@Override
	public void setMaxLinearAcceleration(float maxLinearAcceleration) {
		this.maxLinearAcceleration = maxLinearAcceleration;
	}

	@Override
	public float getMaxAngularSpeed() {
		return maxAngularSpeed;
	}

	@Override
	public void setMaxAngularSpeed(float maxAngularSpeed) {
		this.maxAngularSpeed = maxAngularSpeed;
	}

	@Override
	public float getMaxAngularAcceleration() {
		return maxAngularAcceleration;
	}

	@Override
	public void setMaxAngularAcceleration(float maxAngularAcceleration) {
		this.maxAngularAcceleration = maxAngularAcceleration;
	}

	@Override
	public Vector2 getLinearVelocity() {
		return body.getLinearVelocity();
	}

	@Override
	public float getAngularVelocity() {
		return body.getAngularVelocity();
	}

	@Override
	public float getBoundingRadius() {
		return boundingRadius;
	}

	@Override
	public boolean isTagged() {
		return tagged;
	}

	@Override
	public void setTagged(boolean tagged) {
		this.tagged = tagged;
	}
	
	public Body getBody(){
		return body;
	}
	
	public void setBehavior(SteeringBehavior<Vector2> behavior){
		this.behavior = behavior;
	}
	
	public SteeringBehavior<Vector2> getBehavior(){
		return behavior;
	}
}
