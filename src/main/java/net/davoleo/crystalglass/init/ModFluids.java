package net.davoleo.crystalglass.init;

import net.davoleo.crystalglass.util.DefaultProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;

public final class ModFluids extends ModRegistry {

    public static final ResourceLocation STILL_TEXTURE = resourceLoc("fluid/molten_crystal_still");
    public static final ResourceLocation FLOWING_TEXTURE = resourceLoc("fluid/molten_crystal_flow");
    public static final ResourceLocation OVERLAY_TEXTURE = resourceLoc("fluid/molten_crystal_overlay");

    public static final ForgeFlowingFluid.Properties DEFAULT_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> new ForgeFlowingFluid.Source(ModFluids.DEFAULT_FLUID_PROPERTIES),
            () -> new ForgeFlowingFluid.Flowing(ModFluids.DEFAULT_FLUID_PROPERTIES),
            FluidAttributes.builder(STILL_TEXTURE, FLOWING_TEXTURE)
                    .overlay(OVERLAY_TEXTURE)
                    .density(15)
                    .luminosity(2)
                    .viscosity(5)
                    .sound(SoundEvents.AMETHYST_BLOCK_CHIME)
                    .temperature(1000)
                    .sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA)
    ).levelDecreasePerBlock(2).slopeFindDistance(4)
            .block(BLOCKS.register(
                            "molten_crystal",
                            () -> new LiquidBlock(ModFluids.MOLTEN_CRYSTAL, BlockBehaviour.Properties.of(Material.LAVA))
                    )
            )
            .bucket(ITEMS.register(
                    "molten_crystal_bucket",
                    () -> new BucketItem(ModFluids.MOLTEN_CRYSTAL, DefaultProperties.ITEM)
            ));

    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_CRYSTAL = FLUIDS.register("molten_crystal",
            () -> new ForgeFlowingFluid.Source(DEFAULT_FLUID_PROPERTIES));
    public static final RegistryObject<ForgeFlowingFluid> MOLTEN_CRYSTAL_FLOWING = FLUIDS.register("molten_crystal_flowing",
            () -> new ForgeFlowingFluid.Flowing(DEFAULT_FLUID_PROPERTIES));

    //public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_CRYSTAL = REGISTRATE
    //        .object("molten_crystal")
    //        .fluid(
    //                "molten_crystal", STILL_TEXTURE, FLOWING_TEXTURE
    //        )
    //        .attributes((attrBuilder) -> attrBuilder
    //                .density(15)
    //                .luminosity(2)
    //                .viscosity(5)
    //                .sound(SoundEvents.AMETHYST_BLOCK_CHIME)
    //                .overlay(OVERLAY_TEXTURE)
    //                .temperature(1000)
    //                .sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA)
    //        )
    //        .properties(properties ->
    //                properties.slopeFindDistance(4)
    //                        .levelDecreasePerBlock(2)
    //        )
    //        .tag(FluidTags.LAVA)
    //        .source(ForgeFlowingFluid.Source::new)
    //        .bucket()
    //        .model((context, provider) -> provider.generated(() -> ModFluids.MOLTEN_CRYSTAL.get().getBucket()))
    //        .build()
    //        .block()
    //        .initialProperties(Material.LAVA)
    //        .build()
    //        .register();

}