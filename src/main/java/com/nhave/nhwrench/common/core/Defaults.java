package com.nhave.nhwrench.common.core;

public class Defaults
{
    public static final boolean logging = false;
    public static final boolean enableUtilMode = true;
    public static final boolean enableHoe = true;
    public static final boolean enableShears = true;
    public static final boolean disableRecipes = false;
    public static final boolean cleanConfig = false;
    public static final boolean easyModeShaders = false;
    public static final boolean disableMobDrops = false;
    public static final int shaderDropChance = 20;
    public static final int legendaryChance = 10;
    public static final boolean exoticFromLegendary = false;
    public static final boolean disableMemCards = false;
    public static final boolean usePower = false;
    public static final int maxPower = 80000;
    public static final int powerUsage = 200;
    public static final boolean disableForgeDirection = false;
    
    public static String[] allowedDismantle = new String[] {"tile.hopper;-1"};
    public static String[] forgeDirBlacklist = new String[] {};
    
    public static final double FROM_IC2 = 10D;
    public static final double TO_IC2 = 0.1D;
    public static final double FROM_TE = 2.5D;
    public static final double TO_TE = 0.4D;
    public static String powerUnit = "RF";
    public static String[] powerUnits = new String[] {"RF", "EU", "J", "MJ"};
}