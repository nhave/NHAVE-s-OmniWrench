package com.carpentersblocks.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface ICarpentersChisel
{
	public abstract void onChiselUse(World world, EntityPlayer player);
	
	public abstract boolean canUseChisel(World world, EntityPlayer player);
}