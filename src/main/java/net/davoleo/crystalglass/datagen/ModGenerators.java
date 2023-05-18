package net.davoleo.crystalglass.datagen;

import net.davoleo.crystalglass.CrystalGlassMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * They Allow us to generate many assets automatically without having to type JSON files by hand
 */
@Mod.EventBusSubscriber(modid = CrystalGlassMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        PackOutput packOutput = generator.getPackOutput();

        //Adding a blockstate provided that will generate blockstates automatically
        //+ First Param: only run this datagen on client side
        generator.addProvider(event.includeClient(), new BlockStateGenerator(packOutput, fileHelper));
    }
}
