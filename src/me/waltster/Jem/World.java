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
	public PerlinNoiseGenerator perlinGen;
	int counter = 0;
	
	public World(Player p){
		chunks = new Chunk[4][2][4];
		players[0] = p;
		perlinGen = new PerlinNoiseGenerator();
		
		try{
			Class.forName("me.waltster.Jem.Chunk");
		}catch(Exception e){
			Jem.LOGGER.log(Level.SEVERE, null, e);
		}
		
		generateWorld();
	}
	
	public void render(){
		for(int x = 0; x < chunks.length; x++){
			for(int y = 0; y < chunks[x].length; y++){
				for(int z = 0; z < chunks[x][y].length; z++){
					if(chunks[x][y][z] != null){
						chunks[x][y][z].render();
					}
				}
			}
		}
	}
	
	public void generateWorld(){
		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 2; y++){
				for(int z = 0; z < 4; z++){
					if(chunks[x][y][z] == null){
						Chunk c = new Chunk(new Vector3d(x, y, z));
						chunks[x][y][z] = c;
					}
					
					this.generateChunk(chunks[x][y][z]);
				}
			}
		}
	}
	
	public void setBlock(Vector3d position, int type){
		Vector3d chunkPosition = new Vector3d((float)Math.floor(position.x / Chunk.chunkDimensions.x), (float)Math.floor(position.y / Chunk.chunkDimensions.y), (float)Math.floor(position.z / Chunk.chunkDimensions.z));
		Vector3d blockCoord = new Vector3d(position.x - (chunkPosition.x * Chunk.chunkDimensions.x), position.y - (chunkPosition.y * Chunk.chunkDimensions.y), position.z - (chunkPosition.z * Chunk.chunkDimensions.z));
		
		Chunk c = chunks[(int)chunkPosition.x][(int)chunkPosition.y][(int)chunkPosition.z];
		
		if(c == null){
			c = new Chunk(new Vector3d(chunkPosition.x, chunkPosition.y, chunkPosition.z));
			chunks[(int)chunkPosition.x][(int)chunkPosition.y][(int)chunkPosition.z] = c;
		}
		
		c.setBlock((int)blockCoord.x, (int)blockCoord.y, (int)blockCoord.z, type);
		c.updateList();
	}
	
	public void generateChunk(Chunk c){
		int[][][] blocks = c.getBlocks();
		
		for(int x = 0; x < Chunk.chunkDimensions.x; x++){
			for(int z = 0; z < Chunk.chunkDimensions.z; z++){
				float height = perlinGen.getTerrainHeightAt((float)(x + (c.getPosition().x * Chunk.chunkDimensions.x)) / 2f, (float)(z + (c.getPosition().z * Chunk.chunkDimensions.z)) / 2f);
				
				if(height < 0){
					height = 0;
				}
				
				float y = height * 512 + 16;
				Jem.LOGGER.info(y + "");
				if(y > 126){
					y = 126;
				}
				
				int specialY = (int)y;
				Jem.LOGGER.info("Y: " + specialY);
				blocks[x][specialY][z] = 1;
				specialY--;
				
				while(specialY >= 0){
					blocks[x][specialY][z] = 1;
					specialY--;
				}
			}
		}
		/*for(int x = 0; x < Chunk.chunkDimensions.x; x++){
			for(int y = 0; y < Chunk.chunkDimensions.y; y++){
				for(int z = 0; z < Chunk.chunkDimensions.z; z++){
					c.setBlock(x, y, z, 1);
				}
			}
		}*/
		
		for(int x = 0; x < Chunk.chunkDimensions.x; x++){
			for(int y = 0; y < Chunk.chunkDimensions.y; y++){
				for(int z = 0; z < Chunk.chunkDimensions.z; z++){
					c.setBlock(x, y, z, blocks[x][y][z]);
				}
			}
		}
		c.updateList();
	}
}
