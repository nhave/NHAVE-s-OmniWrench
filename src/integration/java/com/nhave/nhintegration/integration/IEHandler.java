package com.nhave.nhintegration.integration;

import com.nhave.nhwrench.api.IWrenchHandler;

import blusunrize.immersiveengineering.api.MultiblockHandler;
import blusunrize.immersiveengineering.api.MultiblockHandler.IMultiblock;
import blusunrize.immersiveengineering.api.TargetingInfo;
import blusunrize.immersiveengineering.api.energy.IImmersiveConnectable;
import blusunrize.immersiveengineering.api.energy.ImmersiveNetHandler;
import blusunrize.immersiveengineering.common.IESaveData;
import blusunrize.immersiveengineering.common.util.Lib;
import blusunrize.immersiveengineering.common.util.Utils;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class IEHandler implements IWrenchHandler
{
	//CoFHCore
	@Override
	public boolean handleWrench(String mode, ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (!mode.equals("wrmode.wrench") && !mode.equals("wrmode.tune") && !mode.equals("wrmode.util")) return false;
		
		if(mode.equals("wrmode.wrench"))
		{
			for(IMultiblock mb : MultiblockHandler.getMultiblocks())
			{
				if(mb.isBlockTrigger(world.getBlock(x, y, z), world.getBlockMetadata(x, y, z)) && mb.createStructure(world, x, y, z, side, player))
				{
					player.swingItem();
					return !world.isRemote;
				}
			}
		}
		else if(mode.equals("wrmode.tune") && world.getTileEntity(x, y, z) instanceof IImmersiveConnectable)
		{
			player.swingItem();
			if(!world.isRemote)
			{
				IImmersiveConnectable nodeHere = (IImmersiveConnectable)world.getTileEntity(x, y, z);
				ImmersiveNetHandler.INSTANCE.clearAllConnectionsFor(Utils.toCC(nodeHere),world, new TargetingInfo(side,hitX,hitY,hitZ));
				IESaveData.setDirty(world.provider.dimensionId);
				return true;
			}
		}
		if(!world.isRemote)
		{
			if(mode.equals("wrmode.util"))
			{
				if(!player.isSneaking() && (world.getTileEntity(x, y, z) instanceof IEnergyReceiver || world.getTileEntity(x, y, z) instanceof IEnergyProvider))
				{
					int max = 0;
					int stored = 0;
					if(world.getTileEntity(x, y, z) instanceof IEnergyReceiver)
					{
						max = ((IEnergyReceiver)world.getTileEntity(x, y, z)).getMaxEnergyStored(ForgeDirection.getOrientation(side));
						stored = ((IEnergyReceiver)world.getTileEntity(x, y, z)).getEnergyStored(ForgeDirection.getOrientation(side));
					}
					else if (world.getTileEntity(x, y, z) instanceof IEnergyProvider)
					{
						max = ((IEnergyProvider)world.getTileEntity(x, y, z)).getMaxEnergyStored(ForgeDirection.getOrientation(side));
						stored = ((IEnergyProvider)world.getTileEntity(x, y, z)).getEnergyStored(ForgeDirection.getOrientation(side));
					}
					if(max>0) player.addChatMessage(new ChatComponentTranslation(Lib.CHAT_INFO+"energyStorage", stored,max));
					
					return true;
				}
			}
		}
		return false;
	}
}