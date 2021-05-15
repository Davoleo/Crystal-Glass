package net.davoleo.crystalglass.util;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.ToolType;

public class Utils {

    public static final AbstractBlock.Properties DEFAULT_ROCK_PROPERTIES =
            AbstractBlock.Properties.create(Material.ROCK)
                    .harvestTool(ToolType.PICKAXE)
                    .hardnessAndResistance(0.8F, 9);

    public static VoxelShape[] generateDirectionalVoxelShapes(Vector3f shapeStart, Vector3f shapeEnd)
    {
        VoxelShape[] shapes = new VoxelShape[6];
        //S-W-N-E
        for (int face = 0; face < 6; face++)
        {
            switch (face)
            {
                //South: Swap Z and Y Coordinates
                case 0:
                    shapes[face] = Block.makeCuboidShape(shapeStart.getX(), shapeStart.getZ(), shapeStart.getY(), shapeEnd.getX(), shapeEnd.getZ(), shapeEnd.getY());
                    break;
                //North: Swap Z and (16 - Y) Coordinates
                case 2:
                    shapes[face] = Block.makeCuboidShape(shapeStart.getX(), shapeStart.getZ(), 16 - shapeStart.getY(), shapeEnd.getX(), shapeEnd.getZ(), 16 - shapeEnd.getY());
                    break;
                //East: Swap X and Y Coordinates
                case 3:
                    shapes[face] = Block.makeCuboidShape(shapeStart.getY(), shapeStart.getX(), shapeStart.getZ(), shapeEnd.getY(), shapeEnd.getX(), shapeEnd.getZ());
                    break;
                //West: Swap X and (16 - Y) Coordinates
                case 1:
                    shapes[face] = Block.makeCuboidShape(16 - shapeStart.getY(), shapeStart.getX(), shapeStart.getZ(), 16 - shapeEnd.getY(), shapeEnd.getX(), shapeEnd.getZ());
                    break;
                //Attach Face: Floor (Default no transform)
                case 5:
                    shapes[face] = Block.makeCuboidShape(shapeStart.getX(), shapeStart.getY(), shapeStart.getZ(), shapeEnd.getX(), shapeEnd.getY(), shapeEnd.getZ());
                    break;
                //Attach Face: Ceiling (16 - Y) [invert vertical coordinates]
                case 4:
                    shapes[face] = Block.makeCuboidShape(shapeStart.getX(), 16 - shapeStart.getY(), shapeStart.getZ(), shapeEnd.getX(), 16 - shapeEnd.getY(), shapeEnd.getZ());
                    break;
            }
        }

        return shapes;
    }

}
