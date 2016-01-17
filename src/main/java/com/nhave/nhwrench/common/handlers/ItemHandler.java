package com.nhave.nhwrench.common.handlers;

import java.util.ArrayList;
import java.util.List;

import com.nhave.nhwrench.api.WrenchRegistry;
import com.nhave.nhwrench.common.core.NHWrench;
import com.nhave.nhwrench.common.core.Reference;
import com.nhave.nhwrench.common.handlers.wrench.UtilsHandler;
import com.nhave.nhwrench.common.helpers.CustomDismantleHelper;
import com.nhave.nhwrench.common.items.ItemComponent;
import com.nhave.nhwrench.common.items.ItemLumar;
import com.nhave.nhwrench.common.items.ItemOmniWrench;
import com.nhave.nhwrench.common.items.ItemShader;
import com.nhave.nhwrench.common.misc.Colors;
import com.nhave.nhwrench.common.modes.ModeMain;
import com.nhave.nhwrench.common.modes.ModeUtil;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemHandler
{
	public static Item itemWrench;
	public static Item itemLumar;
	public static Item itemComp;
	public static Item itemShaderPack;
	public static Item itemShaderBasicWhite;
	public static Item itemShaderBasicBlack;
	public static Item itemShaderDalek;
	public static Item itemShaderCofh;
	public static Item itemShaderNyan;
	public static Item itemShaderDanish;
	public static Item itemShaderBotany;
	public static Item itemShaderTaint;
	public static Item itemShaderAeonic;
	public static Item itemShaderPrototype;

	public static List<ItemStack> commonShaders = new ArrayList<ItemStack>();
	public static List<ItemStack> rareShaders = new ArrayList<ItemStack>();
	public static List<ItemStack> legendaryShaders = new ArrayList<ItemStack>();
	
	public static ModeMain modeWrench;
	public static ModeMain modeRotate;
	public static ModeMain modeTune;
	public static ModeMain modeNone;
	public static ModeUtil modeUtil;
	
	public static void preInit()
	{
		registerItems();
		addModes();
		addHandlers();
	}
	
	public static void postInit()
	{
		setCustomDismantle();
		registerOres();
	}
	
	public static void registerItems()
	{
		itemWrench = new ItemOmniWrench().setCreativeTab(NHWrench.creativeTab).setFull3D().setMaxStackSize(1).setUnlocalizedName("nhwrench.itemWrench");
		itemLumar = new ItemLumar().setCreativeTab(NHWrench.creativeTab).setUnlocalizedName("nhwrench.itemLumar");
		itemComp = new ItemComponent().setCreativeTab(NHWrench.creativeTab).setUnlocalizedName("nhwrench.itemComp");
		itemShaderPack = new ItemShaderPack().setCreativeTab(NHWrench.creativeTab).setUnlocalizedName("nhwrench.itemShaderPack");
		itemShaderBasicWhite = new ItemShader("White").setShaderColor(Colors.white).setBaseName("nhwrench:Shaders/White_0").setOverlayName("nhwrench:Shaders/Basic_1").setSupportsColor().setCreativeTab(NHWrench.creativeTab);
		itemShaderBasicBlack = new ItemShader("Black").setShaderColor(Colors.black).setBaseName("nhwrench:Shaders/Black_0").setOverlayName("nhwrench:Shaders/Basic_1").setSupportsColor().setCreativeTab(NHWrench.creativeTab);
		itemShaderDalek = new ItemShader("Dalek").setShaderColor(Colors.orange).setTextureName("nhwrench:Shaders/Dalek").setSupportsColor().setRarity(3).setCreativeTab(NHWrench.creativeTab);
		itemShaderCofh = new ItemShader("Cofh").setShaderColor(Colors.lightBlue).setTextureName("nhwrench:Shaders/Cofh").setSupportsColor().setRarity(1).setCreativeTab(NHWrench.creativeTab);
		itemShaderNyan = new ItemShader("Nyan").setShaderColor(Colors.pink).setTextureName("nhwrench:Shaders/Nyan").setRarity(2).setCreativeTab(NHWrench.creativeTab);
		itemShaderDanish = new ItemShader("Danish").setShaderColor(Colors.red).setTextureName("nhwrench:Shaders/Danish").setRarity(1).setCreativeTab(NHWrench.creativeTab);
		itemShaderBotany = new ItemShader("Botany").setShaderColor(Colors.green).setBaseName("nhwrench:Shaders/Botany_0").setOverlayName("nhwrench:Blank").setRarity(3).setCreativeTab(NHWrench.creativeTab);
		itemShaderTaint = new ItemShader("Taint").setShaderColor(Colors.purple).setBaseName("nhwrench:Shaders/Taint_0").setOverlayName("nhwrench:Blank").setRarity(3).setCreativeTab(NHWrench.creativeTab);
		itemShaderAeonic = new ItemShader("Aeonic").setShaderColor(Colors.lightBlue).setBaseName("nhwrench:Shaders/Aeonic_0").setOverlayName("nhwrench:Blank").setRarity(2).setCreativeTab(NHWrench.creativeTab);
		itemShaderPrototype = new ItemShader("Prototype").setShaderColor(Colors.lime).setTextureName("nhwrench:Shaders/Prototype").setRarity(3).setCreativeTab(NHWrench.creativeTab);
		
		registerItem(itemWrench);
		registerItem(itemLumar);
		registerItem(itemComp);
		registerItem(itemShaderPack);
		registerShader(itemShaderBasicWhite, commonShaders);
		registerShader(itemShaderBasicBlack, commonShaders);
		registerItem(itemShaderDalek);
		registerShader(itemShaderCofh, rareShaders);
		registerShader(itemShaderNyan, legendaryShaders);
		registerShader(itemShaderDanish, rareShaders);
		registerItem(itemShaderBotany);
		registerItem(itemShaderTaint);
		registerShader(itemShaderAeonic, legendaryShaders);
		registerItem(itemShaderPrototype);
	}
	
	private static void registerItem(Item item)
	{
		GameRegistry.registerItem(item, item.getUnlocalizedName(), Reference.MODID);
	}
	
	private static void registerShader(Item item, List<ItemStack> list)
	{
		GameRegistry.registerItem(item, item.getUnlocalizedName(), Reference.MODID);
		list.add(new ItemStack(item, 1, 0));
	}
	
	private static void registerOres()
	{
		String[] oreDict = new String[] {"lumarBlack", "lumarRed",  "lumarGreen", "lumarBrown", "lumarBlue", "lumarPurple", "lumarCyan", "lumarLightGray", "lumarGray", "lumarPink", "lumarLime", "lumarYellow", "lumarLightBlue", "lumarMagenta", "lumarOrange", "lumarWhite"};
		
		for (int i = 0; i < oreDict.length; ++i)
		{
			OreDictionary.registerOre(oreDict[i], new ItemStack(itemLumar, 1, i));
		}
	}
	
	private static void addModes()
	{
		modeWrench = new ModeMain("wrmode.wrench");
		modeRotate = new ModeMain("wrmode.rotate");
		modeTune = new ModeMain("wrmode.tune");
		modeNone = new ModeMain("wrmode.none");
		modeUtil = new ModeUtil("wrmode.util");
	}
	
	private static void addHandlers()
	{
		WrenchRegistry.registerHandler(new UtilsHandler());
	}
	
	private static void setCustomDismantle()
	{
		for (int i = 0; i < ConfigHandler.allowedDismantle.length; ++i)
		{
			CustomDismantleHelper.addBlockNoMeta(ConfigHandler.allowedDismantle[i], false);
		}
	}
}