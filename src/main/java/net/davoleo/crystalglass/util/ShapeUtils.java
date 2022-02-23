package net.davoleo.crystalglass.util;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShapeUtils {

    /**
     * @return a new Rotated Block box VoxelShape
     */
    @Deprecated
    public static VoxelShape alignBox(VoxelShape shape, Direction oldDirection, Direction newDirection)
    {
        Direction.Axis oldAxis = oldDirection.getAxis();
        Direction.Axis newAxis = newDirection.getAxis();
        AABB bounds = shape.bounds();

        if (oldAxis == newAxis)
        {
            if (oldDirection.getAxisDirection() != newDirection.getAxisDirection())
            {
                AABB newBB = cloneAABB(bounds);
                newBB = setAxisBoxValue(newBB, true, oldAxis, 1 - bounds.max(oldAxis));
                newBB = setAxisBoxValue(newBB, false, oldAxis, 1 - bounds.min(oldAxis));
                return Shapes.create(newBB);
            } else
                return shape;
        } else
        {
            AABB newBB = cloneAABB(bounds);
            newBB = setAxisBoxValue(newBB, true, newAxis, bounds.min(oldAxis));
            newBB = setAxisBoxValue(newBB, false, newAxis, bounds.max(oldAxis));
            newBB = setAxisBoxValue(newBB, true, oldAxis, bounds.min(newAxis));
            newBB = setAxisBoxValue(newBB, false, oldAxis, bounds.max(newAxis));
            if (oldDirection.getAxisDirection() == newDirection.getAxisDirection())
                return Shapes.create(newBB);
            else
                return alignBox(Shapes.create(newBB), newDirection.getOpposite(), newDirection);
        }
    }

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
                case 2 -> shapes[face] = Block.box(shapeStart.x, shapeStart.z, 16 - shapeEnd.y, shapeEnd.x, shapeEnd.z, 16 - shapeStart.y);
                //East: Swap X and Y Coordinates
                case 3 -> shapes[face] = Block.box(shapeStart.y, shapeStart.x, shapeStart.z, shapeEnd.y, shapeEnd.x, shapeEnd.z);
                //West: Swap X and (16 - Y) Coordinates
                case 1 -> shapes[face] = Block.box(16 - shapeEnd.y, shapeStart.x, shapeStart.z, 16 - shapeStart.y, shapeEnd.x, shapeEnd.z);
                //Attach Face: Floor (Default no transform)
                case 5 -> shapes[face] = Block.box(shapeStart.x, shapeStart.y, shapeStart.z, shapeEnd.x, shapeEnd.y, shapeEnd.z);
                //Attach Face: Ceiling (16 - Y) [invert vertical coordinates]
                case 4 -> shapes[face] = Block.box(shapeStart.x, 16 - shapeEnd.y, shapeStart.z, shapeEnd.x, 16 - shapeStart.y, shapeEnd.z);
            }

            System.out.println(face + ": coords " + String.join(", ",
                    Double.toString(shapes[face].min(Direction.Axis.X)), Double.toString(shapes[face].min(Direction.Axis.Y)), Double.toString(shapes[face].min(Direction.Axis.Z)),
                    Double.toString(shapes[face].max(Direction.Axis.X)), Double.toString(shapes[face].max(Direction.Axis.Y)), Double.toString(shapes[face].max(Direction.Axis.Z))
            ));
        }

        return shapes;
    }

    public static AABB cloneAABB(AABB source)
    {
        return new AABB(source.minX, source.minY, source.minZ, source.maxX, source.maxY, source.maxZ);
    }

    public static AABB setAxisBoxValue(AABB aabb, boolean min, Direction.Axis axis, double value)
    {
        return switch (axis)
                {
                    case X -> min ? aabb.setMinX(value) : aabb.setMaxX(value);
                    case Y -> min ? aabb.setMinY(value) : aabb.setMaxY(value);
                    case Z -> min ? aabb.setMinZ(value) : aabb.setMaxZ(value);
                };
    }

}
