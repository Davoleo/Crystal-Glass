package net.davoleo.crystalglass;

import com.tterrag.registrate.Registrate;
import net.davoleo.crystalglass.init.ClientSetup;
import net.davoleo.crystalglass.init.CommonSetup;
import net.davoleo.crystalglass.init.ModRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CrystalGlassMod.MODID)
public class CrystalGlassMod {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "crystalglass";
    public static final String MODNAME = "Crystal Glass";

    public static final Registrate REGISTRATE = Registrate.create(MODID);

    public CrystalGlassMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        //Called on both sides during mod setup
        modBus.addListener(CommonSetup::setup);
        //Called on the client side during mod setup
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modBus.addListener(ClientSetup::init));

        //Initialize Mod Registries
        ModRegistry.init();
    }
}
