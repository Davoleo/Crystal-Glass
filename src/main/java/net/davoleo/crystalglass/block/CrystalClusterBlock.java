package net.davoleo.crystalglass.block;

import net.davoleo.crystalglass.init.ModItems;
import net.davoleo.crystalglass.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

/**
 * This class represents a Crystal Cluster Block<br>
 * This block will have 3 different properties determining its state<br>
 * 1. A facing depending on which side it's attached to<br>
 * 2. An age depending on the stage of growth of the crystal<br>
 * 3. A waterlogged boolean property to describe whether the block is waterlogged or not<br>
 */
public class CrystalClusterBlock extends CrystalBlock {

    private VoxelShape[][] VOXEL_SHAPES;

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public CrystalClusterBlock()
    {
        super(state -> 6 + (3 * state.getValue(AGE)));
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

            shapes[age] = Utils.generateDirectionalVoxelShapes(new Vec3(horizCoord1, 0, horizCoord1), new Vec3(horizCoord2, height, horizCoord2));
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
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context)
    {
        int age = state.getValue(AGE);
        AttachFace face = state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE);
        Direction horizontalDirection = state.getValue(HORIZONTAL_FACING);

        VOXEL_SHAPES = generateVoxelShapes();

        if (face == AttachFace.FLOOR)
            return VOXEL_SHAPES[age][5];
        else if (face == AttachFace.CEILING)
            return VOXEL_SHAPES[age][4];
        else
            return VOXEL_SHAPES[age][horizontalDirection.get2DDataValue()];
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player)
    {
        return new ItemStack(ModItems.CRYSTAL_CLUSTERS.get(state.getValue(AGE)).get());
    }

    /**
     * This Method override is used to build a state container for the block with all the properties that we want it to have
     *
     * @param stateBuilder the state container builder
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder)
    {
        stateBuilder.add(HORIZONTAL_FACING, FaceAttachedHorizontalDirectionalBlock.FACE, AGE, WATERLOGGED);
    }

    @Nonnull
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockPlaceContext context)
    {

        BlockState state = super.getStateForPlacement(context);
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        if (fluidstate.is(FluidTags.WATER))
        {
            state = state.setValue(WATERLOGGED, true);
        }


        //Set the age state
        String itemName = context.getItemInHand().getItem().getRegistryName().getPath();
        char age = itemName.charAt(itemName.length() - 1);
        state = state.setValue(AGE, Character.getNumericValue(age));

        return state;
    }

    /**
     * Called when the block is activated through player right click (plays crystal chime sounds)
     */
    @ParametersAreNonnullByDefault
    @Nonnull
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        world.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 4F, 1F);
        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    @Deprecated
    @Override
    public void randomTick(BlockState state, @Nonnull ServerLevel world, @Nonnull BlockPos pos, @Nonnull Random random)
    {
        int age = state.getValue(AGE);
        boolean waterlogged = state.getValue(WATERLOGGED);
        if (age < 3)
        {
            //Prev 30 and 15
            if (random.nextInt(waterlogged ? 2 : 4) == 0)
                world.setBlock(pos, state.setValue(AGE, age + 1), 2);
        } else
        {
            if (waterlogged)
            {
                world.setBlock(pos, state.setValue(AGE, age - random.nextInt(3)), 3);
            }
            if (!world.isClientSide)
            {
                ResourceLocation resourcelocation = new ResourceLocation("crystalglass:blocks/waterlogged_crystal_cluster_automated");

                LootContext.Builder builder = new LootContext.Builder(world).withRandom(RANDOM);
                LootContext lootcontext = builder.withParameter(LootContextParams.BLOCK_STATE, state).create(LootContextParamSets.BLOCK);
                LootTable loottable = world.getServer().getLootTables().get(resourcelocation);
                List<ItemStack> lootTableItems = loottable.getRandomItems(lootcontext);

                for (ItemStack stack : lootTableItems)
                {
                    //spawnAsEntity(world, pos, stack);
                }
            }

            world.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 4F, 1.5F + world.random.nextFloat() * 0.4F);
        }
    }

    /**
     * Should tick randomly to grow only if the age state is below 3
     *
     * @return if it randomly ticks
     */
    @Override
    public boolean isRandomlyTicking(BlockState state)
    {
        return state.getValue(AGE) < 3 || state.getValue(WATERLOGGED);
    }
}
