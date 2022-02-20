package net.davoleo.crystalglass;

import net.davoleo.crystalglass.init.ClientSetup;
import net.davoleo.crystalglass.init.ModBlocks;
import net.davoleo.crystalglass.init.ModItems;
import net.davoleo.crystalglass.init.ModSounds;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod(CrystalGlass.MODID)
public class CrystalGlass {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "crystalglass";
    public static final String MODNAME = "Crystal Glass";

    public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab(MODID) {
        @Nonnull
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModBlocks.CRYSTAL_CLUSTER_BLOCK.get());
        }
    };

    public CrystalGlass()
    {
        //Called on both sides during mod setup
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        //Called on the client side during mod setup
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);

        //Initializes the Blocks Registry
        ModBlocks.init();
        //Initializes the Items Registry
        ModItems.init();
        //Initializes the Sounds Registry
        ModSounds.init();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Crystal Glass Setup Method...");
        LOGGER.info("MMMH, MONKEY");
    }
}
