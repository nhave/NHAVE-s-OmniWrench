package com.nhave.nhintegration.integration;

import java.util.Random;

import com.nhave.nhwrench.api.IWrenchHandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import jds.bibliocraft.items.ItemDrill;
import jds.bibliocraft.items.ItemLoader;
import jds.bibliocraft.tileentities.TileEntityArmorStand;
import jds.bibliocraft.tileentities.TileEntityBookcase;
import jds.bibliocraft.tileentities.TileEntityClock;
import jds.bibliocraft.tileentities.TileEntityDinnerPlate;
import jds.bibliocraft.tileentities.TileEntityDiscRack;
import jds.bibliocraft.tileentities.TileEntityFancySign;
import jds.bibliocraft.tileentities.TileEntityFancyWorkbench;
import jds.bibliocraft.tileentities.TileEntityFramedChest;
import jds.bibliocraft.tileentities.TileEntityGenericShelf;
import jds.bibliocraft.tileentities.TileEntityLamp;
import jds.bibliocraft.tileentities.TileEntityLantern;
import jds.bibliocraft.tileentities.TileEntityMapFrame;
import jds.bibliocraft.tileentities.TileEntityPainting;
import jds.bibliocraft.tileentities.TileEntityPotionShelf;
import jds.bibliocraft.tileentities.TileEntityPrintPress;
import jds.bibliocraft.tileentities.TileEntitySeat;
import jds.bibliocraft.tileentities.TileEntitySwordPedestal;
import jds.bibliocraft.tileentities.TileEntityTable;
import jds.bibliocraft.tileentities.TileEntityTypeMachine;
import jds.bibliocraft.tileentities.TileEntityTypewriter;
import jds.bibliocraft.tileentities.TileEntityWeaponCase;
import jds.bibliocraft.tileentities.TileEntityWeaponRack;
import jds.bibliocraft.tileentities.TileEntityWritingDesk;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

/**
 * NHAVE's Omniwrench
 * 
 * @author nhave
 */
public class BiblioHandler implements IWrenchHandler
{
	private TileEntity tile1;
	private TileEntity tile2;
	
