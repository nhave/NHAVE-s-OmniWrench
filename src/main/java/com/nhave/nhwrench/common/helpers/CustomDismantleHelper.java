package com.nhave.nhwrench.common.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameData;

/**
 * NHAVE's Omniwrench
 * 
 * @author nhave
 */
public class CustomDismantleHelper
{
	public static final TreeMap<String, Boolean> _Dismantle = new TreeMap<String, Boolean>();
	
	public static void addBlock(Block block, int meta, boolean flag)
	{
		String name = block.getUnlocalizedName()+";"+meta;
		_Dismantle.put(name, flag);
	}
	
	public static void addBlockNoMeta(String name, boolean flag)
	{
		_Dismantle.put(name, flag);
	}
	
	public static void addBlock(String name, boolean flag)
	{
		_Dismantle.put(name+";-1", flag);
	}
	
	public static void addBlock(String name, int meta, boolean flag)
	{
		_Dismantle.put(name+";"+meta, flag);
	}
	
	public static boolean canDismantle(Block block, int meta)
	{
		String name = block.getUnlocalizedName()+";"+meta;
		String nameAlt = block.getUnlocalizedName()+";-1";
		String name1 = GameData.getBlockRegistry().getNameForObject(block)+";"+meta;
		String nameAlt1 = GameData.getBlockRegistry().getNameForObject(block)+";-1";
		return _Dismantle.containsKey(name) || _Dismantle.containsKey(nameAlt) || _Dismantle.containsKey(name1) || _Dismantle.containsKey(nameAlt1);
	}
	
	public static boolean shouldGrabTile(Block block, int meta)
	{
		String name = block.getUnlocalizedName()+";"+meta;
		String nameAlt = block.getUnlocalizedName()+";-1";
		boolean result = false;
		if (_Dismantle.containsKey(nameAlt)) result = _Dismantle.get(nameAlt);
		else if (_Dismantle.containsKey(name)) result = _Dismantle.get(name);
		return result;
	}
	
	public static void dismantleBlock(EntityPlayer thePlayer, World world, int x, int y, int z, boolean grabMeta)
	{
		Block block = world.getBlock(x, y, z);
		int metadata = world.getBlockMetadata(x, y, z);
    	List drops = block.getDrops(world, x, y, z, metadata, 0);
    	block.onBlockHarvested(world, x, y, z, metadata, thePlayer);
	    world.setBlockToAir(x, y, z);
        
        if (!world.isRemote)
        {
        	ArrayList<? extends ItemStack> items = (ArrayList<? extends ItemStack>) drops;
        	for (ItemStack stack : items)
        	{
        		dropBlockAsItem(world, x, y, z, stack);
            }
        }
	}

	public static void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack)
	{
		float f = 0.3F;
    	double x2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
    	double y2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
    	double z2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
    	EntityItem theItem = new EntityItem(world, x + x2, y + y2, z + z2, stack);
    	theItem.delayBeforeCanPickup = 10;
    	world.spawnEntityInWorld(theItem);
	}
}