package com.nhave.nhintegration.integration;

import com.nhave.nhwrench.api.IWrenchHandler;

import cofh.api.block.IDismantleable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CoFHHandler implements IWrenchHandler
{
	//CoFHCore
	@Override
	public boolean handleWrench(String mode, ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
        Block block = world.getBlock(x, y, z);
        int bMeta = world.getBlockMetadata(x, y, z);
        
		if (mode.equals("wrmode.wrench") && (!world.isRemote) && (player.isSneaking()) && ((block instanceof IDismantleable)) && (((IDismantleable)block).canDismantle(player, world, x, y, z)))
	    {
	      ((IDismantleable)block).dismantleBlock(player, world, x, y, z, false);
	      return true;
	    }
		return false;
	}
}