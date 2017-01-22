package me.waltster.Jem;

import java.io.FileInputStream;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import me.waltster.Jem.Block.Blockside;

import static org.lwjgl.opengl.GL11.*;

public class Chunk {
	public static Vector3d chunkDimensions = new Vector3d(64, 128, 64);
	
	private static int maxChunkID = 0;
	private static Texture textureMap;
	
	private int[][][] blocks;
	private int displayListID = -1, debugListID = -1, chunkID = -1;
	private Vector3d position;
	public boolean dirty = false, debugMode = true;
	
	static{
		try{
			textureMap = TextureLoader.getTexture("PNG", new FileInputStream(Chunk.class.getResource("Terrain.png").getPath()), GL_NEAREST);
		}catch(IOException e){
			e.printStackTrace();
		}
	};
	
	public Chunk(Vector3d position){
		this.position = position;
		chunkID = maxChunkID;
		maxChunkID++;
		blocks = new int[(int) chunkDimensions.x][(int) chunkDimensions.y][(int) chunkDimensions.z];
	}
	
	public boolean updateList(){
		if(displayListID == -1){
			displayListID = glGenLists(1);
		}
		
		if(dirty){
			debugListID = glGenLists(1);

            glNewList(debugListID, GL_COMPILE);
            glColor3f(255.0f, 0.0f, 0.0f);
            glBegin(GL_LINE_LOOP);
            glVertex3f(0.0f, 0.0f, 0.0f);
            glVertex3d(chunkDimensions.x, 0.0f, 0.0f);
            glVertex3d(chunkDimensions.x, chunkDimensions.y, 0.0f);
            glVertex3d(0.0f, chunkDimensions.y, 0.0f);
            glEnd();

            glBegin(GL_LINE_LOOP);
            glVertex3d(0.0f, 0.0f, 0.0f);
            glVertex3d(0.0f, 0.0f, chunkDimensions.z);
            glVertex3d(0.0f, chunkDimensions.y, chunkDimensions.z);
            glVertex3d(0.0f, chunkDimensions.y, 0.0f);
            glVertex3d(0.0f, 0.0f, 0.0f);
            glEnd();

            glBegin(GL_LINE_LOOP);
            glVertex3d(0.0f, 0.0f, chunkDimensions.z);
            glVertex3d(chunkDimensions.x, 0.0f, chunkDimensions.z);
            glVertex3d(chunkDimensions.x, chunkDimensions.y, chunkDimensions.z);
            glVertex3d(0.0f, chunkDimensions.y, chunkDimensions.z);
            glVertex3d(0.0f, 0.0f, chunkDimensions.z);
            glEnd();

            glBegin(GL_LINE_LOOP);
            glVertex3d(chunkDimensions.x, 0.0f, 0.0f);
            glVertex3d(chunkDimensions.x, 0.0f, chunkDimensions.z);
            glVertex3d(chunkDimensions.x, chunkDimensions.y, chunkDimensions.z);
            glVertex3d(chunkDimensions.x, chunkDimensions.y, 0.0f);
            glVertex3d(chunkDimensions.x, 0.0f, 0.0f);
            glEnd();
            glEndList();
            
			textureMap.bind();

			glNewList(displayListID, GL_COMPILE);
			glBegin(GL_QUADS);

			boolean front = true, back = true, left = true, right = true, top = true, bottom = true;

			for (int x = 0; x < chunkDimensions.x; x++) {
				for (int y = 0; y < chunkDimensions.y; y++) {
					for (int z = 0; z < chunkDimensions.z; z++) {
						if (blocks[x][y][z] > 0 && !isHidden(x, y, z)) {
							int x1 = x;// + ((int)this.position.x * (int)Chunk.chunkDimensions.x);
							int y1 = y;// + ((int)this.position.y * (int)Chunk.chunkDimensions.y);
							int z1 = z;// + ((int)this.position.z * (int)Chunk.chunkDimensions.z);

							glColor3f(0, 0, 0);
							// Front
							glVertex3d(-0.5f + x1,
									-0.5f + y1,
									0.5f + z1);
							glVertex3d(-0.5f + x1,
									0.5f + y1,
									0.5f + z1);
							glVertex3d(0.5f + x1,
									0.5f + y1,
									0.5f + z1);
							glVertex3d(0.5f + x1,
									-0.5f + y1,
									0.5f + z1);

							glColor3f(0, 0, 0);
							// Back
							glVertex3d(-0.5f + x1,
									-0.5f + y1,
									-0.5f + z1);
							glVertex3d(-0.5f + x1,
									0.5f + y1,
									-0.5f + z1);
							glVertex3d(0.5f + x1,
									0.5f + y1,
									-0.5f + z1);
							glVertex3d(0.5f + x1,
									-0.5f + y1,
									-0.5f + z1);

							glColor3f(0, 0, 1);
							// Left
							glVertex3d(-0.5f + x1,
									-0.5f + y1,
									-0.5f + z1);
							glVertex3d(-0.5f + x1,
									0.5f + y1,
									-0.5f + z1);
							glVertex3d(-0.5f + x1,
									0.5f + y1,
									0.5f + z1);
							glVertex3d(-0.5f + x1,
									-0.5f + y1,
									0.5f + z1);

							glColor3f(1, 0, 0);
							// Right
							glVertex3d(0.5f + x1,
									-0.5f + y1,
									0.5f + z1);
							glVertex3d(0.5f + x1,
									0.5f + y1,
									0.5f + z1);
							glVertex3d(0.5f + x1,
									0.5f + y1,
									-0.5f + z1);
							glVertex3d(0.5f + x1,
									-0.5f + y1,
									-0.5f + z1);

							glColor3f(0, 1, 0);
							// Top
							glVertex3d(-0.5f + x1,
									0.5f + y1,
									0.5f + z1);
							glVertex3d(-0.5f + x1,
									0.5f + y1,
									-0.5f + z1);
							glVertex3d(0.5f + x1,
									0.5f + y1,
									-0.5f + z1);
							glVertex3d(0.5f + x1,
									0.5f + y1,
									0.5f + z1);

							glColor3f(0, 0, 0);
							// Bottom
							glVertex3d(-0.5f + x1,
									-0.5f + y1,
									0.5f + z1);
							glVertex3d(-0.5f + x1,
									-0.5f + y1,
									-0.5f + z1);
							glVertex3d(0.5f + x1,
									-0.5f + y1,
									-0.5f + z1);
							glVertex3d(0.5f + x1,
									-0.5f + y1,
									0.5f + z1);
						}
					}
				}
			}

			glEnd();
			glEndList();
			
			dirty = false;
			return true;
		}
		
		return false;
	}
	
