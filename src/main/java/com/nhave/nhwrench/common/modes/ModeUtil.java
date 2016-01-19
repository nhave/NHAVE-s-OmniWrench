package com.nhave.nhwrench.common.modes;

import com.nhave.nhwrench.api.API;
import com.nhave.nhwrench.api.IWrenchMode;
import com.nhave.nhwrench.common.handlers.ConfigHandler;

import net.minecraft.item.ItemStack;

public class ModeUtil implements IWrenchMode
{
	public String name;
	
	public ModeUtil(String mode)
	{
		this.name = mode;
		API.modeRegister.registerMode(this);
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public boolean isActive(ItemStack itemStack)
	{
		return ConfigHandler.enableUtilMode;
	}
}