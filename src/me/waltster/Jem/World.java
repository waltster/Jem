package me.waltster.Jem;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class World {
	public static final Vector3d worldDimensions = new Vector3d(64, 256, 64);
	
	public String name = "World";
	public String seed = "JEM";
	public Chunk[][][] chunks;
	public Random random = new Random();
	public Player[] players = new Player[32];
	public ArrayList<Vector4f> blocks = new ArrayList<Vector4f>();
	
	public World(Player p){
		chunks = new Chunk[(int)4][2][(int)4];
		players[0] = p;
		
		try{
			Class.forName("me.waltster.Jem.Chunk");
		}catch(Exception e){
			Jem.LOGGER.log(Level.SEVERE, null, e);
		}
		
				generateWorld();
				updateWorld();
		
	}
	
	public void render(){
		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 2; y++){
				for(int z = 0; z < 4; z++){
					if(chunks[x][y][z] != null){
						chunks[x][y][z].render();
					}
				}
			}
		}
	}
	
	public void updateWorld(){
		for(Vector4f v : blocks){
			int chunkX = (int)Math.floor(v.x / (int)Chunk.chunkDimensions.x);
			int chunkY = (int)Math.floor(v.y / (int)Chunk.chunkDimensions.y);
			int chunkZ = (int)Math.floor(v.z / (int)Chunk.chunkDimensions.z);
			
			if(chunks[chunkX][chunkY][chunkZ] == null){
				chunks[chunkX][chunkY][chunkZ] = new Chunk(new Vector3d(chunkX, chunkY, chunkZ));
			}
			
			Vector3d blockCoord = new Vector3d(v.x - (chunkX * Chunk.chunkDimensions.x), v.y - (chunkY * Chunk.chunkDimensions.y), v.z - (chunkZ * Chunk.chunkDimensions.z));
			chunks[chunkX][chunkY][chunkZ].setBlock((int)blockCoord.x, (int)blockCoord.y, (int)blockCoord.z, (int)v.w);
		}
		
		for(int x = 0; x < chunks.length; x++){
			for(int y = 0; y < chunks[x].length; y++){
				for(int z = 0; z < chunks[x][y].length; z++){
					chunks[x][y][z].updateList();
				}
			}
		}
	}
	
	public void generateWorld(){
		for(int x = 0; x < worldDimensions.x; x++){
			for(int y = 0; y < worldDimensions.y; y++){
				for(int z = 0; z < worldDimensions.z; z++){
					if(random.nextInt(10) > 5){
						blocks.add(new Vector4f(x, 10, z, 1.0f));
					}
				}
			}
		}
	}
}
