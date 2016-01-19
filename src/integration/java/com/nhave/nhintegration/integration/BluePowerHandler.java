package com.nhave.nhintegration.integration;

import java.util.Random;

import com.bluepowermod.api.block.IAdvancedSilkyRemovable;
import com.bluepowermod.api.block.ISilkyRemovable;
import com.nhave.nhwrench.api.IWrenchHandler;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import uk.co.qmunity.lib.part.IPart;
import uk.co.qmunity.lib.part.ITilePartHolder;
import uk.co.qmunity.lib.part.compat.MultipartCompatibility;
import uk.co.qmunity.lib.raytrace.QMovingObjectPosition;
import uk.co.qmunity.lib.raytrace.RayTracer;

public class BluePowerHandler implements IWrenchHandler
{
	//Blue Power
	@Override
	public boolean handleWrench(String mode, ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (!player.isSneaking()) return false;
		
        Block block = world.getBlock(x, y, z);
        TileEntity tile = world.getTileEntity(x, y, z);
        
        ITilePartHolder partHolder = MultipartCompatibility.getPartHolder(world, x, y, z);
        if (partHolder != null)
        {
            QMovingObjectPosition mop = partHolder.rayTrace(RayTracer.instance().getStartVector(player), RayTracer.instance().getEndVector(player));
            if (mop != null)
            {
                IPart part = mop.getPart();
                if (part instanceof ISilkyRemovable)
                {
                	if (!world.isRemote)
                	{
	                    if (part instanceof IAdvancedSilkyRemovable && !((IAdvancedSilkyRemovable) part).preSilkyRemoval(world, x, y, z)) return false;
	                    NBTTagCompound tag = new NBTTagCompound();
	                    boolean hideTooltip = false;
	                    if (part instanceof IAdvancedSilkyRemovable)
	                    {
	                        hideTooltip = ((IAdvancedSilkyRemovable) part).writeSilkyData(world, x, y, z, tag);
	                    }
	                    else
	                    {
	                        part.writeToNBT(tag);
	                    }
	                    ItemStack droppedStack = part.getItem();
	                    NBTTagCompound stackTag = droppedStack.getTagCompound();
	                    if (stackTag == null)
	                    {
	                        stackTag = new NBTTagCompound();
	                        droppedStack.setTagCompound(stackTag);
	                    }
	                    stackTag.setTag("tileData", tag);
	                    stackTag.setBoolean("hideSilkyTooltip", hideTooltip);
	                    world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, droppedStack));
	                    partHolder.removePart(part);
	                    if (part instanceof IAdvancedSilkyRemovable) ((IAdvancedSilkyRemovable) part).postSilkyRemoval(world, x, y, z);
	                    itemStack.damageItem(1, player);
	                    return true;
	                }
                	else player.swingItem();
                }
            }
            return false;
        }
        
        if (block instanceof ISilkyRemovable)
        {
        	if (!world.isRemote)
        	{
	            if (block instanceof IAdvancedSilkyRemovable && !((IAdvancedSilkyRemovable) block).preSilkyRemoval(world, x, y, z)) return false;
	            if (tile == null)
	                throw new IllegalStateException("Block doesn't have a TileEntity?! Implementers of ISilkyRemovable should have one. Offender: " + block.getUnlocalizedName());
	            NBTTagCompound tag = new NBTTagCompound();
	            tile.writeToNBT(tag);
	            int metadata = world.getBlockMetadata(x, y, z);
	            Item item = block.getItemDropped(metadata, new Random(), 0);
	            if (item == null) throw new NullPointerException("Block returns null for getItemDropped(meta, rand, fortune)! Offender: " + block.getUnlocalizedName());
	            ItemStack droppedStack = new ItemStack(item, 1, block.damageDropped(metadata));
	            NBTTagCompound stackTag = droppedStack.getTagCompound();
	            if (stackTag == null)
	            {
	                stackTag = new NBTTagCompound();
	                droppedStack.setTagCompound(stackTag);
	            }
	            stackTag.setTag("tileData", tag);
	            world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, droppedStack));
	            world.setBlockToAir(x, y, z);
	            if (block instanceof IAdvancedSilkyRemovable) ((IAdvancedSilkyRemovable) block).postSilkyRemoval(world, x, y, z);
	            itemStack.damageItem(1, player);
	            return true;
        	}
        	else player.swingItem();
        }
        return false;
	}
}