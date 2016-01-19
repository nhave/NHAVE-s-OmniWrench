package com.nhave.nhintegration.integration;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import pneumaticCraft.api.block.IPneumaticWrenchable;
import pneumaticCraft.common.semiblock.ISemiBlock;
import pneumaticCraft.common.semiblock.SemiBlockManager;

import com.nhave.nhwrench.api.IEntityInteraction;
import com.nhave.nhwrench.api.IWrenchHandler;

public class PneuHandler implements IWrenchHandler, IEntityInteraction
{
	@Override
	public boolean handleWrench(String mode, ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
        if (!world.isRemote)
        {
        	Block block = world.getBlock(x, y, z);
            if (block instanceof IPneumaticWrenchable && mode.equals("wrmode.wrench"))
            {
            	if (((IPneumaticWrenchable)block).rotateBlock(world, player, x, y, z, ForgeDirection.getOrientation(side)))
            	{
            		return true;
            	}
            }
            else if (mode.equals("wrmode.tune"))
            {
	            ISemiBlock semiBlock = SemiBlockManager.getInstance(world).getSemiBlock(world, x, y, z);
	            if(semiBlock != null)
	            {
	                if(player.isSneaking())
	                {
	                    SemiBlockManager.getInstance(world).breakSemiBlock(world, x, y, z);
	                    return true;
	                }
	                else
	                {
	                    if(semiBlock.onRightClickWithConfigurator(player))
	                    {
	                        return true;
	                    }
	                }
	            }
            }
            return false;
      	}
        return false;
	}

	@Override
	public boolean handleEntity(String mode, ItemStack itemStack, EntityPlayer player, EntityLivingBase entity)
	{
		if ((!player.worldObj.isRemote) && (entity.isEntityAlive()) && ((entity instanceof IPneumaticWrenchable)) && (mode.equals("wrmode.wrench")) && (((IPneumaticWrenchable)entity).rotateBlock(entity.worldObj, player, 0, 0, 0, ForgeDirection.UNKNOWN)))
		{
			return true;
	    }
		
		return false;
	}
}