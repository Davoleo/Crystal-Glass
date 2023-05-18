package net.davoleo.crystalglass.init;

import net.davoleo.crystalglass.CrystalGlassMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = CrystalGlassMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetup {

    public static void setup(FMLCommonSetupEvent event) {
        CrystalGlassMod.LOGGER.info("Crystal Glass Setup Method...");
        CrystalGlassMod.LOGGER.info("MMMH, MONKEY");
    }

    @SubscribeEvent
    public static void registerCrystalTab(CreativeModeTabEvent.Register event) {

        event.registerCreativeModeTab(new ResourceLocation(CrystalGlassMod.MODID, "crystal_glass"), builder ->
                builder.icon(() -> new ItemStack(ModItems.CRYSTAL_CLUSTERS.get(3).get()))
                        .title(Component.literal(CrystalGlassMod.MODNAME))
                        .displayItems((displayParams, output) -> {
                            ModItems.CRYSTAL_CLUSTERS.forEach(cluster -> output.accept(cluster.get()));
                            output.accept(ModItems.MOLTEN_CRYSTAL_BUCKET.get());
                            ModBlocks.CRYSTAL_SHARDS.forEach(shard -> output.accept(shard.get()));
                        })
        );
    }

}
