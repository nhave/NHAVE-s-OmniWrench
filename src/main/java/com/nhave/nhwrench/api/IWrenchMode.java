package com.nhave.nhwrench.api;

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
}