package com.nhave.nhwrench.client.creativetabs;

import java.util.ArrayList;
import java.util.List;

import com.nhave.nhwrench.common.handlers.ItemHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabShaders extends CreativeTabs
{
	private List<ItemStack> additionalItems = new ArrayList<ItemStack>();
	
	public CreativeTabShaders(String name)
	{
		super(name);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void displayAllReleventItems(List par1List)
    {
		for (ItemStack stack : additionalItems)
		{
			par1List.add(stack);
		}
    	super.displayAllReleventItems(par1List);
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return ItemHandler._TabIconShaders;
	}
	
	public void addStackToTab(ItemStack stack)
	{
		additionalItems.add(stack);
	}
}