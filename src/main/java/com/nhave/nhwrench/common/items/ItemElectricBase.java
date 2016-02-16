package com.nhave.nhwrench.common.items;

import com.nhave.nhlib.helpers.NBTHelper;
import com.nhave.nhwrench.common.handlers.ConfigHandler;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemElectricBase extends Item implements IEnergyContainerItem
{
	protected int maxPower;
	protected int powerUsage;
	protected int chargeTick;
	
	public ItemElectricBase(int maxPower, int chargeTich, int usePower)
	{
		this.maxPower = maxPower;
		this.powerUsage = usePower;
		this.chargeTick = chargeTich;
	}
	
	public boolean hasPower(ItemStack stack)
	{
		return !ConfigHandler.usePower || getEnergyStored(stack) >= this.powerUsage;
	}
	
	public boolean hasPower(ItemStack stack, EntityPlayer player)
	{
		return !ConfigHandler.usePower || player.capabilities.isCreativeMode || getEnergyStored(stack) >= this.powerUsage;
	}
	
	public void drawPower(ItemStack stack, EntityPlayer player)
	{
		if (player.capabilities.isCreativeMode) return;
		if (!ConfigHandler.usePower) return;
		int power = (getEnergyStored(stack) - this.powerUsage);
		if (power < 0) power = 0;
		NBTHelper.setInteger(stack, "POWER", "STORED", power);
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return ConfigHandler.usePower;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return Math.max(1.0 - (double)getEnergyStored(stack) / (double)getMaxEnergyStored(stack), 0);
	}
	
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate)
	{
		if (!ConfigHandler.usePower) return 0;
		int power = this.maxPower - getEnergyStored(container);
		if (power > this.chargeTick) power = this.chargeTick;
		if (power > maxReceive) power = maxReceive;
		if (!simulate) NBTHelper.setInteger(container, "POWER", "STORED", getEnergyStored(container) + power);
		return power;
	}
	
	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate)
	{
		return 0;
	}
	
	@Override
	public int getEnergyStored(ItemStack container)
	{
		if (!ConfigHandler.usePower) return 0;
		int power = NBTHelper.getInteger(container, "POWER", "STORED", 0);
		if (power > this.maxPower) power = this.maxPower;
		return power;
	}
	
	@Override
	public int getMaxEnergyStored(ItemStack container)
	{
		if (!ConfigHandler.usePower) return 0;
		return this.maxPower;
	}
}