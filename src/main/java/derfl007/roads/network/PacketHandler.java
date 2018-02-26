package derfl007.roads.network;

import derfl007.roads.Reference;
import derfl007.roads.network.message.MessageRoadTownSign;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

    public static void init() {
        INSTANCE.registerMessage(MessageRoadTownSign.class, MessageRoadTownSign.class, 18, Side.SERVER);
    }
}