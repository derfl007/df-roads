package derfl007.roads.init;

import derfl007.roads.Reference;
import derfl007.roads.common.tileentities.TileEntityRoadLantern;
import derfl007.roads.common.tileentities.TileEntityRoadTownSign;
import derfl007.roads.common.tileentities.TileEntitySignPrinter;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RoadTileEntities {
    public static void register() {
        GameRegistry.registerTileEntity(TileEntityRoadTownSign.class, Reference.MOD_ID + ":road_town_sign");
        GameRegistry.registerTileEntity(TileEntityRoadLantern.class, Reference.MOD_ID + ":road_lantern");
        GameRegistry.registerTileEntity(TileEntitySignPrinter.class, Reference.MOD_ID + ":sign_printer");
    }
}