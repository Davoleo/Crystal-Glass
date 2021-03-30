package net.davoleo.crystalglass;

import net.davoleo.crystalglass.init.ClientSetup;
import net.davoleo.crystalglass.init.ModRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CrystalGlass.MODID)
public class CrystalGlass {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "crystalglass";
    public static final String MODNAME = "Crystal Glass";

    public static final ItemGroup CREATIVE_TAB = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModRegistry.CRYSTAL_CLUSTER_BLOCK.get());
        }
    };

    public CrystalGlass()
    {
        //Called on both sides during mod setup
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        //Called on the client side during mod setup
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);

        //Initializes the Deferred Registry
        ModRegistry.init();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }
}