	public void render(){
		glPushMatrix();
		glTranslated(this.position.x * Chunk.chunkDimensions.x, this.position.y * Chunk.chunkDimensions.y, this.position.z * Chunk.chunkDimensions.z);
		glCallList(displayListID);
		
		if(this.debugMode){
			glCallList(debugListID);
		}
		glPopMatrix();
	}
	
	public int getBlock(int x, int y, int z){
		if(x > chunkDimensions.x - 1 || y > chunkDimensions.y - 1 || z > chunkDimensions.z - 1 || x < 0 || y < 0 || z < 0){
			return -1;
		}
		
		return blocks[x][y][z];
	}
	
	public void setBlock(int x, int y, int z, int type){
		blocks[x][y][z] = type;
		dirty = true;
	}
	
	public Vector3d getPosition(){
		return this.position;
	}
	
	public int[][][] getBlocks(){
		return blocks;
	}
	
	public int getID(){
		return chunkID;
	}
	
	public boolean isHidden(int x, int y, int z){
		if(x == 0 || y == 0 || z == 0 || x == Chunk.chunkDimensions.x - 1 || y == Chunk.chunkDimensions.y - 1 || z == Chunk.chunkDimensions.x - 1){
			return false;
		}
		
		if(blocks[x + 1][y][z] > 0 && blocks[x - 1][y][z] > 0 && blocks[x][y - 1][z] > 0 && blocks[x][y + 1][z] > 0 && blocks[x][y][z - 1] > 0 && blocks[x][y][z + 1] > 0){
			return true;
		}
		
		return false;
	}
}