	@Override
	public boolean handleWrench(String mode, ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
    	ItemDrill drill = (ItemDrill) ItemLoader.screwgun;
		TileEntity tile = world.getTileEntity(x, y, z);
	    boolean returnValue = false;
	    if (mode.equals("wrmode.wrench") && !world.isRemote)
	    {
	    	if (tile != null)
	    	{
	    		if (player.isSneaking())
	    		{
	    			if ((tile instanceof TileEntitySeat))
	    			{
	    				if (side == 1)
	    				{
	    					returnValue = drill.connectChairs(player, world, x, y, z);
	    				}
	    				else
	    				{
	    					returnValue = drill.rotateSeat(tile);
	    				}
	    			}
	    			if ((tile instanceof TileEntityDiscRack))
	    			{
	    				returnValue = drill.discRackAngle(player, world, x, y, z);
	    			}
	    			if ((tile instanceof TileEntityBookcase))
	    			{
	    				returnValue = drill.rotateBookcase(tile);
	    			}
	    			if ((tile instanceof TileEntityDinnerPlate))
	    			{
	    				returnValue = drill.rotateDinnerPlate(tile);
	    			}
	    			if ((tile instanceof TileEntityGenericShelf))
	    			{
	    				returnValue = drill.rotateGenericShelf(tile);
	    			}
	    			if ((tile instanceof TileEntityLamp))
	    			{
	    				returnValue = drill.rotateLamp(tile);
	    			}
	    			if ((tile instanceof TileEntityLantern))
	    			{
	    				returnValue = drill.rotateLantern(tile);
	    			}
	    			if ((tile instanceof TileEntityMapFrame))
	    			{
	    				returnValue = drill.rotateMapFrame(tile);
	    			}
	    			if ((tile instanceof TileEntityPotionShelf))
	    			{
	    				returnValue = drill.rotatePotionShelf(tile);
	    			}
	    			if ((tile instanceof TileEntityWeaponCase))
	    			{
	    				returnValue = drill.rotateDisplayCase(tile);
	    			}
	    			if ((tile instanceof TileEntityWeaponRack))
	    			{
	    				returnValue = drill.rotateWeaponRack(tile);
	    			}
	    			if ((tile instanceof TileEntityWritingDesk))
	    			{
	    				returnValue = drill.rotateDesk(tile);
	    			}
	    			if ((tile instanceof TileEntityTypeMachine))
	    			{
	    				returnValue = drill.rotate4baseMeta(world, x, y, z);
	    				world.markBlockForUpdate(x, y, z);
	    			}
	    			if ((tile instanceof TileEntityPrintPress))
	    			{
	    				returnValue = drill.rotate4baseMeta(world, x, y, z);
	    				world.markBlockForUpdate(x, y, z);
	    			}
	    			if ((tile instanceof TileEntityArmorStand))
	    			{
	    				returnValue = drill.rotateArmorStand(world, tile, x, y, z);
	    			}
	    			if ((tile instanceof TileEntityTable))
	    			{
	    				returnValue = drill.removeTableCarpet(world, tile, x, y, z);
	    			}
					if (tile instanceof TileEntityPainting)
					{
						returnValue = connectTwoPaintings(world, tile, player, x, y, z);
					}
					if (tile instanceof TileEntityClock)
					{
						returnValue = connectTwoClocks(world, tile, player, x, y, z);
					}
					if (tile instanceof TileEntitySwordPedestal)
					{
						returnValue = rotateSwordPedestal((TileEntitySwordPedestal) tile);
					}
					if (tile instanceof TileEntityTypewriter)
					{
						returnValue = rotateTypewriter((TileEntityTypewriter) tile);
					}
					if (tile instanceof TileEntityFancySign)
					{
						returnValue = rotateFancySign((TileEntityFancySign) tile);
					}
					if (tile instanceof TileEntityFancyWorkbench)
					{
						returnValue = rotateFancyWorkbench((TileEntityFancyWorkbench) tile);
					}
					if ((tile instanceof TileEntityFramedChest))
					{
						returnValue = connectTwoChests(world, tile, player);
					}
	    		}
	    		else
	    		{
	    			if ((tile instanceof TileEntityDiscRack))
	    			{
	    				returnValue = drill.discRackRotate(player, world, x, y, z);
	    			}
	    			if ((tile instanceof TileEntityLamp))
	    			{
	    				returnValue = drill.setLampStyle(tile);
	    			}
	    			if ((tile instanceof TileEntityLantern))
	    			{
	    				returnValue = drill.setLanternStyle(tile);
	    			}
	    			if ((tile instanceof TileEntityWeaponCase))
	    			{
	    				returnValue = drill.setDisplayCaseStyle(tile);
	    			}
	    			if ((tile instanceof TileEntityBookcase))
	    			{
	    				returnValue = drill.setBookcaseShift(tile);
	    			}
	    			if ((tile instanceof TileEntityFancySign))
	    			{
	    				returnValue = drill.setFancySignShift(tile);
	    			}
					if (tile instanceof TileEntityTable)
					{
						returnValue = removeTableCloth(world, x, y, z);
					}
					if (tile instanceof TileEntitySeat)
					{
						returnValue = removeSeatPart(world, x, y, z);
					}
					if (tile instanceof TileEntityPainting)
					{
						returnValue = rotatePaintingCanvas((TileEntityPainting) tile);
					}
					if (tile instanceof TileEntityClock)
					{
						returnValue = setClockShift(world, (TileEntityClock) tile);
					}
					if (tile instanceof TileEntityGenericShelf)
					{
						returnValue = setShelfShift((TileEntityGenericShelf) tile);
					}
					if (tile instanceof TileEntityPotionShelf)
					{
						returnValue = setPotionShelfShift((TileEntityPotionShelf) tile);
					}
					if (tile instanceof TileEntityWeaponRack)
					{
						returnValue = setToolRackShift((TileEntityWeaponRack) tile);
					}
					if ((tile instanceof TileEntityFramedChest))
					{
						 returnValue = rotateFramedChest((TileEntityFramedChest)tile);
					}
	    		}
	    		if (!returnValue) {}
	    	}
	    }
	    return returnValue;
	}
	
