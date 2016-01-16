package com.nhave.nhwrench.common.handlers;

import com.nhave.nhwrench.api.WrenchRegistry;

import cpw.mods.fml.common.Loader;

/**
 * NHAVE's Omniwrench
 * 
 * @author nhave
 */
public class IntegrationHandler
{
	public static void postInit()
	{
		if (Loader.isModLoaded("BuildCraft"))
		{
			try
			{
				WrenchRegistry.registerStatic("BuildCraft");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("CarpentersBlocks"))
		{
			try
			{
				WrenchRegistry.registerStatic("CarpentersBlocks");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("EnderIO"))
		{
			try
			{
				WrenchRegistry.registerStatic("EnderIO");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("Railcraft"))
		{
			try
			{
				WrenchRegistry.registerStatic("Railcraft");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("appliedenergistics2"))
		{
			try
			{
				WrenchRegistry.registerStatic("AE2");
			}
			catch (Exception e) {}
		}
	}
}