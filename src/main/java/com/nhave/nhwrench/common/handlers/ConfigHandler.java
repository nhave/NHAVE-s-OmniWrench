package com.nhave.nhwrench.common.handlers;

import java.io.File;

import com.nhave.nhwrench.common.core.Defaults;
import com.nhave.nhwrench.common.core.NHWrench;
import com.nhave.nhwrench.common.core.Reference;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{
	public static boolean isClientConfig;
	
	public static Configuration config;
    public static boolean logging = Defaults.logging;
    public static boolean enableUtilMode = Defaults.enableUtilMode;
    public static boolean enableHoe = Defaults.enableHoe;
    public static boolean enableShears = Defaults.enableShears;
    public static boolean disableRecipes = Defaults.disableRecipes;
    public static boolean cleanConfig = Defaults.cleanConfig;
    public static boolean easyModeShaders = Defaults.easyModeShaders;
    public static boolean disableMobDrops = Defaults.disableMobDrops;
    public static int shaderDropChance = Defaults.shaderDropChance;
    public static int legendaryChance = Defaults.legendaryChance;
    public static boolean exoticFromLegendary = Defaults.exoticFromLegendary;
    public static boolean disableMemCards = Defaults.disableMemCards;
    public static boolean usePower = Defaults.usePower;
    public static int maxPower = Defaults.maxPower;
    public static int powerUsage = Defaults.powerUsage;
    public static int powerCharge = Defaults.powerCharge;
    
    public static String[] allowedDismantle = Defaults.allowedDismantle;
	
	public ConfigHandler(boolean isClient)
	{
		this.isClientConfig = isClient;
	}

	public static void init(File configFile)
	{
		config = new Configuration(configFile);
		loadConfig(false);
		if (cleanConfig)
		{
			NHWrench.logger.info("[nhwrench]: Cleaning Config");
			config.getConfigFile().delete();
			config = new Configuration(configFile);
			loadConfig(false);
			NHWrench.logger.info("[nhwrench]: Config cleaned");
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
		loadCommonConfig();
		if (isClientConfig) loadClientConfig();
		
		if (!config.hasChanged()) return;
		config.save();
	}
	
	public static void loadCommonConfig()
	{
		config.setCategoryComment("common", "Configuration for all Common configs");
		cleanConfig = config.get("common", "CleanConfig", Defaults.cleanConfig, "If set to true the config will be cleaned").setRequiresMcRestart(true).getBoolean(Defaults.cleanConfig);
		enableUtilMode = config.get("common", "EnableUtilMode", Defaults.enableUtilMode, "Set to false to Disable the Util Mode").getBoolean(Defaults.enableUtilMode);
		enableHoe = config.get("common", "EnableHoe", Defaults.enableHoe, "Set to false to Disable the Hoe feature").getBoolean(Defaults.enableHoe);
		enableShears = config.get("common", "EnableShears", Defaults.enableShears, "Set to false to Disable the Shears feature").getBoolean(Defaults.enableShears);
		disableRecipes = config.get("common", "DisableRecipes", Defaults.disableRecipes, "Set to true to disable the recipes for the parts").setRequiresMcRestart(true).getBoolean(Defaults.disableRecipes);
		allowedDismantle = config.get("common", "AllowedDismantle", Defaults.allowedDismantle, "Add unlocalized block names to allow them to be dismantled").setRequiresMcRestart(true).getStringList();
		easyModeShaders = config.get("common", "EasyModeShaders", Defaults.easyModeShaders, "Allow Rare and Legendary Shader Packs to be crafted").setRequiresMcRestart(true).getBoolean(Defaults.easyModeShaders);
		disableMobDrops = config.get("common", "DisableMobDrops", Defaults.disableMobDrops, "Disable all mobs from dropping shaders").getBoolean(Defaults.disableMobDrops);
		shaderDropChance = config.get("common", "ShaderDropChance", Defaults.shaderDropChance, "If 0 Shaders will not drop from normal mobs").setMaxValue(100).setMinValue(0).getInt(Defaults.shaderDropChance);
		legendaryChance = config.get("common", "LegendaryChance", Defaults.legendaryChance, "If 0 Legendary Shaders will not drop from normal mobs").setMaxValue(100).setMinValue(0).getInt(Defaults.legendaryChance);
		exoticFromLegendary = config.get("common", "ExoticFromLegendary", Defaults.exoticFromLegendary, "Allow only Legendary ShaderPacks to be turned into Exotics").getBoolean(Defaults.exoticFromLegendary);
		disableMemCards = config.get("common", "DisableMemCards", Defaults.disableMemCards, "Disable crafting of Memory Cards").setRequiresMcRestart(true).getBoolean(Defaults.disableMemCards);
		usePower = config.get("common", "UsePower", Defaults.usePower, "For the more Hardcore players who want the wrench to require power to work").getBoolean(Defaults.usePower);

		maxPower = config.get("common", "MaxPower", Defaults.maxPower, "If power is enabled. Sets max power stored").setMinValue(0).setRequiresMcRestart(true).getInt(Defaults.maxPower);
		powerUsage = config.get("common", "PowerUsage", Defaults.powerUsage, "If power is enabled. Sets power used per action").setMinValue(0).setRequiresMcRestart(true).getInt(Defaults.powerUsage);
		powerCharge = config.get("common", "PowerCharge", Defaults.powerCharge, "If power is enabled. Sets max power charged per Tick").setMinValue(0).setRequiresMcRestart(true).getInt(Defaults.powerCharge);
	}
	
	public static void loadClientConfig()
	{
		//config.setCategoryComment("client", "Configuration for all Client configs");
	}
}