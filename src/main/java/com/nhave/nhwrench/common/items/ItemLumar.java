package com.nhave.nhwrench.common.items;

import java.util.List;

import com.nhave.nhlib.util.StringUtils;
import com.nhave.nhwrench.common.misc.Colors;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

public class ItemLumar extends Item
{
	public static String[] oreDict = new String[] {"lumarBlack", "lumarRed",  "lumarGreen", "lumarBrown", "lumarBlue", "lumarPurple", "lumarCyan", "lumarLightGray", "lumarGray", "lumarPink", "lumarLime", "lumarYellow", "lumarLightBlue", "lumarMagenta", "lumarOrange", "lumarWhite"};
	
	public ItemLumar()
	{
		this.setHasSubtypes(true);
		this.setTextureName("nhwrench:Lumar");
		for (int i = 0; i < oreDict.length; ++i)
		{
			OreDictionary.registerOre(oreDict[i], new ItemStack(this, 1, i));
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		int meta = stack.getItemDamage();
		if (meta < 0 || meta > Colors.colorNames.length) meta = 0;
		return StatCollector.translateToLocal("nh.color."+Colors.colorNames[meta]) + " " + super.getItemStackDisplayName(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		int meta = stack.getItemDamage();
		if (meta < 0 || meta > 15) meta = 0;
		return Colors.colorCodes[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list)
	{
		for (int i = 0; i < Colors.colorCodes.length; ++i)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		list.add(StringUtils.BOLD + StatCollector.translateToLocal("tooltip.item.craft") + "     ");
		list.add(StringUtils.WHITE + StatCollector.translateToLocal("tooltip.shader.rarity.basic"));
	}
}