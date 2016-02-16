package com.nhave.nhwrench.common.modes;

import java.util.List;

import com.nhave.nhwrench.api.API;
import com.nhave.nhwrench.api.IWrenchMode;

import net.minecraft.entity.player.EntityPlayer;
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

	@Override
	public void onSubModeChange(EntityPlayer entityPlayer, ItemStack itemStack, boolean chat) {}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {}
}