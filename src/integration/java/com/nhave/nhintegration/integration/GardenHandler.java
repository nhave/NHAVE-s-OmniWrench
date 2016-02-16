package com.nhave.nhintegration.integration;

import com.jaquadro.minecraft.gardencore.GardenCore;
import com.jaquadro.minecraft.gardencore.api.IPlantProxy;
import com.jaquadro.minecraft.gardencore.api.event.UseTrowelEvent;
import com.jaquadro.minecraft.gardencore.block.BlockGarden;
import com.jaquadro.minecraft.gardencore.block.tile.TileEntityGarden;
import com.jaquadro.minecraft.gardencore.core.handlers.GuiHandler;
import com.nhave.nhwrench.api.IPostUse;
import com.nhave.nhwrench.api.IWrenchHandler;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class GardenHandler implements IWrenchHandler, IPostUse
{
	@Override
	public boolean handlePostUse(String mode, ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (!player.canPlayerEdit(x, y, z, side, itemStack) || !mode.equals("wrmode.tune")) return false;
		
        UseTrowelEvent event = new UseTrowelEvent(player, itemStack, world, x, y, z);
        if (MinecraftForge.EVENT_BUS.post(event)) return false;
        
        if (event.getResult() == Event.Result.ALLOW)
        {
            return true;
        }
        
        if (side == 0) return false;
        
        Block block = world.getBlock(x, y, z);
        
        if (block instanceof BlockGarden)
        {
            player.openGui(GardenCore.instance, GuiHandler.gardenLayoutGuiID, world, x, y, z);
            return true;
        }
        else if (block instanceof IPlantProxy)
        {
            IPlantProxy proxy = (IPlantProxy) block;
            TileEntityGarden te = proxy.getGardenEntity(world, x, y, z);
            
            if (te != null)
            {
                player.openGui(GardenCore.instance, GuiHandler.gardenLayoutGuiID, world, te.xCoord, te.yCoord, te.zCoord);
                return true;
            }
        }
		return false;
	}
	
	@Override
	public boolean handleWrench(String mode, ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		return false;
	}
}
