package com.twinklez.se;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.twinklez.se.common.SEClassTransformer;

public class Configuration {

	public static void createConfig() {
		Properties p = new Properties();
		OutputStream stream = null;
		
		try {	
			stream = new FileOutputStream("config/SuperEnchants.cfg");  
		      p.setProperty("arrowDmg", "10");
		      p.setProperty("arrowFire", "1");
		      p.setProperty("arrowInfinity", "1");
		      p.setProperty("arrowKnockback", "5");
		      p.setProperty("damage", "10");
		      p.setProperty("unbreaking", "5");
		      p.setProperty("efficiency", "10");
		      p.setProperty("fireAspect", "5");
		      p.setProperty("knockback", "5");
		      p.setProperty("lootBonus", "5");
		      p.setProperty("waterOxygen", "3");
		      p.setProperty("protection", "10");
		      p.setProperty("thorns", "5");
		      p.setProperty("untouching", "1");
		      p.setProperty("waterWorker", "1");
		      p.setProperty("Reset", "100000000000");
		      p.store(stream, "## SuperEnchants Configuration! Delete Reset, if you want to reset the config to default settings! ##");
		}
		catch (IOException io) {
			io.printStackTrace();
		} 
		finally
		{
			if (stream != null) {
				try {
					stream.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void readConfig() {
		Properties p = new Properties();
		InputStream stream = null;
		
		try {
			File file = new File ("config/SuperEnchants.cfg");
			if (!(file.exists())) {
				System.out.println("SEConfiguration file not found. Creating a new one...");
				createConfig();
			}
			
			stream = new FileInputStream("config/SuperEnchants.cfg");
			p.load(stream);
			
			if (p.getProperty("Reset") == null) {
				System.out.println("APDTSetting was removed! Re-writing configuration...");
				createConfig();
			}
			
			SEClassTransformer.seLevels[0] = Integer.valueOf(Integer.parseInt(p.getProperty("arrowDmg", "10"))).intValue();
			SEClassTransformer.seLevels[1] = Integer.valueOf(Integer.parseInt(p.getProperty("arrowFire", "1"))).intValue();
			SEClassTransformer.seLevels[2] = Integer.valueOf(Integer.parseInt(p.getProperty("arrowInfinity", "1"))).intValue();
		    SEClassTransformer.seLevels[3] = Integer.valueOf(Integer.parseInt(p.getProperty("arrowKnockback", "5"))).intValue();
		    SEClassTransformer.seLevels[4] = Integer.valueOf(Integer.parseInt(p.getProperty("damage", "10"))).intValue();
		    SEClassTransformer.seLevels[5] = Integer.valueOf(Integer.parseInt(p.getProperty("unbreaking", "5"))).intValue();
		    SEClassTransformer.seLevels[6] = Integer.valueOf(Integer.parseInt(p.getProperty("efficiency", "10"))).intValue();
		    SEClassTransformer.seLevels[7] = Integer.valueOf(Integer.parseInt(p.getProperty("fireAspect", "5"))).intValue();
		    SEClassTransformer.seLevels[8] = Integer.valueOf(Integer.parseInt(p.getProperty("knockback", "5"))).intValue();
		    SEClassTransformer.seLevels[9] = Integer.valueOf(Integer.parseInt(p.getProperty("lootBonus", "5"))).intValue();
		    SEClassTransformer.seLevels[10] = Integer.valueOf(Integer.parseInt(p.getProperty("waterOxygen", "3"))).intValue();
		    SEClassTransformer.seLevels[11] = Integer.valueOf(Integer.parseInt(p.getProperty("protection", "10"))).intValue();
		    SEClassTransformer.seLevels[12] = Integer.valueOf(Integer.parseInt(p.getProperty("thorns", "5"))).intValue();
		    SEClassTransformer.seLevels[13] = Integer.valueOf(Integer.parseInt(p.getProperty("untouching", "1"))).intValue();
		    SEClassTransformer.seLevels[14] = Integer.valueOf(Integer.parseInt(p.getProperty("waterWorker", "1"))).intValue();
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally 
		{
			if (stream != null) {
				try {
					stream.close();
				}
				catch (IOException io) {
					io.printStackTrace();
				}
			}
		}
	}
}
