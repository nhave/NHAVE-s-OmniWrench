package com.nhave.nhwrench.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * NHAVE's Omniwrench
 * 
 * @author nhave
 */
public interface IPostUse
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
     * @param world
     *            The world the player is in.
     * @param x
     *            X Cord.
     * @param y
     *            Y Cord.
     * @param z
     *            Z Cord.
     * @param side
     *            The Side of the block.
     */
	public boolean handlePostUse(String mode, ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ);
}