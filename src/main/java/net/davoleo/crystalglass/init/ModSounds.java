package net.davoleo.crystalglass.init;

import net.davoleo.crystalglass.CrystalGlass;
import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModSounds {

    private static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CrystalGlass.MODID);

    public static void init()
    {
        REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final class Events {
        public static final Supplier<SoundEvent> CRYSTAL_STEP = () -> new SoundEvent(new ResourceLocation(CrystalGlass.MODID, "block.crystal_block.step"));
        public static final Supplier<SoundEvent> CRYSTAL_BREAK = () -> new SoundEvent(new ResourceLocation(CrystalGlass.MODID, "block.crystal_block.break"));
        public static final Supplier<SoundEvent> CRYSTAL_PLACE = () -> new SoundEvent(new ResourceLocation(CrystalGlass.MODID, "block.crystal_block.place"));
        public static final Supplier<SoundEvent> CRYSTAL_HIT = () -> new SoundEvent(new ResourceLocation(CrystalGlass.MODID, "block.crystal_block.hit"));
        public static final Supplier<SoundEvent> CRYSTAL_FALL = () -> new SoundEvent(new ResourceLocation(CrystalGlass.MODID, "block.crystal_block.fall"));
        public static final Supplier<SoundEvent> CRYSTAL_SHIMMER = () -> new SoundEvent(new ResourceLocation(CrystalGlass.MODID, "block.crystal_cluster_block.chime"));

        static
        {
            REGISTER.register("block.crystal_block.step", CRYSTAL_STEP);
            REGISTER.register("block.crystal_block.break", CRYSTAL_BREAK);
            REGISTER.register("block.crystal_block.place", CRYSTAL_HIT);
            REGISTER.register("block.crystal_block.hit", CRYSTAL_PLACE);
            REGISTER.register("block.crystal_block.fall", CRYSTAL_FALL);
        }
    }

    public static final SoundType CRYSTAL_SOUND_TYPE = new ForgeSoundType(1, 1, Events.CRYSTAL_BREAK, Events.CRYSTAL_STEP, Events.CRYSTAL_PLACE, Events.CRYSTAL_HIT, Events.CRYSTAL_FALL);
}
