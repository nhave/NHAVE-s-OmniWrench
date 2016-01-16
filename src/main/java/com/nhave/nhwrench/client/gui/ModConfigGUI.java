package com.nhave.nhwrench.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.ConfigElement;

import com.nhave.nhwrench.common.handlers.ConfigHandler;

import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.IConfigElement;

public class ModConfigGUI extends GuiConfig
{
    public ModConfigGUI(GuiScreen parent)
    {
        super(parent, getConfigElements(), "nhwrench", false, false, StatCollector.translateToLocal("nh.cfg.title.main"));
    }
    
    private static List<IConfigElement> getConfigElements()
    {
		List list = new ArrayList();
		//list.add(new DummyConfigElement.DummyCategoryElement("nh.cfg.entry.client", "nh.cfg.entry.client", ClientEntry.class));
		list.add(new DummyConfigElement.DummyCategoryElement("nh.cfg.entry.common", "nh.cfg.entry.common", CommonEntry.class));
		list.add(new DummyConfigElement.DummyCategoryElement("nh.cfg.entry.modules", "nh.cfg.entry.modules", ModuleEntry.class).setRequiresMcRestart(true));
		return list;
    }
    
    public static class ClientEntry extends GuiConfigEntries.CategoryEntry
    {
		public ClientEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop)
		{
			super(owningScreen, owningEntryList, prop);
		}

		protected GuiScreen buildChildScreen()
		{
			return new GuiConfig(this.owningScreen,
					new ConfigElement(ConfigHandler.config.getCategory("client")).getChildElements(),
					this.owningScreen.modID, "client",
					(this.configElement.requiresWorldRestart())
							|| (this.owningScreen.allRequireWorldRestart),
					(this.configElement.requiresMcRestart())
							|| (this.owningScreen.allRequireMcRestart),
					StatCollector.translateToLocal("nh.cfg.title.client"));
		}
	}
    
    public static class CommonEntry extends GuiConfigEntries.CategoryEntry
    {
		public CommonEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop)
		{
			super(owningScreen, owningEntryList, prop);
		}

		protected GuiScreen buildChildScreen()
		{
			return new GuiConfig(this.owningScreen,
					new ConfigElement(ConfigHandler.config.getCategory("common")).getChildElements(),
					this.owningScreen.modID, "common",
					(this.configElement.requiresWorldRestart())
							|| (this.owningScreen.allRequireWorldRestart),
					(this.configElement.requiresMcRestart())
							|| (this.owningScreen.allRequireMcRestart),
					GuiConfig.getAbridgedConfigPath(StatCollector.translateToLocal("nh.cfg.title.common")));
		}
	}
    
    public static class ModuleEntry extends GuiConfigEntries.CategoryEntry
    {
		public ModuleEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop)
		{
			super(owningScreen, owningEntryList, prop);
		}

		protected GuiScreen buildChildScreen()
		{
			return new GuiConfig(this.owningScreen,
					new ConfigElement(ConfigHandler.config.getCategory("module")).getChildElements(),
					this.owningScreen.modID, "module",
					(this.configElement.requiresWorldRestart())
							|| (this.owningScreen.allRequireWorldRestart),
					(this.configElement.requiresMcRestart())
							|| (this.owningScreen.allRequireMcRestart),
					GuiConfig.getAbridgedConfigPath(StatCollector.translateToLocal("nh.cfg.title.modules")));
		}
	}
}