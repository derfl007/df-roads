package derfl007.roads.network;

import derfl007.roads.Reference;
import derfl007.roads.network.message.MessageRoadTownSign;
import derfl007.roads.network.message.MessageSignPrinterClosed;
import derfl007.roads.network.message.MessageSignPrinterPrint;
import derfl007.roads.network.message.MessageSignPrinterSelect;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

    public static void init() {
        INSTANCE.registerMessage(MessageRoadTownSign.class, MessageRoadTownSign.class, 0, Side.SERVER);
        INSTANCE.registerMessage(MessageSignPrinterPrint.class, MessageSignPrinterPrint.class, 1, Side.SERVER);
        INSTANCE.registerMessage(MessageSignPrinterClosed.class, MessageSignPrinterClosed.class, 2, Side.SERVER);
        INSTANCE.registerMessage(MessageSignPrinterSelect.class, MessageSignPrinterSelect.class, 3, Side.SERVER);
    }
}