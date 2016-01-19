package com.nhave.nhintegration.integration;

import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.tile.IWrenchable;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.nhave.nhwrench.api.IWrenchHandler;

/**
 * NHAVE's Omniwrench
 * 
 * @author nhave
 */
public class IC2Handler implements IWrenchHandler
{
    public static final int[] SIDE_OPPOSITE = {1, 0, 3, 2, 5, 4};
	
	@Override
	public boolean handleWrench(String mode, ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		
        Block block = world.getBlock(x, y, z);
        int bMeta = world.getBlockMetadata(x, y, z);
		
		/** === Industrial Craft 2 === **/
		TileEntity tile = world.getTileEntity(x, y, z);
		if(!mode.equals("wrmode.wrench") || tile == null)
		{
			return false;
		}
		if (tile != null && tile.toString().contains("mekanism"))
		{
			return false;
		}
        if ((tile instanceof IWrenchable))
        {
            IWrenchable wrenchTile = (IWrenchable) tile;

            if (player.isSneaking())
            {
                side = this.SIDE_OPPOSITE[side];
            }
            
            if ((!rotateBlock(world, side, wrenchTile)) && (wrenchTile.wrenchCanRemove(player)))
            {
                ItemStack dropBlock = wrenchTile.getWrenchDrop(player);
                
                if (dropBlock != null)
                {
                    world.setBlock(x, y, z, Blocks.air);
                    if (!world.isRemote)
                    {
                    	List drops = block.getDrops(world, x, y, z, bMeta, 0);
                    	if (dropBlock != null)
                    	{
                            if (drops.isEmpty())
                            {
                            	drops.add(dropBlock);
                            }
                            else
                            {
                              drops.set(0, dropBlock);
                            }
                    	}
                    	ArrayList<? extends ItemStack> items = (ArrayList<? extends ItemStack>) drops;
                    	for (ItemStack stack : items)
                    	{
                    		dropAsEntity(world, x, y, z, stack);
                        }
                    }
                }
            	player.swingItem();
                return !world.isRemote;
            }
            player.swingItem();
            return !world.isRemote;
        }
		return false;
	}
	
	public static boolean rotateBlock( World world, int side, IWrenchable wrenchTile)
	{
		boolean rotated = true;
		if (!world.isRemote)
		{
			short facing = wrenchTile.getFacing();
	        if ((side == 0) || (side == 1))
	        {
	            if (((wrenchTile instanceof IEnergySource)) && ((wrenchTile instanceof IEnergySink)))
	            {
	                wrenchTile.setFacing((short) side);
	            }
	        }
	        else
	        {
	            wrenchTile.setFacing((short) side);
	        }
	        if (wrenchTile.getFacing() == facing) rotated = false;
		}
        return rotated;
	}
	
	public static void dropAsEntity(World world, int x, int y, int z, ItemStack itemStack)
	{
	    if (itemStack == null) return;

	    double f = 0.7D;
	    double dx = world.rand.nextFloat() * f + (1.0D - f) * 0.5D;
	    double dy = world.rand.nextFloat() * f + (1.0D - f) * 0.5D;
	    double dz = world.rand.nextFloat() * f + (1.0D - f) * 0.5D;

	    EntityItem entityItem = new EntityItem(world, x + dx, y + dy, z + dz, itemStack.copy());
	    entityItem.delayBeforeCanPickup = 10;
	    world.spawnEntityInWorld(entityItem);
	  }
}