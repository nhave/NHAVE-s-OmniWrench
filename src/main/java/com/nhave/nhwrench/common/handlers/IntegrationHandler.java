package com.nhave.nhwrench.common.handlers;

import com.nhave.nhwrench.api.API;

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
				API.integrationRegister.registerStatic("BuildCraft");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("CarpentersBlocks"))
		{
			try
			{
				API.integrationRegister.registerStatic("CarpentersBlocks");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("EnderIO"))
		{
			try
			{
				API.integrationRegister.registerStatic("EnderIO");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("Railcraft"))
		{
			try
			{
				API.integrationRegister.registerStatic("Railcraft");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("appliedenergistics2"))
		{
			try
			{
				API.integrationRegister.registerStatic("AE2");
			}
			catch (Exception e) {}
		}
	}
}