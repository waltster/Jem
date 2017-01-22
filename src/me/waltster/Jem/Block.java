package me.waltster.Jem;

public class Block {
	protected int indexInTexture;
	protected int id;
	protected float hardness;
	protected float resistance;
	protected boolean constructorCalled;
	protected Material material;
	protected String name;
	
	public enum Blockside{
		FRONT, BACK, LEFT, RIGHT, TOP, BOTTOM;
	}
	
	public Block(String name, int indexInTexture, Material material, int id){
		this.name = name;
		this.indexInTexture = indexInTexture;
		this.material = material;
		this.id = id;
		this.constructorCalled = true;
	}
	
	public float[] getTextureCoordinates(Blockside side){
		return new float[]{0, 0};
	}
	
	public String getName(){
		return name;
	}
	
	public Material getType(){
		return this.material;
	}
	
	public int getID(){
		return id;
	}
	
	public boolean constructed(){
		return this.constructorCalled;
	}
}
