package derfl007.roads;

import derfl007.roads.gui.GuiHandler;
import derfl007.roads.init.RoadCrafting;
import derfl007.roads.init.RoadTileEntities;
import derfl007.roads.network.PacketHandler;
import derfl007.roads.proxy.CommonProxy;
import derfl007.roads.world.WorldGenOre;
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
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;

@Mod(modid = "df-roads", useMetadata = true, updateJSON = "https://raw.githubusercontent.com/derfl007/df-roads/master/update.json")
public class Roads {

    @Instance(Reference.MOD_ID)
    public static Roads instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static final CreativeTabs ROADS_TAB = new RoadsTab();
    public static final CreativeTabs YELLOW_ROADS_TAB = new YellowRoadsTab();
    public static final CreativeTabs SIGNS_TAB = new SignsTab();

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
        GameRegistry.registerWorldGenerator(new WorldGenOre(), 0);
        RoadCrafting.register();

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        System.out.println("Post Init");
        proxy.postInit();
    }
}