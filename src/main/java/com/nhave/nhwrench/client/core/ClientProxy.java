package com.nhave.nhwrench.client.core;

import java.io.File;

import com.nhave.nhwrench.common.core.CommonProxy;
import com.nhave.nhwrench.common.handlers.ConfigHandler;

import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers() {}
	
	@Override
	public void setupConfig(File configFile)
	{
		FMLCommonHandler.instance().bus().register(new ConfigHandler(true));
		ConfigHandler.init(configFile);
	}
	
	@Override
    public void registerEventHandlers()
    {
        super.registerEventHandlers();
    }
}