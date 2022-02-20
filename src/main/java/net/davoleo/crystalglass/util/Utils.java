package net.davoleo.crystalglass.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Utils {

    public static final Block.Properties DEFAULT_ROCK_PROPERTIES =
            Block.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(0.8F, 9);

    //IntelliJ, listen, I know it's sussy, but I have to do it
    @SuppressWarnings("SuspiciousNameCombination")
    public static VoxelShape[] generateDirectionalVoxelShapes(Vec3 shapeStart, Vec3 shapeEnd)
    {
        VoxelShape[] shapes = new VoxelShape[6];
        //S-W-N-E
        for (int face = 0; face < 6; face++)
        {
            switch (face)
            {
                //South: Swap Z and Y Coordinates
                case 0 -> shapes[face] = Block.box(shapeStart.x, shapeStart.z, shapeStart.y, shapeEnd.x, shapeEnd.z, shapeEnd.y);
                //North: Swap Z and (16 - Y) Coordinates
                case 2 -> shapes[face] = Block.box(shapeStart.x, shapeStart.z, 16 - shapeStart.y, shapeEnd.x, shapeEnd.z, 16 - shapeEnd.y);
                //East: Swap X and Y Coordinates
                case 3 -> shapes[face] = Block.box(shapeStart.y, shapeStart.x, shapeStart.z, shapeEnd.y, shapeEnd.x, shapeEnd.z);
                //West: Swap X and (16 - Y) Coordinates
                case 1 -> shapes[face] = Block.box(16 - shapeStart.y, shapeStart.x, shapeStart.z, 16 - shapeEnd.y, shapeEnd.x, shapeEnd.z);
                //Attach Face: Floor (Default no transform)
                case 5 -> shapes[face] = Block.box(shapeStart.x, shapeStart.y, shapeStart.z, shapeEnd.x, shapeEnd.y, shapeEnd.z);
                //Attach Face: Ceiling (16 - Y) [invert vertical coordinates]
                case 4 -> shapes[face] = Block.box(shapeStart.x, 16 - shapeStart.y, shapeStart.z, shapeEnd.x, 16 - shapeEnd.y, shapeEnd.z);
            }

            System.out.println(face + ": coords " + String.join(", ",
                    Double.toString(shapeStart.x), Double.toString(shapeStart.y), Double.toString(shapeStart.z),
                    Double.toString(shapeEnd.x), Double.toString(shapeEnd.y), Double.toString(shapeEnd.z)
            ));
        }

        return shapes;
    }

}
