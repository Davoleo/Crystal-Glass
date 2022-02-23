package net.davoleo.crystalglass.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.davoleo.crystalglass.CrystalGlass;
import net.davoleo.crystalglass.block.CrystalClusterBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

import static net.davoleo.crystalglass.CrystalGlass.REGISTRATE;

public class ModItems {

    private static final Item.Properties DEFAULT_ITEM_PROPERTIES = new Item.Properties().tab(CrystalGlass.CREATIVE_TAB);

    public static final List<RegistryEntry<BlockItem>> CRYSTAL_CLUSTERS = new ArrayList<>(CrystalClusterBlock.AGE.getPossibleValues().size());
    static
    {
        CrystalClusterBlock.AGE.getPossibleValues().forEach(age ->
                CRYSTAL_CLUSTERS.add(REGISTRATE
                        .item("crystal_cluster_age_" + age, (props) -> new BlockItem(ModBlocks.CRYSTAL_CLUSTER_BLOCK.get(), DEFAULT_ITEM_PROPERTIES))
                        .defaultModel()
                        .register()
                )
        );
    }

    /*public static final Map<DyeColor, RegistryObject<Item>> CRYSTALS_BLOCK_ITEMS = new HashMap<>();
    static
    {
        for (CrystalBlock.Size size : CrystalBlock.Size.values())
            CRYSTALS.add(registerFromBlock(ModBlocks.CRYSTAL_BLOCKS.get(size.ordinal())));
        for (DyeColor color : ModBlocks.FULL_CRYSTAL_BLOCKS.keySet())
        {
            RegistryObject<Item> CRYSTAL_ITEMBLOCK = registerFromBlock(ModBlocks.FULL_CRYSTAL_BLOCKS.get(color));
            CRYSTALS_BLOCK_ITEMS.put(color, CRYSTAL_ITEMBLOCK);
        }

    }*/

}