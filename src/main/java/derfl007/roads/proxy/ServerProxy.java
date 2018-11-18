package derfl007.roads.proxy;

import derfl007.roads.world.WorldGenOre;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ServerProxy implements CommonProxy {
    @Override
    public void preInit() {

    }

    @Override
    public void init() {
        GameRegistry.registerWorldGenerator(new WorldGenOre(), 0);
    }

    @Override
    public void postInit() {

    }
}
