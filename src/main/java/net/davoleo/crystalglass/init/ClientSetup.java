package net.davoleo.crystalglass.init;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(final FMLClientSetupEvent event)
    {
        //Translucent Blocks
        //ModBlocks.CRYSTAL_SHARDS.forEach(crystal -> ItemBlockRenderTypes.setRenderLayer(crystal.get(), RenderType.translucent()));

        //ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTAL_CLUSTER.get(), RenderType.translucent());
        //ItemBlockRenderTypes.setRenderLayer(ModBlocks.BASE_CRYSTAL_BLOCK.get(), RenderType.translucent());

        //ItemBlockRenderTypes.setRenderLayer(ModBlocks.MOLTEN_CRYSTAL_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.MOLTEN_CRYSTAL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.MOLTEN_CRYSTAL_FLOWING.get(), RenderType.translucent());
    }
}

