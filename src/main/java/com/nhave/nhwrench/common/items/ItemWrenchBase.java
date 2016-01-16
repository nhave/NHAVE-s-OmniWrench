package com.nhave.nhwrench.common.items;

import java.util.Set;

import com.carpentersblocks.api.ICarpentersChisel;
import com.carpentersblocks.api.ICarpentersHammer;
import com.nhave.nhlib.helpers.NBTHelper;
import com.nhave.nhlib.interfaces.IKeyBound;
import com.nhave.nhwrench.api.IEntityInteraction;
import com.nhave.nhwrench.api.IWrenchMode;
import com.nhave.nhwrench.api.WrenchModeRegistry;
import com.nhave.nhwrench.api.WrenchRegistry;
import com.nhave.nhwrench.common.handlers.ItemHandler;
import com.nhave.nhwrench.common.helpers.BlockRotationHelper;
import com.nhave.nhwrench.common.helpers.CustomDismantleHelper;
import com.nhave.nhwrench.common.helpers.HarvestLevelHelper;

import buildcraft.api.tools.IToolWrench;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import crazypants.enderio.api.tool.IConduitControl;
import mods.railcraft.api.core.items.IToolCrowbar;
import mrtjp.projectred.api.IScrewdriver;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class ItemWrenchBase extends Item implements 
	IKeyBound, IScrewdriver, IToolWrench,
	IToolCrowbar, ICarpentersHammer, ICarpentersChisel,
	IConduitControl, com.bluepowermod.api.misc.IScrewdriver
{
	public static int[] colorCodes = new int[] {1644825, 16711680, 65280, 6704179, 255, 11685080, 5013401, 10066329, 6710886, 15892389, 8388371, 15059968, 6730495, 15027416, 16757299, 16777215};
	public static String[] ForgeDirBlacklist = new String[] {"bibliocraft"};
	
	public IWrenchMode getWrenchMode(ItemStack stack)
	{
		int mode = NBTHelper.getInteger(stack, "WRENCH", "MODE", 0);
		if (mode < 0 || mode > WrenchModeRegistry.getSize()-1) mode = 0;
		if (!WrenchModeRegistry._Modes.containsKey(mode)) return ItemHandler.modeWrench;
		return WrenchModeRegistry.getMode(mode);
	}
	
	public String getWrenchModeAsString(ItemStack stack)
	{
		return StatCollector.translateToLocal("tooltip." + getWrenchMode(stack).getName());
	}
	
	public void updateMode(ItemStack stack)
	{
		int mode = NBTHelper.getInteger(stack, "WRENCH", "MODE", 0);
		int newMode = WrenchModeRegistry.getNext(mode);
		NBTHelper.setInteger(stack, "WRENCH", "MODE", newMode);
		if (WrenchModeRegistry._Modes.containsKey(newMode) && !WrenchModeRegistry.getMode(newMode).isActive(stack)) updateMode(stack);
	}
	
	/* =========================================================== FUNCTIONING START ===============================================================*/
	
	@Override
	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		//NHWrench.logger.info(world.getBlock(x, y, z).getUnlocalizedName());
		
		/* === Vanilla Rotation === */
		Block block = world.getBlock(x, y, z);
        int bMeta = world.getBlockMetadata(x, y, z);
        
		if (BlockRotationHelper.canRotate(block) && this.getWrenchMode(itemStack) == ItemHandler.modeRotate)
		{
            if (player.isSneaking())
            {
                world.setBlockMetadataWithNotify(x, y, z, BlockRotationHelper.rotateVanillaBlockAlt(world, block, bMeta, x, y, z), 3);
                if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
                {
                    String soundName = block.stepSound.func_150496_b();
                    player.playSound(soundName, 1.0F, 0.6F);
                    player.swingItem();
                }
            }
            else
            {
                world.setBlockMetadataWithNotify(x, y, z, BlockRotationHelper.rotateVanillaBlock(world, block, bMeta, x, y, z), 3);
                if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
                {
                    String soundName = block.stepSound.func_150496_b();
                    player.playSound(soundName, 1.0F, 0.6F);
                    player.swingItem();
                }
            }
            return !world.isRemote;
        }
		
		/* === Custom Dismantle === */
		if (this.getWrenchMode(itemStack) == ItemHandler.modeWrench && player.isSneaking())
		{
			if (CustomDismantleHelper.canDismantle(block, bMeta))
			{
				CustomDismantleHelper.dismantleBlock(player, world, x, y, z, CustomDismantleHelper.shouldGrabTile(block, bMeta));
                player.swingItem();
	            return !world.isRemote;
			}
		}
		
		if (!WrenchRegistry.isEmpty())
		{
	        for (int i = 0; i < WrenchRegistry.getSize(); ++i)
	        {
	        	if (WrenchRegistry.getHandler(i).handleWrench(this.getWrenchMode(itemStack).getName(), itemStack, player, world, x, y, z, side, hitX, hitY, hitZ))
        		{
	        		player.swingItem();
	        		return true;
        		}
	        }
		}
		
		/* === Forge Rotation === */
		if (this.getWrenchMode(itemStack) == ItemHandler.modeWrench && !player.isSneaking())
		{
			if ((block != null) && block.toString().contains("net.minecraft.block")) return false;
			for (int i = 0; i < ForgeDirBlacklist.length; ++i)
			{
				if (tile != null && tile.toString().contains(ForgeDirBlacklist[i])) return false;
			}
	        if ((block != null) && (block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side))))
	        {
	            player.swingItem();
	            return !world.isRemote;
	        }
		}
		
		return false;
	}
	
	public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer player, EntityLivingBase entity)
  	{
		if (!WrenchRegistry.isEmpty())
		{
	        for (int i = 0; i < WrenchRegistry.getSize(); ++i)
	        {
	        	if (WrenchRegistry.getHandler(i) instanceof IEntityInteraction)
        		{
	        		if(((IEntityInteraction)WrenchRegistry.getHandler(i)).handleEntity(this.getWrenchMode(itemStack).getName(), itemStack, player, entity))
	        		{
	        			player.swingItem();
	        			return true;
	        		}
        		}
	        }
		}
		return false;	
  	}
	
	@Override
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player)
	{
		return true;
	}

	@Override
	public void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, String key)
	{
		if (key.equals("TOGGLE"))
		{
			this.updateMode(itemStack);
			entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip.wrmode.mode") + ": " + "§e" + "§o" + this.getWrenchModeAsString(itemStack)));
		}
	}
	
	/* =========================================================== HARVEST LEVELS ===============================================================*/
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass)
	{
		if(getToolClasses(stack).contains(toolClass)) return 2;
		else return -1;
	}
	
	@Override
	public Set<String> getToolClasses(ItemStack stack)
	{
		if (this.getWrenchMode(stack) == ItemHandler.modeWrench) return HarvestLevelHelper.toolClassesWrench.keySet();
		else if (this.getWrenchMode(stack) == ItemHandler.modeRotate) return HarvestLevelHelper.toolClassesRotate.keySet();
		else if (this.getWrenchMode(stack) == ItemHandler.modeTune) return HarvestLevelHelper.toolClassesTune.keySet();
		else if (this.getWrenchMode(stack) == ItemHandler.modeUtil) return HarvestLevelHelper.toolClassesUtil.keySet();
		else return super.getToolClasses(stack);
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta)
	{
		if(ForgeHooks.isToolEffective(stack, block, meta)) return 6;
		return super.getDigSpeed(stack, block, meta);
	}
	
	/* =========================================================== PROJECT RED ===============================================================*/
	
	@Override
	public boolean canUse(EntityPlayer player, ItemStack stack)
	{
		return this.getWrenchMode(stack) == ItemHandler.modeTune;
	}
	
	@Override
	public void damageScrewdriver(EntityPlayer player, ItemStack stack)
	{
		player.swingItem();
	}

	/* =========================================================== BUILDCRAFT ===============================================================*/
	
	@Override
	public boolean canWrench(EntityPlayer player, int x, int y, int z)
	{
		return this.getWrenchMode(player.getCurrentEquippedItem()) == ItemHandler.modeWrench;
	}

	@Override
	public void wrenchUsed(EntityPlayer player, int x, int y, int z)
	{
		player.swingItem();
	}

	/* =========================================================== RAILCRAFT ===============================================================*/
	
	@Override
	public boolean canWhack(EntityPlayer player, ItemStack crowbar, int x, int y, int z)
	{
		return this.getWrenchMode(player.getCurrentEquippedItem()) == ItemHandler.modeWrench;
	}

	@Override
	public void onWhack(EntityPlayer player, ItemStack crowbar, int x, int y, int z)
	{
		player.swingItem();
	}

	@Override
	public boolean canLink(EntityPlayer player, ItemStack crowbar, EntityMinecart cart)
	{
		return player.isSneaking() && this.getWrenchMode(player.getCurrentEquippedItem()) == ItemHandler.modeWrench;
	}

	@Override
	public void onLink(EntityPlayer player, ItemStack crowbar, EntityMinecart cart)
	{
		player.swingItem();
	}

	@Override
	public boolean canBoost(EntityPlayer player, ItemStack crowbar, EntityMinecart cart)
	{
		return !player.isSneaking() && this.getWrenchMode(player.getCurrentEquippedItem()) == ItemHandler.modeWrench;
	}

	@Override
	public void onBoost(EntityPlayer player, ItemStack crowbar, EntityMinecart cart)
	{
		player.swingItem();
	}

	/* =========================================================== CARPENTERS BLOCKS ===============================================================*/
	
	@Override
	public void onHammerUse(World world, EntityPlayer player)
	{
		player.swingItem();
	}

	@Override
	public boolean canUseHammer(World world, EntityPlayer player)
	{
		return this.getWrenchMode(player.getCurrentEquippedItem()) == ItemHandler.modeWrench;
	}

	@Override
	public void onChiselUse(World world, EntityPlayer player)
	{
		player.swingItem();
	}

	@Override
	public boolean canUseChisel(World world, EntityPlayer player)
	{
		return this.getWrenchMode(player.getCurrentEquippedItem()) == ItemHandler.modeTune;
	}
	
	/* =========================================================== ENDER IO ===============================================================*/
	
	@Override
	public boolean shouldHideFacades(ItemStack stack, EntityPlayer player)
	{
		return this.getWrenchMode(player.getCurrentEquippedItem()) == ItemHandler.modeWrench || this.getWrenchMode(player.getCurrentEquippedItem()) == ItemHandler.modeTune;
	}
	
	@Override
	public boolean showOverlay(ItemStack stack, EntityPlayer player)
	{
		return this.getWrenchMode(player.getCurrentEquippedItem()) == ItemHandler.modeWrench;
	}
	
	/* =========================================================== Blue Power ===============================================================*/
	
	@Override
	public boolean damage(ItemStack stack, int damage, EntityPlayer player, boolean simulated)
	{
		player.swingItem();
		return true;
	}
}