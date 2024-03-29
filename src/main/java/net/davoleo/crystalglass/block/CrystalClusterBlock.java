package net.davoleo.crystalglass.block;

import net.davoleo.crystalglass.init.ModItems;
import net.davoleo.crystalglass.util.ShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

/**
 * This class represents a Crystal Cluster Block<br>
 * This block will have 3 different properties determining its state<br>
 * 1. A facing depending on which side it's attached to<br>
 * 2. An age depending on the stage of growth of the crystal<br>
 * 3. A waterlogged boolean property to describe whether the block is waterlogged or not<br>
 */
public class CrystalClusterBlock extends CrystalShardBlock {

    private static final ResourceLocation automatedDroppingLoot = new ResourceLocation("crystalglass:blocks/waterlogged_crystal_cluster_automated");

    private final VoxelShape[][] voxelShapes;

    //Properties
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public CrystalClusterBlock() {
        super(state -> 6 + (3 * state.getValue(AGE)));
        voxelShapes = generateVoxelShapes();
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
            float horizCoord1 = 7F - age;
            float horizCoord2 = 9F + age;

            //Reset the hacky change made before
            if (age > 3)
                age = 3;

            float height = 6 + (age > 1 ? age * age + 1 : 0);

            shapes[age] = ShapeUtils.generateDirectionalVoxelShapes(new Vec3(horizCoord1, 0, horizCoord1), new Vec3(horizCoord2, height, horizCoord2));
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

        if (face == AttachFace.CEILING)
            return voxelShapes[age][4];
        if (face == AttachFace.FLOOR)
            return voxelShapes[age][5];

        return voxelShapes[age][horizontalDirection.get2DDataValue()];
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
        stateBuilder.add(HORIZONTAL_FACING, FaceAttachedHorizontalDirectionalBlock.FACE, AGE, WATERLOGGED, POWERED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockPlaceContext context)
    {
        BlockState state = super.getStateForPlacement(context);
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());

        if (state == null)
            return null;

        if (fluidstate.is(FluidTags.WATER)) {
            state = state.setValue(WATERLOGGED, true);
        }

        //Set POWERED
        state = state.setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));

        //Set the age state
        String itemName = ForgeRegistries.ITEMS.getKey(context.getItemInHand().getItem()).getPath();
        char age = itemName.charAt(itemName.length() - 1);
        state = state.setValue(AGE, Character.getNumericValue(age));

        return state;
    }

    /**
     * same as super-super-class version, restores behaviour after being removed in superclass
     *
     * @see FaceAttachedHorizontalDirectionalBlock#canSurvive(BlockState, LevelReader, BlockPos)
     */
    @ParametersAreNonnullByDefault
    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canAttach(pLevel, pPos, getConnectedDirection(pState).getOpposite());
    }

    /**
     * Called when the block is activated through player right click (plays crystal chime sounds)
     */
    @ParametersAreNonnullByDefault
    @Nonnull
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        world.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 8F, 1F);
        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    @Override
    public void randomTick(BlockState state, @Nonnull ServerLevel world, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
        int age = state.getValue(AGE);
        boolean waterlogged = state.getValue(WATERLOGGED);
        if (age < 3) {
            //Prev 30 and 15
            if (random.nextInt(waterlogged ? 2 : 4) == 0)
                world.setBlock(pos, state.setValue(AGE, age + 1), 2);
        } else if (state.getValue(POWERED)) {

            world.setBlock(pos, state.setValue(AGE, age - random.nextInt(3)), 1 | 2);

            if (!world.isClientSide) {
                LootContext.Builder builder = new LootContext.Builder(world).withRandom(random);
                LootContext lootContext = builder
                        .withParameter(LootContextParams.BLOCK_STATE, state)
                        .withParameter(LootContextParams.ORIGIN, new Vec3(pos.getX(), pos.getY(), pos.getZ()))
                        .withParameter(LootContextParams.TOOL, new ItemStack(Items.IRON_PICKAXE))
                        .create(LootContextParamSets.BLOCK);
                LootTable lootTable = world.getServer().getLootTables().get(automatedDroppingLoot);
                List<ItemStack> lootTableItems = lootTable.getRandomItems(lootContext);

                for (ItemStack stack : lootTableItems) {
                    popResource(world, pos, stack);
                }
            }

            world.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 4F, 1.5F + world.random.nextFloat() * 0.4F);
        }
    }

    @ParametersAreNonnullByDefault
    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            boolean powered = pState.getValue(POWERED);
            if (powered != pLevel.hasNeighborSignal(pPos)) {
                pLevel.setBlock(pPos, pState.cycle(POWERED), 2);
            }
        }
    }

    /**
     * Should tick randomly to grow only if the age state is below 3
     *
     * @return if it randomly ticks
     */
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(POWERED) || state.getValue(AGE) < 3;
    }

    @ParametersAreNonnullByDefault
    @Override
    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.DESTROY;
    }
}
