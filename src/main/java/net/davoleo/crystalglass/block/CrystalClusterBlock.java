package net.davoleo.crystalglass.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * This class represents a Crystal Cluster Block<br>
 * This block will have 3 different properties determining its state<br>
 * 1. A facing depending on which side it's attached to<br>
 * 2. An age depending on the stage of growth of the crystal<br>
 * 3. A waterlogged boolean property to describe whether the block is waterlogged or not<br>
 */
public class CrystalClusterBlock extends HorizontalFaceBlock implements IWaterLoggable {

    private final VoxelShape[][] VOXEL_SHAPES;

    private static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // TODO: 29/03/2021 Fix WATERLOGGED state on placement set to true
    // TODO: 29/03/2021 Add Items for each age state
    public CrystalClusterBlock()
    {
        super(Properties.create(Material.ROCK).setOpaque((p_test_1_, p_test_2_, p_test_3_) -> false));
        // TODO: 24/03/2021 work on these properties
        Properties props = Properties.create(Material.ROCK);
        VOXEL_SHAPES = generateVoxelShapes();
    }

    /**
     * @return A matrix with all the block voxel shapes for each direction and age state
     */
    private VoxelShape[][] generateVoxelShapes()
    {
        VoxelShape[][] shapes = new VoxelShape[8][6];

        for (int age = 0; age < 8; age++)
        {

            double height = 4 + (age < 3 ? age : (age * 2) - 2);
            double horizCoord1 = 6.5D - 0.5 * age;
            double horizCoord2 = 9.5D + 0.5 * age;

            for (int face = 0; face <= 6; face++)
            {
                switch (face)
                {
                    case 6:
                        shapes[age][face] = Block.makeCuboidShape(horizCoord1, 0.0D, horizCoord1, horizCoord2, height, horizCoord2);
                        break;
                    case 5:
                        shapes[age][face] = Block.makeCuboidShape(horizCoord1, 16.0D, horizCoord1, horizCoord2, 16 - height, horizCoord2);
                        break;
                    case 0:
                        shapes[age][face] = Block.makeCuboidShape(horizCoord1, horizCoord1, 0.0D, horizCoord2, horizCoord2, height);
                        break;
                    case 2:
                        shapes[age][face] = Block.makeCuboidShape(horizCoord1, horizCoord1, 16.0D, horizCoord2, horizCoord2, 16 - height);
                        break;
                    case 3:
                        shapes[age][face] = Block.makeCuboidShape(0.0D, horizCoord1, horizCoord1, height, horizCoord2, horizCoord2);
                        break;
                    case 1:
                        shapes[age][face] = Block.makeCuboidShape(16.0D, horizCoord1, horizCoord1, 16 - height, horizCoord2, horizCoord2);
                        break;
                }
            }
        }

        return shapes;
    }

    /**
     * Will be called whenever mincraft needs to render the line around the block <br>
     * (probably best not to do many calculations here)
     *
     * @param state The state of the block
     * @return The shape of the block
     */
    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context)
    {
        int age = state.get(AGE);
        AttachFace face = state.get(HorizontalFaceBlock.FACE);
        Direction horizontalDirection = state.get(HORIZONTAL_FACING);

        if (face == AttachFace.FLOOR)
            return VOXEL_SHAPES[age][6];
        else if (face == AttachFace.CEILING)
            return VOXEL_SHAPES[age][5];
        else
            return VOXEL_SHAPES[age][horizontalDirection.getHorizontalIndex()];
    }

    /**
     * This Method override is used to build a state container for the block with all the properties that we want it to have
     *
     * @param stateBuilder the state container builder
     */
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateBuilder)
    {
        stateBuilder.add(HORIZONTAL_FACING, HorizontalFaceBlock.FACE, AGE, WATERLOGGED);
    }

    /**
     * Used in crops blocks to make them grow, is called randomly once in a while
     *
     * @param state the current block state
     * @param world the world this block is in
     * @param pos   the position this block is at
     */
    @Override
    public void randomTick(BlockState state, @Nonnull ServerWorld world, @Nonnull BlockPos pos, @Nonnull Random random)
    {
        int age = state.get(AGE);
        boolean waterlogged = state.get(WATERLOGGED);
        if (age < 7)
        {
            //Prev 30 and 15
            if (random.nextInt(waterlogged ? 16 : 8) == 0)
                world.setBlockState(pos, state.with(AGE, age + 1), 2);
        }
    }

    /**
     * @return the state of the fluid inside of the block if it's waterlogged
     */
    @Nonnull
    public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean ticksRandomly(@Nonnull BlockState state)
    {
        return state.get(AGE) < 7;
    }
}
