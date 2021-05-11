package derfl007.roads;

import java.io.File;

import org.apache.logging.log4j.Logger;

import derfl007.roads.common.commands.CommandTrafficLights;
import derfl007.roads.common.commands.group.CommandTrafficLightsGroup;
import derfl007.roads.common.commands.group.CommandTrafficLightsGroupAdd;
import derfl007.roads.common.commands.group.CommandTrafficLightsGroupFinish;
import derfl007.roads.common.commands.group.CommandTrafficLightsGroupList;
import derfl007.roads.common.commands.group.CommandTrafficLightsGroupMove;
import derfl007.roads.common.commands.group.CommandTrafficLightsGroupRemove;
import derfl007.roads.common.commands.group.CommandTrafficLightsGroupReset;
import derfl007.roads.common.commands.group.CommandTrafficLightsGroupSelect;
import derfl007.roads.common.commands.light.CommandTrafficLightsLight;
import derfl007.roads.common.commands.light.CommandTrafficLightsLightAdd;
import derfl007.roads.common.commands.light.CommandTrafficLightsLightRemove;
import derfl007.roads.common.commands.set.CommandTrafficLightsSet;
import derfl007.roads.common.commands.set.CommandTrafficLightsSetCancel;
import derfl007.roads.common.commands.set.CommandTrafficLightsSetCheck;
import derfl007.roads.common.commands.set.CommandTrafficLightsSetCreate;
import derfl007.roads.common.commands.set.CommandTrafficLightsSetEdit;
import derfl007.roads.common.commands.set.CommandTrafficLightsSetList;
import derfl007.roads.common.commands.set.CommandTrafficLightsSetRemove;
import derfl007.roads.common.commands.set.CommandTrafficLightsSetRename;
import derfl007.roads.common.commands.set.CommandTrafficLightsSetReplace;
import derfl007.roads.common.commands.set.CommandTrafficLightsSetSave;
import derfl007.roads.gui.GuiHandler;
import derfl007.roads.init.RoadCrafting;
import derfl007.roads.init.RoadTileEntities;
import derfl007.roads.network.PacketHandler;
import derfl007.roads.proxy.CommonProxy;
import derfl007.roads.trafficlights.ServerTickHandler;
import derfl007.roads.world.WorldGenOre;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

@Mod(modid = "df-roads", useMetadata = true, updateJSON = "https://raw.githubusercontent.com/E-Mans-Application/df-roads/master/update.json")
public class Roads {

	@Instance(Reference.MOD_ID)
	public static Roads instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static final CreativeTabs ROADS_TAB = new RoadsTab();
	public static final CreativeTabs YELLOW_ROADS_TAB = new YellowRoadsTab();
	public static final CreativeTabs SIGNS_TAB = new SignsTab();

	public static Logger logger;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println("Pre Init");
		MinecraftForge.EVENT_BUS.register(proxy);
		PacketHandler.init();
		RoadTileEntities.register();

		proxy.preInit();
		logger = event.getModLog();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println("Init");

		Configuration config = new Configuration(new File("config/df-roads.cfg"));
		config.load();

		boolean generateAsphalt = config.get("general", "generate_asphalt", true).getBoolean();
		config.save();

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		proxy.init();
		if (generateAsphalt)
			GameRegistry.registerWorldGenerator(new WorldGenOre(), 0);

		RoadCrafting.register();
		MinecraftForge.EVENT_BUS.register(new ServerTickHandler());
		PermissionAPI.registerNode("dfroads.command.trafficlights", DefaultPermissionLevel.ALL,
				"Allows players to use the traffic lights command");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		System.out.println("Post Init");
		proxy.postInit();
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {

		CommandTrafficLights baseCommand = new CommandTrafficLights();
		CommandTrafficLightsSet baseSetCommand = new CommandTrafficLightsSet();
		CommandTrafficLightsGroup baseGroupCommand = new CommandTrafficLightsGroup();
		CommandTrafficLightsLight baseLightCommand = new CommandTrafficLightsLight();

		baseSetCommand.addSubcommand(new CommandTrafficLightsSetCancel());
		baseSetCommand.addSubcommand(new CommandTrafficLightsSetCreate());
		baseSetCommand.addSubcommand(new CommandTrafficLightsSetSave());
		baseSetCommand.addSubcommand(new CommandTrafficLightsSetRemove());
		baseSetCommand.addSubcommand(new CommandTrafficLightsSetList());
		baseSetCommand.addSubcommand(new CommandTrafficLightsSetCheck());
		baseSetCommand.addSubcommand(new CommandTrafficLightsSetRename());
		baseSetCommand.addSubcommand(new CommandTrafficLightsSetReplace());
		baseSetCommand.addSubcommand(new CommandTrafficLightsSetEdit());
		// baseSetCommand.addSubcommand(new CommandTrafficLightsHelp(baseSetCommand));

		baseGroupCommand.addSubcommand(new CommandTrafficLightsGroupFinish());
		baseGroupCommand.addSubcommand(new CommandTrafficLightsGroupReset());
		baseGroupCommand.addSubcommand(new CommandTrafficLightsGroupList());
		baseGroupCommand.addSubcommand(new CommandTrafficLightsGroupRemove());
		baseGroupCommand.addSubcommand(new CommandTrafficLightsGroupSelect());
		baseGroupCommand.addSubcommand(new CommandTrafficLightsGroupAdd());
		baseGroupCommand.addSubcommand(new CommandTrafficLightsGroupMove());

		baseLightCommand.addSubcommand(new CommandTrafficLightsLightAdd());
		baseLightCommand.addSubcommand(new CommandTrafficLightsLightRemove());
		// baseGroupCommand.addSubcommand(new
		// CommandTrafficLightsHelp(baseGroupCommand));

		baseCommand.addSubcommand(baseGroupCommand);
		baseCommand.addSubcommand(baseSetCommand);
		baseCommand.addSubcommand(baseLightCommand);
		// baseCommand.addSubcommand(new CommandTrafficLightsHelp(baseCommand));

		event.registerServerCommand(baseCommand);
	}

}