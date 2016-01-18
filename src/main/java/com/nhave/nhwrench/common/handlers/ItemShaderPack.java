package com.nhave.nhwrench.common.handlers;

import java.util.List;
import java.util.Random;

import com.nhave.nhlib.util.StringUtils;
import com.nhave.nhwrench.common.misc.Colors;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemShaderPack extends Item
{
	private String[] itemNames = new String[] {"basic", "rare", "legendary"};
	private int[] itemColor = new int[] {Colors.white, Colors.lightBlue, Colors.purple};
	private String[] itemColorStrings = new String[] {StringUtils.WHITE, StringUtils.LIGHT_BLUE, StringUtils.PURPLE};
	
	public ItemShaderPack()
	{
		this.setHasSubtypes(true);
		this.setTextureName("nhwrench:ShaderPack");
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		int meta = stack.getItemDamage();
		if (meta < 0 || meta > itemNames.length) meta = 0;
		return StatCollector.translateToLocal("tooltip.shader.rarity."+itemNames[meta]) + " " + super.getItemStackDisplayName(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		int meta = stack.getItemDamage();
		if (meta < 0 || meta > itemNames.length) meta = 0;
		return itemColor[meta];
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			List<ItemStack> shaderList = getListFromMeta(stack.getItemDamage());
			if (!shaderList.isEmpty())
			{
				for (int i = 0; i < 11; ++i)
				{
					ItemStack shader = getRandomShader(shaderList);
					if (i < 10)
					{
						Random rand = new Random();
						if (player.inventory.hasItemStack(shader) && rand.nextFloat() > 0.4F) continue;
					}
					if (player.inventory.addItemStackToInventory(shader))
					{
						--stack.stackSize;
						break;
					}
				}
			}
		}
		return stack;
	}
	
	public List<ItemStack> getListFromMeta(int meta)
	{
		if (meta == 0) return ItemHandler.commonShaders;
		else if (meta == 1) return ItemHandler.rareShaders;
		else if (meta == 2) return ItemHandler.legendaryShaders;
		else return ItemHandler.commonShaders;
	}
	
	public ItemStack getRandomShader(List<ItemStack> list)
	{
		Random randomizer = new Random();
		ItemStack random = list.get(randomizer.nextInt(list.size())).copy();
		return random;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list)
	{
		for (int i = 0; i < itemNames.length; ++i)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		int meta = stack.getItemDamage();
		list.add(StringUtils.BOLD + super.getItemStackDisplayName(stack) + "     ");
		list.add(itemColorStrings[meta] + StatCollector.translateToLocal("tooltip.shader.rarity."+itemNames[meta]));
	}
}