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
    public static boolean cleanConfig = false;
    public static boolean easyModeShaders = false;
    
    public static String[] allowedDismantle = new String[] {"tile.hopper;-1"};
	
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
		
		prop = config.get("common", "EasyModeShaders", easyModeShaders);
		prop.comment = "Allow Rare and Legendary Shader Packs to be crafted";
		prop.setRequiresMcRestart(true);
		easyModeShaders = prop.getBoolean();
		
		prop = config.get("common", "CleanConfig", false);
		prop.comment = "If set to true the config will be cleaned\nAll settings will be kept";
		prop.setRequiresMcRestart(true);
		cleanConfig = prop.getBoolean(false);
	}
	
	public static void loadClientConfig() {}
}