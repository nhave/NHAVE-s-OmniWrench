package com.nhave.nhwrench.common.handlers;

import java.util.ArrayList;
import java.util.List;

import com.nhave.nhwrench.api.API;
import com.nhave.nhwrench.common.core.NHWrench;
import com.nhave.nhwrench.common.core.Reference;
import com.nhave.nhwrench.common.handlers.wrench.UtilsHandler;
import com.nhave.nhwrench.common.helpers.CustomDismantleHelper;
import com.nhave.nhwrench.common.items.ItemComponent;
import com.nhave.nhwrench.common.items.ItemLumar;
import com.nhave.nhwrench.common.items.ItemMemCard;
import com.nhave.nhwrench.common.items.ItemOmniWrench;
import com.nhave.nhwrench.common.items.ItemShader;
import com.nhave.nhwrench.common.items.ItemShaderMultiTexture;
import com.nhave.nhwrench.common.items.ItemShaderPack;
import com.nhave.nhwrench.common.misc.Colors;
import com.nhave.nhwrench.common.modes.ModeMain;
import com.nhave.nhwrench.common.modes.ModeUtil;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.oredict.OreDictionary;

public class ItemHandler
{
	public static Item itemWrench;
	public static Item itemLumar;
	public static Item itemComp;
	public static Item itemShaderPack;
	public static Item itemMemCard;
	public static Item itemMemCardADV;
	
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
	public static Item itemShaderMillenium;
	public static Item itemShaderGold;
	public static Item itemShaderVibrantRed;
	public static Item itemShaderVibrantGreen;
	public static Item itemShaderVibrantBlue;
	public static Item itemShaderBound;
	public static Item itemShaderNHAVE;
	public static Item itemShaderFuture;
	public static Item itemShaderNVIDIA;
	public static Item itemShaderTemple;
	public static Item itemShaderSteam;
	public static Item itemShaderTeam;
	public static Item itemShaderBugFix;
	public static Item itemShaderScorpion;
	public static Item itemShaderHighEnd;
	public static Item itemShaderMario;
	public static Item itemShaderLuigi;

	public static Item _TabIconItems;
	public static Item _TabIconShaders;
	
	public static List<ItemStack> commonShaders = new ArrayList<ItemStack>();
	public static List<ItemStack> rareShaders = new ArrayList<ItemStack>();
	public static List<ItemStack> legendaryShaders = new ArrayList<ItemStack>();
	
	public static List<Item> allShaders = new ArrayList<Item>();
	
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
		itemWrench = new ItemOmniWrench().setCreativeTab(NHWrench.creativeTabItems).setFull3D().setMaxStackSize(1).setUnlocalizedName("nhwrench.itemWrench");
		itemLumar = new ItemLumar().setCreativeTab(NHWrench.creativeTabItems).setUnlocalizedName("nhwrench.itemLumar");
		itemComp = new ItemComponent().setCreativeTab(NHWrench.creativeTabItems).setUnlocalizedName("nhwrench.itemComp");
		itemShaderPack = new ItemShaderPack().setCreativeTab(NHWrench.creativeTabItems).setUnlocalizedName("nhwrench.itemShaderPack");
		itemMemCard = new ItemMemCard(false).setTextureName("nhwrench:MemCard").setCreativeTab(NHWrench.creativeTabItems).setUnlocalizedName("nhwrench.itemMemCard");
		itemMemCardADV = new ItemMemCard(true).setTextureName("nhwrench:MemCardADV").setCreativeTab(NHWrench.creativeTabItems).setUnlocalizedName("nhwrench.itemMemCardADV");
		
