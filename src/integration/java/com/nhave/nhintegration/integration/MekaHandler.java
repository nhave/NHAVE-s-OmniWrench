package com.nhave.nhintegration.integration;

import com.nhave.nhwrench.api.IWrenchHandler;

import mekanism.api.IConfigurable;
import mekanism.common.block.BlockEnergyCube;
import mekanism.common.block.BlockMachine;
import mekanism.common.tile.TileEntityBasicBlock;
import mekanism.common.tile.TileEntityEnergyCube;
import mekanism.common.tile.TileEntityLogisticalSorter;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MekaHandler implements IWrenchHandler
{
	public static final int[] SIDE_OPPOSITE = {1, 0, 3, 2, 5, 4};
	
	//Mekanism
	//"[NHWRENCH]"//
	@Override
	public boolean handleWrench(String mode, ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
        Block block = world.getBlock(x, y, z);
        int bMeta = world.getBlockMetadata(x, y, z);
        TileEntity tile = world.getTileEntity(x, y, z);
        
        if (block instanceof BlockEnergyCube)
        {
        	if(world.isRemote)
    		{
    			return false;
    		}
        	
        	TileEntityEnergyCube tileEntity = (TileEntityEnergyCube)world.getTileEntity(x, y, z);
        	if(mode.equals("wrmode.wrench"))
			{
				if(player.isSneaking())
				{
					((BlockEnergyCube)block).dismantleBlock(world, x, y, z, false);
					return true;
				}

				int change = 0;
				
				TileEntityBasicBlock tileEntityBlock = (TileEntityBasicBlock)tileEntity;
				switch(tileEntityBlock.facing)
				{
					case 3:
						change = 5;
						break;
					case 5:
						change = 2;
						break;
					case 2:
						change = 4;
						break;
					case 4:
						change = 1;
						break;
					case 1:
						change = 0;
						break;
					case 0:
						change = 3;
						break;
				}

				tileEntity.setFacing((short)change);
				world.notifyBlocksOfNeighborChange(x, y, z, block);
				return true;
			}
        }
        else if (block instanceof BlockMachine)
        {
        	if(world.isRemote)
    		{
    			return false;
    		}
        	
        	TileEntityBasicBlock tileEntity = (TileEntityBasicBlock)world.getTileEntity(x, y, z);
        	if(mode.equals("wrmode.wrench"))
			{
				if(player.isSneaking() && bMeta != 13)
				{
					((BlockMachine)block).dismantleBlock(world, x, y, z, false);
					return true;
				}

				int change = 0;

				switch(tileEntity.facing)
				{
					case 3:
						change = 5;
						break;
					case 5:
						change = 2;
						break;
					case 2:
						change = 4;
						break;
					case 4:
						change = 3;
						break;
				}

				if(tileEntity instanceof TileEntityLogisticalSorter)
				{
					change = 0;
					
					switch(tileEntity.facing)
					{
						case 3:
							change = 5;
							break;
						case 5:
							change = 2;
							break;
						case 2:
							change = 4;
							break;
						case 4:
							change = 1;
							break;
						case 1:
							change = 0;
							break;
						case 0:
							change = 3;
							break;
					}
				}

				tileEntity.setFacing((short)change);
				world.notifyBlocksOfNeighborChange(x, y, z, block);
				return true;
			}
        }

    	if ((!world.isRemote) && (mode.equals("wrmode.wrench")))
		{
    		if (tile instanceof IConfigurable)
			{
				return true;
			}
		}
    	
		return false;
	}
}
