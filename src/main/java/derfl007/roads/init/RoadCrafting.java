package derfl007.roads.init;

import derfl007.roads.Reference;
import derfl007.roads.common.blocks.BlockRoadRotatable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.LinkedList;
import java.util.List;

public class RoadCrafting {

    public static void register() {
        GameRegistry.addSmelting(RoadBlocks.asphalt, new ItemStack(RoadItems.tar, 9), 0.05F);
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class RegistrationHandler
    {
        public static final List<IRecipe> RECIPES = new LinkedList<>();

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<IRecipe> event)
        {
            RECIPES.stream().forEach(recipe -> event.getRegistry().register(recipe));
        }
    }
}
