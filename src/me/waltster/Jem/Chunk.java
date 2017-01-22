package me.waltster.Jem;

import java.io.FileInputStream;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import me.waltster.Jem.Block.Blockside;

import static org.lwjgl.opengl.GL11.*;

public class Chunk {
	public static Vector3d chunkDimensions = new Vector3d(16, 128, 16);
	
	private static int maxChunkID = 0;
	private static Texture textureMap;
	
	private int[][][] blocks;
	private int displayListID = -1, chunkID = -1;
	private Vector3d position;
	public boolean dirty = false;
	
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
		displayListID = glGenLists(1);
		blocks = new int[(int) chunkDimensions.x][(int) chunkDimensions.y][(int) chunkDimensions.z];
	}
	
	public boolean updateList(){
		if(dirty){
			textureMap.bind();

			glNewList(displayListID, GL_COMPILE);
			glBegin(GL_QUADS);

			boolean front = true, back = true, left = true, right = true, top = true, bottom = true;

			for (int x = 0; x < chunkDimensions.x; x++) {
				for (int y = 0; y < chunkDimensions.y; y++) {
					for (int z = 0; z < chunkDimensions.z; z++) {
						if (blocks[x][y][z] > 0) {
							glColor3f(0, 0, 0);
							// Front
							glVertex3d(-0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									-0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(-0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									-0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									0.5f + z + (this.position.z * Chunk.chunkDimensions.z));

							glColor3f(0, 0, 0);
							// Back
							glVertex3d(-0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									-0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									-0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(-0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									-0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									-0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									-0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									-0.5f + z + (this.position.z * Chunk.chunkDimensions.z));

							glColor3f(0, 0, 1);
							// Left
							glVertex3d(-0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									-0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									-0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(-0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									-0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(-0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(-0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									-0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									0.5f + z + (this.position.z * Chunk.chunkDimensions.z));

							glColor3f(1, 0, 0);
							// Right
							glVertex3d(0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									-0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									-0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									-0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									-0.5f + z + (this.position.z * Chunk.chunkDimensions.z));

							glColor3f(0, 1, 0);
							// Top
							glVertex3d(-0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(-0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									-0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									-0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									0.5f + z + (this.position.z * Chunk.chunkDimensions.z));

							glColor3f(0, 0, 0);
							// Bottom
							glVertex3d(-0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									-0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(-0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									-0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									-0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									-0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									-0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
							glVertex3d(0.5f + x + (this.position.x * Chunk.chunkDimensions.x),
									-0.5f + y + (this.position.y * Chunk.chunkDimensions.y),
									0.5f + z + (this.position.z * Chunk.chunkDimensions.z));
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
		glCallList(displayListID);
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
}
