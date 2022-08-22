package net.davoleo.crystalglass.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;

public final class ModFluids extends ModRegistry {

    public static void init()
    {
    }

    public static final ResourceLocation STILL_TEXTURE = resourceLoc("fluid/molten_crystal_still");
    public static final ResourceLocation FLOWING_TEXTURE = resourceLoc("fluid/molten_crystal_flow");
    public static final ResourceLocation OVERLAY_TEXTURE = resourceLoc("fluid/molten_crystal_overlay");

    public static final RegistryObject<FlowingFluid> MOLTEN_CRYSTAL = FLUIDS.register("molten_crystal",
            () -> new ForgeFlowingFluid.Source(ModFluids.MOLTEN_CRYSTAL_PROPS));

    public static final RegistryObject<FlowingFluid> MOLTEN_CRYSTAL_FLOWING = FLUIDS.register("molten_crystal_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.MOLTEN_CRYSTAL_PROPS));

    public static final ForgeFlowingFluid.Properties MOLTEN_CRYSTAL_PROPS = new ForgeFlowingFluid.Properties(
            MOLTEN_CRYSTAL, MOLTEN_CRYSTAL_FLOWING,
            FluidAttributes.builder(STILL_TEXTURE, FLOWING_TEXTURE)
                    .density(15)
                    .luminosity(2)
                    .viscosity(5)
                    .sound(SoundEvents.AMETHYST_BLOCK_CHIME)
                    .overlay(OVERLAY_TEXTURE)
                    .color(0xBFFFFFFF)
                    .temperature(1000)
                    .sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA)
    ).slopeFindDistance(4).levelDecreasePerBlock(2)
            .block(ModBlocks.MOLTEN_CRYSTAL_BLOCK)
            .bucket(ModItems.MOLTEN_CRYSTAL_BUCKET);


}