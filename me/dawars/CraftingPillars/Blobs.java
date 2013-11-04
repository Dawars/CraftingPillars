package me.dawars.CraftingPillars;

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
		
		if(this.y > 12F || this.y < 3F)
			this.velY *= -1F;
		
		
		this.x += speed * this.velX;
		this.y += speed * this.velY;
		this.z += speed * this.velZ;
	}
}