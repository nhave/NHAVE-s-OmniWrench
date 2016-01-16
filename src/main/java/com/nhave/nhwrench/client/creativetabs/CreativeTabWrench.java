package com.nhave.nhwrench.client.creativetabs;

import java.util.List;

import com.nhave.nhwrench.common.handlers.ItemHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabWrench extends CreativeTabs
{
	public CreativeTabWrench(String arg0)
	{
		super(arg0);
		this.setBackgroundImageName("item_search.png");
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void displayAllReleventItems(List par1List)
    {
    	super.displayAllReleventItems(par1List);
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return ItemHandler.itemWrench;
	}
	
	@Override
	public boolean hasSearchBar()
	{
		return true;
	}
}