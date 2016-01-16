package com.nhave.nhwrench.common.handlers;

import java.io.File;

import com.nhave.nhwrench.common.core.NHWrench;
import com.nhave.nhwrench.common.core.Reference;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigHandler
{
	public static boolean isClientConfig;
	
	public static Configuration config;
    public static boolean logging = false;
    public static boolean enableUtilMode = true;
    public static boolean enableHoe = true;
    public static boolean enableShears = true;
    public static boolean disableRecipes = false;
    public static boolean basicComponentsRecipes = false;
    public static boolean cleanConfig = false;
    
    public static String[] allowedDismantle = new String[] {"tile.hopper;-1"};
    
    public static boolean enableIC2 = true;
    public static boolean enableCofh = true;
    public static boolean enableEPort = true;
    public static boolean enablePneuCraft = true;
    public static boolean enableSteamCraft = true;
    public static boolean enableBiblio = true;
    public static boolean enableIE = true;
    public static boolean enableBluePower = true;
    public static boolean enableQuantumFlux = true;
    public static boolean enableSFM = true;
    public static boolean enableMekanism = true;
	
	public ConfigHandler(boolean isClient)
	{
		this.isClientConfig = isClient;
	}

	public static void init(File configFile)
	{
		if (config == null)
		{
			config = new Configuration(configFile);
			loadConfig(true);
			if (cleanConfig)
			{
				NHWrench.logger.info("[nhwrench]: Cleaning Config");
				config.getConfigFile().delete();
				config = new Configuration(configFile);
				loadConfig(false);
				NHWrench.logger.info("[nhwrench]: Config cleaned");
			}
		}
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if(eventArgs.modID.equalsIgnoreCase(Reference.MODID))
		{
			loadConfig(false);
		}
	}
	
	public static void loadConfig(boolean load)
	{
		if (load) config.load();

		loadCommonConfig();
		if (isClientConfig) loadClientConfig();
		
		if (!config.hasChanged()) return;
		config.save();
	}
	
	public static void loadCommonConfig()
	{
		config.setCategoryComment("common", "Configuration for all Common configs");
		Property prop;
		
		prop = config.get("common", "EnableUtilMode", enableUtilMode);
		prop.comment = "Set to false to Disable the Util Mode";
		enableUtilMode = prop.getBoolean(enableUtilMode);
		
		prop = config.get("common", "EnableHoe", enableHoe);
		prop.comment = "Set to false to Disable the Hoe feature";
		enableHoe = prop.getBoolean(enableHoe);
		
		prop = config.get("common", "EnableShears", enableShears);
		prop.comment = "Set to false to Disable the Shears feature";
		enableShears = prop.getBoolean(enableShears);
		
		prop = config.get("common", "DisableRecipes", disableRecipes);
		prop.comment = "Set to true to disable the recipes for the parts";
		prop.setRequiresMcRestart(true);
		disableRecipes = prop.getBoolean(disableRecipes);
		
		prop = config.get("common", "AllowedDismantle", allowedDismantle);
		prop.comment = "Add unlocalized block names to allow them to be dismantled";
		prop.setRequiresMcRestart(true);
		allowedDismantle = prop.getStringList();
		
		prop = config.get("common", "CleanConfig", false);
		prop.comment = "If set to true the config will be cleaned\nAll settings will be kept";
		prop.setRequiresMcRestart(true);
		cleanConfig = prop.getBoolean(false);
		
		//loadModuleConfig();
	}
	
	public static void loadModuleConfig()
	{
		config.setCategoryComment("module", "Enable/Disable integration modules");
		Property prop = config.get("module", "EnableIC2", enableIC2);
		prop.setRequiresMcRestart(true);
		enableIC2 = prop.getBoolean(enableIC2);
		prop = config.get("module", "EnableCofh", enableCofh);
		prop.setRequiresMcRestart(true);
		enableCofh = prop.getBoolean(enableCofh);
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
	}
	
	public static void loadClientConfig() {}
}