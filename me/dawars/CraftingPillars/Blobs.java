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
	
	private int f(int r, int strength)
	{
		//TODO:
		
		return 0;
	}
	
	public int[][][] fieldStrength(List<Blobs> blobs){
		int result[][][] = new int[16][16][16];
		
		for(int x = 0; x < 16; x++)
		{
			for(int y = 0; y < 16; y++)
			{
				for(int z = 0; z < 16; z++)
				{
					
					for(int i = 0; i < blobs.size(); i++)
					{
						result[x][y][z] += f(1/*pitagorasz*/, blobs.get(i).strength);
					}
					
				}
			}
		}
		
		return null;
	}
}