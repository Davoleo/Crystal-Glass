package net.davoleo.crystalglass.init;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.function.Consumer;

public final class ModFluids extends ModRegistry {

    public static void init() {
    }

    private static final ResourceLocation STILL_TEXTURE = resourceLoc("block/molten_crystal_still");
    private static final ResourceLocation FLOWING_TEXTURE = resourceLoc("block/molten_crystal_flow");
    private static final ResourceLocation OVERLAY_TEXTURE = resourceLoc("block/molten_crystal_overlay");

    public static final RegistryObject<FlowingFluid> MOLTEN_CRYSTAL = FLUIDS.register("molten_crystal",
            () -> new ForgeFlowingFluid.Source(ModFluids.MOLTEN_CRYSTAL_PROPS));
    public static final RegistryObject<FlowingFluid> MOLTEN_CRYSTAL_FLOWING = FLUIDS.register("molten_crystal_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.MOLTEN_CRYSTAL_PROPS));

    public static final FluidType MOLTEN_CRYSTAL_FLUIDTYPE = new FluidType(FluidType.Properties.create()
            .density(15)
            .lightLevel(2)
            .viscosity(5)
            .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.AMETHYST_BLOCK_CHIME)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
            .temperature(1000)) {
        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture() {
                    return STILL_TEXTURE;
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return FLOWING_TEXTURE;
                }

                @Override
                public ResourceLocation getOverlayTexture() {
                    return OVERLAY_TEXTURE;
                }

                @Override
                public int getTintColor() {
                    return 0xBFFFFFFF;
                }

                @Override
                public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                    int color = this.getTintColor();
                    return new Vector3f((color >> 16 & 0xFF) / 255F, (color >> 8 & 0xFF) / 255F, (color & 0xFF) / 255F);
                }
            });
        }
    };

    public static final RegistryObject<FluidType> MOLTEN_CRYSTAL_TYPE_OBJECT = FLUID_TYPES.register("molten_crystal", () -> MOLTEN_CRYSTAL_FLUIDTYPE);

    public static final ForgeFlowingFluid.Properties MOLTEN_CRYSTAL_PROPS = new ForgeFlowingFluid.Properties(
            () -> MOLTEN_CRYSTAL_FLUIDTYPE,
            MOLTEN_CRYSTAL, MOLTEN_CRYSTAL_FLOWING
    ).slopeFindDistance(4).levelDecreasePerBlock(2)
            .block(ModBlocks.MOLTEN_CRYSTAL_BLOCK)
            .bucket(ModItems.MOLTEN_CRYSTAL_BUCKET);

}