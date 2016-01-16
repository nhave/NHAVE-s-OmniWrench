package com.nhave.nhwrench.common.handlers.wrench;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import com.nhave.nhwrench.api.IEntityInteraction;
import com.nhave.nhwrench.api.IWrenchHandler;
import com.nhave.nhwrench.common.handlers.ConfigHandler;

public class UtilsHandler implements IWrenchHandler, IEntityInteraction
{
	@Override
	public boolean handleWrench(String mode, ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (!ConfigHandler.enableUtilMode || !mode.equals("wrmode.util")) return false;
        
		if (ConfigHandler.enableHoe)
		{
			Block block = world.getBlock(x, y, z);
	
			if ((side != 0) && (world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z)) && (((block == Blocks.grass) || (block == Blocks.dirt))))
			{
				Block block1 = Blocks.farmland;
				world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getPitch() * 0.8F);
							
				world.setBlock(x, y, z, block1);
				player.swingItem();
				return !world.isRemote;
			}
			else if (side != 0 && block == Blocks.farmland)
			{
				Block block1 = Blocks.dirt;
				world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getPitch() * 0.8F);
							
				world.setBlock(x, y, z, block1);
				player.swingItem();
				return !world.isRemote;
			}
			return false;
		}
		return false;
	}
	
	@Override
	public boolean handleEntity(String mode, ItemStack itemStack, EntityPlayer player, EntityLivingBase entity)
	{
		if (!ConfigHandler.enableUtilMode || !mode.equals("wrmode.util")) return false;
		
		if (ConfigHandler.enableShears)
		{
			if (entity.worldObj.isRemote) {
				return false;
			}
			if (entity instanceof IShearable)
			{
				IShearable target = (IShearable) entity;
				if (target.isShearable(itemStack, entity.worldObj, (int) entity.posX, (int) entity.posY, (int) entity.posZ))
				{
					ArrayList drops = target.onSheared(itemStack, entity.worldObj, (int) entity.posX, (int) entity.posY, (int) entity.posZ, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemStack));
					
					Random rand = new Random();
					ArrayList<? extends ItemStack> items = (ArrayList<? extends ItemStack>) drops;
					for (ItemStack stack : items)
					{
						EntityItem ent = entity.entityDropItem(stack, 1.0F);
						ent.motionY += rand.nextFloat() * 0.05F;
						ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
						ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
					}
				}
				return true;
			}
			return false;
		}
		return false;
	}
}