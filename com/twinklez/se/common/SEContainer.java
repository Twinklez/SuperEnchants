package com.twinklez.se.common;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.twinklez.se.Configuration;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class SEContainer extends DummyModContainer {
	
	private String string;
	
	public SEContainer() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = "se";
		meta.name = "Super Enchants";
		meta.version = "0.4.2";
		meta.credits = "";
		meta.authorList = Arrays.asList("Twinklez");
		meta.description = "Modifies vanilla enchantments to have a higher enchantment cap.";
		meta.url = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller)  {
		bus.register(this);
		return true;
	}
	
	@Subscribe
	public void modConstruction(FMLConstructionEvent e) {

	}
	
	@Subscribe
	public void preInit(FMLPreInitializationEvent e) {
		if (string != null) {
			return;
		}
		Configuration.readConfig();
		this.string = "SEConfig Status: GENERATED";
	}
	
	@Subscribe
	public void init(FMLInitializationEvent e) {
		
	}
	
	@Subscribe
	public void postInit(FMLPostInitializationEvent e) {
	 
	}
}
