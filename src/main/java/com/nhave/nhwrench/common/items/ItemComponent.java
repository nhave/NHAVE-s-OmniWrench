package com.nhave.nhwrench.common.items;

import java.util.List;

import com.nhave.nhlib.util.StringUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemComponent extends Item
{
	private IIcon itemIcon[];
	private String[] itemNames = new String[] {"Claw", "Piston", "Handle"};
	
	public ItemComponent()
	{
		this.setHasSubtypes(true);
	}
	
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = new IIcon[itemNames.length];
		for (int i = 0; i < itemNames.length; ++i)
		{
			this.itemIcon[i] = par1IconRegister.registerIcon("nhwrench:"+itemNames[i]);
		}
	}
	
	@Override
	public IIcon getIconFromDamage(int meta)
	{
		if (meta < 0 || meta > itemNames.length) meta = 0;
		return this.itemIcon[meta];
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		int meta = stack.getItemDamage();
		if (meta < 0 || meta > itemNames.length) meta = 0;
		return StatCollector.translateToLocal("item.nhwrench.itemComp." + itemNames[meta] + ".name");
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
		list.add(StringUtils.BOLD + StatCollector.translateToLocal("tooltip.item.craft") + "     ");
		list.add(StringUtils.LIGHT_BLUE + StatCollector.translateToLocal("tooltip.shader.rarity.rare"));
	}
}