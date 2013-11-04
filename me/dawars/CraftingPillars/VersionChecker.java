package me.dawars.CraftingPillars;

import java.awt.Desktop;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

import javax.swing.JOptionPane;

public class VersionChecker
{
	public static class Version
	{
		public int[] digits;
		
		public Version(String text)
		{
			String[] split = text.split("\\.");
			this.digits = new int[split.length];
			for(int i = 0; i < split.length; i++)
				this.digits[i] = Integer.parseInt(split[i]);
		}
		
		public boolean greater(Version v)
		{
			for(int i = 0; i < this.digits.length && i < v.digits.length; i++)
				if(this.digits[i] > v.digits[i])
					return true;
				else if(this.digits[i] < v.digits[i])
					return false;
			return this.digits.length > v.digits.length;
		}
		
		public boolean less(Version v)
		{
			for(int i = 0; i < this.digits.length && i < v.digits.length; i++)
				if(this.digits[i] < v.digits[i])
					return true;
				else if(this.digits[i] > v.digits[i])
					return false;
			return this.digits.length < v.digits.length;
		}
		
		@Override
		public boolean equals(Object o)
		{
			if(!(o instanceof Version))
				return false;
			
			Version v = (Version) o;
			if(v.digits.length != this.digits.length)
				return false;
			for(int i = 0; i < this.digits.length; i++)
				if(this.digits[i] != v.digits[i])
					return false;
			return true;
		}
		
		@Override
		public String toString()
		{
			String s = ""+this.digits[0];
			for(int i = 1; i < this.digits.length; i++)
				s += "."+this.digits[i];
			return s;
		}
	}
	
	/*public static class MultiVersion
	{
		public Version[] versions;
		
		public MultiVersion(String text)
		{
			String[] split = text.replace(" ", "").split(",");
			this.versions = new Version[split.length];
			for(int i = 0; i < split.length; i++)
				this.versions[i] = new Version(split[i]);
		}
		
		@Override
		public String toString()
		{
			String s = ""+this.versions[0];
			for(int i = 1; i < this.versions.length; i++)
				s += ", "+this.versions[i];
			return s;
		}
	}*/
	
	public static void check()
	{
		try
		{
			URL url = new URL("https://dl.dropboxusercontent.com/s/pfrwfrj5m03jsrz/version.txt");
			url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String updateLink = null, line = br.readLine();
			while(line != null)
			{
				if(line.split(" ")[0].equals("1.6.4")/* && new Version(CraftingPillars.version).less(new Version(line.split(" ")[1]))*/)
					updateLink = line.split(" ")[2];
				line = br.readLine();
			}
			br.close();
			
			if(updateLink != null && JOptionPane.showConfirmDialog(new Frame(), "New update available for the Crafting Pillars mod! Do you want to download it?") == JOptionPane.YES_OPTION)
			{
				Desktop.getDesktop().browse(new URI(updateLink));
				if(JOptionPane.showConfirmDialog(new Frame(), "Do you want to visit the mod's forum page?") == JOptionPane.YES_OPTION)
					Desktop.getDesktop().browse(new URI(line));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
