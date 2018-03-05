package derfl007.roads.init;

import derfl007.roads.Reference;
import derfl007.roads.common.items.ItemTar;
import derfl007.roads.common.items.ItemWrench;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.LinkedList;
import java.util.List;

public class RoadItems {

    public static Item wrench;
    public static Item tar;

    public static void init() {
        wrench = new ItemWrench();
        tar = new ItemTar();
    }

    public static void register()
    {
        registerItem(wrench);
        registerItem(tar);
    }

    public static void registerItem(Item item)
    {
        RegistrationHandler.ITEMS.add(item);
    }

    public static void registerModels() {
        registerModel(wrench);
        registerModel(tar);
    }

    public static void registerModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @Mod.EventBusSubscriber()
    public static class RegistrationHandler
    {
        public static final List<Item> ITEMS = new LinkedList<>();

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event)
        {
            RoadItems.init();
            RoadItems.register();
            ITEMS.stream().forEach(item -> event.getRegistry().register(item));
        }
    }
}
