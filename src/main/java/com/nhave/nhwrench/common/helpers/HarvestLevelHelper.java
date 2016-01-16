package com.nhave.nhwrench.common.helpers;

import java.util.HashMap;

public class HarvestLevelHelper
{
	/* 
	 * "wrmode.wrench"
	 * "wrmode.rotate"
	 * "wrmode.tune"
	 * "wrmode.none"
	 * "wrmode.util"
	 * */
	
	public static HashMap<String, Integer> toolClassesWrench = new HashMap();
	public static HashMap<String, Integer> toolClassesRotate = new HashMap();
	public static HashMap<String, Integer> toolClassesTune = new HashMap();
	public static HashMap<String, Integer> toolClassesUtil = new HashMap();
	
	public static void setHarvestLevelWrench(String toolClass, int level)
	{
		if (level < 0)
			toolClassesWrench.remove(toolClass);
		else
			toolClassesWrench.put(toolClass, Integer.valueOf(level));
	}
	
	public static void setHarvestLevelRotate(String toolClass, int level)
	{
		if (level < 0)
			toolClassesRotate.remove(toolClass);
		else
			toolClassesRotate.put(toolClass, Integer.valueOf(level));
	}
	
	public static void setHarvestLevelTune(String toolClass, int level)
	{
		if (level < 0)
			toolClassesTune.remove(toolClass);
		else
			toolClassesTune.put(toolClass, Integer.valueOf(level));
	}
	
	public static void setHarvestLevelUtil(String toolClass, int level)
	{
		if (level < 0)
			toolClassesUtil.remove(toolClass);
		else
			toolClassesUtil.put(toolClass, Integer.valueOf(level));
	}
}