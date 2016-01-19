package pneumaticCraft.api.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract interface IPneumaticWrenchable
{
	public abstract boolean rotateBlock(World paramWorld, EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3, ForgeDirection paramForgeDirection);
}