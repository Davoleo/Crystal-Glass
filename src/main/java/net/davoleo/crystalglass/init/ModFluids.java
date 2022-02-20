package net.davoleo.crystalglass.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.davoleo.crystalglass.CrystalGlass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import static net.davoleo.crystalglass.CrystalGlass.REGISTRATE;

public class ModFluids {

    public static final ResourceLocation STILL_TEXTURE = new ResourceLocation(CrystalGlass.MODID, "fluid/molten_crystal_still");
    public static final ResourceLocation FLOWING_TEXTURE = new ResourceLocation(CrystalGlass.MODID, "fluid/molten_crystal_flowing");
    public static final ResourceLocation OVERLAY_TEXTURE = new ResourceLocation(CrystalGlass.MODID, "fluid/molten_crystal_overlay");

    public static final RegistryEntry<ForgeFlowingFluid> MOLTEN_CRYSTAL_BUILDER = REGISTRATE
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
            )
            .properties(properties ->
                    properties.slopeFindDistance(4)
                            .levelDecreasePerBlock(2)
            )
            //.defaultSource()
            //.defaultBlock()
            //.defaultBucket()
            .build().get(Fluid.class);

}