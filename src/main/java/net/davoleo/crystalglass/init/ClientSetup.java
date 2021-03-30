package net.davoleo.crystalglass.init;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(final FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ModRegistry.CRYSTAL_CLUSTER_BLOCK.get(), RenderType.getTranslucent());
    }
}
