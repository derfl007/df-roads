package derfl007.roads.init;

import derfl007.roads.common.tileentities.TileEntityRoadTownSign;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RoadTileEntities {
    public static void register() {
        GameRegistry.registerTileEntity(TileEntityRoadTownSign.class, "road_town_sign");
        System.out.println("tile entitiy registered!");
    }
}