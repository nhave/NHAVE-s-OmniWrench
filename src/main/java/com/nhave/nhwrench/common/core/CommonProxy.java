package com.nhave.nhwrench.common.core;

import java.io.File;

import com.nhave.nhwrench.common.eventhandlers.ShaderDropHandler;
import com.nhave.nhwrench.common.handlers.ConfigHandler;
import com.nhave.nhwrench.common.handlers.CraftingHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
{
	public void registerRenderers() {}
	
	public void setupConfig(File configFile)
	{
		FMLCommonHandler.instance().bus().register(new ConfigHandler(false));
		ConfigHandler.init(configFile);
	}
	
	public void registerEventHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new CraftingHandler());
		MinecraftForge.EVENT_BUS.register(new ShaderDropHandler());
	}
    
    public boolean isNHAVE()
    {
    	if (Minecraft.getMinecraft().thePlayer.getDisplayName().equals("nhave")) return true;
    	else return false;
    }
}