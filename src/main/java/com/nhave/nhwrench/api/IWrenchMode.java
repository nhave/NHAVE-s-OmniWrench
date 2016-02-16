package com.nhave.nhwrench.api;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * NHAVE's Omniwrench
 * 
 * @author nhave
 */
public interface IWrenchMode
{
	/**
     * The name of the Mode.
     */
	public String getName();
	
	/**
     * Called to see if the mode is active.
     * 
     * @param itemStack
     *            The Wrench being used.
     */
	public boolean isActive(ItemStack itemStack);

	
	/**
     * Called when the user toggles mode while sneaking.
     * 
     * @param entityPlayer
     *            The Player using the Wrench.
     * @param itemStack
     *            The Wrench being used.
     */
	public void onSubModeChange(EntityPlayer entityPlayer, ItemStack itemStack, boolean chat);
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag);
}