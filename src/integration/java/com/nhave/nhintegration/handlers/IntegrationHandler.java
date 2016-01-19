package com.nhave.nhintegration.handlers;

import com.nhave.nhintegration.integration.BiblioHandler;
import com.nhave.nhintegration.integration.BluePowerHandler;
import com.nhave.nhintegration.integration.EnhPortHandler;
import com.nhave.nhintegration.integration.IC2Handler;
import com.nhave.nhintegration.integration.IEHandler;
import com.nhave.nhintegration.integration.MekaHandler;
import com.nhave.nhintegration.integration.PneuHandler;
import com.nhave.nhintegration.integration.SteamHandler;
import com.nhave.nhwrench.api.API;
import com.nhave.nhwrench.common.helpers.CustomDismantleHelper;
import com.nhave.nhwrench.common.helpers.HarvestLevelHelper;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.MinecraftForge;

/**
 * NHAVE's Omniwrench
 * 
 * @author nhave
 */
public class IntegrationHandler
{
	public static void postInit()
	{
		if (Loader.isModLoaded("IC2") && ConfigHandler.enableIC2)
		{
			try
			{
				API.integrationRegister.registerHandler(new IC2Handler(), "IC2");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("Mekanism") && ConfigHandler.enableMekanism)
		{
			try
			{
				API.integrationRegister.registerHandler(new MekaHandler(), "Mekanism");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("enhancedportals") && ConfigHandler.enableEPort)
		{
			try
			{
				API.integrationRegister.registerHandler(new EnhPortHandler(), "EnhancedPortals");
				CustomDismantleHelper.addBlock("enhancedportals:dbs", false);
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("PneumaticCraft") && ConfigHandler.enablePneuCraft)
		{
			try
			{
				API.integrationRegister.registerHandler(new PneuHandler(), "PneumaticCraft");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("Steamcraft") && ConfigHandler.enableSteamCraft)
		{
			try
			{
				API.integrationRegister.registerHandler(new SteamHandler(), "Steamcraft");
				MinecraftForge.EVENT_BUS.register(new SteamHandler());
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("BiblioCraft") && ConfigHandler.enableBiblio)
		{
			try
			{
				API.integrationRegister.registerHandler(new BiblioHandler(), "BiblioCraft");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("ImmersiveEngineering") && ConfigHandler.enableIE)
		{
			try
			{
				HarvestLevelHelper.setHarvestLevelWrench("IE_HAMMER", 2);
				API.integrationRegister.registerHandler(new IEHandler(), "ImmersiveEngineering");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("bluepower") && ConfigHandler.enableBluePower)
		{
			try
			{
				API.integrationRegister.registerHandler(new BluePowerHandler(), "Blue Power");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("quantumflux") && ConfigHandler.enableQuantumFlux)
		{
			try
			{
				CustomDismantleHelper.addBlock("tile.entropyAccelerator", false);
				CustomDismantleHelper.addBlock("tile.zeroPointExtractor", false);
				CustomDismantleHelper.addBlock("tile.rfEntangler", false);
				CustomDismantleHelper.addBlock("tile.rfExciter", false);
				CustomDismantleHelper.addBlock("tile.imaginaryTime", false);
				CustomDismantleHelper.addBlock("tile.itemFabricator", false);
				CustomDismantleHelper.addBlock("tile.quibitCluster", false);
				CustomDismantleHelper.addBlock("tile.storehouse", false);
				CustomDismantleHelper.addBlock("tile.creativeCluster", false);
				API.integrationRegister.registerStatic("QuantumFlux");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("StevesFactoryManager") && ConfigHandler.enableSFM)
		{
			try
			{
				CustomDismantleHelper.addBlock("StevesFactoryManager:BlockMachineManagerName", false);
				CustomDismantleHelper.addBlock("StevesFactoryManager:BlockCableName", false);
				CustomDismantleHelper.addBlock("StevesFactoryManager:BlockCableRelayName", false);
				CustomDismantleHelper.addBlock("StevesFactoryManager:BlockCableOutputName", false);
				CustomDismantleHelper.addBlock("StevesFactoryManager:BlockCableInputName", false);
				CustomDismantleHelper.addBlock("StevesFactoryManager:BlockCableCreativeName", false);
				CustomDismantleHelper.addBlock("StevesFactoryManager:BlockCableIntakeName", false);
				CustomDismantleHelper.addBlock("StevesFactoryManager:BlockCableBUDName", false);
				CustomDismantleHelper.addBlock("StevesFactoryManager:BlockCableBreakerName", false);
				CustomDismantleHelper.addBlock("StevesFactoryManager:BlockCableClusterName", false);
				CustomDismantleHelper.addBlock("StevesFactoryManager:BlockCableCamouflageName", false);
				CustomDismantleHelper.addBlock("StevesFactoryManager:BlockCableSignName", false);
				API.integrationRegister.registerStatic("Steve's Factory Manager");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("powersuits") && ConfigHandler.enablePowersuits)
		{
			try
			{
				CustomDismantleHelper.addBlock("tile.tinkerTable", false);
				API.integrationRegister.registerStatic("Machinemuse's Modular Power Suits");
			}
			catch (Exception e) {}
		}
	}
}