package pneumaticCraft.common.semiblock;

import net.minecraft.world.World;

public class SemiBlockManager {

    private static final SemiBlockManager INSTANCE = new SemiBlockManager();
    private static final SemiBlockManager CLIENT_INSTANCE = new SemiBlockManager();
    
	 public static SemiBlockManager getServerInstance(){
	        return INSTANCE;
	    }

	    public static SemiBlockManager getClientOldInstance(){
	        return CLIENT_INSTANCE;
	    }

	    public static SemiBlockManager getInstance(World world){
	        return world.isRemote ? CLIENT_INSTANCE : INSTANCE;
	    }

		public ISemiBlock getSemiBlock(World world, int x, int y, int z) {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean breakSemiBlock(World world, int x, int y, int z) {
			// TODO Auto-generated method stub
			return false;
		}
}
