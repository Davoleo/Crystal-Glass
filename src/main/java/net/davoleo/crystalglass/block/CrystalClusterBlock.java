package net.davoleo.crystalglass.block;

import net.davoleo.crystalglass.init.ModItems;
import net.davoleo.crystalglass.init.ModSounds;
import net.davoleo.crystalglass.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
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
public class CrystalClusterBlock extends CrystalBlock {

    private VoxelShape[][] VOXEL_SHAPES;

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;

    public CrystalClusterBlock()
    {
        super(state -> 6 + (3 * state.get(AGE)));
        VOXEL_SHAPES = generateVoxelShapes();
    }

    /**
     * @return A matrix with all the block voxel shapes for each direction and age state
     */
    private VoxelShape[][] generateVoxelShapes()
    {
        VoxelShape[][] shapes = new VoxelShape[4][6];

        for (int age = 0; age < 4; age++)
        {
            //Increase the age right away so that voxel boxes are increased of 2 pixels instead of 1
            if (age == 3)
                age++;
            float horizCoord1 = 9F + age;
            float horizCoord2 = 7F - age;

            //Reset the hacky change made before
            if (age > 3)
                age = 3;

            float height = 6 + (age > 1 ? age * age + 1 : 0);

            shapes[age] = Utils.generateDirectionalVoxelShapes(new Vector3f(horizCoord1, 0, horizCoord1), new Vector3f(horizCoord2, height, horizCoord2));
        }

        return shapes;
    }

    /**
     * Will be called whenever minecraft needs to render the line around the block <br>
     * (probably best not to do many calculations here)
     *
     * @param state The state of the block
     * @return The shape of the block
     */
    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context)
    {
        int age = state.get(AGE);
        AttachFace face = state.get(HorizontalFaceBlock.FACE);
        Direction horizontalDirection = state.get(HORIZONTAL_FACING);

        VOXEL_SHAPES = generateVoxelShapes();

        if (face == AttachFace.FLOOR)
            return VOXEL_SHAPES[age][5];
        else if (face == AttachFace.CEILING)
            return VOXEL_SHAPES[age][4];
        else
            return VOXEL_SHAPES[age][horizontalDirection.getHorizontalIndex()];
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
    {
        return new ItemStack(ModItems.CRYSTAL_CLUSTERS.get(state.get(AGE)).get());
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockItemUseContext context)
    {
        BlockState state = super.getStateForPlacement(context);

        if (state == null)
            return null;

        //Set the age state
        String itemName = context.getItem().getItem().getRegistryName().getPath();
        char age = itemName.charAt(itemName.length() - 1);
        state = state.with(AGE, Character.getNumericValue(age));

        return state;
    }

    /**
     * Called when the block is activated through player right click (plays crystal chime sounds)
     */
    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit)
    {
        worldIn.playSound(null, pos, ModSounds.Events.CRYSTAL_SHIMMER.get(), SoundCategory.BLOCKS, 1F, 1F);
        return ActionResultType.PASS;
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
        //boolean waterlogged = state.get(WATERLOGGED);
        if (age < 3)
        {
            //Prev 30 and 15
            if (random.nextInt(6) == 0)
                world.setBlockState(pos, state.with(AGE, age + 1), 2);
        }
    }

    @Override
    public boolean isTransparent(@Nonnull BlockState state)
    {
        return true;
    }

    /**
     * Should tick randomly to grow only if the age state is below 7
     *
     * @return if it randomly ticks
     */
    @Override
    public boolean ticksRandomly(BlockState state)
    {
        return state.get(AGE) < 3;
    }
}
