package com.nhave.nhwrench.common.items;

import java.util.List;

import com.nhave.nhlib.api.item.IItemShader;
import com.nhave.nhlib.util.StringUtils;
import com.nhave.nhwrench.common.handlers.ItemHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

public class ItemShader extends Item implements IItemShader
{
	private String textureName = "nhwrench:Blank";
	private String textureOverlayName = "nhwrench:Blank";
	private IIcon itemIcon[];
	private Boolean supportsColor = false;
	private String shaderName;
	private String[] rarityNames = new String[] {"basic", "rare", "legendary", "exotic"};
	private String[] rarityColors = new String[] {StringUtils.WHITE, StringUtils.LIGHT_BLUE, StringUtils.PURPLE, StringUtils.ORANGE};
	private int rarity = 0;
	private int shaderColor = 16777215;
	
	public ItemShader(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName("nhwrench.itemShader."+name);
		this.shaderName = name;
	}
	
	public ItemShader setSupportsColor()
	{
		this.supportsColor = true;
		return this;
	}
	
	public ItemShader setShaderColor(int color)
	{
		this.shaderColor = color;
		return this;
	}
	
	public ItemShader setTextureName(String name)
	{
		this.textureName = name+"_0";
		this.textureOverlayName = name+"_1";
		return this;
	}
	
	public ItemShader setBaseName(String name)
	{
		this.textureName = name;
		return this;
	}
	
	public ItemShader setOverlayName(String name)
	{
		this.textureOverlayName = name;
		return this;
	}
	
	public ItemShader setRarity(int rarity)
	{
		int result = rarity;
		if (result > 3) result = 3;
		else if (result < 0) result = 0;
		this.rarity = result;
		return this;
	}
	
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = new IIcon[4];
		
		this.itemIcon[0] = par1IconRegister.registerIcon("nhwrench:Shader_0");
		this.itemIcon[1] = par1IconRegister.registerIcon("nhwrench:Shader_1");
		this.itemIcon[2] = par1IconRegister.registerIcon(textureName);
		this.itemIcon[3] = par1IconRegister.registerIcon(textureOverlayName);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
	
	@Override
	public int getRenderPasses(int metadata)
	{
		return 2;
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return this.itemIcon[pass];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		if (pass == 1) return this.shaderColor;
		else return 16777215;
	}
	
	@Override
	public boolean canApplyTo(ItemStack shader, ItemStack shadeable)
	{
		return shadeable != null && shadeable.getItem() instanceof ItemOmniWrench;
	}

	@Override
	public Object getShaderData(ItemStack shader, ItemStack Owner, String tag)
	{
		if (tag.equals("SUPPORTS_COLOR")) return supportsColor;
		else if (tag.equals("ICON_BASE")) return this.itemIcon[2];
		else if (tag.equals("ICON_OVERLAY")) return this.itemIcon[3];
		else return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		list.add(StringUtils.BOLD + StatCollector.translateToLocal("tooltip.item.shader") + "     ");
		list.add(rarityColors[rarity] + StatCollector.translateToLocal("tooltip.shader.rarity."+rarityNames[rarity]));
		list.add(StatCollector.translateToLocal("tooltip.shader.appliesto") + ":");
		list.add("  " + "§e" + "§o" + ItemHandler.itemWrench.getItemStackDisplayName(new ItemStack(ItemHandler.itemWrench)));
	}
}