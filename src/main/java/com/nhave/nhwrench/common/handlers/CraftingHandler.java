package com.nhave.nhwrench.common.handlers;

import com.nhave.nhlib.helpers.NBTHelper;
import com.nhave.nhlib.shaders.ShaderManager;
import com.nhave.nhwrench.common.items.ItemLumar;
import com.nhave.nhwrench.common.items.ItemOmniWrench;
import com.nhave.nhwrench.common.misc.Colors;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CraftingHandler
{
	public static int[] colorCodes = new int[] {1644825, 16711680, 65280, 6704179, 255, 11685080, 5013401, 10066329, 6710886, 15892389, 8388371, 15059968, 6730495, 15027416, 16757299, 16777215};
	public static String[] oreDict = new String[] {"dyeBlack", "dyeRed",  "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"};
	
	public static void postInit()
	{
		addRecipes();
	}
	
	public static void addRecipes()
	{
		//Lumar recipe
		for (int i = 0; i < oreDict.length; ++i)
		{
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemHandler.itemLumar, 2, i),
				Items.glowstone_dust,
				Items.glowstone_dust,
				oreDict[i],
				Items.redstone));
		}
		//Wrench recipe
		GameRegistry.addRecipe(NBTHelper.setStackInteger(new ItemStack(ItemHandler.itemWrench), "WRENCH", "COLOR", 16777215),
			new Object[] {" X ", " YX", "Z  ",
			'X', new ItemStack(ItemHandler.itemComp,1,0),
			'Y', new ItemStack(ItemHandler.itemComp,1,1),
			'Z', new ItemStack(ItemHandler.itemComp,1,2)});
		if (!ConfigHandler.disableRecipes)
		{
			//Claw recipe
			GameRegistry.addRecipe(new ItemStack(ItemHandler.itemComp, 1, 0),
				new Object[] {" XY", "XZY", "XY ",
				'X', Items.iron_ingot,
				'Y', Blocks.obsidian,
				'Z', Items.redstone});
			//Piston recipe
			GameRegistry.addRecipe(new ItemStack(ItemHandler.itemComp, 1, 1),
				new Object[] {"YXY", "ZAZ", "YXY",
				'X', Items.iron_ingot,
				'Y', Blocks.piston,
				'Z', Items.quartz,
				'A', Items.emerald});
			//Handle recipe
			GameRegistry.addRecipe(new ItemStack(ItemHandler.itemComp, 1, 2),
				new Object[] {" XY", "XZX", "YX ",
				'X', Items.iron_ingot,
				'Y', Items.quartz,
				'Z', Items.redstone});
		}
		//Common ShaderPack
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemHandler.itemShaderPack, 1, 0),
			new Object[] {"XYX", "YZY", "XYX",
			'X', Items.iron_ingot,
			'Y', "lumarWhite",
			'Z', Items.ender_pearl}));
		if (ConfigHandler.easyModeShaders)
		{
			//Rare ShaderPack
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemHandler.itemShaderPack, 1, 1),
				new Object[] {"XYX", "YZY", "XYX",
				'X', Items.gold_ingot,
				'Y', "lumarLightBlue",
				'Z', Items.ender_pearl}));
			//Legendary ShaderPack
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemHandler.itemShaderPack, 1, 2),
				new Object[] {"XYX", "YZY", "XYX",
				'X', Items.diamond,
				'Y', "lumarPurple",
				'Z', Items.ender_pearl}));
		}
	}
	
	public static ItemStack getStackFromOreDict(String modid, String ore)
	{
		ItemStack output = GameRegistry.findItemStack(modid, ore, 1);
		if (output == null && !OreDictionary.getOres(ore).isEmpty()) output = OreDictionary.getOres(ore).get(0);
		return output;
	}
	
	@SubscribeEvent
	public void handleAnvilUpdateEvent(AnvilUpdateEvent evt)
	{
		if (evt.left == null || evt.right == null)
		{
			return;
		}
		if (evt.left.getItem() instanceof ItemOmniWrench && evt.right.getItem() instanceof ItemLumar)
		{
			ItemStack wrench = evt.left.copy();
			int meta = evt.right.getItemDamage();
			if (meta < 0 || meta > 15) meta = 0;
			int color = Colors.colorCodes[meta];
			if (!ShaderManager.getBooleanTag(wrench, "SUPPORTS_COLOR", true)) return;
			if (NBTHelper.getInteger(wrench, "WRENCH", "COLOR") != color)
			{
				NBTHelper.setInteger(wrench, "WRENCH", "COLOR", color);
				evt.cost=2;
				evt.materialCost=1;
				evt.output=wrench;
			}
		}
		else if (evt.left.getItem() instanceof ItemShaderPack && evt.left.stackSize == 1 && evt.right.getItem() == Items.gold_nugget)
		{
			if (evt.left.getItemDamage() == 0) return;
			if (evt.name.equals("FriendOfTheDoctor"))
			{
				evt.cost=2;
				evt.materialCost=1;
				evt.output=new ItemStack(ItemHandler.itemShaderDalek);
			}
			else if (evt.name.equals("FlowerPower"))
			{
				evt.cost=2;
				evt.materialCost=1;
				evt.output=new ItemStack(ItemHandler.itemShaderBotany);
			}
			else if (evt.name.equals("TaintedLands"))
			{
				evt.cost=2;
				evt.materialCost=1;
				evt.output=new ItemStack(ItemHandler.itemShaderTaint);
			}
			else if (evt.name.equals("TEST:108.A"))
			{
				evt.cost=2;
				evt.materialCost=1;
				evt.output=new ItemStack(ItemHandler.itemShaderPrototype);
			}
			else if (evt.name.equals("LetsKickSomeAstroid"))
			{
				evt.cost=2;
				evt.materialCost=1;
				evt.output=new ItemStack(ItemHandler.itemShaderMillenium);
			}
		}
	}
}