package com.nhave.nhintegration.handlers;

import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

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
    public static boolean enableCofh = true;
    public static boolean enableBuildCraft = true;
    public static boolean enableGardenStuff = true;
    public static boolean enableStorageDrawers = true;
    
	public static void init(File configFile)
	{
		config = com.nhave.nhwrench.common.handlers.ConfigHandler.config;
		//config = new Configuration(configFile);
		loadConfig();
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if(eventArgs.modID.equalsIgnoreCase("nhwrench"))
		{
			loadConfig();
		}
	}
	
	public static void loadConfig()
	{
		loadCommonConfig();
		
		if (!config.hasChanged()) return;
		config.save();
	}
	
	public static void loadCommonConfig()
	{
		config.setCategoryComment("module", "Enable/Disable integration modules");
		
		enableIC2 = config.get("module", "EnableIC2", true).setRequiresMcRestart(true).getBoolean(true);
		enableEPort = config.get("module", "EnableEnhancedPortals", true).setRequiresMcRestart(true).getBoolean(true);
		enablePneuCraft = config.get("module", "EnablePneumaticCraft", true).setRequiresMcRestart(true).getBoolean(true);
		enableSteamCraft = config.get("module", "EnableSteamCraft", true).setRequiresMcRestart(true).getBoolean(true);
		enableBiblio = config.get("module", "EnableBiblioCraft", true).setRequiresMcRestart(true).getBoolean(true);
		enableIE = config.get("module", "EnableImmersiveEngineering", true).setRequiresMcRestart(true).getBoolean(true);
		enableBluePower = config.get("module", "EnableBluePower", true).setRequiresMcRestart(true).getBoolean(true);
		enableQuantumFlux = config.get("module", "EnableQuantumFlux", true).setRequiresMcRestart(true).getBoolean(true);
		enableSFM = config.get("module", "EnableStevesFactoryManager", true).setRequiresMcRestart(true).getBoolean(true);
		enableMekanism = config.get("module", "EnableMekanism", true).setRequiresMcRestart(true).getBoolean(true);
		enablePowersuits = config.get("module", "EnablePowersuitsDismantle", true).setRequiresMcRestart(true).getBoolean(true);
		enableCofh = config.get("module", "EnableCofh", true).setRequiresMcRestart(true).getBoolean(true);
		enableBuildCraft = config.get("module", "EnableBuildcraftDismantle", true).setRequiresMcRestart(true).getBoolean(true);
		enableGardenStuff = config.get("module", "EnableGardenCraft", true).setRequiresMcRestart(true).getBoolean(true);
		enableStorageDrawers = config.get("module", "EnableStorageDrawers", true).setRequiresMcRestart(true).getBoolean(true);
	}
}