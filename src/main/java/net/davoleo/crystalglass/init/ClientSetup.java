package net.davoleo.crystalglass.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.davoleo.crystalglass.block.FullCrystalBlock;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(final FMLClientSetupEvent event)
    {
        //Translucent Blocks
        ModBlocks.CRYSTAL_BLOCKS.forEach(crystal -> ItemBlockRenderTypes.setRenderLayer(crystal.get(), RenderType.translucent()));

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTAL_CLUSTER_BLOCK.get(), RenderType.translucent());
        for (RegistryEntry<FullCrystalBlock> block : ModBlocks.FULL_CRYSTAL_BLOCKS.values())
        {
            ItemBlockRenderTypes.setRenderLayer(block.get(), RenderType.translucent());
        }

        ItemBlockRenderTypes.setRenderLayer(ModFluids.MOLTEN_CRYSTAL_BUILDER.get(), RenderType.translucent());
        //ItemBlockRenderTypes.setRenderLayer(ModFluids.MOLTEN_CRYSTAL_FLOW.get(), RenderType.translucent());
    }


    // TODO: 30/03/2021 Temporary solution to ModRegistry group initialization (the other one always adds age 7 crystals)
    //NonNullList<ItemStack> creativeStacks = ModRegistry.Items.CRYSTAL_CLUSTERS
    //        .stream()
    //        .map(obj -> new ItemStack(obj.get()))
    //        .collect(Collectors.toCollection(NonNullList::create));
    //CrystalGlass.CREATIVE_TAB.fill(creativeStacks);
}

