package com.nhave.nhintegration.core;

import com.nhave.nhintegration.handlers.ConfigHandler;
import com.nhave.nhintegration.handlers.IntegrationHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.Item;

@Mod(modid = Reference.MODID, version = Reference.VERSION, acceptedMinecraftVersions = Reference.MCVERSIONS, dependencies = Reference.DEPENDENCIES)
public class NHIntegration
{
	public static Item itemShaderNewMonarchy;
	public static Item itemShaderDeadOrbit;
	public static Item itemShaderWarCult;
	public static Item itemShaderTaken;
	public static Item itemShaderHarrowed;
    
    @Mod.Instance(Reference.MODID)
	public static NHIntegration instance = new NHIntegration();
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	FMLCommonHandler.instance().bus().register(new ConfigHandler());
		ConfigHandler.init();
    }
    
    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {}
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	IntegrationHandler.postInit();
    }
}