package com.nhave.nhwrench.common.items;

import java.util.List;

import com.nhave.nhwrench.common.handlers.ConfigHandler;

import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import cpw.mods.fml.common.Optional.Method;
import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

@InterfaceList({
	@Interface(iface = "cofh.api.energy.IEnergyContainerItem", modid = "CoFHCore"),
	@Interface(iface = "ic2.api.item.ISpecialElectricItem", modid = "IC2")
})
public class ItemElectricBase extends Item implements ISpecialElectricItem, IEnergyContainerItem, IElectricItemManager
{
	/** The maximum amount of energy this item can hold. */
	public double MAX_ELECTRICITY;
	
	public ItemElectricBase(double maxElectricity)
	{
		super();
		MAX_ELECTRICITY = maxElectricity;
		setMaxStackSize(1);
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return ConfigHandler.usePower;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return Math.max(1.0 - (double)getEnergyStored(stack) / (double)getMaxEnergyStored(stack), 0);
	}

	/* =========================================================== Power Code ===============================================================*/
	
	public ItemStack getUnchargedItem()
	{
		ItemStack stack = new ItemStack(this);
		return stack;
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		ItemStack discharged = new ItemStack(this);
		list.add(discharged);
		ItemStack charged = new ItemStack(this);
		setEnergy(charged, ((ItemElectricBase)charged.getItem()).getMaxEnergy(charged));
		list.add(charged);
	}
	
	public double getEnergy(ItemStack itemStack)
	{
		if(itemStack.stackTagCompound == null)
		{
			return 0;
		}
	
		double electricityStored = itemStack.stackTagCompound.getDouble("electricity");
	
		return electricityStored;
	}
	
	public void setEnergy(ItemStack itemStack, double amount)
	{
		if(itemStack.stackTagCompound == null)
		{
			itemStack.setTagCompound(new NBTTagCompound());
		}
	
		double electricityStored = Math.max(Math.min(amount, getMaxEnergy(itemStack)), 0);
		itemStack.stackTagCompound.setDouble("electricity", electricityStored);
	}
	
	public double getMaxEnergy(ItemStack itemStack)
	{
		return MAX_ELECTRICITY;
	}
	
	public double getMaxTransfer(ItemStack itemStack)
	{
		return getMaxEnergy(itemStack)*0.005;
	}
	
	public boolean canReceive(ItemStack itemStack)
	{
		return getMaxEnergy(itemStack)-getEnergy(itemStack) > 0;
	}
	
	public boolean canSend(ItemStack itemStack)
	{
		return getEnergy(itemStack) > 0;
	}
	
	/* =========================================================== IEnergyContainerItem ===============================================================*/
	
	@Override
	public int receiveEnergy(ItemStack theItem, int energy, boolean simulate)
	{
		if(canReceive(theItem))
		{
			double energyNeeded = getMaxEnergy(theItem)-getEnergy(theItem);
			double toReceive = Math.min(energy* ConfigHandler.FROM_TE, energyNeeded);
	
			if(!simulate)
			{
				setEnergy(theItem, getEnergy(theItem) + toReceive);
			}
	
			return (int)Math.round(toReceive* ConfigHandler.TO_TE);
		}
	
		return 0;
	}
	
	@Override
	public int extractEnergy(ItemStack theItem, int energy, boolean simulate)
	{
		if(canSend(theItem))
		{
			double energyRemaining = getEnergy(theItem);
			double toSend = Math.min((energy* ConfigHandler.FROM_TE), energyRemaining);
	
			if(!simulate)
			{
				setEnergy(theItem, getEnergy(theItem) - toSend);
			}
	
			return (int)Math.round(toSend* ConfigHandler.TO_TE);
		}
	
		return 0;
	}
	
	@Override
	public int getEnergyStored(ItemStack theItem)
	{
		return (int)Math.round(getEnergy(theItem)* ConfigHandler.TO_TE);
	}
	
	@Override
	public int getMaxEnergyStored(ItemStack theItem)
	{
		return (int)Math.round(getMaxEnergy(theItem)* ConfigHandler.TO_TE);
	}

	/* =========================================================== ISpecialElectricItem ===============================================================*/
	
	@Override
	@Method(modid = "IC2")
	public boolean canProvideEnergy(ItemStack itemStack)
	{
		return canSend(itemStack);
	}
	
	@Override
	@Method(modid = "IC2")
	public Item getChargedItem(ItemStack itemStack)
	{
		return this;
	}
	
	@Override
	@Method(modid = "IC2")
	public Item getEmptyItem(ItemStack itemStack)
	{
		return this;
	}
	
	@Override
	@Method(modid = "IC2")
	public double getMaxCharge(ItemStack itemStack)
	{
		return 0;
	}
	
	@Override
	@Method(modid = "IC2")
	public int getTier(ItemStack itemStack)
	{
		return 4;
	}
	
	@Override
	@Method(modid = "IC2")
	public double getTransferLimit(ItemStack itemStack)
	{
		return 0;
	}
	
	@Override
	@Method(modid = "IC2")
	public IElectricItemManager getManager(ItemStack itemStack)
	{
		return this;
	}

	/* =========================================================== IElectricItemManager ===============================================================*/
	
	@Override
	public double charge(ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate)
	{
		if(canReceive(itemStack))
		{
			double energyNeeded = getMaxEnergy(itemStack)-getEnergy(itemStack);
			double energyToStore = Math.min(Math.min(amount* ConfigHandler.FROM_IC2, getMaxEnergy(itemStack)*0.01), energyNeeded);

			if(!simulate)
			{
				setEnergy(itemStack, getEnergy(itemStack) + energyToStore);
			}

			return (int)Math.round(energyToStore* ConfigHandler.TO_IC2);
		}

		return 0;
	}

	@Override
	public double discharge(ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean external, boolean simulate)
	{
		if(canSend(itemStack))
		{
			double energyWanted = amount* ConfigHandler.FROM_IC2;
			double energyToGive = Math.min(Math.min(energyWanted, getMaxEnergy(itemStack)*0.01), getEnergy(itemStack));

			if(!simulate)
			{
				setEnergy(itemStack, getEnergy(itemStack) - energyToGive);
			}

			return (int)Math.round(energyToGive* ConfigHandler.TO_IC2);
		}

		return 0;
	}

	@Override
	public boolean canUse(ItemStack itemStack, double amount)
	{
		return getEnergy(itemStack) >= amount* ConfigHandler.FROM_IC2;
	}

	@Override
	public double getCharge(ItemStack itemStack)
	{
		return (int)Math.round(getEnergy(itemStack)* ConfigHandler.TO_IC2);
	}

	@Override
	public boolean use(ItemStack itemStack, double amount, EntityLivingBase entity)
	{
		return false;
	}

	@Override
	public void chargeFromArmor(ItemStack itemStack, EntityLivingBase entity)
	{

	}

	@Override
	public String getToolTip(ItemStack itemStack)
	{
		return null;
	}
}