package com.nhave.nhwrench.common.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.nhave.nhlib.api.item.IShadeAble;
import com.nhave.nhlib.helpers.NBTHelper;
import com.nhave.nhlib.main.KeyBinds;
import com.nhave.nhlib.shaders.ShaderManager;
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

public class ItemOmniWrench extends ItemWrenchBase implements IShadeAble
{
	private IIcon itemIcon[];
	
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = new IIcon[2];
		
		this.itemIcon[0] = par1IconRegister.registerIcon("nhwrench:Shaders/Basic_0");
		this.itemIcon[1] = par1IconRegister.registerIcon("nhwrench:Shaders/Basic_1");
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return super.getItemStackDisplayName(stack);
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
		if (pass == 0) return ShaderManager.getIIconTag(stack, "ICON_BASE", this.itemIcon[0]);
		else if (pass == 1) return ShaderManager.getIIconTag(stack, "ICON_OVERLAY", this.itemIcon[1]);
		else return this.itemIcon[pass];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		if (pass == 1 && ShaderManager.getBooleanTag(stack, "SUPPORTS_COLOR", true)) return NBTHelper.getInteger(stack, "WRENCH", "COLOR", 16777215);
		else return 16777215;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list)
	{
		for (int i = 0; i < colorCodes.length; ++i)
		{
			ItemStack stack = new ItemStack(item);
			list.add(NBTHelper.setStackInteger(stack, "WRENCH", "COLOR", colorCodes[i]));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		list.add(StringUtils.BOLD + StatCollector.translateToLocal("tooltip.item.omnitool"));
		list.add(StringUtils.PURPLE + StatCollector.translateToLocal("tooltip.shader.rarity.legendary"));
		
		String shaderName = StatCollector.translateToLocal("tooltip.shader.none");
		if (ShaderManager.hasShader(stack)) shaderName = ShaderManager.getShader(stack).getDisplayName();
		list.add(StatCollector.translateToLocal("tooltip.shader.current") + ": " + "§e" + "§o" + shaderName);
		
		list.add("§7" + StringUtils.localize("tooltip.wrmode.press") + " " + "§e" + "§o" + Keyboard.getKeyName(KeyBinds.toggle.getKeyCode()) + " " + "§r" + "§7" + StringUtils.localize("tooltip.wrmode.change") + "§r");
		list.add(StatCollector.translateToLocal("tooltip.wrmode.mode") + ": " + "§e" + "§o" + this.getWrenchModeAsString(stack));
	}
}