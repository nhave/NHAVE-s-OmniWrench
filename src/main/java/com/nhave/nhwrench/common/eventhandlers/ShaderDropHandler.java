package com.nhave.nhwrench.common.eventhandlers;

import java.util.Random;

import com.nhave.nhwrench.common.handlers.ItemHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class ShaderDropHandler
{
	@SubscribeEvent
	public void handleLivingDropsEvent(LivingDropsEvent evt)
	{
		if (evt.entityLiving != null)
		{
			if (evt.entityLiving instanceof IBossDisplayData)
			{
				evt.entityLiving.entityDropItem(new ItemStack(ItemHandler.itemShaderPack, 1, 2), 0.0F);
			}
			else if ((evt.entityLiving.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD || evt.entityLiving.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) && new Random().nextFloat() < 0.2F)
			{
				ItemStack shader = new ItemStack(ItemHandler.itemShaderPack, 1, 1);
				if (new Random().nextFloat() < 0.1F) shader = new ItemStack(ItemHandler.itemShaderPack, 1, 2);
				evt.entityLiving.entityDropItem(shader, 0.0F);
			}
		}
	}
}