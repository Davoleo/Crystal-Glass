package net.davoleo.crystalglass.init;

import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> registryEvent)
    {
        registryEvent.getRegistry().register(new CrystalClusterBlock());
    }

}
