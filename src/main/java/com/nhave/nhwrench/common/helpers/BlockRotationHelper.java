package com.nhave.nhwrench.common.helpers;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockRotationHelper
{
	public static byte[] rotateType = new byte[4096];
    public static final int[][] SIDE_COORD_MOD = {{0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}, {-1, 0, 0}, {1, 0, 0}};
    public static final int[] SIDE_LEFT = {4, 5, 5, 4, 2, 3};
    public static final int[] SIDE_RIGHT = {5, 4, 4, 5, 3, 2};
    public static final int[] SIDE_OPPOSITE = {1, 0, 3, 2, 5, 4};
    public static final int[] SIDE_UP = {2, 3, 1, 1, 1, 1};
    public static final int[] SIDE_DOWN = {3, 2, 0, 0, 0, 0};

    public static boolean canRotate(Block block)
    {
        return rotateType[Block.getIdFromBlock(block)] != 0;
    }
    
    public static int rotateVanillaBlock(World world, Block block, int meta, int x, int y, int z)
    {
    	int blockId = Block.getIdFromBlock(block);
        switch (rotateType[blockId])
        {
            case 1:
                return SIDE_LEFT[meta];
            case 2:
                if (meta < 6)
                {
                    meta++;
                    return meta % 6;
                }
                return meta;
            case 3:
                if (meta < 2)
                {
                    meta++;
                    return meta % 2;
                }
                return meta;
            case 4:
                meta++;
                return meta % 4;
            case 5:
                meta++;
                return meta % 8;
            case 6:
                int upper = meta & 0xC;
                int lower = meta & 0x3;
                return upper + ++lower % 4;
            case 7:
                return (meta + 4) % 12;
            case 8:
                return (meta + 8) % 16;
            case 9:
                for (int i = 2; i < 6; i++) {
                    int[] coords;
                    coords = getAdjacentCoordinatesForSide(x, y, z, i);
                    if (Block.getIdFromBlock(world.getBlock(coords[0], coords[1], coords[2])) == blockId)
                    {
                        world.setBlockMetadataWithNotify(coords[0], coords[1], coords[2], SIDE_OPPOSITE[meta], 1);
                        return SIDE_OPPOSITE[meta];
                    }
                }
                return SIDE_LEFT[meta];
            case 10:
                int shift = 0;
                if (meta > 7)
                {
                    meta -= 8;
                    shift = 8;
                }
                if (meta == 5)
                    return 6 + shift;
                if (meta == 6)
                    return 5 + shift;
                if (meta == 7)
                    return 0 + shift;
                if (meta == 0) {
                    return 7 + shift;
                }
                return meta + shift;
            case 11:
                meta++; return meta % 16;
        }
        return meta;
    }

    public static int rotateVanillaBlockAlt(World world, Block block, int meta, int x, int y, int z)
    {
    	int blockId = Block.getIdFromBlock(block);
        switch (rotateType[blockId])
        {
            case 1:
                return SIDE_RIGHT[meta];
            case 2:
                if (meta < 6) {
                    return (meta + 5) % 6;
                }
                return meta;
            case 3:
                if (meta < 2)
                {
                    meta++;
                    return meta % 2;
                }
                return meta;
            case 4:
                return (meta + 3) % 4;
            case 5:
                return (meta + 7) % 8;
            case 6:
                int upper = meta & 0xC;
                int lower = meta & 0x3;
                return upper + (lower + 3) % 4;
            case 7:
                return (meta + 8) % 12;
            case 8:
                return (meta + 8) % 16;
            case 9:
                for (int i = 2; i < 6; i++)
                {
                    int[] coords;
                    coords = getAdjacentCoordinatesForSide(x, y, z, i);
                    if (Block.getIdFromBlock(world.getBlock(coords[0], coords[1], coords[2])) == blockId)
                    {
                        world.setBlockMetadataWithNotify(coords[0], coords[1], coords[2], SIDE_OPPOSITE[meta], 1);
                        return SIDE_OPPOSITE[meta];
                    }
                }
                return SIDE_RIGHT[meta];
            case 10:
                int shift = 0;
                if (meta > 7)
                {
                    meta -= 8;
                    shift = 8;
                }
                if (meta == 5)
                    return 6 + shift;
                if (meta == 6)
                    return 5 + shift;
                if (meta == 7)
                    return 0 + shift;
                if (meta == 0)
                    return 7 + shift;
                break;
            case 11:
                meta++; return meta % 16;
        }
        return meta;
    }

    public static int[] getAdjacentCoordinatesForSide(int x, int y, int z, int side)
    {
        return new int[]{x + SIDE_COORD_MOD[side][0], y + SIDE_COORD_MOD[side][1], z + SIDE_COORD_MOD[side][2]};
    }

    static
    {
    	rotateType[Block.getIdFromBlock(Blocks.log)] = 7;
        rotateType[Block.getIdFromBlock(Blocks.dispenser)] = 2;
        rotateType[Block.getIdFromBlock(Blocks.golden_rail)] = 3;
        rotateType[Block.getIdFromBlock(Blocks.detector_rail)] = 3;
        rotateType[Block.getIdFromBlock(Blocks.sticky_piston)] = 2;
        rotateType[Block.getIdFromBlock(Blocks.piston)] = 2;
        rotateType[Block.getIdFromBlock(Blocks.stone_slab)] = 8;

        rotateType[Block.getIdFromBlock(Blocks.oak_stairs)] = 5;
        rotateType[Block.getIdFromBlock(Blocks.chest)] = 9;
        rotateType[Block.getIdFromBlock(Blocks.furnace)] = 1;
        rotateType[Block.getIdFromBlock(Blocks.lit_furnace)] = 1;
        rotateType[Block.getIdFromBlock(Blocks.standing_sign)] = 11;

        rotateType[Block.getIdFromBlock(Blocks.rail)] = 3;
        rotateType[Block.getIdFromBlock(Blocks.stone_stairs)] = 5;
        rotateType[Block.getIdFromBlock(Blocks.lever)] = 10;
        rotateType[Block.getIdFromBlock(Blocks.pumpkin)] = 4;
        rotateType[Block.getIdFromBlock(Blocks.lit_pumpkin)] = 4;

        rotateType[Block.getIdFromBlock(Blocks.unpowered_repeater)] = 6;
        rotateType[Block.getIdFromBlock(Blocks.powered_repeater)] = 6;
        rotateType[Block.getIdFromBlock(Blocks.brick_stairs)] = 5;
        rotateType[Block.getIdFromBlock(Blocks.stone_brick_stairs)] = 5;
        rotateType[Block.getIdFromBlock(Blocks.nether_brick_stairs)] = 5;

        rotateType[Block.getIdFromBlock(Blocks.wooden_slab)] = 8;
        rotateType[Block.getIdFromBlock(Blocks.sandstone_stairs)] = 5;
        rotateType[Block.getIdFromBlock(Blocks.ender_chest)] = 1;
        rotateType[Block.getIdFromBlock(Blocks.spruce_stairs)] = 5;
        rotateType[Block.getIdFromBlock(Blocks.birch_stairs)] = 5;

        rotateType[Block.getIdFromBlock(Blocks.jungle_stairs)] = 5;
        rotateType[Block.getIdFromBlock(Blocks.trapped_chest)] = 9;
        rotateType[Block.getIdFromBlock(Blocks.quartz_stairs)] = 5;
        rotateType[Block.getIdFromBlock(Blocks.hopper)] = 2;
        rotateType[Block.getIdFromBlock(Blocks.activator_rail)] = 3;
        rotateType[Block.getIdFromBlock(Blocks.dropper)] = 2;

    	rotateType[Block.getIdFromBlock(Blocks.log2)] = 7;
        rotateType[Block.getIdFromBlock(Blocks.acacia_stairs)] = 5;
        rotateType[Block.getIdFromBlock(Blocks.dark_oak_stairs)] = 5;
    }
}