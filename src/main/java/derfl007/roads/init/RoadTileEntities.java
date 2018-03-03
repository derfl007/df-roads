package derfl007.roads.init;

import derfl007.roads.common.tileentities.TileEntityRoadLantern;
import derfl007.roads.common.tileentities.TileEntityRoadTownSign;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RoadTileEntities {
    public static void register() {
        GameRegistry.registerTileEntity(TileEntityRoadTownSign.class, "road_town_sign");
        GameRegistry.registerTileEntity(TileEntityRoadLantern.class, "road_lantern");
        System.out.println("tile entitiies registered!");
    }
}