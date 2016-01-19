package com.nhave.nhwrench.common.modes;

import com.nhave.nhwrench.api.API;
import com.nhave.nhwrench.api.IWrenchMode;

import net.minecraft.item.ItemStack;

public class ModeMain implements IWrenchMode
{
	public String name;
	
	public ModeMain(String mode)
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
		return true;
	}
}