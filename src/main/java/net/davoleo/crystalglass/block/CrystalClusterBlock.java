package net.davoleo.crystalglass.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

/**
 * This class represents a Crystal Cluster Block<br>
 * This block will have 3 different properties determining its state<br>
 * 1. A facing depending on which side it's attached to<br>
 * 2. An age depending on the stage of growth of the crystal<br>
 * 3. A waterlogged boolean property to describe whether the block is waterlogged or not<br>
 */
public class CrystalClusterBlock extends Block {

    private static final DirectionProperty FACING = BlockStateProperties.FACING;
    private static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public CrystalClusterBlock()
    {
        super(Properties.create(Material.ROCK));
        // TODO: 24/03/2021 work on these properties
        Properties props = Properties.create(Material.ROCK);
    }

    /**
     * This Method override is used to build a state container for the block with all the properties that we want it to have
     *
     * @param stateBuilder the state container builder
     */
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateBuilder)
    {
        stateBuilder.add(FACING, AGE, WATERLOGGED);
    }

    /**
     * Called when you place the block onto a surface
     *
     * @return the correct facing depending on the placement context
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection());
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
            if (random.nextInt(waterlogged ? 30 : 15) == 0)
                world.setBlockState(pos, state.with(AGE, age + 1), 2);
        }
    }

    @Override
    public boolean ticksRandomly(@Nonnull BlockState state)
    {
        return state.get(AGE) < 7;
    }
}
