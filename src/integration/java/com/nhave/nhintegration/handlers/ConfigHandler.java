package com.nhave.nhintegration.handlers;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigHandler
{	
	public static Configuration config;
	
	public static boolean enableIC2 = true;
    public static boolean enableEPort = true;
    public static boolean enablePneuCraft = true;
    public static boolean enableSteamCraft = true;
    public static boolean enableBiblio = true;
    public static boolean enableIE = true;
    public static boolean enableBluePower = true;
    public static boolean enableQuantumFlux = true;
    public static boolean enableSFM = true;
    public static boolean enableMekanism = true;
    public static boolean enablePowersuits = true;
    
	public static void init()
	{
		if (config == null)
		{
			config = com.nhave.nhwrench.common.handlers.ConfigHandler.config;
			loadConfig(true);
		}
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if(eventArgs.modID.equalsIgnoreCase("nhwrench"))
		{
			loadConfig(false);
		}
	}
	
	public static void loadConfig(boolean load)
	{
		if (load) config.load();

		loadCommonConfig();
		
		if (!config.hasChanged()) return;
		config.save();
	}
	
	public static void loadCommonConfig()
	{
		config.setCategoryComment("module", "Enable/Disable integration modules");
		Property prop = config.get("module", "EnableIC2", enableIC2);
		prop.setRequiresMcRestart(true);
		enableIC2 = prop.getBoolean(enableIC2);
		prop = config.get("module", "EnableEnhancedPortals", enableEPort);
		prop.setRequiresMcRestart(true);
		enableEPort = prop.getBoolean(enableEPort);
		prop = config.get("module", "EnablePneumaticCraft", enablePneuCraft);
		prop.setRequiresMcRestart(true);
		enablePneuCraft = prop.getBoolean(enablePneuCraft);
		prop = config.get("module", "EnableSteamCraft", enableSteamCraft);
		prop.setRequiresMcRestart(true);
		enableSteamCraft = prop.getBoolean(enableSteamCraft);
		prop = config.get("module", "EnableBiblioCraft", enableBiblio);
		prop.setRequiresMcRestart(true);
		enableBiblio = prop.getBoolean(enableBiblio);
		prop = config.get("module", "EnableImmersiveEngineering", enableIE);
		prop.setRequiresMcRestart(true);
		enableIE = prop.getBoolean(enableIE);
		prop = config.get("module", "EnableBluePower", enableBluePower);
		prop.setRequiresMcRestart(true);
		enableBluePower = prop.getBoolean(enableBluePower);
		prop = config.get("module", "EnableQuantumFlux", enableQuantumFlux);
		prop.setRequiresMcRestart(true);
		enableQuantumFlux = prop.getBoolean(enableQuantumFlux);
		prop = config.get("module", "EnableStevesFactoryManager", enableSFM);
		prop.setRequiresMcRestart(true);
		enableSFM = prop.getBoolean(enableSFM);
		prop = config.get("module", "EnableMekanism", enableMekanism);
		prop.setRequiresMcRestart(true);
		enableMekanism = prop.getBoolean(enableMekanism);
		prop = config.get("module", "EnablePowersuitsDismantle", enablePowersuits);
		prop.setRequiresMcRestart(true);
		enableSFM = prop.getBoolean(enablePowersuits);
	}
}