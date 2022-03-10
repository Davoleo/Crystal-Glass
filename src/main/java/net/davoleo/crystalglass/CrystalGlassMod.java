package net.davoleo.crystalglass;

import com.tterrag.registrate.Registrate;
import net.davoleo.crystalglass.init.ClientSetup;
import net.davoleo.crystalglass.init.ModItems;
import net.davoleo.crystalglass.init.ModRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod(CrystalGlassMod.MODID)
public class CrystalGlassMod {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "crystalglass";
    public static final String MODNAME = "Crystal Glass";

    public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab(MODID) {
        @Nonnull
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModItems.CRYSTAL_CLUSTERS.get(3).get());
        }
    };

    public static final Registrate REGISTRATE = Registrate.create(MODID);

    public CrystalGlassMod()
    {
        //Called on both sides during mod setup
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        //Called on the client side during mod setup
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);

        //Initialize Mod Registries
        ModRegistry.init();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Crystal Glass Setup Method...");
        LOGGER.info("MMMH, MONKEY");
    }
}
