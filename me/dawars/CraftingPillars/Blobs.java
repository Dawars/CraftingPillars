package me.dawars.CraftingPillars;

import java.util.List;
import java.util.Random;

public class Blobs
{
	public float x, y, z, velX, velY, velZ;
	public int strength;

	private Random random = new Random();
	
	public Blobs(float x, float y, float z, int strength)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.strength = strength;
		this.setVelocity();
	}
	
	public void setVelocity(float velX, float velY, float velZ)
	{
		this.velX = velX;
		this.velY = velY;
		this.velZ = velZ;
	}
	
	public void setVelocity()
	{
		this.velX = this.velY = this.velZ = 1F;
		
		if(random.nextBoolean())
			this.velX *= -1F;
		if(random.nextBoolean())
			this.velY *= -1F;
		if(random.nextBoolean())
			this.velZ *= -1F;
	}
	
	

	public void update()
	{
		this.update(1F);
	}
	
	public void update(float speed)
	{
		if(this.x > 14F || this.x < 2F)
			this.velX *= -1F;
		
		if(this.z > 14F || this.z < 2F)
			this.velZ *= -1F;
		
		if(this.y > 14F || this.y < 4F)
			this.velY *= -1F;
		
		
		this.x += speed * this.velX;
		this.y += speed * this.velY;
		this.z += speed * this.velZ;
	}
	
	private static float f(float r, int strength)
	{
		r /= 16;
		return r * r * r * (r * (r * 6 - 15) + 10) * strength * 16;
	}
	
	private static float f(float r)
	{
		return f(r, 3);
	}
	
	public static int[][][] fieldStrength(List<Blobs> blobs){
		int result[][][] = new int[16][16][16];
		
		for(int x = 0; x < 16; x++)
		{
			for(int y = 0; y < 16; y++)
			{
				for(int z = 0; z < 16; z++)
				{
					
					for(int i = 0; i < blobs.size(); i++)
					{
						float xDist = blobs.get(i).x - x;
						float yDist = blobs.get(i).y - y;
						float zDist = blobs.get(i).z - z;
						float r = xDist*xDist + yDist*yDist + zDist*zDist; //distance square
						if(r <= blobs.get(i).strength*blobs.get(i).strength)
						{
							result[x][y][z] += f((float) Math.sqrt(r), blobs.get(i).strength);//not working
//							result[x][y][z] += r);
						}
					}
					
				}
			}
		}
		
		return result;
	}
}