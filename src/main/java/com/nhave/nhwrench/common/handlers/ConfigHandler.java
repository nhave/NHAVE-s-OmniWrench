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
    public static boolean disableForgeDirection = Defaults.disableForgeDirection;

    public static String[] allowedDismantle = Defaults.allowedDismantle;
    public static String[] forgeDirBlacklist = Defaults.forgeDirBlacklist;
    
    public static double FROM_IC2 = Defaults.FROM_IC2;
    public static double TO_IC2 = Defaults.TO_IC2;
    public static double FROM_TE = Defaults.FROM_TE;
    public static double TO_TE = Defaults.TO_TE;
    public static String powerUnit = Defaults.powerUnit;
	
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
		config.setCategoryComment("common.features", "Configuration to enable/disable faetures");
		config.setCategoryComment("common.crafting", "Configuration enable/disable crafting");
		config.setCategoryComment("common.power", "Configuration for power settings");
		config.setCategoryComment("common.power.conversion", "Change conversion rate for Universal Power");
		config.setCategoryComment("common.forgedirecttion", "Configuration for Forge Direction");
		
		cleanConfig = config.get("common", "CleanConfig", Defaults.cleanConfig, "If set to true the config will be cleaned").setRequiresMcRestart(true).getBoolean(Defaults.cleanConfig);
		enableUtilMode = config.get("common.features", "EnableUtilMode", Defaults.enableUtilMode, "Set to false to Disable the Util Mode").getBoolean(Defaults.enableUtilMode);
		enableHoe = config.get("common.features", "EnableHoe", Defaults.enableHoe, "Set to false to Disable the Hoe feature").getBoolean(Defaults.enableHoe);
		enableShears = config.get("common.features", "EnableShears", Defaults.enableShears, "Set to false to Disable the Shears feature").getBoolean(Defaults.enableShears);
		disableRecipes = config.get("common.crafting", "DisableRecipes", Defaults.disableRecipes, "Set to true to disable the recipes for the parts").setRequiresMcRestart(true).getBoolean(Defaults.disableRecipes);
		allowedDismantle = config.get("common", "AllowedDismantle", Defaults.allowedDismantle, "Add unlocalized block names to allow them to be dismantled").getStringList();
		easyModeShaders = config.get("common.crafting", "EasyModeShaders", Defaults.easyModeShaders, "Allow Rare and Legendary Shader Packs to be crafted").setRequiresMcRestart(true).getBoolean(Defaults.easyModeShaders);
		disableMobDrops = config.get("common", "DisableMobDrops", Defaults.disableMobDrops, "Disable all mobs from dropping shaders").getBoolean(Defaults.disableMobDrops);
		shaderDropChance = config.get("common", "ShaderDropChance", Defaults.shaderDropChance, "If 0 Shaders will not drop from normal mobs").setMaxValue(100).setMinValue(0).getInt(Defaults.shaderDropChance);
		legendaryChance = config.get("common", "LegendaryChance", Defaults.legendaryChance, "If 0 Legendary Shaders will not drop from normal mobs").setMaxValue(100).setMinValue(0).getInt(Defaults.legendaryChance);
		exoticFromLegendary = config.get("common", "ExoticFromLegendary", Defaults.exoticFromLegendary, "Allow only Legendary ShaderPacks to be turned into Exotics").getBoolean(Defaults.exoticFromLegendary);
		disableMemCards = config.get("common.crafting", "DisableMemCards", Defaults.disableMemCards, "Disable crafting of Memory Cards").setRequiresMcRestart(true).getBoolean(Defaults.disableMemCards);
		
		usePower = config.get("common.power", "UsePower", Defaults.usePower, "For the more Hardcore players who want the wrench to require power to work").getBoolean(Defaults.usePower);
		maxPower = config.get("common.power", "MaxPower", Defaults.maxPower, "If power is enabled. Sets max power stored in RF").setMinValue(0).setRequiresMcRestart(true).getInt(Defaults.maxPower);
		powerUsage = config.get("common.power", "PowerUsage", Defaults.powerUsage, "If power is enabled. Sets power used per action in RF").setMinValue(0).setRequiresMcRestart(true).getInt(Defaults.powerUsage);
		powerUnit = config.get("common.power", "PowerUnit", Defaults.powerUnit, "Sets the Power Unit to display").setValidValues(Defaults.powerUnits).getString();
		
		FROM_IC2 = config.get("common.power.conversion", "FROM_IC2", Defaults.FROM_IC2, "IC2 from MJ").getDouble(Defaults.FROM_IC2);
		TO_IC2 = config.get("common.power.conversion", "TO_IC2", Defaults.TO_IC2, "IC2 to MJ").getDouble(Defaults.FROM_IC2);
		FROM_TE = config.get("common.power.conversion", "FROM_TE", Defaults.FROM_TE, "TE from MJ").getDouble(Defaults.FROM_TE);
		TO_TE = config.get("common.power.conversion", "TO_TE", Defaults.TO_TE, "TE to MJ").getDouble(Defaults.TO_TE);
		
		disableForgeDirection = config.get("common.forgedirecttion", "DisableForgeDirection", Defaults.disableForgeDirection, "Disable the Forge Direction feature").getBoolean(Defaults.disableForgeDirection);
		forgeDirBlacklist = config.get("common.forgedirecttion", "ForgeDirBlacklist", Defaults.forgeDirBlacklist, "Blacklists a Class path or Package from calling Forge Direction and prevent World = null").getStringList();
	}
	
	public static void loadClientConfig()
	{
		//config.setCategoryComment("client", "Configuration for all Client configs");
	}
}