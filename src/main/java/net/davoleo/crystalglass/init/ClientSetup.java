package net.davoleo.crystalglass.init;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(final FMLClientSetupEvent event)
    {
        RenderTypeLookup.setRenderLayer(ModRegistry.CRYSTAL_CLUSTER_BLOCK.get(), RenderType.getTranslucent());

        // TODO: 30/03/2021 Temporary solution to ModRegistry group initialization (the other one always adds age 7 crystals)
        //NonNullList<ItemStack> creativeStacks = ModRegistry.Items.CRYSTAL_CLUSTERS
        //        .stream()
        //        .map(obj -> new ItemStack(obj.get()))
        //        .collect(Collectors.toCollection(NonNullList::create));
        //CrystalGlass.CREATIVE_TAB.fill(creativeStacks);
    }
}
