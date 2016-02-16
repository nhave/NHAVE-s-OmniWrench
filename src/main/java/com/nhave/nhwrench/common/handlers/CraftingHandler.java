package com.nhave.nhwrench.common.handlers;

import java.util.Map;
import java.util.TreeMap;

import com.nhave.nhlib.events.ToolStationUpdateEvent;
import com.nhave.nhlib.helpers.NBTHelper;
import com.nhave.nhlib.shaders.ShaderManager;
import com.nhave.nhwrench.common.items.ItemLumar;
import com.nhave.nhwrench.common.items.ItemOmniWrench;
import com.nhave.nhwrench.common.items.ItemShaderPack;
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
	private static final Map<String, ItemStack> _AnvilCrafting = new TreeMap<String, ItemStack>();
	
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
			'X', "nuggetGold",
			'Y', "lumarWhite",
			'Z', Items.redstone}));
		if (ConfigHandler.easyModeShaders)
		{
			//Rare ShaderPack
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemHandler.itemShaderPack, 1, 1),
				new Object[] {"XYX", "YZY", "XYX",
				'X', "nuggetGold",
				'Y', "lumarLightBlue",
				'Z', Items.diamond}));
			//Legendary ShaderPack
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemHandler.itemShaderPack, 1, 2),
				new Object[] {"XYX", "YZY", "XYX",
				'X', "nuggetGold",
				'Y', "lumarPurple",
				'Z', Items.ender_pearl}));
		}
		//Golden Shader
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemHandler.itemShaderGold),
			new Object[] {"XYX", "YZY", "XYX",
			'X', Items.gold_ingot,
			'Y', "lumarOrange",
			'Z', Blocks.gold_block}));
		//Exotic Shaders
		addAnvilNamedShader("FriendOfTheDoctor", new ItemStack(ItemHandler.itemShaderDalek));
		addAnvilNamedShader("FlowerPower", new ItemStack(ItemHandler.itemShaderBotany));
		addAnvilNamedShader("TaintedLands", new ItemStack(ItemHandler.itemShaderTaint));
		addAnvilNamedShader("TEST:108.A", new ItemStack(ItemHandler.itemShaderPrototype));
		addAnvilNamedShader("LetsKickSomeAstroid", new ItemStack(ItemHandler.itemShaderMillenium));
		addAnvilNamedShader("BoundByBlood", new ItemStack(ItemHandler.itemShaderBound));
		addAnvilNamedShader("OneToRuleThemAll", new ItemStack(ItemHandler.itemShaderNHAVE));
		//Memory Cards
		if (!ConfigHandler.disableMemCards)
		{
			//Basic
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemHandler.itemMemCard),
				new Object[] {"XXX", "YZY", "AAA",
				'X', "dyeGreen",
				'Y', Items.repeater,
				'Z', Items.redstone,
				'A', "nuggetGold"}));
			//Advanced
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemHandler.itemMemCardADV),
				new Object[] {"XXX", "YZY", "AAA",
				'X', "dyeRed",
				'Y', Items.comparator,
				'Z', ItemHandler.itemMemCard,
				'A', "nuggetGold"}));
		}
	}
	
	public static ItemStack getStackFromOreDict(String modid, String ore)
	{
		ItemStack output = GameRegistry.findItemStack(modid, ore, 1);
		if (output == null && !OreDictionary.getOres(ore).isEmpty()) output = OreDictionary.getOres(ore).get(0);
		return output;
	}
	
	public static void addAnvilNamedShader(String name, ItemStack stack)
	{
		if (_AnvilCrafting.containsKey(name)) return;
		_AnvilCrafting.put(name, stack);
	}
	
	@SubscribeEvent
	public void handleAnvilUpdateEvent(AnvilUpdateEvent evt)
	{
		if (evt.left == null || evt.right == null)
		{
			return;
		}
		/*if (evt.left.getItem() instanceof ItemOmniWrench && evt.right.getItem() instanceof ItemLumar)
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
		else */if (evt.left.getItem() instanceof ItemShaderPack && evt.left.stackSize == 1 && evt.right.getItem() == Items.gold_nugget)
		{
			if ((evt.left.getItemDamage() < 1) || (ConfigHandler.exoticFromLegendary && evt.left.getItemDamage() < 2)) return;
			if (_AnvilCrafting.containsKey(evt.name))
			{
				evt.cost=2;
				evt.materialCost=1;
				evt.output=_AnvilCrafting.get(evt.name).copy();
			}
		}
	}
	
	@SubscribeEvent
	public void handleToolStationUpdateEvent(ToolStationUpdateEvent evt)
	{
		if (evt.input == null || evt.mod == null)
		{
			return;
		}
		if (evt.input.getItem() instanceof ItemOmniWrench && evt.mod.getItem() instanceof ItemLumar)
		{
			ItemStack wrench = evt.input.copy();
			int meta = evt.mod.getItemDamage();
			if (meta < 0 || meta > 15) meta = 0;
			int color = Colors.colorCodes[meta];
			if (!ShaderManager.getBooleanTag(wrench, "SUPPORTS_COLOR", true)) return;
			if (NBTHelper.getInteger(wrench, "WRENCH", "COLOR") != color)
			{
				NBTHelper.setInteger(wrench, "WRENCH", "COLOR", color);
				evt.materialCost=0;
				evt.output=wrench;
			}
		}
	}
}