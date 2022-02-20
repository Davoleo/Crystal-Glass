package net.davoleo.crystalglass.init;

import net.davoleo.crystalglass.CrystalGlass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {

    public static final ResourceLocation CRYSTAL_STILL_RL = new ResourceLocation("fluid/molten_crystal_still");
    public static final ResourceLocation CRYSTAL_FLOWING_RL = new ResourceLocation("fluid/molten_crystal_flowing");
    public static final ResourceLocation CRYSTAL_OVERLAY_RL = new ResourceLocation("fluid/molten_crystal_overlay");

    public static final DeferredRegister<Fluid> REGISTER = DeferredRegister.create(ForgeRegistries.FLUIDS, CrystalGlass.MODID);


    public static final RegistryObject<FlowingFluid> MOLTEN_CRYSTAL
            = REGISTER.register("molten_crystal", () -> new ForgeFlowingFluid.Source(ModFluids.CRYSTAL_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MOLTEN_CRYSTAL_FLOW
            = REGISTER.register("molten_crystal_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.CRYSTAL_PROPERTIES));


    public static final ForgeFlowingFluid.Properties CRYSTAL_PROPERTIES = new ForgeFlowingFluid.Properties(
            MOLTEN_CRYSTAL, MOLTEN_CRYSTAL_FLOW, FluidAttributes.builder(CRYSTAL_STILL_RL, CRYSTAL_FLOWING_RL)
            .density(15)
            .luminosity(2)
            .viscosity(5)
            .sound(SoundEvents.AMETHYST_BLOCK_CHIME)
            .overlay(CRYSTAL_OVERLAY_RL)
            //.slopFindDistance(4)
            //.levelDecresePerBlock(2)
            //.block(() -> ModFluids.get())
            //.bucket(ModItems.CRYSTAL_BUCKET.get())
    );


}