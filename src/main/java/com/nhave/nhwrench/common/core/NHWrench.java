package com.nhave.nhwrench.common.core;

import org.apache.logging.log4j.Logger;

import com.nhave.nhwrench.api.API;
import com.nhave.nhwrench.client.creativetabs.CreativeTabShaders;
import com.nhave.nhwrench.client.creativetabs.CreativeTabWrench;
import com.nhave.nhwrench.common.handlers.CraftingHandler;
import com.nhave.nhwrench.common.handlers.IntegrationHandler;
import com.nhave.nhwrench.common.handlers.ItemHandler;
import com.nhave.nhwrench.common.register.WrenchModeRegistry;
import com.nhave.nhwrench.common.register.WrenchRegistry;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, version = Reference.VERSION, acceptedMinecraftVersions = Reference.MCVERSIONS, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUIFACTORY)
public class NHWrench
{
    public static Logger logger;
    
	public static String MC_CONFIG_DIR;

	public static final CreativeTabWrench creativeTabItems = new CreativeTabWrench("nhwrench.items");
	public static final CreativeTabShaders creativeTabShaders = new CreativeTabShaders("nhwrench.shaders");
    
    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static CommonProxy proxy;
    
    @Mod.Instance(Reference.MODID)
	public static NHWrench instance = new NHWrench();
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
    	
    	MC_CONFIG_DIR = event.getModConfigurationDirectory().getAbsolutePath();
		proxy.setupConfig(event.getSuggestedConfigurationFile());

		API.modeRegister = new WrenchModeRegistry();
		API.integrationRegister = new WrenchRegistry();
		
    	ItemHandler.preInit();
    	
		WrenchRegistry.updateModMeta(event.getModMetadata().description);
    }
    
    @Mod.EventHandler
    public void load(FMLInitializationEvent event)
    {
    	IntegrationHandler.init();
    	proxy.registerRenderers();
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	ItemHandler.postInit();
    	CraftingHandler.postInit();
    	proxy.registerEventHandlers();
    	IntegrationHandler.postInit();
    }
}