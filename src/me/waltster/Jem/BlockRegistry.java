package me.waltster.Jem;

import java.util.HashMap;

public class BlockRegistry {
	private static HashMap<Integer, Block> blocks;
	
	static{
		blocks = new HashMap<Integer, Block>();
		populate();
		
	}
	
	public static Block getBlock(int id){
		return blocks.get(id);
	}
	
	public static void addBlock(Block b){
		blocks.put(b.getID(), b);
	}
	
	public static void reset(){
		blocks.clear();
		populate();
	}
	
	private static void populate(){
		blocks.put(0, new Block("air", -1, Material.AIR, 0));
		blocks.put(1, new Block("dirt", 0, Material.DIRT, 1));
		blocks.put(2, new Block("grass", 1, Material.GRASS, 2));
		blocks.put(3, new Block("wood", 2, Material.WOOD, 3));
	}
}
