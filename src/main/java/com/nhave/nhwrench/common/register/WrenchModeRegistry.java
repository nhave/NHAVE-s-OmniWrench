package com.nhave.nhwrench.common.register;

import java.util.Iterator;
import java.util.TreeMap;

import com.nhave.nhwrench.api.IModeRegister;
import com.nhave.nhwrench.api.IWrenchMode;


/**
 * NHAVE's Omniwrench
 * 
 * @author nhave
 */
public final class WrenchModeRegistry implements IModeRegister
{
	public static final TreeMap<Integer, IWrenchMode> _Modes = new TreeMap<Integer, IWrenchMode>();
    
    /**
     * Registers a new WrenchMode to the database.
     * 
     * @param mode
     *            The mode to register.
     */
    public void registerMode(IWrenchMode mode) 
    {
    	_Modes.put(_Modes.size(), mode);
    }
    
    /**
     * Returns a mode from its id.
     * 
     * @param id
     *            The id.
     */
    public static IWrenchMode getMode(int id)
    {
    	IWrenchMode result = _Modes.get(id);
        return result;
    }
    
    /**
     * Returns next mode in the list.
     * 
     * @param current
     *            Current mode as int.
     */
    public static int getNext(int current)
	{
		String result = "0";
		String cur = "" + current;
		
	    Iterator itr = _Modes.keySet().iterator();
		while(itr.hasNext())
	    {
	    	if (("" + itr.next()).equals(cur))
	    	{
	    		if (itr.hasNext())result = "" + itr.next();
	    		else result = "" + _Modes.firstKey();
	    		break;
	    	}
	    }
		return Integer.parseInt(result);
	}
    
    /**
     * Returns the amount of registered Modes.
     */
    public static int getSize()
    {
    	return _Modes.size();
    }
    
    public static boolean isEmpty()
    {
    	return _Modes.isEmpty();
    }
}