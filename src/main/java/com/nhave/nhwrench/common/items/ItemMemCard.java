package com.nhave.nhwrench.common.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.nhave.nhlib.api.item.IHudItem;
import com.nhave.nhlib.helpers.NBTHelper;
import com.nhave.nhlib.helpers.TooltipHelper;
import com.nhave.nhlib.interfaces.IKeyBound;
import com.nhave.nhlib.main.KeyBinds;
import com.nhave.nhlib.util.StringUtils;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemMemCard extends Item implements IKeyBound, IHudItem
{
	public static String[] allowedBlocks = new String[]
			{"StevesFactoryManager:BlockMachineManagerName"};
	public boolean advanced;
	
	public ItemMemCard(boolean advanced)
	{
		this.advanced = advanced;
		this.setMaxStackSize(1);
	}
	
	@Override
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player)
	{
		return true;
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int arg6, float arg7, float arg8, float arg9)
	{
		if (stack.stackTagCompound == null)  stack.stackTagCompound = new NBTTagCompound();
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity tile = world.getTileEntity(x, y, z);
		String blockName = GameData.getBlockRegistry().getNameForObject(block);
		
		boolean isBlockAllowed = false;
		for (int i = 0; i < allowedBlocks.length; ++i)
		{
			if (blockName.equals(allowedBlocks[i]))
			{
				isBlockAllowed = true;
				break;
			}
		}
		if (!isBlockAllowed) return false;
		if (!world.isRemote && tile != null)
		{
			if (!player.isSneaking() && !isLocked(stack))
			{
		        NBTTagCompound tileData = new NBTTagCompound();
		        world.getTileEntity(x, y, z).writeToNBT(tileData);
				stack.stackTagCompound.setTag("TILEDATA", tileData);
				NBTHelper.setString(stack, "MEMCARD", "BLOCK", blockName);
				
				return true;
			}
			else if (player.isSneaking() && NBTHelper.getString(stack, "MEMCARD", "BLOCK") != null && block == Block.getBlockFromName(NBTHelper.getString(stack, "MEMCARD", "BLOCK")))
			{
				NBTTagCompound tileData = (NBTTagCompound) stack.stackTagCompound.getTag("TILEDATA");
		        world.getTileEntity(x, y, z).readFromNBT(tileData);
		        tile.xCoord = x;
		        tile.yCoord = y;
		        tile.zCoord = z;

				return true;
			}
		}
		return false;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (player.isSneaking() && !isLocked(stack))
		{
			stack.stackTagCompound.removeTag("TILEDATA");
			stack.stackTagCompound.removeTag("MEMCARD");
		}
		return super.onItemRightClick(stack, world, player);
	}

	@Override
	public boolean shouldAddChat(EntityPlayer arg0, ItemStack arg1)
	{
		return com.nhave.nhlib.config.ConfigHandler.postModeToChat;
	}
	
	@Override
	public void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, String key, boolean chat)
	{
		if (key.equals("TOGGLE") && (getOwner(itemStack).equals("NONE") || getOwner(itemStack).equals(entityPlayer.getDisplayName())))
		{
			setLocked(itemStack);
			String modeString = "nhutils.tooltip.readwrite";
			if (isLocked(itemStack)) modeString = "nhutils.tooltip.readonly";
			if (chat) entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("nhutils.tooltip.mode") + ": " + "§e" + "§o" + StatCollector.translateToLocal(modeString)));
			if (this.advanced)
			{
				if (isLocked(itemStack)) NBTHelper.setString(itemStack, "MEMCARD", "OWNER", entityPlayer.getDisplayName());
				else NBTHelper.setString(itemStack, "MEMCARD", "OWNER", "NONE");
			}
		}
	}
	
	public String getOwner(ItemStack itemStack)
	{
		if (!this.advanced) return "NONE";
		return NBTHelper.getString(itemStack, "MEMCARD", "OWNER", "NONE");
	}
	
	public void setLocked(ItemStack stack)
	{
		NBTHelper.setBoolean(stack, "MEMCARD", "LOCKED", !isLocked(stack));
	}
	
	public boolean isLocked(ItemStack stack)
	{
		return NBTHelper.getBoolean(stack, "MEMCARD", "LOCKED");
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean arg3)
	{
		list.add(StringUtils.BOLD + StatCollector.translateToLocal("tooltip.item.memcard") + "     ");
		if (this.advanced) list.add(StringUtils.PURPLE + StatCollector.translateToLocal("tooltip.shader.rarity.legendary"));
		else list.add(StringUtils.LIGHT_BLUE + StatCollector.translateToLocal("tooltip.shader.rarity.rare"));
		if (StringUtils.isShiftKeyDown())
		{
			list.add("§7" + StringUtils.localize("tooltip.wrmode.press") + " " + "§e" + "§o" + Keyboard.getKeyName(KeyBinds.toggle.getKeyCode()) + " " + "§r" + "§7" + StringUtils.localize("tooltip.wrmode.change") + "§r");
			if (isLocked(stack)) list.add(StatCollector.translateToLocal("tooltip.wrmode.mode") + ": " + "§e" + "§o" + StatCollector.translateToLocal("tooltip.readonly") + "§r");
			else  list.add(StatCollector.translateToLocal("tooltip.wrmode.mode") + ": " + "§e" + "§o" + StatCollector.translateToLocal("tooltip.readwrite") + "§r");
			if (NBTHelper.getString(stack, "MEMCARD", "BLOCK") != null)
			{
				Block block = Block.getBlockFromName(NBTHelper.getString(stack, "MEMCARD", "BLOCK"));
				if (block != null) list.add(StringUtils.YELLOW+StringUtils.ITALIC+block.getLocalizedName());
			}
			if (this.advanced && isLocked(stack)) list.add(StatCollector.translateToLocal("tooltip.owner") + ": " + StringUtils.YELLOW + StringUtils.ITALIC + getOwner(stack));
			list.add(StatCollector.translateToLocal("tooltip.memcard.info1"));
			list.add(StatCollector.translateToLocal("tooltip.memcard.info2"));
			list.add(StatCollector.translateToLocal("tooltip.memcard.info3"));
			if (this.advanced && isLocked(stack)) TooltipHelper.addLocalString(list, "tooltip.memcardadv.info");
		}
		else list.add(StringUtils.shiftForInfo);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addHudInfo(ItemStack stack, EntityPlayer player, List list)
	{
		list.add("§e" + "§o" + this.getItemStackDisplayName(stack) + "§r");
		if (isLocked(stack)) list.add(StatCollector.translateToLocal("tooltip.wrmode.mode") + ": " + "§e" + "§o" + StatCollector.translateToLocal("tooltip.readonly") + "§r");
		else  list.add(StatCollector.translateToLocal("tooltip.wrmode.mode") + ": " + "§e" + "§o" + StatCollector.translateToLocal("tooltip.readwrite") + "§r");
	}
}