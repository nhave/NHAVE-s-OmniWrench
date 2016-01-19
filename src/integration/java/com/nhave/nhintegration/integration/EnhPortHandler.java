package com.nhave.nhintegration.integration;

import com.nhave.nhwrench.api.IWrenchHandler;

import enhancedportals.network.GuiHandler;
import enhancedportals.tile.TileController;
import enhancedportals.tile.TileDialingDevice;
import enhancedportals.tile.TileFrame;
import enhancedportals.utility.IDismantleable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class EnhPortHandler implements IWrenchHandler
{
	@Override
	public boolean handleWrench(String mode, ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		Block block = world.getBlock(x, y, z);
        int bMeta = world.getBlockMetadata(x, y, z);
        
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileFrame)
        {
        	if (mode.equals("wrmode.tune"))
			{
            	if (tile instanceof TileDialingDevice) return false;
	    		TileFrame frame = (TileFrame)tile;
	    		TileController controller = frame.getPortalController();
	    		if (controller != null)
				{
	    			GuiHandler.openGui(player, controller, 8);
	    			return !world.isRemote;
				}
			}
	        else if (mode.equals("wrmode.wrench"))
			{
	        	if (player.isSneaking())
	            {
	        		if ((block instanceof IDismantleable))
	        		{
	        			if (!world.isRemote)
	        			{
	        				((IDismantleable)block).dismantleBlock(player, world, x, y, z);
	        				return true;
	        			}
	        			else player.swingItem();
	        		}
	            }
			}
        	if (tile instanceof TileDialingDevice) return false;
        }
		return false;
	}
}