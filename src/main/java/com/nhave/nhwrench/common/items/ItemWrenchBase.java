package com.nhave.nhwrench.common.items;

import java.util.Set;

import com.carpentersblocks.api.ICarpentersChisel;
import com.carpentersblocks.api.ICarpentersHammer;
import com.nhave.nhlib.helpers.NBTHelper;
import com.nhave.nhlib.interfaces.IKeyBound;
import com.nhave.nhwrench.api.IEntityInteraction;
import com.nhave.nhwrench.api.IPostUse;
import com.nhave.nhwrench.api.IWrenchMode;
import com.nhave.nhwrench.common.handlers.ConfigHandler;
import com.nhave.nhwrench.common.handlers.ItemHandler;
import com.nhave.nhwrench.common.helpers.BlockRotationHelper;
import com.nhave.nhwrench.common.helpers.CustomDismantleHelper;
import com.nhave.nhwrench.common.helpers.HarvestLevelHelper;
import com.nhave.nhwrench.common.register.WrenchModeRegistry;
import com.nhave.nhwrench.common.register.WrenchRegistry;

import buildcraft.api.tools.IToolWrench;
import cofh.api.item.IToolHammer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crazypants.enderio.api.tool.IConduitControl;
import mods.railcraft.api.core.items.IToolCrowbar;
import mrtjp.projectred.api.IScrewdriver;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class ItemWrenchBase extends ItemElectricBase implements 
	IKeyBound, IScrewdriver, IToolWrench, IToolHammer,
	IToolCrowbar, ICarpentersHammer, ICarpentersChisel,
	IConduitControl, com.bluepowermod.api.misc.IScrewdriver
{
	public static int[] colorCodes = new int[] {1644825, 16711680, 65280, 6704179, 255, 11685080, 5013401, 10066329, 6710886, 15892389, 8388371, 15059968, 6730495, 15027416, 16757299, 16777215};
	public static String[] ForgeDirBlacklist = new String[] {"bibliocraft", "ic2.core.block"};
	
	public double POWER_USE;
	
	public ItemWrenchBase()
	{
		super(ConfigHandler.maxPower * ConfigHandler.FROM_TE);
		this.POWER_USE = ConfigHandler.powerUsage * ConfigHandler.FROM_TE;
	}
	
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
		if (!hasPower(itemStack, player)) return false;
		
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
            drawPower(itemStack, player);
            return !world.isRemote;
        }
		
		/* === Custom Dismantle === */
		if (this.getWrenchMode(itemStack) == ItemHandler.modeWrench && player.isSneaking())
		{
			if (CustomDismantleHelper.canDismantle(block, bMeta))
			{
				CustomDismantleHelper.dismantleBlock(player, world, x, y, z, CustomDismantleHelper.shouldGrabTile(block, bMeta));
                player.swingItem();
                drawPower(itemStack, player);
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
	                drawPower(itemStack, player);
	        		return true;
        		}
	        }
		}
		
		/* === Forge Rotation === */
		if (!ConfigHandler.disableForgeDirection && this.getWrenchMode(itemStack) == ItemHandler.modeWrench && !player.isSneaking())
		{
			if ((block != null) && block.toString().contains("net.minecraft.block")) return false;
			for (int i = 0; i < ForgeDirBlacklist.length; ++i)
			{
				if (tile != null && tile.toString().contains(ForgeDirBlacklist[i])) return false;
				else if (block != null && block.toString().contains(ForgeDirBlacklist[i])) return false;
			}
			for (int i = 0; i < ConfigHandler.forgeDirBlacklist.length; ++i)
			{
				if (tile != null && tile.toString().contains(ConfigHandler.forgeDirBlacklist[i])) return false;
				else if (block != null && block.toString().contains(ConfigHandler.forgeDirBlacklist[i])) return false;
			}
	        if ((block != null) && (block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side))))
	        {
	            player.swingItem();
	            drawPower(itemStack, player);
	            return !world.isRemote;
	        }
		}
		
		return false;
	}
	
	public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer player, EntityLivingBase entity)
  	{
		if (!hasPower(itemStack, player)) return false;
		
		if (!WrenchRegistry.isEmpty())
		{
	        for (int i = 0; i < WrenchRegistry.getSize(); ++i)
	        {
	        	if (WrenchRegistry.getHandler(i) instanceof IEntityInteraction)
        		{
	        		if(((IEntityInteraction)WrenchRegistry.getHandler(i)).handleEntity(this.getWrenchMode(itemStack).getName(), itemStack, player, entity))
	        		{
	        			player.swingItem();
	                    drawPower(itemStack, player);
	        			return true;
	        		}
        		}
	        }
		}
		return false;	
  	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (!hasPower(stack, player)) return false;
		
		if (!WrenchRegistry.isEmpty())
		{
	        for (int i = 0; i < WrenchRegistry.getSize(); ++i)
	        {
	        	if (WrenchRegistry.getHandler(i) instanceof IPostUse)
        		{
	        		if(((IPostUse)WrenchRegistry.getHandler(i)).handlePostUse(this.getWrenchMode(stack).getName(), stack, player, world, x, y, z, side, hitX, hitY, hitZ))
	        		{
	        			player.swingItem();
	                    drawPower(stack, player);
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
	@SideOnly(Side.CLIENT)
	public boolean shouldAddChat(EntityPlayer entityPlayer, ItemStack itemStack)
	{
		return com.nhave.nhlib.config.ConfigHandler.postModeToChat;
	}
	
	@Override
	public void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, String key, boolean chat)
	{
		if (key.equals("TOGGLE"))
		{
			if (entityPlayer.isSneaking())
			{
				this.getWrenchMode(itemStack).onSubModeChange(entityPlayer, itemStack, chat);
			}
			else
			{
				this.updateMode(itemStack);
				if (chat) entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tooltip.wrmode.mode") + ": " + "§e" + "§o" + this.getWrenchModeAsString(itemStack) + "§r"));
			}
		}
	}
	
	/* =========================================================== Power Code ===============================================================*/
	
	public boolean hasPower(ItemStack stack)
	{
		return !ConfigHandler.usePower || getEnergy(stack) >= this.POWER_USE;
	}
	
	public boolean hasPower(ItemStack stack, EntityPlayer player)
	{
		return !ConfigHandler.usePower || player.capabilities.isCreativeMode || getEnergy(stack) >= this.POWER_USE;
	}
	
	public void drawPower(ItemStack stack, EntityPlayer player)
	{
		setEnergy(stack, getEnergy(stack) - (this.POWER_USE));
	}

	@Override
	public boolean canReceive(ItemStack itemStack)
	{
		if (ConfigHandler.usePower) return super.canReceive(itemStack);
		else return false;
	}

	@Override
	public boolean canSend(ItemStack itemStack)
	{
		return false;
	}
	
	/* =========================================================== HARVEST LEVELS ===============================================================*/
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass)
	{
		if (!hasPower(stack)) return -1;
		if(getToolClasses(stack).contains(toolClass)) return 2;
		else return -1;
	}
	
	@Override
	public Set<String> getToolClasses(ItemStack stack)
	{
		if (!hasPower(stack)) return super.getToolClasses(stack);
		if (this.getWrenchMode(stack) == ItemHandler.modeWrench) return HarvestLevelHelper.toolClassesWrench.keySet();
		else if (this.getWrenchMode(stack) == ItemHandler.modeRotate) return HarvestLevelHelper.toolClassesRotate.keySet();
		else if (this.getWrenchMode(stack) == ItemHandler.modeTune) return HarvestLevelHelper.toolClassesTune.keySet();
		else if (this.getWrenchMode(stack) == ItemHandler.modeUtil) return HarvestLevelHelper.toolClassesUtil.keySet();
		else return super.getToolClasses(stack);
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta)
	{
		if (!hasPower(stack)) super.getDigSpeed(stack, block, meta);
		if(ForgeHooks.isToolEffective(stack, block, meta)) return 6;
		return super.getDigSpeed(stack, block, meta);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entityLiving)
	{
		if (ForgeHooks.isToolEffective(stack, block, world.getBlockMetadata(x, y, z))) drawPower(stack, (EntityPlayer) entityLiving);
		return false;
	}
	
	/* =========================================================== PROJECT RED ===============================================================*/
	
	@Override
	public boolean canUse(EntityPlayer player, ItemStack stack)
	{
		return this.getWrenchMode(stack) == ItemHandler.modeTune && hasPower(stack, player);
	}
	
	@Override
	public void damageScrewdriver(EntityPlayer player, ItemStack stack)
	{
		drawPower(stack, player);
		player.swingItem();
	}

	/* =========================================================== BUILDCRAFT ===============================================================*/
	
	@Override
	public boolean canWrench(EntityPlayer player, int x, int y, int z)
	{
		return this.getWrenchMode(player.getCurrentEquippedItem()) == ItemHandler.modeWrench && hasPower(player.getCurrentEquippedItem(), player);
	}

	@Override
	public void wrenchUsed(EntityPlayer player, int x, int y, int z)
	{
		drawPower(player.getCurrentEquippedItem(), player);
		player.swingItem();
	}
	
	/* =========================================================== Cofh ===============================================================*/

	@Override
	public boolean isUsable(ItemStack item, EntityLivingBase user, int x, int y, int z)
	{
		return this.getWrenchMode(user.getHeldItem()) == ItemHandler.modeWrench && hasPower(item, (EntityPlayer) user);
	}
	
	@Override
	public void toolUsed(ItemStack item, EntityLivingBase user, int x, int y, int z)
	{
		drawPower(item, (EntityPlayer) user);
		user.swingItem();
	}

	/* =========================================================== RAILCRAFT ===============================================================*/
	
	@Override
	public boolean canWhack(EntityPlayer player, ItemStack crowbar, int x, int y, int z)
	{
		return this.getWrenchMode(player.getCurrentEquippedItem()) == ItemHandler.modeWrench && hasPower(crowbar, player);
	}

	@Override
	public void onWhack(EntityPlayer player, ItemStack crowbar, int x, int y, int z)
	{
		drawPower(crowbar, player);
		player.swingItem();
	}

	@Override
	public boolean canLink(EntityPlayer player, ItemStack crowbar, EntityMinecart cart)
	{
		return player.isSneaking() && this.getWrenchMode(player.getCurrentEquippedItem()) == ItemHandler.modeWrench && hasPower(crowbar, player);
	}

	@Override
	public void onLink(EntityPlayer player, ItemStack crowbar, EntityMinecart cart)
	{
		drawPower(crowbar, player);
		player.swingItem();
	}

	@Override
	public boolean canBoost(EntityPlayer player, ItemStack crowbar, EntityMinecart cart)
	{
		return !player.isSneaking() && this.getWrenchMode(player.getCurrentEquippedItem()) == ItemHandler.modeWrench && hasPower(crowbar, player);
	}

	@Override
	public void onBoost(EntityPlayer player, ItemStack crowbar, EntityMinecart cart)
	{
		drawPower(crowbar, player);
		player.swingItem();
	}

	/* =========================================================== CARPENTERS BLOCKS ===============================================================*/
	
	@Override
	public void onHammerUse(World world, EntityPlayer player)
	{
		drawPower(player.getCurrentEquippedItem(), player);
		player.swingItem();
	}

	@Override
	public boolean canUseHammer(World world, EntityPlayer player)
	{
		return this.getWrenchMode(player.getCurrentEquippedItem()) == ItemHandler.modeWrench && hasPower(player.getCurrentEquippedItem(), player);
	}

	@Override
	public void onChiselUse(World world, EntityPlayer player)
	{
		drawPower(player.getCurrentEquippedItem(), player);
		player.swingItem();
	}

	@Override
	public boolean canUseChisel(World world, EntityPlayer player)
	{
		return this.getWrenchMode(player.getCurrentEquippedItem()) == ItemHandler.modeTune && hasPower(player.getCurrentEquippedItem(), player);
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
		drawPower(stack, player);
		return true;
	}
}