	private void playSound(EntityPlayer player)
	{
		player.playSound("dig.stone", 1.0F, 0.8F);
	}
	
	/**
	 * Hacky method of removing the Table Cloth.
	 * 
	 * @param world The world.
	 * @param x Cord X.
	 * @param y Cord Y.
	 * @param z Cord Z.
	 */
	public boolean removeTableCloth(World world, int x, int y, int z)
	{
		TileEntityTable tabletile = (TileEntityTable)world.getTileEntity(x, y, z);
		dropTableItem(world, x, y, z, 1);
		tabletile.setTableCloth(null);
		return true;
	}

	/**
	 * Clone of the dropTableItem from BlockTable.
	 * 
	 * @param world The world.
	 * @param x Cord X.
	 * @param y Cord Y.
	 * @param z Cord Z.
	 * @param slot The slot.
	 */
	private void dropTableItem(World world, int x, int y, int z, int slot)
	{
	    TileEntity tileEntity = world.getTileEntity(x, y, z);
	    if (!(tileEntity instanceof IInventory))
	    {
	    	return;
	    }
	    
	    IInventory inventory = (IInventory)tileEntity;
	    TileEntityTable tableTile = (TileEntityTable)tileEntity;
	    ItemStack stuff;
	    if ((tableTile.isSlotFull()) && (slot == 0))
	    {
	    	stuff = inventory.getStackInSlot(0);
	    }
	    else
	    {
	    	if ((tableTile.isClothSlotFull()) && (slot == 1))
	    	{
	    		stuff = inventory.getStackInSlot(1);
	    	}
	     	else
	     	{
	     		stuff = null;
	     	}
	    }
	    if ((stuff != null) && (stuff.stackSize > 0))
	    {
	    	float iAdjust = 0.0F;
	    	
	    	EntityItem entityItem = new EntityItem(world, x + 0.5F, y + 1.4F, z + 0.5F, new ItemStack(stuff.getItem(), stuff.stackSize, stuff.getItemDamage()));
	    	
	    	if (stuff.hasTagCompound())
	    	{
	    		entityItem.getEntityItem().setTagCompound((NBTTagCompound)stuff.getTagCompound().copy());
	    	}
	    	entityItem.motionX = 0.0D;
	    	entityItem.motionY = 0.0D;
	    	entityItem.motionZ = 0.0D;
	    	world.spawnEntityInWorld(entityItem);
	    	stuff.stackSize = 0;
    	}
	}
	
	/**
	 * Hacky method of dealing with hardcoded Seat handling.
	 * 
	 * @param world The world.
	 * @param x Cord X.
	 * @param y Cord Y.
	 * @param z Cord Z.
	 */
	public boolean removeSeatPart(World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
	    IInventory inventory = (IInventory)tileEntity;
	    TileEntitySeat seatTile = (TileEntitySeat)tileEntity;
	    if (inventory.getStackInSlot(0) != null)
	    {
	    	dropSeatSlot(world, x, y, z, 0);
	    	seatTile.removeCover();
	    	return true;
	    }
	    if (seatTile.hasBack > 0)
     	{
	    	dropSeatSlot(world, x, y, z, 1);
	    	seatTile.removeBack();
	    	return true;
     	}
	    return true;
	}
	
