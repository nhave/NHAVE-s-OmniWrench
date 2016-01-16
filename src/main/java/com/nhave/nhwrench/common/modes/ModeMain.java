package com.nhave.nhwrench.common.modes;

import net.minecraft.item.ItemStack;

import com.nhave.nhwrench.api.IWrenchMode;
import com.nhave.nhwrench.api.WrenchModeRegistry;

public class ModeMain implements IWrenchMode
{
	public String name;
	
	public ModeMain(String mode)
	{
		this.name = mode;
		WrenchModeRegistry.registerMode(this);
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