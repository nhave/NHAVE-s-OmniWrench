package com.nhave.nhwrench.api;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;


/**
 * NHAVE's Omniwrench
 * 
 * @author nhave
 */
public final class WrenchRegistry
{
	private static final Map<Integer, IWrenchHandler> _Handlers = new TreeMap<Integer, IWrenchHandler>();
	
	@Mod.Metadata("nhwrench")
	private static ModMetadata modMeta;
	
	private static String description;

	private static List<String> handlerList = Lists.newArrayList();
    
    /**
     * Registers a new WrenchHandler to the database.
     * 
     * @param handler
     *            The handler for a mod.
     */
    public static void registerHandler(IWrenchHandler handler) 
    {
    	_Handlers.put(_Handlers.size(), handler);
    	//updateModMeta(description);
    }
    
    /**
     * Registers a new WrenchHandler to the database.
     * 
     * @param handler
     *            The handler for a mod.
     * @param name
     *            A name for the handler.
     */
    public static void registerHandler(IWrenchHandler handler, String name) 
    {
    	registerHandler(handler);
    	handlerList.add(name);
    	updateModMeta(description);
    }
    
    /**
     * Registers a static string to the database.
     * 
     * @param name
     *            A name for the handler.
     */
    public static void registerStatic(String name) 
    {
    	handlerList.add(name);
    	updateModMeta(description);
    }
    
    /**
     * Returns a handler from its id.
     * 
     * @param id
     *            The id.
     */
    public static IWrenchHandler getHandler(int id)
    {
    	IWrenchHandler result = _Handlers.get(id);
        return result;
    }
	
	public static void updateModMeta(String desc)
	{
		description = desc;
		modMeta.description = description;
		
		if(WrenchRegistry.handlerList.size() == 0)
        {
			modMeta.description += "\n\n\247cNo installed plugins.";
        }
        else
        {
        	modMeta.description += "\n\n\247aInstalled plugins: ";
            for(int i = 0; i < WrenchRegistry.handlerList.size(); i++)
            {
                if(i > 0) modMeta.description += ", ";
                modMeta.description += WrenchRegistry.handlerList.get(i);
            }
            modMeta.description += ".";
        }
	}
    
    /**
     * Returns the amount of registered Handlers.
     */
    public static int getSize()
    {
    	return _Handlers.size();
    }
    
    public static boolean isEmpty()
    {
    	return _Handlers.isEmpty();
    }
}