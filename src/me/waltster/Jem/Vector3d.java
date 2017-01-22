package me.waltster.Jem;

public class Vector3d {
	public double x, y, z;
	
	public Vector3d(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public String toString(){
		return x + "," + y + "," + z;
	}
}