	/**
	 * Clone of the dropSlot from BlockSeat.
	 * 
	 * @param world The world.
	 * @param x Cord X.
	 * @param y Cord Y.
	 * @param z Cord Z.
	 * @param slot The slot.
	 */
	private void dropSeatSlot(World world, int x, int y, int z, int slot)
	{
	    Random rando = new Random();
	    
	    TileEntity tileEntity = world.getTileEntity(x, y, z);
	    if (!(tileEntity instanceof IInventory))
	    {
	    	return;
	    }
	    IInventory inventory = (IInventory)tileEntity;
	    
	    ItemStack item = inventory.getStackInSlot(slot);
	    
    	if ((item != null) && (item.stackSize > 0))
    	{
    		EntityItem entityItem = new EntityItem(world, x + 0.5F, y + 1.0F, z + 0.5D, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
    		
    		if (item.hasTagCompound())
    		{
    			entityItem.getEntityItem().setTagCompound((NBTTagCompound)item.getTagCompound().copy());
    		}
    		
    		entityItem.motionX = 0.0D;
    		entityItem.motionY = 0.0D;
    		entityItem.motionZ = 0.0D;
    		world.spawnEntityInWorld(entityItem);
	    }
	}
	
	private boolean connectTwoClocks(World world, TileEntity tile, EntityPlayer player, int i, int j, int k)
	{
		if ((tile1 == null) || (!(tile1 instanceof TileEntityClock)))
		{
			tile1 = tile;
			
			sendPacketToClient(StatCollector.translateToLocal("screwgun.selected.clock1"), (EntityPlayerMP) player);
			return true;
		}
		if ((tile2 == null) || (!(tile2 instanceof TileEntityClock)))
		{
			tile2 = tile;

			sendPacketToClient(StatCollector.translateToLocal("screwgun.selected.clock2"), (EntityPlayerMP) player);
		}
		
		if ((tile1 != null) && (tile1 instanceof TileEntityClock) && (tile2 != null) && (tile2 instanceof TileEntityClock))
		{
			if (tile1.yCoord == tile2.yCoord + 1)
			{
				TileEntityClock clockTop = (TileEntityClock) tile1;
				TileEntityClock clockBottom = (TileEntityClock) tile2;
				if (!(configureClocks(clockTop, clockBottom)))
				{
					sendPacketToClient(StatCollector.translateToLocal("drill.failed"), (EntityPlayerMP) player);
				}
				tile1 = null;
				tile2 = null;
				return true;
			}
			if (tile1.yCoord == tile2.yCoord - 1)
			{
				TileEntityClock clockTop = (TileEntityClock) tile2;
				TileEntityClock clockBottom = (TileEntityClock) tile1;
				if (!(configureClocks(clockTop, clockBottom)))
				{
					sendPacketToClient(StatCollector.translateToLocal("drill.failed"), (EntityPlayerMP) player);
				}
				tile1 = null;
				tile2 = null;
				return true;
			}
			
			sendPacketToClient(StatCollector.translateToLocal("drill.failed"), (EntityPlayerMP) player);
			tile1 = null;
			tile2 = null;
		}

		return false;
	}

	public boolean configureClocks(TileEntityClock top, TileEntityClock bottom)
	{
		if ((top.getClockType() == 0) && (bottom.getClockType() == 0))
		{
			top.setClockType(1);
			bottom.setClockType(2);
			return true;
		}
		if ((top.getClockType() == 1) && (bottom.getClockType() == 2))
		{
			top.setClockType(0);
			bottom.setClockType(0);
			return true;
		}
		
		return false;
	}
	
	private boolean setClockShift(World world, TileEntityClock tile)
	{
		tile.setShift(!(tile.getShift()));
		if (tile.getClockType() == 1)
		{
			TileEntity otherClock = world.getTileEntity(tile.xCoord, tile.yCoord - 1, tile.zCoord);
			if ((otherClock != null) && (otherClock instanceof TileEntityClock))
			{
				TileEntityClock otherClockTile = (TileEntityClock) otherClock;
				otherClockTile.setShift(!(otherClockTile.getShift()));
			}
		}
		if (tile.getClockType() == 2)
		{
			TileEntity otherClock = world.getTileEntity(tile.xCoord, tile.yCoord + 1, tile.zCoord);
			if ((otherClock != null) && (otherClock instanceof TileEntityClock))
			{
				TileEntityClock otherClockTile = (TileEntityClock) otherClock;
				otherClockTile.setShift(!(otherClockTile.getShift()));
			}
		}
		return true;
	}
	
	private void sendPacketToClient(String displayString, EntityPlayerMP player)
	{
		ByteBuf buffer = Unpooled.buffer();
	    cpw.mods.fml.common.network.ByteBufUtils.writeUTF8String(buffer, displayString);
	    
	    jds.bibliocraft.BiblioCraft.ch_BiblioDrillText.sendTo(new cpw.mods.fml.common.network.internal.FMLProxyPacket(buffer, "BiblioDrillText"), player);
	}
	
	private boolean setShelfShift(TileEntityGenericShelf tile)
	{
		tile.setShift(!(tile.getShift()));
		return true;
	}
	
	private boolean setPotionShelfShift(TileEntityPotionShelf tile)
	{
		tile.setShift(!(tile.getShift()));
		return true;
	}
	
	private boolean setToolRackShift(TileEntityWeaponRack tile)
	{
		tile.setShift(!(tile.getShift()));
		return true;
	}
	
	private boolean rotateSwordPedestal(TileEntitySwordPedestal pedestalTile)
	{
		if (pedestalTile.getAngle() < 3)
		{
			pedestalTile.setAngle(pedestalTile.getAngle() + 1);
		}
		else
		{
			pedestalTile.setAngle(0);
		}
		return true;
	}
	
	private boolean rotateFancySign(TileEntityFancySign tile)
	{
		if (tile.getAngle() < 3)
		{
			tile.setAngle(tile.getAngle() + 1);
		}
		else
		{
			tile.setAngle(0);
		}
		return true;
	}
	
	private boolean rotateFancyWorkbench(TileEntityFancyWorkbench tile)
	{
		if (tile.getAngle() < 3)
		{
			tile.setAngle(tile.getAngle() + 1);
		}
		else
		{
			tile.setAngle(0);
		}
		return true;
	}
	
	private boolean connectTwoPaintings(World world, TileEntity tile, EntityPlayer player, int x, int y, int z)
	{
		if ((this.tile1 == null) || (!(this.tile1 instanceof TileEntityPainting)))
		{
			this.tile1 = tile;

			sendPacketToClient(StatCollector.translateToLocal("screwgun.selected.painting1"), (EntityPlayerMP) player);
			return true;
		}
		if ((this.tile2 == null) || (!(this.tile2 instanceof TileEntityPainting)))
		{
			this.tile2 = tile;
			
			sendPacketToClient(StatCollector.translateToLocal("screwgun.selected.painting2"), (EntityPlayerMP) player);
		}
		
		if ((this.tile1 != null) && (this.tile1 instanceof TileEntityPainting) && (this.tile2 != null) && (this.tile2 instanceof TileEntityPainting))
		{
			TileEntityPainting painting1 = (TileEntityPainting) this.tile1;
			TileEntityPainting painting2 = (TileEntityPainting) this.tile2;
			if (painting1.getAngle() == painting2.getAngle())
			{
				if (this.tile1.yCoord == this.tile2.yCoord + 1)
				{
					if ((painting1.getConnectBottom()) && (painting2.getConnectTop()))
					{
						painting1.setConnectBottom(false);
						painting2.setConnectTop(false);
					}
					else
					{
						painting1.setConnectBottom(true);
						painting2.setConnectTop(true);
					}
					completedPaintingConnect(player);
				}
				else if (this.tile1.yCoord == this.tile2.yCoord - 1)
				{
					if ((painting1.getConnectTop()) && (painting2.getConnectBottom()))
					{
						painting1.setConnectTop(false);
						painting2.setConnectBottom(false);
					}
					else
					{
						painting1.setConnectTop(true);
						painting2.setConnectBottom(true);
					}
					completedPaintingConnect(player);
				}
				else if (checkLeftSidePainting(painting1, painting2))
				{
					if ((painting1.getConnectLeft()) && (painting2.getConnectRight()))
					{
						painting1.setConnectLeft(false);
						painting2.setConnectRight(false);
					} else {
						painting1.setConnectLeft(true);
						painting2.setConnectRight(true);
					}
					completedPaintingConnect(player);
				}
				else if (checkRightSidePainting(painting1, painting2))
				{
					if ((painting1.getConnectRight()) && (painting2.getConnectLeft()))
					{
						painting1.setConnectRight(false);
						painting2.setConnectLeft(false);
					}
					else
					{
						painting1.setConnectRight(true);
						painting2.setConnectLeft(true);
					}
					completedPaintingConnect(player);
				}
				else
				{
					sendPacketToClient(StatCollector.translateToLocal("drill.failed"), (EntityPlayerMP) player);
					this.tile1 = null;
					this.tile2 = null;
				}

			}
			else
			{
				sendPacketToClient(StatCollector.translateToLocal("drill.failed"), (EntityPlayerMP) player);
				this.tile1 = null;
				this.tile2 = null;
			}
		}
		return false;
	}
	
	private void completedPaintingConnect(EntityPlayer player)
	{
		this.tile1 = null;
		this.tile2 = null;
	}
	
	private boolean checkLeftSidePainting(TileEntityPainting centerPainting, TileEntityPainting otherPainting)
	{
		switch (centerPainting.getAngle())
		{
		case 0:
			if (centerPainting.zCoord != otherPainting.zCoord + 1) break;
			return true;
		case 1:
			if (centerPainting.xCoord != otherPainting.xCoord - 1) break;
			return true;
		case 2:
			if (centerPainting.zCoord != otherPainting.zCoord - 1) break;
			return true;
		case 3:
			if (centerPainting.xCoord != otherPainting.xCoord + 1) break;
			return true;
		}
		return false;
	}
	
	private boolean checkRightSidePainting(TileEntityPainting centerPainting, TileEntityPainting otherPainting)
	{
		switch (centerPainting.getAngle())
		{
		case 0:
			if (centerPainting.zCoord != otherPainting.zCoord - 1) break;
			return true;
		case 1:
			if (centerPainting.xCoord != otherPainting.xCoord + 1) break;
			return true;
		case 2:
			if (centerPainting.zCoord != otherPainting.zCoord + 1) break;
			return true;
		case 3:
			if (centerPainting.xCoord != otherPainting.xCoord - 1) break;
			return true;
		}
		return false;
	}
	
	private boolean rotatePaintingCanvas(TileEntityPainting painting)
	{
		if (painting.getPaintingRotation() < 3)
		{
			painting.setPaintingRotation(painting.getPaintingRotation() + 1);
		}
		else
		{
			painting.setPaintingRotation(0);
		}
		return true;
	}
	
	private boolean rotateTypewriter(TileEntityTypewriter tile)
	{
		if (tile.getAngle() < 3)
		{
			tile.setAngle(tile.getAngle() + 1);
		}
		else
		{
			tile.setAngle(0);
		}
		return true;
	}
	
	private boolean connectTwoChests(World world, TileEntity tile, EntityPlayer player)
	{
		if ((tile1 == null) || (!(tile1 instanceof TileEntityFramedChest)))
		{
			tile1 = tile;
			sendPacketToClient(StatCollector.translateToLocal("screwgun.selected.chest1"), (EntityPlayerMP)player);
			return true;
		}
		if ((tile2 == null) || (!(tile2 instanceof TileEntityFramedChest)))
		{
			tile2 = tile;
			sendPacketToClient(StatCollector.translateToLocal("screwgun.selected.chest2"), (EntityPlayerMP)player);
		}
		if ((tile1 != null) && ((tile1 instanceof TileEntityFramedChest)) && (tile2 != null) && ((tile2 instanceof TileEntityFramedChest)))
		{
			TileEntityFramedChest chest1 = (TileEntityFramedChest)tile1;
			TileEntityFramedChest chest2 = (TileEntityFramedChest)tile2;
			
			if (chest1.getAngle() == chest2.getAngle())
			{
				int test = isValidChestConnect(chest1.getAngle(), tile1.xCoord, tile1.yCoord, tile1.zCoord, tile2.xCoord, tile2.yCoord, tile2.zCoord);
				if (test == 1)
				{
					if ((chest1.getIsDouble()) && (chest1.getIsLeft()) && (chest2.getIsDouble()) && (!chest2.getIsLeft()))
					{
						chest1.setIsDouble(false, false, null);
						chest2.setIsDouble(false, false, null);
					}
					else if ((!chest1.getIsDouble()) && (!chest2.getIsDouble()))
					{
						chest1.setIsDouble(true, true, chest2);
						chest2.setIsDouble(true, false, chest1);
					}
					else
					{
						sendPacketToClient(StatCollector.translateToLocal("drill.failed"), (EntityPlayerMP)player);
					}
				}
				else if (test == 2)
				{
					if ((chest1.getIsDouble()) && (!chest1.getIsLeft()) && (chest2.getIsDouble()) && (chest2.getIsLeft()))
					{
						chest1.setIsDouble(false, false, null);
						chest2.setIsDouble(false, false, null);
					}
					else if ((!chest1.getIsDouble()) && (!chest2.getIsDouble()))
					{
						chest1.setIsDouble(true, false, chest2);
						chest2.setIsDouble(true, true, chest1);
					}
					else
					{
						sendPacketToClient(StatCollector.translateToLocal("drill.failed"), (EntityPlayerMP)player);
					}     
				}
				else
				{
					sendPacketToClient(StatCollector.translateToLocal("drill.failed"), (EntityPlayerMP)player);
				}
			}
			else
			{
				sendPacketToClient(StatCollector.translateToLocal("drill.failed"), (EntityPlayerMP)player);
			}
			tile1 = null;
			tile2 = null;
			return true;
		}
		return false;
	}
	
	private int isValidChestConnect(int angle, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		if (y1 == y2)
		{
			switch (angle)
			{
			case 0: 
				if ((x1 == x2) && (z1 == z2 + 1))
				{
					return 2;
				}
				if ((x1 == x2) && (z1 == z2 - 1))
				{
					return 1;
				}
			case 1: 
				if ((z1 == z2) && (x1 == x2 - 1))
				{
					return 2;
				}
				if ((z1 == z2) && (x1 == x2 + 1))
				{
					return 1;
				}
			case 2: 
				if ((x1 == x2) && (z1 == z2 - 1))
				{
					return 2;
				}
				if ((x1 == x2) && (z1 == z2 + 1))
				{
					return 1;
				}
			case 3: 
				if ((z1 == z2) && (x1 == x2 + 1))
				{
					return 2;
				}
				if ((z1 == z2) && (x1 == x2 - 1))
				{
					return 1;
				}
				break;
			}
		}
		return 0;
	}
	
	private boolean rotateFramedChest(TileEntityFramedChest chest)
	{
		if (!chest.getIsDouble())
		{
			int currAngle = chest.getAngle();
			if (currAngle >= 3)
			{
				chest.setAngle(0);
			}
			else
			{
				chest.setAngle(currAngle + 1);
			}
		}
		return true;
	}
}