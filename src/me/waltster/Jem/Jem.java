package me.waltster.Jem;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Jem {
	public static final int DISPLAY_HEIGHT = 768;
	public static final int DISPLAY_WIDTH = 1024;
	public static final Logger LOGGER = Logger.getLogger("Jem");
	public static final float MOUSE_SENSITIVITY = 0.1f;
	private Player player;
	private World world;
	
	public static void main(String args[]){
		Jem jem = null;
		
		try{
			jem = new Jem();
			
			jem.create();
			jem.init();
			jem.run();
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}finally{
			Display.destroy();
		}
		
		System.exit(0);
	}
	
	public Jem(){}
	
	public void create() throws LWJGLException{
		Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
		Display.setFullscreen(true);
		Display.setTitle("Jem");
		Display.create();
		Keyboard.create();
		Mouse.setGrabbed(true);
		Mouse.create();
	}
	
	public void init(){
		glClearColor(0.5f, 0.75f, 1.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		//glEnable(GL_FOG);
		//glDisable(GL_LIGHTING);
		//glDisable(GL_COLOR_MATERIAL);
		/*glFogi(GL_FOG_MODE, GL_LINEAR);
		glFogf(GL_FOG_DENSITY, 1.0f);
		glHint(GL_FOG_HINT, GL_DONT_CARE);
		glFogf(GL_FOG_START, 512.0f);
		glFogf(GL_FOG_END, 1024.0f);
		*/
		try{
			Class.forName("me.waltster.Jem.Chunk");
			Class.forName("me.waltster.Jem.BlockRegistry");
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, null, e);
		}
		
		world = new World(player);
		player=  new Player();
		
		glViewport(0, 0, (int)DISPLAY_WIDTH, DISPLAY_HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(64.0f, (float)DISPLAY_WIDTH / (float)DISPLAY_HEIGHT, 1f, 1024f);
		glPushMatrix();
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glPushMatrix();
	}
	
	public void processKeyboard(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			player.walkForward();
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			player.walkBackwards();
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			player.strafeLeft();
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			player.strafeRight();
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			player.rise();
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			player.fall();
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			if(!Keyboard.isRepeatEvent()){
				Mouse.setGrabbed(!Mouse.isGrabbed());
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_T)){
			Jem.LOGGER.info("Breakpoint");
		}
	}
	
	public void render(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
		glPushMatrix();
			player.render();
			world.render();
		glPopMatrix();
	}
	
	public void run(){
		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			processKeyboard();
			update();
			render();
			
			Display.update();
		}
	}
	
	public void update(){
		player.yaw(Mouse.getDX() * MOUSE_SENSITIVITY);
		player.pitch(-1 * (Mouse.getDY() * MOUSE_SENSITIVITY));
	}
}