		itemShaderBasicWhite = new ItemShader("White").setShaderColor(Colors.white).setBaseName("nhwrench:Shaders/White_0").setOverlayName("nhwrench:Shaders/Basic_1").setSupportsColor();
		itemShaderBasicBlack = new ItemShader("Black").setShaderColor(Colors.black).setBaseName("nhwrench:Shaders/Black_0").setOverlayName("nhwrench:Shaders/Basic_1").setSupportsColor();
		itemShaderDalek = new ItemShader("Dalek").setShaderColor(Colors.orange).setTextureName("nhwrench:Shaders/Dalek").setSupportsColor().setRarity(3);
		itemShaderCofh = new ItemShader("Cofh").setShaderColor(Colors.lightBlue).setTextureName("nhwrench:Shaders/Cofh").setSupportsColor().setRarity(1);
		itemShaderNyan = new ItemShader("Nyan").setShaderColor(Colors.pink).setTextureName("nhwrench:Shaders/Nyan").setRarity(2);
		itemShaderDanish = new ItemShader("Danish").setShaderColor(Colors.red).setBaseName("nhwrench:Shaders/Danish_0").setOverlayName("nhwrench:Blank").setRarity(1);
		itemShaderBotany = new ItemShader("Botany").setShaderColor(Colors.green).setBaseName("nhwrench:Shaders/Botany_0").setOverlayName("nhwrench:Blank").setRarity(3);
		itemShaderTaint = new ItemShader("Taint").setShaderColor(Colors.purple).setBaseName("nhwrench:Shaders/Taint_0").setOverlayName("nhwrench:Blank").setRarity(3);
		itemShaderAeonic = new ItemShader("Aeonic").setShaderColor(Colors.lightBlue).setBaseName("nhwrench:Shaders/Aeonic_0").setOverlayName("nhwrench:Blank").setRarity(2);
		itemShaderPrototype = new ItemShaderMultiTexture("Prototype").setShaderColor(Colors.lime).setTextureName("nhwrench:Shaders/Prototype").setRarity(3);
		itemShaderMillenium = new ItemShader("Millenium").setShaderColor(Colors.lightGray).setBaseName("nhwrench:Shaders/Millenium_0").setOverlayName("nhwrench:Blank").setRarity(3);
		itemShaderGold = new ItemShader("Gold").setShaderColor(Colors.orange).setBaseName("nhwrench:Shaders/Gold_0").setOverlayName("nhwrench:Blank").setRarity(2);
		itemShaderVibrantRed = new ItemShader("VibrantRed").setShaderColor(Colors.red).setBaseName("nhwrench:Shaders/VibrantRed_0").setOverlayName("nhwrench:Shaders/Basic_1").setSupportsColor();
		itemShaderVibrantGreen = new ItemShader("VibrantGreen").setShaderColor(Colors.green).setBaseName("nhwrench:Shaders/VibrantGreen_0").setOverlayName("nhwrench:Shaders/Basic_1").setSupportsColor();
		itemShaderVibrantBlue = new ItemShader("VibrantBlue").setShaderColor(Colors.blue).setBaseName("nhwrench:Shaders/VibrantBlue_0").setOverlayName("nhwrench:Shaders/Basic_1").setSupportsColor();
		itemShaderBound = new ItemShader("Bound").setArtist("Voxel_Friend").setShaderColor(Colors.yellow).setBaseName("nhwrench:Shaders/Bound_0").setOverlayName("nhwrench:Blank").setRarity(3);
		itemShaderNHAVE = new ItemShaderMultiTexture("NHAVE").setShaderColor(Colors.red).setTextureName("nhwrench:Shaders/NHAVE").setRarity(3);
		itemShaderFuture = new ItemShader("Future").setShaderColor(Colors.lightBlue).setBaseName("nhwrench:Shaders/Future_0").setOverlayName("nhwrench:Blank").setRarity(2);
		itemShaderNVIDIA = new ItemShader("NVIDIA").setShaderColor(Colors.lime).setBaseName("nhwrench:Shaders/NVIDIA_0").setOverlayName("nhwrench:Blank").setRarity(1);
		itemShaderTemple = new ItemShader("Temple").setShaderColor(4418111).setBaseName("nhwrench:Shaders/Temple_0").setOverlayName("nhwrench:Blank").setRarity(1);
		itemShaderSteam = new ItemShader("Steam").setShaderColor(Colors.yellow).setBaseName("nhwrench:Shaders/Steam_0").setOverlayName("nhwrench:Blank").setRarity(2);
		itemShaderTeam = new ItemShader("Team").setShaderColor(Colors.white).setTextureName("nhwrench:Shaders/Team").setSupportsColor().setRarity(1);
		itemShaderBugFix = new ItemShader("BugFix").setShaderColor(Colors.yellow).setBaseName("nhwrench:Shaders/BugFix_0").setOverlayName("nhwrench:Blank").setRarity(1);
		itemShaderScorpion = new ItemShader("Scorpion").setShaderColor(Colors.black).setTextureName("nhwrench:Shaders/Scorpion").setRarity(2);
		itemShaderHighEnd = new ItemShader("HighEnd").setShaderColor(Colors.gray).setBaseName("nhwrench:Shaders/HighEnd_0").setOverlayName("nhwrench:Blank").setRarity(1);
		itemShaderMario = new ItemShader("Mario").setShaderColor(Colors.red).setBaseName("nhwrench:Shaders/Mario_0").setOverlayName("nhwrench:Blank").setRarity(3);
		itemShaderLuigi = new ItemShader("Luigi").setShaderColor(Colors.green).setBaseName("nhwrench:Shaders/Luigi_0").setOverlayName("nhwrench:Blank").setRarity(3);
		
		_TabIconItems = new Item().setCreativeTab(null).setTextureName("nhwrench:TabIconItems").setUnlocalizedName("nhwrench.tabicon.items");
		_TabIconShaders = new Item().setCreativeTab(null).setTextureName("nhwrench:TabIconShaders").setUnlocalizedName("nhwrench.tabicon.shaders");
		
		registerItem(itemWrench);
		registerItem(itemLumar);
		registerItem(itemComp);
		registerItem(itemShaderPack);
		registerItem(itemMemCard);
		registerItem(itemMemCardADV);
		
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
		registerItem(itemShaderMillenium);
		registerItem(itemShaderGold);
		registerShader(itemShaderVibrantRed, commonShaders);
		registerShader(itemShaderVibrantGreen, commonShaders);
		registerShader(itemShaderVibrantBlue, commonShaders);
		registerItem(itemShaderBound);
		registerItem(itemShaderNHAVE);
		registerShader(itemShaderFuture, legendaryShaders);
		registerShader(itemShaderNVIDIA, rareShaders);
		registerShader(itemShaderTemple, rareShaders);
		registerShader(itemShaderSteam, legendaryShaders);
		registerItem(itemShaderTeam);
		registerItem(itemShaderBugFix);
		registerShader(itemShaderScorpion, legendaryShaders);
		registerShader(itemShaderHighEnd, rareShaders);
		registerItem(itemShaderMario);
		registerItem(itemShaderLuigi);
		
		registerItem(_TabIconItems);
		registerItem(_TabIconShaders);
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
			ItemStack item = new ItemStack(itemLumar, 1, i);
			OreDictionary.registerOre(oreDict[i], item);
			OreDictionary.registerOre("listAlllumar", item);
		}
		
		if (!allShaders.isEmpty())
		{
			for (Item item: allShaders)
			{
				OreDictionary.registerOre("listAllshaders", item);
			}
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
		API.integrationRegister.registerHandler(new UtilsHandler());
	}
	
	private static void setCustomDismantle()
	{
		for (int i = 0; i < ConfigHandler.allowedDismantle.length; ++i)
		{
			CustomDismantleHelper.addBlockNoMeta(ConfigHandler.allowedDismantle[i], false);
		}
	}
}