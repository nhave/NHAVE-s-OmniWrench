package com.nhave.nhwrench.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * NHAVE's Omniwrench
 * 
 * @author nhave
 */
public interface IEntityInteraction
{
	/**
     * Called when the wrench is being used.
     * 
     * @param mode
     *            The Wrench Mode.
     * @param itemStack
     *            The Wrench being used.
     * @param player
     *            The Player Using the Wrench.
     * @param entity
     *            The Entity interacted with.
     */
	public boolean handleEntity(String mode, ItemStack itemStack, EntityPlayer player, EntityLivingBase entity);
}