package me.waltster.Jem;

import static org.lwjgl.opengl.GL11.*;

public class Player {
	public float yaw = 0.0f, pitch = 0.0f;
	private float walkingSpeed = 01.5f;
	public Vector3d position;
	
	public Player(){
		this.position = new Vector3d(0.0f, -100.0f, 0.0f);
	}
	
	public void render(){
		glRotatef(pitch, 1.0f, 0.0f, 0.0f);
		glRotatef(yaw, 0.0f, 1.0f, 0.0f);
		glTranslated(position.x, position.y, position.z);
	}
	
	public void yaw(float y){
		yaw += y;
	}
	
	public void pitch(float p){
		pitch += p;
	}
	
	public void walkForward(){
		position.x -= walkingSpeed * (float)Math.sin(Math.toRadians(yaw));
		position.z += walkingSpeed * (float)Math.cos(Math.toRadians(yaw));
	}
	
	public void walkBackwards(){
		position.x += walkingSpeed * (float)Math.sin(Math.toRadians(yaw));
		position.z -= walkingSpeed * (float)Math.cos(Math.toRadians(yaw));
	}
	
	public void strafeLeft(){
		position.x -= walkingSpeed * (float)Math.sin(Math.toRadians(yaw - 90));
		position.z -= walkingSpeed * (float)Math.cos(Math.toRadians(yaw - 90));
	}
	
	public void strafeRight(){
		position.x -= walkingSpeed * (float)Math.sin(Math.toRadians(yaw + 90));
		position.z += walkingSpeed * (float)Math.cos(Math.toRadians(yaw + 90));
	}
	
	public void fall(){
		position.y += walkingSpeed;
	}
	
	public void rise(){
		position.y -= walkingSpeed;
	}
}
