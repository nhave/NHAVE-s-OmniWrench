package com.nhave.nhwrench.common.items;

import com.nhave.nhwrench.common.handlers.ItemHandler;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemShaderMultiTexture extends ItemShader
{
	public ItemShaderMultiTexture(String name)
	{
		super(name);
	}

	private String textureName = "nhwrench:Blank";
	private IIcon itemIcon[];
	
	public ItemShader setTextureName(String name)
	{
		this.textureName = name;
		return this;
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return this.itemIcon[pass];
	}
	
	@Override
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = new IIcon[8];
		
		this.itemIcon[0] = par1IconRegister.registerIcon("nhwrench:Shader_0");
		this.itemIcon[1] = par1IconRegister.registerIcon("nhwrench:Shader_1");
		this.itemIcon[2] = par1IconRegister.registerIcon(textureName+"_0");
		this.itemIcon[3] = par1IconRegister.registerIcon(textureName+"_1");
		this.itemIcon[4] = par1IconRegister.registerIcon(textureName+"_2");
		this.itemIcon[5] = par1IconRegister.registerIcon(textureName+"_3");
		this.itemIcon[6] = par1IconRegister.registerIcon(textureName+"_4");
		this.itemIcon[7] = par1IconRegister.registerIcon("nhwrench:Blank");
	}
	
	@Override
	public Object getShaderData(ItemStack shader, ItemStack Owner, String tag)
	{
		if (tag.equals("SUPPORTS_COLOR")) return false;
		else if (tag.equals("ICON_BASE")) return getIconForMode(Owner);
		else if (tag.equals("ICON_OVERLAY")) return this.itemIcon[7];
		else return null;
	}
	
	public IIcon getIconForMode(ItemStack stack)
	{
		if (stack != null && stack.getItem() instanceof ItemOmniWrench)
		{
			ItemOmniWrench wrench = (ItemOmniWrench)stack.getItem();
			if (wrench.getWrenchMode(stack) == ItemHandler.modeTune) return this.itemIcon[3];
			else if (wrench.getWrenchMode(stack) == ItemHandler.modeRotate) return this.itemIcon[4];
			else if (wrench.getWrenchMode(stack) == ItemHandler.modeUtil) return this.itemIcon[5];
			else if (wrench.getWrenchMode(stack) == ItemHandler.modeNone) return this.itemIcon[6];
			else return this.itemIcon[2];
		}
		return this.itemIcon[2];
	}
}