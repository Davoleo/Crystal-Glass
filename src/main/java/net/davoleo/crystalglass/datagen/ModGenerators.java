package net.davoleo.crystalglass.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

/**
 * They Allow us to generate many assets automatically without having to type JSON files by hand
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        //We check if we're generating assets for the client (e.g. textures and models)
        if (event.includeClient())
        {
            //Adding a blockstate provided that will generate blockstates automatically
            generator.addProvider(new BlockStateGenerator(generator, fileHelper));
        }
    }
}
