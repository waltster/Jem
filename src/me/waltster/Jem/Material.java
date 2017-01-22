package me.waltster.Jem;

public enum Material {
	AIR("air", 0, false),
	DIRT("dirt", 1, true),
	GRASS("grass", 2, true),
	WOOD("wood", 3, true);
	
	private String name;
	private int id;
	private boolean stackable;
	
	Material(String name, int id, boolean stackable){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public int getID(){
		return id;
	}
}
