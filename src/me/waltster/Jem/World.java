package me.waltster.Jem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

import org.lwjgl.util.vector.Vector4f;

public class World {
	public static final int CHUNK_X_COUNT = 7;
	public static final int CHUNK_Y_COUNT = 2;
	public static final int CHUNK_Z_COUNT = 7;
		
	public String name = "World";
	public String seed = "JEM";
	public List<Chunk> activeChunks;
	public Map<String, Chunk> loadedChunks;
	public Random random = new Random();
	public Player[] players = new Player[32];
	public ArrayList<Vector4f> blocks = new ArrayList<Vector4f>();
	public PerlinNoiseGenerator perlinGen;
	private long timeSinceLastDisplayUpdate = 0;
	int counter = 0;
	
	public World(Player p){
		activeChunks = new ArrayList<Chunk>();
		loadedChunks = new HashMap<String, Chunk>();
		
		players[0] = p;
		perlinGen = new PerlinNoiseGenerator();
		
		try{
			Class.forName("me.waltster.Jem.Chunk");
		}catch(Exception e){
			Jem.LOGGER.log(Level.SEVERE, null, e);
		}
		
	/*	Runnable r = new Runnable(){
			@Override
			public void run() {
				generateWorld();
			}
		};
		
		new Thread(r).start();*/
		
		Vector3d v;
		Chunk temp;
		
		for(int x = 0; x < CHUNK_X_COUNT; x++){
			for(int y= 0; y < CHUNK_Y_COUNT; y++){
				for(int z = 0; z < CHUNK_Z_COUNT; z++){
					v = new Vector3d(x,y,z);
					temp = new Chunk(v);
					
					loadedChunks.put(v.toString(), temp);
					generateChunk(temp);
			//		temp.updateList();
				}
			}
		}
	}
	
	public void render(){
		for(Chunk c : loadedChunks.values()){
			c.render();
			
			if(c.updateList()){
				timeSinceLastDisplayUpdate = System.currentTimeMillis();
			}
		}
	}
	
	public void generateChunk(Chunk c){
		for(int x = 0; x < Chunk.chunkDimensions.x; x++){
			for(int z = 0; z < Chunk.chunkDimensions.z; z++){
				float height = perlinGen.getTerrainHeightAt((x + (float)c.getPosition().x * (float)Chunk.chunkDimensions.x) / 4.0f, (z + (float)c.getPosition().z * (float)Chunk.chunkDimensions.z) / 4.0f);
				
				if(height < 0){
					height = 0;
				}
				
				float y = height * 256 + 32.0f;
				
				if(y > 128){
					y = 128;
				}
				
				c.setBlock((int)x, (int)y, (int)z, 1);
				y--;
				
				while(y > 0){
					c.setBlock((int)x, (int)y, (int)z, 2);
					y--;
				}
			}
		}
	}
	
	public void setBlock(Vector3d position, int type){
		getChunk(position).setBlock((int)(position.x % Chunk.chunkDimensions.x), (int)(position.y % Chunk.chunkDimensions.y), (int)(position.z % Chunk.chunkDimensions.z), type);
	}
	
	public Chunk getChunk(Vector3d position){
		int x = (int)(position.x / Chunk.chunkDimensions.x);
		int y=  (int)(position.y / Chunk.chunkDimensions.y);
		int z = (int)(position.z / Chunk.chunkDimensions.z);
		
		Chunk c = loadedChunks.get(new Vector3d(x, y, z).toString());
		
		if(c == null){
			c = new Chunk(new Vector3d(x,y,z));
			loadedChunks.put(position.toString(), c);
			generateChunk(loadedChunks.get(position.toString()));
		}
		
		return c;
	}
	
	public Vector3d worldToChunkCoordinates(Vector3d coords){
		return new Vector3d((int)(coords.x % Chunk.chunkDimensions.x), (int)(coords.y % Chunk.chunkDimensions.y), (int)(coords.z % Chunk.chunkDimensions.z));
	}
}
