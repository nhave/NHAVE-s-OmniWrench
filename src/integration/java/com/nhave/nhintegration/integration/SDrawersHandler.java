package com.nhave.nhintegration.integration;

import com.jaquadro.minecraft.storagedrawers.api.storage.attribute.LockAttribute;
import com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityController;
import com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityDrawers;
import com.jaquadro.minecraft.storagedrawers.block.tile.TileEntitySlave;
import com.nhave.nhwrench.api.IWrenchHandler;
import com.nhave.nhwrench.common.helpers.CustomDismantleHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class SDrawersHandler implements IWrenchHandler
{
	@Override
	public boolean handleWrench(String mode, ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if (tile == null || (!mode.equals("wrmode.tune") && !mode.equals("wrmode.wrench"))) return false;
		
		if (mode.equals("wrmode.tune") && tile instanceof TileEntityDrawers)
		{
			TileEntityDrawers tileDrawers = ((TileEntityDrawers)tile);
			if (player.isSneaking())
			{
				tileDrawers.setIsShrouded(!tileDrawers.isShrouded());
				player.swingItem();
				return !world.isRemote;
			}
			else
			{
				boolean locked = tileDrawers.isLocked(LockAttribute.LOCK_POPULATED);
				tileDrawers.setLocked(LockAttribute.LOCK_POPULATED, !locked);
				tileDrawers.setLocked(LockAttribute.LOCK_EMPTY, !locked);
				player.swingItem();
				return !world.isRemote;
			}

		}
		else if (mode.equals("wrmode.wrench") && player.isSneaking() && (tile instanceof TileEntityDrawers || tile instanceof TileEntityController || tile instanceof TileEntitySlave))
		{
			CustomDismantleHelper.dismantleBlock(player, world, x, y, z, false);
			player.swingItem();
			return !world.isRemote;
		}
		
		return false;
	}
}
