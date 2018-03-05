package derfl007.roads;

import derfl007.roads.gui.GuiHandler;
import derfl007.roads.init.RoadTileEntities;
import derfl007.roads.network.PacketHandler;
import derfl007.roads.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;

@Mod(modid = "df-roads", useMetadata = true)
public class Roads {

    @Instance(Reference.MOD_ID)
    public static Roads instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static final CreativeTabs ROADS_TAB = new RoadsTab();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("Pre Init");
        MinecraftForge.EVENT_BUS.register(proxy);
        PacketHandler.init();
        RoadTileEntities.register();
        proxy.preInit();

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        System.out.println("Init");
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        proxy.init();

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        System.out.println("Post Init");
        proxy.postInit();
    }
}