package net.davoleo.crystalglass.init;

import com.tterrag.registrate.util.entry.FluidEntry;
import net.davoleo.crystalglass.CrystalGlass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import static net.davoleo.crystalglass.CrystalGlass.REGISTRATE;

public class ModFluids {

    public static final ResourceLocation STILL_TEXTURE = new ResourceLocation(CrystalGlass.MODID, "fluid/molten_crystal_still");
    public static final ResourceLocation FLOWING_TEXTURE = new ResourceLocation(CrystalGlass.MODID, "fluid/molten_crystal_flow");
    public static final ResourceLocation OVERLAY_TEXTURE = new ResourceLocation(CrystalGlass.MODID, "fluid/molten_crystal_overlay");

    public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_CRYSTAL = REGISTRATE
            .object("molten_crystal")
            .fluid(
                    "molten_crystal", STILL_TEXTURE, FLOWING_TEXTURE
            )
            .attributes((attrBuilder) -> attrBuilder
                    .density(15)
                    .luminosity(2)
                    .viscosity(5)
                    .sound(SoundEvents.AMETHYST_BLOCK_CHIME)
                    .overlay(OVERLAY_TEXTURE)
                    .temperature(1000)
                    .sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA)
            )
            .properties(properties ->
                    properties.slopeFindDistance(4)
                            .levelDecreasePerBlock(2)
            )
            .tag(FluidTags.LAVA)
            .source(ForgeFlowingFluid.Source::new)
            .bucket()
            .model((context, provider) -> provider.generated(() -> ModFluids.MOLTEN_CRYSTAL.get().getBucket()))
            .build()
            .register();

}