package derfl007.roads.init;

import java.util.LinkedList;
import java.util.List;

import derfl007.roads.common.items.ItemColorCartridge;
import derfl007.roads.common.items.ItemSignTemplate;
import derfl007.roads.common.items.ItemTar;
import derfl007.roads.common.items.ItemTrafficRemote;
import derfl007.roads.common.items.ItemWrench;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RoadItems {

	public static Item wrench;
	public static Item trafficRemote;
	public static Item tar;
	public static Item round_sign_template;
	public static Item triangle_sign_template;
	public static Item square_sign_template;
	public static Item magenta_ink_cartridge;
	public static Item yellow_ink_cartridge;
	public static Item cyan_ink_cartridge;

	public static void init() {
		wrench = new ItemWrench();
		trafficRemote = new ItemTrafficRemote();
		tar = new ItemTar();
		round_sign_template = new ItemSignTemplate("round_sign_template");
		triangle_sign_template = new ItemSignTemplate("triangle_sign_template");
		square_sign_template = new ItemSignTemplate("square_sign_template");
		magenta_ink_cartridge = new ItemColorCartridge("magenta_ink_cartridge");
		yellow_ink_cartridge = new ItemColorCartridge("yellow_ink_cartridge");
		cyan_ink_cartridge = new ItemColorCartridge("cyan_ink_cartridge");
	}

	public static void register() {
		registerItem(wrench);
		registerItem(trafficRemote);
		registerItem(tar);
		registerItem(round_sign_template);
		registerItem(square_sign_template);
		registerItem(triangle_sign_template);
		registerItem(magenta_ink_cartridge);
		registerItem(yellow_ink_cartridge);
		registerItem(cyan_ink_cartridge);
	}

	public static void registerItem(Item item) {
		RegistrationHandler.ITEMS.add(item);
	}

	public static void registerModels() {
		registerModel(wrench);
		registerModel(trafficRemote);
		registerModel(tar);
		registerModel(round_sign_template);
		registerModel(square_sign_template);
		registerModel(triangle_sign_template);
		registerModel(magenta_ink_cartridge);
		registerModel(yellow_ink_cartridge);
		registerModel(cyan_ink_cartridge);
	}

	public static void registerModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0,
				new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	@Mod.EventBusSubscriber()
	public static class RegistrationHandler {
		public static final List<Item> ITEMS = new LinkedList<>();

		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			RoadItems.init();
			RoadItems.register();
			ITEMS.stream().forEach(item -> event.getRegistry().register(item));
		}
	}
}
