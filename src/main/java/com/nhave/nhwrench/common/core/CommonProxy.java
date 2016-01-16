package com.nhave.nhwrench.common.core;

import java.io.File;

import net.minecraft.client.Minecraft;

import com.nhave.nhwrench.common.handlers.ConfigHandler;

import cpw.mods.fml.common.FMLCommonHandler;

public class CommonProxy
{
	public void registerRenderers() {}
	
	public void setupConfig(File configFile)
	{
		FMLCommonHandler.instance().bus().register(new ConfigHandler(false));
		ConfigHandler.init(configFile);
	}
	
	public void registerEventHandlers() {}
    
    public boolean isNHAVE()
    {
    	if (Minecraft.getMinecraft().thePlayer.getDisplayName().equals("nhave")) return true;
    	else return false;
    }
}