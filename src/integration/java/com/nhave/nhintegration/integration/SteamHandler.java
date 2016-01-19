package com.nhave.nhintegration.integration;

import com.nhave.nhwrench.api.IWrenchHandler;
import com.nhave.nhwrench.common.handlers.ItemHandler;
import com.nhave.nhwrench.common.helpers.CustomDismantleHelper;
import com.nhave.nhwrench.common.items.ItemOmniWrench;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.steamcraft.api.IWrenchDisplay;
import flaxbeard.steamcraft.api.IWrenchable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class SteamHandler implements IWrenchHandler
{
	@Override
	public boolean handleWrench(String mode, ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			if (mode.equals("wrmode.wrench") || (mode.equals("wrmode.tune") && player.isSneaking()))
			{
				if (world.getBlock(x, y, z) != null && world.getBlock(x, y, z) instanceof IWrenchable)
				{
					boolean result = ((IWrenchable)world.getBlock(x, y, z)).onWrench(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
					if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof IWrenchable)
					{
						((IWrenchable)world.getTileEntity(x, y, z)).onWrench(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
					}
					if (result == true) world.playSoundEffect(x+0.5F, y+0.5F, z+0.5F, "steamcraft:wrench", 2.0F, 0.9F);
					if (player.isSneaking() && mode.equals("wrmode.wrench"))
					{
						CustomDismantleHelper.dismantleBlock(player, world, x, y, z, false);
						return true;
					}
					return result;
				}
				else if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof IWrenchable)
				{
					boolean result = ((IWrenchable)world.getTileEntity(x, y, z)).onWrench(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
					if (result == true) world.playSoundEffect(x+0.5F, y+0.5F, z+0.5F, "steamcraft:wrench", 2.0F, 0.9F);
					if (player.isSneaking() && mode.equals("wrmode.wrench"))
					{
						CustomDismantleHelper.dismantleBlock(player, world, x, y, z, false);
						return true;
					}
					return result;
				}
			}
		}
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onDrawScreen(RenderGameOverlayEvent.Post event)
	{
		if(event.type == ElementType.ALL)
		{
			Minecraft mc = Minecraft.getMinecraft();
			EntityPlayer player = mc.thePlayer;
			MovingObjectPosition pos = mc.objectMouseOver;
			if(pos != null && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemOmniWrench)
			{
				TileEntity te = mc.theWorld.getTileEntity(pos.blockX, pos.blockY, pos.blockZ);
				ItemOmniWrench wrench = (ItemOmniWrench)mc.thePlayer.getCurrentEquippedItem().getItem();
				if (te instanceof IWrenchDisplay && wrench.getWrenchMode(mc.thePlayer.getCurrentEquippedItem()) == ItemHandler.modeTune)
				{
					((IWrenchDisplay)te).displayWrench(event);
				}
			}
		}
	}
}